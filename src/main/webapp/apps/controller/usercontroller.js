/**
 * Author : Girdhari Agrawal
 * Date : 2 December 2015
 * Description : User Controller contains all the user related operation controller
 */


metice.controller("userController",['$scope','$http', '$window','UserService','NoticeService', function($scope, $http, $window, UserService, NoticeService) {

	UserService.getCurrentUser()
	.success(function(user, status) {
		$scope.user = user;
	});
	UserService.getAllNotices()
	.success(function(notices, status) {
		if(status == 200) {
			$scope.notices = notices;
		} else if(status == 204) {
			$scope.notices = null;
		}
	});

	$scope.updateuser = function(user) {
		$scope.isSuccess = true;
		UserService.updateUser($scope.user)
			.success(function(data, status) {
				if(status == 200) {  
					$scope.alert = true;
					$scope.isSuccess = true;
					$scope.alertCode = "Success";
					$scope.alertMessage = "Your data has been successfully updated";
				}
			}).error(function(status) {
					$scope.alert = true;
					$scope.isSuccess = false;
					$scope.alertCode = "Error";
					$scope.alertMessage = "An Error occured while updating data";
			});
	}
	
	UserService.getTodayBirthdays()
	.success(function(events) {
				$scope.events = events;
	});
	
	$scope.logout = function() {
			User.logout()
			.success(function(data, status) {
				$window.location.href = "./index.html";
			})};
	}]);