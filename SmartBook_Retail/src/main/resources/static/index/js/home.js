function MyController($scope, $http, $rootScope) {
	$rootScope.books = [];
	$scope.booksTrend={
		content:[],
		page:0,
		totalPages:0
	}
	$scope.booksHot={
		content:[],
		page:0,
		totalPages:0
	}
	$scope.booksNew={
		content:[],
		page:0,
		totalPages:0
	}
	$http.get("/api/book/suggest").then(function (response) {
		$rootScope.books = response.data;
	});
	$http.get("/api/home/future?condition=createdTime&page=0").then(function (response) {
		$scope.booksNew.content = response.data.content;
		$scope.booksNew.page=response.data.number;
		$scope.booksNew.totalPages=response.data.totalPages;
	});
	$http.get("/api/home/future?condition=discount&page=0").then(function (response) {
		$scope.booksHot.content = response.data.content;
		$scope.booksHot.page=response.data.number;
		$scope.booksHot.totalPages=response.data.totalPages;
	});
	$http.get("/api/home/future?condition=saleAmount&page=0").then(function (response) {
		$scope.booksTrend.content = response.data.content;
		$scope.booksTrend.page=response.data.number;
		$scope.booksTrend.totalPages=response.data.totalPages;
	});
	$scope.getMore = function (condition, page) {
		switch (condition) {
			case "saleAmount":
				$http.get("/api/home/future?condition=saleAmount&page="+page).then(function (response) {
					$scope.booksTrend.content=$scope.booksTrend.content.concat(response.data.content);
					$scope.booksTrend.page=response.data.number;
					$scope.booksTrend.totalPages=response.data.totalPages;
					console.log($scope.booksTrend.content.length)
				});
				break;
			case "discount":
				$http.get("/api/home/future?condition=discount&page="+page).then(function (response) {
					$scope.booksHot.content=$scope.booksHot.content.concat(response.data.content);
					$scope.booksHot.page=response.data.number;
					$scope.booksHot.totalPages=response.data.totalPages;
				});
				break;
			case "createdTime":
				$http.get("/api/home/future?condition=createdTime&page="+page).then(function (response) {
					$scope.booksNew.content=$scope.booksNew.content.concat(response.data.content);
					$scope.booksNew.page=response.data.number;
					$scope.booksNew.totalPages=response.data.totalPages;
				});
				break;
		}
	}
	$scope.search = function () {
		let search = document.getElementById("searchText").value;
		let dataSearch = {
			key: search,
			min: -1
		}
		$http.post("/api/book/search", dataSearch).then(function (response) {
			$scope.books = response.data;
		});
	}
	$scope.addToCart = function (id) {
		$http.post("/api/book/" + id + "?amount=1").then(function (response) {
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
	$scope.detail = function (id) {
		window.location.href = "/smart-book#/book/" + id;
	}
	$scope.convertText = function (price) {
		var x = Math.ceil(Number(price));
		x = x.toLocaleString('it-IT', { style: 'currency', currency: 'VND' });
		return x;
	}

};