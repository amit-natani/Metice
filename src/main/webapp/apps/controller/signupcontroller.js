/**
 * Author : Girdhari Agrawal
 * 
 * Date: 2/December/2015
 * 
 * Description: Script contains controller code for Sign Up page
 */

metice.controller("signupcontroller", [ '$scope', '$http','$window',function($scope,$http, $window) {
			/* Get Google User */
	$http.get("./getGoogleUserInfo").success(function(user) {
				$scope.user = user;
			}).error(function() {
				$scope.user = "Not Available";
			});

			/* Get All Companies */
	$http.get("./getAllCompanies").success(function(listOfCompanies) {
				$scope.companies = listOfCompanies;
			}).error(function() {
				$scope.companies = [ {
					name : "Couldn't Connect this time."
				} ];
			});

			/* Get All Roles */
	$http.get("./roles").success(function(listOfRoles) {
				$scope.roles = listOfRoles;
			}).error(function() {
				$scope.roles = [ {
					name : "Couldn't Connect this time."
				} ];
			});

			/* Save Sign Up page form data */
			$scope.sendForm = function() {
				$http.post("./saveUser",$scope.user).success(function(data, status) {
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

		} ]);