const app = angular.module('myApp', ['ngRoute']);
app.config(function($routeProvider, $locationProvider) {
	$locationProvider.hashPrefix('');
	$routeProvider.when("/home", {
		templateUrl: '/smart-book/home',
		controller: 'MyController'
	}).when("/cart", {
		templateUrl: '/smart-book/cart',
		controller: 'CartController'
	})
	.when("/history", {
		templateUrl: '/smart-book/history',
		controller: 'HistoryController'
	})
	.when("/book/:id", {
		templateUrl: '/smart-book/book',
		controller: 'BookController'
	})
	.when("/login", {
		templateUrl: '/smart-book/login',
		controller: 'BookController'
	})
	.otherwise({redirectTo: '/home'})
});

app.controller("MyController", MyController);
app.controller("CartController", CartController);
app.controller("HistoryController", HistoryController);
app.controller("BookController", BookController);
app.controller("readyCtrl",function($rootScope,$http){
	$http.get("/api/user").then(function(response) {
		if(response.data==""){
			$rootScope.authen = null;
		}else{
			$rootScope.authen = response.data;
		}
	})
	$rootScope.logout = function() {
		window.location.href = "/logout";
		$rootScope.authen = null;
	}
})
