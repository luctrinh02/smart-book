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
	})
});
app.controller("ctrlType", ctrlType);
app.controller("ctrlPublisher", ctrlPublisher);
app.controller("ctrlAuthor", ctrlAuthor);
app.controller("ctrlContent", ctrlContent);
app.controller("ctrlCharactor", ctrlCharactor);
app.controller("ctrlModule", ctrlModule);
app.controller("ctrlBook", ctrBook);