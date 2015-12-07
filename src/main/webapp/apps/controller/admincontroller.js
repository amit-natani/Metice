metice.controller('adminController', ['$scope','$http','$window','$timeout', function($scope, $http, $window, $timeout) {

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
	
	
	$http.get("./roles").success(function(roles) {
		$scope.roles = roles;
	});
	
	$http.get('./getAllValidUsersByCompany')
	.success( function(users, status) {
		if(status == 401) {
			$window.location.href = "./noaccess.html";
		} else if(status == 204) {
			console.log("No User Found");
		} else if(status == 200) {
			$scope.users = users;
		}	
	})
	.error( function() {
		
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
	
	$http.get('./getAllArchivedNoticesByCompany')
	.success( function(notices, status) {
		if(status == 204) {
			console.log("No User Found");
		} else if(status == 200) {
			$scope.archivedNotices = notices;
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
	
	$scope.unarchiveNotice = function(notice) {
		$http.post('./unArchiveNotice', notice.noticeId)
		.success( function(notices, status) {
			if(status == 204) {
				console.log("No User Found");
			} else if(status == 200) {
				$scope.archivedNotices = notices;
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
	}
	$http.get('./getAllInvalidUserRequests')
	.success( function(requests, status) {
		if(status == 204) {
			console.log("No User Found");
		}  else if(status == 200) {
			$scope.requests = requests;
			userRequests = requests;
		}	
	})
	.error( function(status) {
		if(status == 401) {
			$window.location.href = "./noaccess.html";
		} else if (status == 403) {
			$window.location.href = "./index.html";
		}
	});
	
	 
	  $scope.deleteUser = function(userInformation) {
		  $scope.isUserSuccess = true;
		  $scope.userAlert = true;
		  $scope.userAlertCode = "Success";
		  $http.post("./deleteUser", userInformation.email)
		  	.success(function(data) {
		  		 $scope.isUserSuccess = true;
	        	  $scope.userAlertCode = "Success";
	        	  $scope.userAlertMessage = "User had been deleted successfully";
	        	  $timeout(function() { $scope.userAlert = false;}, 3000);
	        	  var index = $scope.requests.indexOf($scope.userRequestDetails);
	        	  $scope.requests.splice(index,1);
	        	  $scope.userRequestDetails = null;
		  	})
		  	.error(function() {
				  $scope.isUserSuccess = false;
				  $scope.userAlertCode = "Error";
	        	  $scope.userAlertMessage = "While deleting user";
	        	  $timeout(function() { $scope.userAlert = false;}, 3000);
		  });
	  }
	  
	
	$scope.acceptUser = function(userDetail) {
		$scope.isUserSuccess = true;
		$scope.userAlert = true;
		$scope.userAlertCode = "Success";
		
		$http.post("./updateUserByAdmin",userDetail).success(function(data) {
        	  $scope.isUserSuccess = true;
        	  $scope.userAlertCode = "Success";
        	  $scope.userAlertMessage = "Your data has been successfully updated";
        	  $timeout(function() { $scope.userAlert = false;}, 5000);
        	  var index = $scope.requests.indexOf(userDetail);
        	  if(index != -1) {
        		  $scope.requests.splice(index, 1);
        	  }
        	  if($scope.userRequestDetails == userDetail) {
        		  $scope.userRequestDetails = null;
        	  } else {
        		  $scope.userDetail = null;
        	  }
        	  
		}).error(function() {
				  $scope.isUserSuccess = false;
				  $scope.userAlertCode = "Error";
	        	  $scope.userAlertMessage = "An Error occured while updating data";
	        	  $timeout(function() { $scope.userAlert = false;}, 5000);
			  }
	  )};
	  
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
		
	$scope.createRole = function() {
		$http.post("./createRole",$scope.role)
			.success( function() {
				  $scope.roleAlert = true;
	        	  $scope.isRoleSuccess = true;
	        	  $scope.roleAlertCode = "Success";
	        	  $scope.roleAlertMessage = "Role has been created Successfully";
	        	  $scope.role = null;
			})
			.error( function(response, status) {
				  $scope.roleAlert = true;
				  $scope.isRoleSuccess = false;
				  $scope.roleAlertCode = "Error";
				  if(status == 409) {
					  $scope.roleAlertMessage = "Role Already Exists.";
				  } else {
					  $scope.roleAlertMessage = "There is any internal error";
				  }
			});
			
	}
	
	$scope.createNotice = function(notice) {
		$http.post("./saveNotice", notice)
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