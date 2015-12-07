/**
 * Author :
 * Girdhari Agrawal
 * 
 * Date:
 * 2/December/2015
 * 
 * Description : 
 * Script contains all the role related services
 */

metice.factory('roleService', ['$http', function($http) {
		
		var factory = {};
		
		factory.getRoles = function() {
			return $http.get("http://localhost:8080/Metice/roles");
		}
		return factory;
}])