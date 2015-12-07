/**
 * Author : Girdhari Agrawal
 * 
 * Date: 2/December/2015
 * 
 * Description: Script contains angularjs services for user details
 */

metice.factory('userService',['$http',function($http) {
	var factory = {};

	factory.getCurrentUser = function() {
		return $http.get("./getCurrentUser");
	}

	factory.updateUser = function(user) {
		return $http.post("./updateUser",user);
	}

	factory.logout = function() {
		return $http.get("./logout");
	}

	factory.getGoogleUser = function() {
		return $http.get("./getGoogleUserInfo");
	}

	factory.saveUser = function(user) {
		return $http.post("./saveUser",user);
	}
	return factory;
}]);