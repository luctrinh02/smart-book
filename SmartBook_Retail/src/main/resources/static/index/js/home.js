function MyController($scope, $http, $rootScope) {
	$rootScope.books = [];
	$http.get("/api/book/suggest").then(function(response) {
		$rootScope.books = response.data;
	});
	$http.get("/api/home/future?condition=createdTime&page=0").then(function(response) {
		$rootScope.booksNew = response.data;
	});
	$http.get("/api/home/future?condition=discount&page=0").then(function(response) {
		$rootScope.booksHot = response.data;
	});
	$http.get("/api/home/future?condition=saleAmount&page=0").then(function(response) {
		$rootScope.booksTrend = response.data;
	});
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