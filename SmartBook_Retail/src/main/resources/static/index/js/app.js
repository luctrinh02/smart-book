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
});
app.controller("MyController", MyController);
app.controller("CartController", CartController);
app.controller("HistoryController", HistoryController);

