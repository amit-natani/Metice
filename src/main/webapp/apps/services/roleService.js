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

metice.factory('RoleService', ['$http', function($http) {
		
		var factory = {};
		
		factory.getRoles = function() {
			return $http.get("http://localhost:8080/Metice/roles");
		}
		
		factory.createRole = function(role) {
			return $http.post("./createRole",role);
		}
		return factory;
}])