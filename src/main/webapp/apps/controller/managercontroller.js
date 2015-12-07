metice.controller('managerController', ['$scope','$http','$window','$timeout', function($scope, $http, $window, $timeout) {

	$scope.date = new Date();
	
	$scope.setNoticeDetails = function(notice) {
		$scope.detail = notice;
	}
	
	$scope.setUserDetails = function(index) {
		$scope.userDetail = $scope.users[index];
	}
	
	$scope.setRequestDetails = function(index) {
		$scope.userRequestDetails = $scope.requests[index];
	}
	
	$scope.requestAlert = false;
	$scope.userAlert = false;
	
	
	$http.get("./getCurrentUser").success( function(user) {
		$scope.user = user;
	});
	
		
	$http.get('./getAllNoticesByCompany')
	.success( function(notices, status) {
		if(status == 204) {
			console.log("No User Found");
		} else if(status == 200) {
			$scope.notices = notices;
			noticeList = $scope.notices;
		}	
	})
	.error( function() {
		if(status == 401) {
			$window.location.href = "./noaccess.html";
		} else if (status == 403) {
			$window.location.href = "./index.html";
		}
	});
	
		  
	  $scope.logout = function() {
		  $http.get("./logout")
	          .success(function(data) {
	        	  alert("Logout Successfully");
	        	  $window.location.href = "./index.html";
		  }).error(
				  function() {
					  alert("Buddhu se");
				  }
		  ) ;
		};
		
	$scope.createNotice = function() {
		$http.post("./saveNotice", $scope.notice)
			.success(function() {
				$scope.postAlert = true;
	        	  $scope.isPostSuccess = true;
	        	  $scope.postAlertCode = "Success";
	        	  $scope.postAlertMessage = "Notice had been posted Successfully";
	        	  $scope.notice = null;
	        	  $timeout(function() { $scope.postAlert = false; },5000);
			})
			.error(function() {
				  $scope.postAlert = true;
				  $scope.isPostSuccess = false;
				  $scope.postAlertCode = "Error";
	        	  $scope.postAlertMessage = "Error while posting notice";
	        	  $timeout(function() { $scope.postAlert = false; },5000);
			})
	}
}]);