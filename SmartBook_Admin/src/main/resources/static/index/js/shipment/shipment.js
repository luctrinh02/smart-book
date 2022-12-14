function ctrlShipment($scope, $http) {
	$scope.sta = { s: 3 }
	$http.get("/api/admin/bill/status").then(function(response) {
		$scope.status = response.data;
	})
	$scope.checkDate = function(index) {
		let today = new Date().setHours(7, 0, 0, 0);
		let chek = new Date($scope.bills[index].createdTime).setHours(7, 0, 0, 0);
		return today - chek;
	}
	$scope.convertText = function(price) {
		var x = Math.ceil(Number(price));
		x = x.toLocaleString('it-IT', {style : 'currency', currency : 'VND'});
		return x;
	}
	$scope.getDate = function(cDate) {
		let today = new Date().setHours(7, 0, 0, 0);
		let chek = new Date(cDate).setHours(7, 0, 0, 0);
		return Math.ceil((today - chek) / 86400000);
	}
	$scope.formatDate = function(date) {
		let s = date.split("-");
		return s[2] + "/" + s[1] + "/" + s[0];
	}
	$scope.bills = [];
	$http.get("/api/admin/shipment?status=" + $scope.sta.s).then(function(response) {
		$scope.bills = response.data;
		console.log($scope.bills)
	})
	$scope.getData = function() {
		$http.get("/api/admin/shipment?status=" + $scope.sta.s).then(function(response) {
			$scope.bills = response.data;
		})
	}
	$scope.getDetail = function(index) {
		if ($scope.bills[index].bill == true) {
			$http.get("/api/admin/bill/" + $scope.bills[index].billId).then(function(response) {
				$scope.details = response.data;
			})
			$("#detailmodalBill").modal("show");
		} else {
			$http.get("/api/admin/return/" + $scope.bills[index].billId).then(function(response) {
				$scope.details = response.data;
			})
			$("#detailmodal").modal("show");
		}
	}
	$scope.showModal = function(index) {
		$("#modal" + index).modal("hide");
		$("#cancel" + index).modal("show");
	}
	$scope.hideModal = function(index) {
		$("#cancel" + index).modal("hide");
		$("#modal" + index).modal("show");
	}
	$scope.cancel = function(index) {
		let data = {
			id: $scope.bills[index].id,
			message: document.getElementById("message" + index).value,
			status: 2
		}
		$http.put("/api/admin/shipment", data).then(function(response) {
			if (response.data.statusCode == "ok") {
				Toast.fire({
					icon: 'success',
					title: "Th??nh c??ng"
				});
				$scope.getData()
			} else if (response.data.statusCode == "blank") {
				Toast.fire({
					icon: 'error',
					title: "Kh??ng b??? tr???ng th??ng b??o"
				});
				$("#modal" + index).modal("show");
			} else if (response.data.statusCode == "amount") {
				Toast.fire({
					icon: 'warning',
					title: "Kh??ng ????? s??ch ????? b?? ????n m???i"
				});
				$scope.getData()
			} else {
				Toast.fire({
					icon: 'error',
					title: "Th???t b???i"
				});
			}
		})
	}
	$scope.userCancel = function(index) {
		let data = {
			id: $scope.bills[index].id,
			message: "",
			status: 5
		}
		$http.put("/api/admin/shipment", data).then(function(response) {
			if (response.data.statusCode == "ok") {
				Toast.fire({
					icon: 'success',
					title: "Th??nh c??ng"
				});
				$scope.getData()
			} else {
				Toast.fire({
					icon: 'error',
					title: "Th???t b???i"
				});
			}
		})
	}
	$scope.submit = function(index) {
		let data = {
			id: $scope.bills[index].id,
			message: "",
			status: 4
		}
		$http.put("/api/admin/shipment", data).then(function(response) {
			if (response.data.statusCode == "ok") {
				Toast.fire({
					icon: 'success',
					title: "Th??nh c??ng"
				});
				$scope.getData()
			} else {
				Toast.fire({
					icon: 'error',
					title: "Th???t b???i"
				});
			}
		})
	}
}