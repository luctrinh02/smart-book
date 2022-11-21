function HistoryController($scope, $http) {
	$scope.checkDate = function(index) {
		let today = new Date().setHours(7, 0, 0, 0);
		let chek = new Date($scope.bills[index].createdTime).setHours(7, 0, 0, 0);
		return today - chek;
	}
	$scope.returnRequest = [];
	$scope.bills = [];
	$scope.details = [];
	$scope.users = {}
	$scope.page = 0;
	$scope.tranSn = "";
	$scope.formatDate = function(date) {
		let s = date.split("-");
		return s[2] + "/" + s[1] + "/" + s[0];
	}
	$scope.convertText = function(price) {
		let newString = "";
		let oldString = price.toString();
		while (oldString.length > 3) {
			newString += "." + oldString.substring(oldString.length - 3);
			oldString = oldString.slice(0, oldString.length - 3);
		}
		oldString += newString;
		return oldString;
	}
	$http.get("/api/bill?page=0").then(function(response) {
		$scope.bills = response.data.data.content;
		$scope.users = response.data.data;
	})
	$scope.getDetail = function(id) {
		if ($scope.details.length == 0) {
			$http.get("/api/bill/" + id).then(function(response) {
				$scope.details = response.data.data;
			});
			document.getElementById('list').classList.add('col-8');
			document.getElementById('list').classList.remove("col-12");
		} else {
			$scope.details = [];
			document.getElementById('list').classList.add('col-12');
			document.getElementById('list').classList.remove("col-8");
		}
	}

	$scope.cancel = function(index) {
		$scope.message = " "
		$http.put("/api/bill/" + $scope.bills[index].id, $scope.message).then(function(response) {
			if (response.data.statusCode == "ok") {
				Toast.fire({
					icon: 'success',
					title: response.data.data
				});
				$scope.bills[index].status.id = 3;
				$scope.bills[index].status.value = "Đã hủy";
				$scope.bills[index].status.color = "danger";
			} else {
				Toast.fire({
					icon: 'error',
					title: response.data.data
				});
			}
		})
	}
	$scope.beforeReturn = function() {
		let size = $scope.details.length;
		if (size > 0) {
			$scope.returnRequest.splice(0, size);
		}
		for (let i = 0; i < size; i++) {
			let detail = $scope.details[i];
			let Request = {
				pk: {
					bookId: detail.book.id,
					billId: $scope.details[0].bill.id
				},
				amount: document.getElementById("returnAmount" + i).value
			};
			$scope.returnRequest.push(Request);
		}
		$http.post("/api/return/before", $scope.returnRequest).then(function(response) {
			if (response.data.statusCode == "error") {
				Toast.fire({
					icon: 'error',
					title: response.data.data
				});
			} else {
				$("#returnBillModal").modal("hide");
				$("#confirmModal").modal("show");
			}
		})
	}
	$scope.return = function() {
		$http.post("/api/return", $scope.returnRequest).then(function(response) {
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
			}
		})
	}
	$scope.tranSn = ""
	$scope.getData = function(index) {
		$scope.page = index;
		$http.get("/api/bill?page=" + index + "&transn=" + $scope.tranSn).then(function(response) {
			$scope.bills = response.data.data.content;
			$scope.users = response.data.data;
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
};