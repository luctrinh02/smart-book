const app = angular.module('myApp', ['ngRoute']);
app.config(function($routeProvider, $locationProvider) {
	$locationProvider.hashPrefix('');
	$routeProvider.when("/type", {
		templateUrl: '/admin/smart-book/type',
		controller: 'ctrlType'
	}).when("/publisher", {
		templateUrl: '/admin/smart-book/publisher',
		controller: 'ctrlPublisher'
	}).when("/author", {
		templateUrl: '/admin/smart-book/author',
		controller: 'ctrlAuthor'
	}).when("/charactor", {
		templateUrl: '/admin/smart-book/charactor',
		controller: 'ctrlCharactor'
	}).when("/content", {
		templateUrl: '/admin/smart-book/content',
		controller: 'ctrlContent'
	}).when("/book", {
		templateUrl: '/admin/smart-book/book',
		controller: 'ctrlBook'
	}).when("/book/create", {
		templateUrl: '/admin/smart-book/book/create',
		controller: 'ctrlCreateBook'
	})
	.when("/book/update/:id", {
		templateUrl: '/admin/smart-book/book/update',
		controller: 'ctrlUpdateBook'
	})
	.when("/login", {
		templateUrl: '/admin/smart-book/login',
		controller: 'ctrlLogin'
	})
	.when("/admin", {
		templateUrl: '/admin/smart-book/user/admin',
		controller: 'adminController'
	})
	.when("/guest", {
		templateUrl: '/admin/smart-book/user/guest',
		controller: 'guestController'
	})
	.when("/shipper", {
		templateUrl: '/admin/smart-book/user/shipper',
		controller: 'shipperController'
	})
	.when("/admin/create/:role", {
		templateUrl: '/admin/smart-book/createUser',
		controller: 'userCreateController'
	})
	.when("/admin/profile/:id", {
		templateUrl: '/admin/smart-book/profileUser',
		controller: 'userUpdateController'
	})
	.when("/home", {
		templateUrl: '/admin/smart-book/home',
		controller: 'ctrlHome'
	})
});
app.controller("ctrlType", ctrlType);
app.controller("ctrlPublisher", ctrlPublisher);
app.controller("ctrlAuthor", ctrlAuthor);
app.controller("ctrlContent", ctrlContent);
app.controller("ctrlCharactor", ctrlCharactor);
app.controller("ctrlModule", ctrlModule);
app.controller("ctrlBook", ctrlBook);
app.controller("ctrlCreateBook", ctrlCreateBook);
app.controller("ctrlUpdateBook", ctrlUpdateBook);
app.controller("ctrlLogin", ctrlLogin);
app.controller("adminController", adminController);
app.controller("guestController", guestController);
app.controller("shipperController", shipperController);
app.controller("userCreateController", userCreateController);
app.controller("userUpdateController", userUpdateController);
app.controller("ctrlHome", ctrlHome);


