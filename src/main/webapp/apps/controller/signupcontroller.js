/**
 * Author : Girdhari Agrawal
 * 
 * Date: 2/December/2015
 * 
 * Description: Script contains controller code for Sign Up page
 */

metice.controller("SignupController", [ '$scope', '$http','$window','UserService','CompanyService','RoleService',function($scope,$http, $window, UserService, CompanyService, RoleService) {
			/* Get Google User */
	UserService.getGoogleUser()
	.success(function(user) {
				$scope.user = user;
			}).error(function() {
				$scope.user = "Not Available";
			});

			/* Get All Companies */
	CompanyService.getAllCompanies()
	.success(function(listOfCompanies) {
				$scope.companies = listOfCompanies;
			}).error(function() {
				$scope.companies = [ {
					name : ""
				}];
			});

		/* Get All Roles */
	RoleService.getRoles()
	.success(function(listOfRoles) {
				$scope.roles = listOfRoles;
			}).error(function() {
				$scope.roles = [{name : ""}];
	});

	/* Save Sign Up page form data */
	$scope.sendForm = function(user) {
		UserService.saveUser(user)
		.success(function(data, status) {
				if(status == 201) {
					$window.location.href = "./adminconsole.html";
				} else {
					$window.location.href = "./wait.html"
				}
		}).error(function(status) {
			if(status == 400) {
				alert("Entered data is not in proper format")
			}
		});
	};

	}]);