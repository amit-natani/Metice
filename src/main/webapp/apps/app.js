var metice = angular.module("metice", ['ngRoute','textAngular']);


metice.config(['$routeProvider', function($routeProvider) {
	$routeProvider
		.when('/loadUserRequest', {
			templateUrl : "userRequests.html",
		})
		.when("/viewNotices", {
			templateUrl : "viewNotices.html",
			controller  : "checkPermission"
		})
		.when("/viewArchiveNotices", {
			templateUrl : "showArchiveNotices.html",
			controller  : "checkPermission"
		})
		.when('/createNotice', {
			templateUrl : "createnotice.html"
		})
		.when('/showUsers', {
			templateUrl : "showUsers.html"
		})
		.when( '/editNotice', {
			templateUrl  : "createnotice.html",
		})
		.when( '/deleteNotice', {
			templateUrl : "createnotice.html",
		})
		.when( '/archiveNotice', {
			templateUrl  : "createnotice.html",
		})
		.otherwise({redirectTo : "/viewNotices"});
}]);


