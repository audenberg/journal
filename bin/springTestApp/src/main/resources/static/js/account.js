var app = angular.module('App', []);

app.controller('AccountCtrl', ['$scope', '$http', function ($scope, $http) {

    // List of Account
    $scope.accs = [];

    // Account Object
    $scope.acc = {};
    
    // Account Details
    $scope.acc_details = {};
    
    //For Cancel Update
     let copiedItem = null;

    // Errors Array
    $scope.errors = [];

    // read records
    $scope.getAccounts = function () {
        $http.get('rest/account/getaccounts.php', {})
            .then(function success(e) {
                $scope.accs = e.data.records;
                setTimeout(function() {
                $('#maxRows').val(10).change();
               },500);
            }, function error(e) {

            });
    };
    $scope.getAccounts();
    flag = true;
    
   $scope.validate = function (x,xErr,value,data,msg){

    /********validate all our form fields***********/
    if(data == null || value == null){ 
        $('#'+x).css('border-color','red'); 
        flag = false;
		
		$('#'+xErr).hide().html(msg).slideDown();  
		return false;
    }else{
        $('#'+xErr).hide();
    }
  
    }
    
    $scope.validateAll = function(data){
        $scope.validate('name','nameErr',data.name,data,'Please Enter name');
        $scope.validate('type','typeErr',data.type,data,'Please Enter the account type');
    }
    
    // Add new Account
    $scope.addAccount = function () {
        var data = $scope.acc;
        flag=true;
        $scope.validateAll(data);
        
    /********Validation end here ****/
    /* If all are ok then we send ajax request to email_send.php *******/
    if(flag) 
    {
        
        $http.post('rest/account/createaccount.php', {
            account: $scope.acc
        })
            .then(function(response) {
        //First function handles success
        console.log("reached response:"+response.data);
        if(response.data.type=="success"){
                
                console.log(response.data);
                $scope.errors = [];
                obalance = $scope.acc.obalance;
                $scope.acc = response.data.account; //get the acc id from server
                $scope.acc.obalance = obalance; // update the balance
                $scope.accs.push( $scope.acc);
                $('#accForm').find("input[type=text], textarea").val("");
                var modal_element = $('#add_new_account_modal');
                modal_element.modal('hide');
                $.notify({message: 'Account has been Created'},{type: 'success'});
                
      
         }else{
                 $scope.errors.push(response.data.text);
            }
            });
        }
    };
    copiedindex = null;
    // open edit Account details popup
    $scope.edit = function (index) {
        copiedindex = index;
//        $scope.acc_details = $scope.accs[index];
        copiedItem = Object.assign({}, copiedItem , $scope.accs[index] );
        $scope.acc_details = copiedItem;
        console.log($scope.accs[index]);
        var modal_element = $('#modal_update_account');
        modal_element.modal('show');
    };

    // update the Account
    $scope.updateAccount = function () {
        var data = $scope.acc_details;
        flag=true;
        $scope.validateAll(data);
        
    /********Validation end here ****/
    /* If all are ok then we send ajax request to email_send.php *******/
    if(flag) 
    {
        
        $http.post('rest/account/updateaccount.php', {
            account: data
        })
            .then(function(response) {
        //First function handles success
        console.log("reached response:"+response);
        if(response.data.type=="error"){

            $scope.errors.push(response.data.text);
         }else{
                console.log(response.data);
                $scope.errors = [];

                $scope.accs[copiedindex] = $scope.acc_details;
                var modal_element = $('#modal_update_account');
                modal_element.modal('hide');
                $.notify({message: 'Account has been Updated'},{type: 'success'}); 
            }
            });
        }
    };
   
    // delete the Account
    $scope.delete = function (index) {

        var conf = confirm("Do you really want to delete the Account?");

        if (conf == true) {
            $http.post('rest/account/deleteaccount.php', {
                account: $scope.accs[index]
            }).then(function(response) {
        //First function handles success
        if(response.data.type=="error"){

            $.notify({message: response.data.text},{type: 'danger'});
         }else{
                
                $scope.errors = [];

                $scope.errors = [];
                $scope.accs.splice(index, 1);
                
                $.notify({message: response.data.text},{type: 'success'}); 
            }
            });
                

                    

                
        }
    };
}]);/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


