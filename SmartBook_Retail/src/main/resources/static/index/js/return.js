function ReturnController($scope, $http) {
	$scope.checkDate = function(index) {
		let today = new Date().setHours(7, 0, 0, 0);
		let chek = new Date($scope.bills[index].createdTime).setHours(7, 0, 0, 0);
		return today - chek;
	}
	$scope.convertText = function(price) {
		let newString = "";
		let oldString = price.toString();
		newString = oldString.substring(0, oldString.length % 3);
		oldString = oldString.slice(oldString.length % 3, oldString.length);
		while (oldString.length >= 3) {
			newString += "." + oldString.substring(0, 3);
			oldString = oldString.slice(3, oldString.length);
		}

		if (price.toString().length % 3 == 0) {
			newString = newString.slice(1, newString.length);
		}
		return newString;
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
	$http.get("/api/return?page=0").then(function(response) {
		$scope.bills = response.data.content;
		$scope.users = response.data;
	})
	$scope.getDetail = function(id) {
		if ($scope.details.length == 0) {
			$http.get("/api/return/" + id).then(function(response) {
			$scope.details = response.data;
			console.log($scope.details[0].returnBill.bill.tranSn);
			})
			document.getElementById('list').classList.add('col-8');
			document.getElementById('list').classList.remove("col-12");
		} else {
			$scope.details = [];
			document.getElementById('list').classList.add('col-12');
			document.getElementById('list').classList.remove("col-8");
		}
	}
	$scope.cancel = function(index) {
		$http.put("/api/return", $scope.bills[index].id).then(function(response) {
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
	$scope.tranSn = ""
	$scope.getData = function(index) {
		$scope.page = index;
		$http.get("/api/return?page=" + index + "&transn=" + $scope.tranSn).then(function(response) {
			$scope.bills = response.data.content;
			$scope.users = response.data;
			console.log(response)
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