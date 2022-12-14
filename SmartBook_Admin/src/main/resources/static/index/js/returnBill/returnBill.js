function returnBillCtrl($scope, $http, $rootScope) {
	$scope.checkDate = function(index) {
		let today = new Date().setHours(7, 0, 0, 0);
		let chek = new Date($scope.bills[index].createdTime).setHours(7, 0, 0, 0);
		return today - chek;
	}

	if ($rootScope.authen.role.id == 2) {
		$scope.sta = { s: 0 };
	} else {
		$scope.sta = { s: 1 };
	}

	$scope.returnRequest = [];
	$scope.bills = [];
	$scope.details = [];
	$scope.status = [];
	$scope.page = 0;
	$scope.tranSn = "";
	$scope.formatDate = function(date) {
		let s = date.split("-");
		return s[2] + "/" + s[1] + "/" + s[0];
	}
	$http.get("/api/admin/bill/status").then(function(response) {
		$scope.status = response.data;
	})
	$http.get("/api/admin/return?page=0&status=0").then(function(response) {
		$scope.bills = response.data;
		$scope.users = response.data;
	})
	$scope.getDetail = function(id) {
		$http.get("/api/admin/return/" + id).then(function(response) {
			$scope.details = response.data;
		})
		$("#detailmodal").modal("show");
	}
	$scope.submit = function(index) {
		let data = {
			id: $scope.bills.content[index].id,
			message: "",
			statusIndex: $scope.sta.s == 1 ? +Number($scope.sta.s) + 2 : Number($scope.sta.s) + 1
		}
		$http.put("/api/admin/return", data).then(function(response) {
			if (response.data.statusCode == "ok") {
				Toast.fire({
					icon: 'success',
					title: "Thành công"
				});
				$scope.getData(0)
			} else {
				Toast.fire({
					icon: 'error',
					title: "Thất bại"
				});
			}
		})
	}
	$scope.cancel = function(index) {
		let data = {
			id: $scope.bills[index].content.id,
			message: document.getElementById("message" + index).value,
			statusIndex: 2
		}
		$http.put("/api/admin/return", data).then(function(response) {
			if (response.data.statusCode == "ok") {
				Toast.fire({
					icon: 'success',
					title: $rootScope.authen.role.id == 2 ? "Xác nhân đơn" : "Nhận đơn" + " thành công"
				});
				$scope.getData(0)
			} else if (response.data.statusCode == "blank") {
				Toast.fire({
					icon: 'error',
					title: "Không bỏ trống thông báo"
				});
				$("#modal" + index).modal("show");
			} else {
				Toast.fire({
					icon: 'error',
					title: "Thất bại"
				});
			}
		})
	}
	$scope.tranSn = ""
	$scope.getData = function(index) {
		if (index < 0 || index > $scope.bills.totalPages - 1 && index != 0) {
			return;
		}
		$http.get("/api/admin/return?page=" + index + "&transn=" + $scope.tranSn + "&status=" + $scope.sta.s).then(function(response) {
			$scope.bills = response.data;
			$scope.users = response.data;
		})
	}
	$scope.search = function() {
		$scope.tranSn = document.getElementById("tranSn").value;
		$scope.getData(0);
	}
	$scope.reset = function() {
		document.getElementById("tranSn").value = "",
			$scope.tranSn = ""
		$scope.getData(0)
	}
	$scope.showModal = function(index) {
		$("#modal" + index).modal("hide");
		$("#cancel" + index).modal("show");
	}
	$scope.hideModal = function(index) {
		$("#cancel" + index).modal("hide");
		$("#modal" + index).modal("show");
	}
};