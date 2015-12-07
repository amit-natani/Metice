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

metice.factory('noticeService', ['$http', function($http) {
	var factory = {};
	
	factory.getAllNotices = function() {
		return $http.get("http://localhost:8080/Metice/getAllNoticesByCompany");
	}
	return factory;
}])