function PaymentController($scope, $http, $rootScope) {
	$scope.cartPKs = angular.copy($rootScope.cartPKs);
	$rootScope.cartPKs = "";
	if ($scope.cartPKs == null) { $('#confirmModal').modal("hide"); window.location.href = "/smart-book#/cart"; };
	$scope.total = 0;
	$scope.totalWeight = 0;

	$scope.paymentTypes = [];
	$scope.transportTypes = [];
	$scope.citys = [];
	$scope.districts = [];
	$scope.wards = [];

	$http.get("/api/city").then(function(response) {
		$scope.citys = response.data.citys;
		$scope.districts = response.data.districts;
		$scope.wards = response.data.wards;
		$scope.fullname = $rootScope.authen.fullname;
		$scope.phoneNumber = $rootScope.authen.phoneNumber;
		$scope.city = $rootScope.authen.ward.district.city.id.toString();
		$scope.district = $rootScope.authen.ward.district.id.toString();
		$scope.ward = "";
		for (let i = 0; i < $scope.wards[$scope.district].length; i++) {
			if ($scope.wards[$scope.district][i].id == $rootScope.authen.ward.id) {
				$scope.ward = i + '';
				return;
			}
		}
	});

	$scope.addressDetail = $rootScope.authen.address;
	$scope.paymentType = { p: 0 };
	$scope.transportType = { t: 0 };
	$scope.transportFee = 0;

	$http.get("/api/transportType").then(function(response) {
		$scope.transportTypes = response.data;
		if ($scope.cartPKs != null) {
			let data = {
				cartPKs: $scope.cartPKs
			}
			$http.post("/api/payment", data).then(function(response) {
				$scope.carts = response.data;
				for (let i = 0; i < $scope.carts.length; i++) {
					$scope.total += ($scope.carts[i].amount * $scope.carts[i].book.price * (100 - $scope.carts[i].book.discount) / 100);
					$scope.totalWeight += ($scope.carts[i].book.weight * $scope.carts[i].amount) / 1000;
				}
				$scope.total.toFixed();
				$scope.totalWeight = Math.floor(Number($scope.totalWeight * 10)) / 10;
				$scope.transportFee = $scope.transportTypes[$scope.transportType.t].fee * $scope.totalWeight;
				$scope.transportFee.toFixed();
			});
		}
	});

	$scope.getTotal = function() {
		if ($scope.total >= 3600000) {
			return $scope.total - 500000 + $scope.transportFee;
		} else if ($scope.total >= 2400000) {
			return $scope.total - 300000 + $scope.transportFee;
		} else if ($scope.total >= 1200000) {
			return $scope.total - 120000 + $scope.transportFee;
		} else {
			return $scope.total + $scope.transportFee;;
		}
	}

	$http.get("/api/paymentType").then(function(response) {
		$scope.paymentTypes = response.data;
	});
	$scope.changeCity = function() {
		if ($scope.city != '1' && $scope.transportType.t == 2) {
			$scope.transportType.t = 0;
		}
		$scope.changeTransportType();
	}
	$scope.changeTransportType = function() {
		$scope.transportFee = 0;
		$scope.totalWeight = 0;
		for (let i = 0; i < $scope.carts.length; i++) {
			$scope.totalWeight += ($scope.carts[i].book.weight * $scope.carts[i].amount) / 1000;
		}
		$scope.totalWeight = Math.ceil(Number($scope.totalWeight * 10)) / 10;
		$scope.transportFee = $scope.transportTypes[$scope.transportType.t].fee * $scope.totalWeight;
		$scope.transportFee.toFixed();
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
		x = x.toLocaleString('it-IT', { style: 'currency', currency: 'VND' });
		return x;
	}

	$scope.init = function() {
		document.getElementById("fullname").innerText = "";
		document.getElementById("phoneNumber").innerText = "";
		document.getElementById("addressDetail").innerText = "";
		document.getElementById("ward").innerText = "";
		document.getElementById("district").innerText = "";
		document.getElementById("city").innerText = "";
	}

	$scope.beforePay = function() {
		let data = {
			cartPKs: $scope.cartPKs,
			transportFee: $scope.transportFee,
			transportType: $scope.transportType.t,
			paymentType: $scope.paymentType.p,
			fullname: $scope.fullname,
			phoneNumber: $scope.phoneNumber,
			addressDetail: $scope.addressDetail,
			ward: $scope.ward == '' ? $scope.ward : $scope.wards[$scope.district][$scope.ward].id,
			district: $scope.district,
			city: $scope.city
		}
		$http.post("/api/bill/before", data).then(function(response) {
			if (response.data.statusCode == "max") {
				Toast.fire({
					icon: 'error',
					title: response.data.data
				});
			}
			else if (response.data.statusCode == "error") {
				let data = response.data.data;
				$scope.init();
				for (let i = 0; i < data.length; i++) {
					try {
						document.getElementById(data[i].field).innerText = data[i].defaultMessage;
					} catch (error) {
						document.getElementById(data[i].code).innerText = data[i].defaultMessage;
					}
				}
			} else {
				$('#confirmModal').modal("show");
			}
		});
	}

	$scope.pay = function() {
		let data = {
			cartPKs: $scope.cartPKs,
			transportFee: $scope.transportFee,
			transportType: $scope.transportType.t,
			paymentType: $scope.paymentType.p,
			fullname: $scope.fullname,
			phoneNumber: $scope.phoneNumber,
			addressDetail: $scope.addressDetail,
			ward: $scope.ward == '' ? $scope.ward : $scope.wards[$scope.district][$scope.ward].id,
			district: $scope.district,
			city: $scope.city
		}
		$http.post("/api/bill", data).then(function(response) {
			if (response.data.statusCode == "max") {
				Toast.fire({
					icon: 'error',
					title: response.data.data
				});
			} else if (response.data.statusCode == "error") {
				let data = response.data.data;
				$scope.init();
				for (let i = 0; i < data.length; i++) {
					try {
						document.getElementById(data[i].field).innerText = data[i].defaultMessage;
					} catch (error) {
						document.getElementById(data[i].code).innerText = data[i].defaultMessage;
					}
				}
			} else {
				window.location.href = "/smart-book#/afterPayment/" + response.data.tranSn;
			}
		});
	}
}