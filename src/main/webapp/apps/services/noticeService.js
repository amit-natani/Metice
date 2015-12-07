/**
 * Author :
 * Girdhari Agrawal
 * 
 * Date:
 * 2/December/2015
 * 
 * Description: 
 * Script contains notice service methods 
 */

metice.factory('NoticeService', ['$http', function($http) {
	var factory = {};
	
	factory.getAllNotices = function() {
		return $http.get("./getAllNoticesByCompany");
	}
	
	factory.getAllArchivedNotices = function() {
		return $http.get('./getAllArchivedNoticesByCompany');
	}
	
	factory.unarchiveNotice = function(noticeId) {
		return $http.post('./unArchiveNotice', noticeId);
	}
	factory.archiveNotice = function(noticeId) {
		return $http.post("./archiveNotice", noticeId);
	}
	
	factory.deleteNotice = function(noticeId) {
		return $http.post("./deleteNotice", noticeId);
	}
	factory.saveNotice = function(notice) {
		return $http.post("./saveNotice", notice);
	}
	return factory;
}])