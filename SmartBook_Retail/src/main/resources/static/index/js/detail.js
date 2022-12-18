function BookController($scope, $http, $routeParams) {
	$scope.book = {};
	$scope.star = 0;
	$scope.types = {};
	$scope.amount = 0;
	$scope.cart = [];
	$scope.amountInCart = 0;
	$scope.types = "";
	$scope.books = [];
	$scope.comments = [];
	$scope.rate={};
	$http.get("/api/book/" + $routeParams.id).then(function(response) {
		$scope.book = response.data;
		$http.get("/api/book/suggest").then(function(response) {
			$scope.books = response.data;
		});
		$http.get("/api/book/comment/" + $routeParams.id).then(function(response) {
			$scope.comments=response.data;
		});
		$http.get("/api/book/rate/" + $routeParams.id).then(function(response) {
			$scope.rate=response.data;
			$scope.star = Math.round(Number($scope.rate.point) / Number($scope.rate.evaluate) * 2) / 2;
			if ($scope.star % 1 > 0) {
				for (let i = 0; i < $scope.star - 1; i++) {
					document.getElementById('star').innerHTML += '<i class="bi bi-star-fill text-warning me-1"></i>';
				}
				document.getElementById('star').innerHTML += '<i class="bi bi-star-half text-warning me-1"></i>';
			} else if ($scope.star % 1 == 0) {
				for (let i = 0; i < $scope.star; i++) {
					document.getElementById('star').innerHTML += '<i class="bi bi-star-fill text-warning me-1"></i>';
				}
			}
			console.log($scope.rete)
			document.getElementById('star').innerHTML += '<span class="text small"> (' + $scope.rate.evaluate + ' đánh giá)</span>';console.log($scope.rate)
		});
		$http.get("/api/cart").then(function(response) {
			$scope.cart = response.data;
			for (let i = 0; i < $scope.cart.length; i++) {
				if ($scope.cart[i].book.id == $scope.book.id) {
					$scope.amountInCart = $scope.cart[i].amount;
					break;
				}
			}
		});
		$http.get("/api/type").then(function(response) {
			let s = $scope.book.type.split(",");
			for (let i = 0; i < s.length - 2; i++) {
				$scope.types += response.data[s[i]] + ", ";
			}
			$scope.types += response.data[s[s.length-2]];
		});
	});


	$scope.convertText = function(price) {
		var x = Math.ceil(Number(price));
		x = x.toLocaleString('it-IT', {style : 'currency', currency : 'VND'});
		return x;
	}
	
	$scope.detail = function(id){
		window.location.href = "/smart-book#/book/"+id;
	}

	$scope.showModal = function(id) {
		$(id).modal("show");
	}

	$scope.changeAmount = function(amount) {
		if (amount < 0) {
			Toast.fire({
				icon: 'warning',
				title: "Số lượng không hợp lệ"
			})
			$scope.amount = 0;
			return;
		} else if (amount > $scope.book.amount - $scope.amountInCart) {
			Toast.fire({
				icon: 'warning',
				title: "Không đủ số lượng"
			})
			$scope.amount = $scope.book.amount - $scope.amountInCart;
			return;
		}

		$scope.amount = amount;
	};

	$scope.addToCart = function(id) {
		if ($scope.amount == 0) {
			Toast.fire({
				icon: 'warning',
				title: "Vui lòng nhập số lượng mua"
			})
			return;
		}
		$http.post("/api/book/" + id + "?amount=" + $scope.amount).then(function(response) {
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
				$scope.amountInCart = $scope.amountInCart + $scope.amount;
				$scope.amount = 0;
			}
		});
	};
	$scope.buyNow = function(id) {
		$http.post("/api/book/" + id + "?amount=" + 1).then(function(response) {
			if (response.data.statusCode == "error") {
				Toast.fire({
					icon: 'error',
					title: response.data.data
				})
			} else {
				window.location = "/smart-book#/cart";
			}
		});
	}

}