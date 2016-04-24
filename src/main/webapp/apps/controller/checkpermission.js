/**
 * 
 */

metice.controller("checkPermission", ['$scope','$timeout','$rootScope','UserService','NoticeService', function($scope, $timeout, $rootScope, UserService, NoticeService) {

	$scope.isEditable = false;

	UserService.getCurrentUser().success(function(user) {
		if(user.permissions == 3) {
			$scope.isEditable = true;
		} else if(user.permission == 2) {
			
		} else if(user.permissions == 1) {
			$scope.isEditable = false;
		}
	});
	$scope.archiveNotice = function(notice) {
		NoticeService.archiveNotice(notice.noticeId)
		.success( function(response) {
			  $scope.isArchiveSuccess = true;
			  $scope.archiveAlert = true;
			  $scope.archiveAlertCode = "Success";
			  $scope.archiveAlertMessage = "Notice had been archived successfully";
			  $timeout(function() { $scope.archiveAlert = false;}, 3000);
		})
		.error(function() {
			$scope.isArchiveSuccess = false;
			$scope.archiveAlert = true;
			$scope.archiveAlertCode = "Error";
			$scope.archiveAlertMessage = "While Archiving Notice";
			$timeout(function() { $scope.archiveAlert = false;}, 3000);
		});
	}
	
	$scope.setValue = function(notice) {
		$rootScope.notice = notice;
	}
	
	$scope.deleteNotice = function(notice) {
		NoticeService.deleteNotice(notice.noticeId)
		.success( function(response) {
			  $scope.isArchiveSuccess = true;
			  $scope.archiveAlert = true;
			  $scope.archiveAlertCode = "Success";
			  $scope.archiveAlertMessage = "Notice had been Deleted successfully";
			  $timeout(function() { $scope.archiveAlert = false;}, 3000);
//			  var index = $scope.notices.indexOf(notice);
//			  $scope.notices.splice(notice,index);
		})
		.error(function() {
			$scope.isArchiveSuccess = false;
			$scope.archiveAlert = true;
			$scope.archiveAlertCode = "Error";
			$scope.archiveAlertMessage = "While Deleting Notice";
			$timeout(function() { $scope.archiveAlert = false;}, 3000);
		});
	}
}]);