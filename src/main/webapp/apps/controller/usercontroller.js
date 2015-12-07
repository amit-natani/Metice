/**
 * Author : Girdhari Agrawal
 * Date : 2 December 2015
 * Description : User Controller contains all the user related operation controller
 */


metice.controller("userController",['$scope','$http', '$window', function($scope, $http, $window) {

	$http.get("./getCurrentUser").success(function(user, status) {
		$scope.user = user;
	});
	$http.get("./getAllNoticesByCompany").success(function(notices, status) {
		if(status == 200) {
			$scope.notices = notices;
		} else if(status == 204) {
			$scope.notices = null;
		}
	});

	$scope.updateuser = function() {
		$scope.isSuccess = true;
		$http.post("./updateUser",$scope.user)
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
	$http.get("./getTodayBirthdays").success(function(events) {
				$scope.events = events;
	});
	$scope.logout = function() {
			$http.get("./logout").success(function(data, status) {
				$window.location.href = "./index.html";
			})};
	}]);