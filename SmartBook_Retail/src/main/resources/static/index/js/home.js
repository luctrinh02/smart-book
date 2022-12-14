function MyController($scope, $http, $rootScope) {
	$rootScope.books = [];
	$http.get("/api/book/suggest").then(function(response) {
		$rootScope.books = response.data;
	});
	// $http.get("/api/book/future?condition=createdTime").then(function(response) {
	// 	$rootScope.book4s = response.data;
	// });
	// $http.get("/api/book/future?condition=discount").then(function(response) {
	// 	$rootScope.book3s = response.data;
	// });
	// $http.get("/api/book/future?condition=saleAmount").then(function(response) {
	// 	$rootScope.book2s = response.data;
	// });
	$scope.search = function() {
		let search = document.getElementById("searchText").value;
		let dataSearch={
			key:search,
			min:-1
		}
		$http.post("/api/book/search",dataSearch).then(function(response) {
			$rootScope.books = response.data;
		});
	}
	$scope.addToCart = function(id) {
		$http.post("/api/book/" + id + "?amount=1").then(function(response) {
			if (response.data.statusCode == "error") {
				Toast.fire({
					icon: 'error',
					title: response.data.data
				})
			} else {
				Toast.fire({
					icon: 'success',
					title: response.data.data
				})
			}
		});
	};
	$scope.detail = function(id){
		window.location.href = "/smart-book#/book/"+id;
	}
	$scope.convertText = function(price) {
		var x = Math.ceil(Number(price));
		x = x.toLocaleString('it-IT', {style : 'currency', currency : 'VND'});
		return x;
	}

};