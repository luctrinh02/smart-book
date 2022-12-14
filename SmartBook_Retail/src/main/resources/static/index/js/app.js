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
	.when("/return", {
		templateUrl: '/smart-book/return',
		controller: 'ReturnController'
	})
	.when("/book/:id", {
		templateUrl: '/smart-book/book',
		controller: 'BookController'
	})
	.when("/login", {
		templateUrl: '/smart-book/login',
	})
	.when("/signup", {
		templateUrl: '/smart-book/registry',
		controller: 'registryController'
	})
	.when("/payment", {
		templateUrl: '/smart-book/payment',
		controller: 'PaymentController'
	})
	.when("/afterPayment/:tranSn", {
		templateUrl: '/smart-book/afterPayment',
		controller: 'AfterPaymentController'
	})
	.otherwise({redirectTo: '/home'})
});

function sleep (time) {
	return new Promise((resolve) => setTimeout(resolve, time));
}

app.controller("MyController", MyController);
app.controller("CartController", CartController);
app.controller("HistoryController", HistoryController);
app.controller("BookController", BookController);
app.controller("ReturnController", ReturnController);
app.controller("registryController", registryController);
app.controller("PaymentController", PaymentController);
app.controller("AfterPaymentController", AfterPaymentController);


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

