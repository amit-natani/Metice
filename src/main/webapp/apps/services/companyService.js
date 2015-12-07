/**
 * Author :
 * Girdhari Agrawal
 * 
 * Date:
 * 2/December/2015
 * 
 * Description: 
 * Script contains all company related services 
 */

metice.factory('companyService', ['$http', function($http) {
	var factory = {};
	factory.getAllCompanies = function() {
		return $http.get("http://localhost:8080/Metice/getAllCompanies");
	}
	
	return factory;
}])