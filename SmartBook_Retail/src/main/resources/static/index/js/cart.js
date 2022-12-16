function CartController($scope, $http, $rootScope) {
	$scope.carts = [];
	$rootScope.cartPKs = [];
	$http.get("/api/cart").then(function(response) {
		$scope.carts = response.data;
	});
	let cartId = [];
	$scope.select = []
	$scope.total = 0;
	$scope.items = 0;
	
	$scope.chkAll = false;
	
	$scope.selectInput = function() {
		$scope.total = 0;
		cartId = []
		let select = document.getElementsByName("book");
		for (let i = 0; i < select.length; i++) {
			if (select[i].checked == true) {
				cartId.push($scope.carts[select[i].value].cartPK)
				$scope.select.push(select[i].value)
				let c = $scope.carts[select[i].value];
				$scope.total += (c.amount * c.book.price * (100 - c.book.discount) / 100);
			}
		}
		$scope.total.toFixed()
		$scope.items = cartId.length
	}
	
	
	$scope.getLack = function(price) {
		return price - $scope.total;
	}

	$scope.getTotal = function() {
		if ($scope.total >= 3600000) {
			return $scope.total - 500000;
		} else if ($scope.total >= 2400000) {
			return $scope.total - 300000;
		} else if ($scope.total >= 1200000) {
			return $scope.total - 120000;
		} else {
			return $scope.total;
		}
	}

	$scope.getCol = function(price) {
		let rate = Math.floor($scope.total / price * 10);
		let col = (rate * 1.2).toFixed(0);
		if (col >= 12) {
			return "col-12"
		} else if (col > 0 && col < 12) {
			return "col-" + col;
		} else {
			return "d-none";
		}
	}

	$scope.getCol = function(price) {
		let rate = Math.floor($scope.total / price * 10);
		let col = (rate * 1.2).toFixed(0);
		if (col >= 12) {
			return "col-12"
		} else if (col > 0 && col < 12) {
			return "col-" + col;
		} else {
			return "d-none";
		}
	}

	$scope.getReduced = function() {
		if ($scope.total >= 3600000) {
			return 500000;
		} else if ($scope.total >= 2400000) {
			return 300000;
		} else if ($scope.total >= 1200000) {
			return 120000;
		} else {
			return 0;
		}
	}

	$scope.convertText = function(price) {
		var x = Math.ceil(Number(price));
		x = x.toLocaleString('it-IT', {style : 'currency', currency : 'VND'});
		return x;
	}

	$scope.changeAmount = function(index) {
		let cartPK = $scope.carts[index].cartPK;
		let input = document.getElementById("input" + index);
		let amount = input.value
		if (amount != "" && amount != 0 && !isNaN(amount)) {
			$http.put("/api/cart?amount=" + amount, cartPK).then(function(response) {
				if (response.status == 200) {
					if (response.data.statusCode == "error") {
						Toast.fire({
							icon: 'error',
							title: response.data.data
						})
						$scope.carts[index].amount = response.data.max;
						input.value = $scope.carts[index].amount;
					} else {
						$scope.carts[index].amount = amount;
						$scope.selectInput();
					}
				} else {
					Toast.fire({
						icon: 'error',
						title: "Lỗi dữ liệu"
					})
				}
			});
		} else {
			input.value = $scope.carts[index].amount
		}
	}
	$scope.deleteCart = function(index) {
		let cartPK = $scope.carts[index].cartPK
		$http.post("/api/cart", cartPK).then(function(response) {
			Toast.fire({
				icon: 'success',
				title: response.data.data
			});
			$scope.carts.splice(index, 1)

		});
	}

	$scope.pay = function() {
		/*let data = {
			cartPKs: cartId,
			transportFee: "30000"
		}
		$http.post("/api/bill", data).then(function(response) {
			if (response.data.statusCode == "error") {
				Toast.fire({
					icon: 'error',
					title: response.data.data
				});
			} else {
				Toast.fire({
					icon: 'success',
					title: response.data.data
				});
				for (let i = 0; i < $scope.select.length; i++) {
					$scope.carts.splice($scope.select[i].value, 1)
				}
			}
		});*/
	}
	$scope.beforePay = function() {
		if (cartId.length == 0) {
			Toast.fire({
				icon: 'error',
				title: "Vui lòng chọn sản phẩm"
			});
		} else {
			$rootScope.cartPKs = cartId;
			window.location.href = "/smart-book#/payment";

		}
	}
};