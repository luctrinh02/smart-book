function profileController($scope, $http, $rootScope) {
	$scope.init = function() {
		$("#fullnameId").val("")
		$("#emailId").val("")
		$("#phoneNumberId").val("")
		$("#adressId").val("")
	}
	$scope.curentUser = angular.copy($rootScope.authen);

	$scope.reset = function() {
		$scope.curentUser = angular.copy($rootScope.authen);
		$("#imgae").val('')
	}
	$scope.save = function() {
		let myForm = new FormData();
		var config = {
			"transformRequest": angular.identity,
			"transformResponse": angular.identity,
			"headers": {
				'Content-Type': undefined
			}
		}
		myForm.append("fullname", $scope.curentUser.fullname)
		myForm.append("email", $scope.curentUser.email)
		myForm.append("phoneNumber", $scope.curentUser.phoneNumber)
		myForm.append("address", $scope.curentUser.address)
		if ($("#imgae").val() != "") {
			myForm.append("file", $scope.curentUser.image)
		}
		$http.put("/api/admin/profile", myForm, config).then(function(response) {
			console.log(response.data);
			if (response.data.statusCode == 'ok') {
				Toast.fire({
					icon: 'success',
					title: "Đổi thông tin thành công"
				})
				$rootScope.authen = response.data.authen;
			} else if (response.data.statusCode == 'error') {
				Toast.fire({
					icon: 'error',
					title: "Lỗi thông tin"
				})
			} else if (response.data.statusCode == 'dupli') {
				Toast.fire({
					icon: 'error',
					title: "Email trùng lặp"
				})
			} else {
				Toast.fire({
					icon: 'error',
					title: "Máy chủ bận"
				})
			}
		})
	}
	$scope.check = function() {
		$http.put("/api/admin/profile/before", $scope.curentUser).then(function(response) {
			if (response.data.statusCode == "ok") {
				$("#saveModal").modal("show");
			} else {
				let data = response.data.data; console.log(response.data.data)
				for (let i = 0; i < data.length; i++) {
					document.getElementById(data[i].field + "Id").innerText = data[i].defaultMessage;
				}
			}
		})
	}
	$scope.req = {
		oldPass: "",
		newPass: "",
		confirm: ""
	}
	$scope.initChange = function() {
		document.getElementById("oldPass").innerText = "";
		document.getElementById("newPass").innerText = "";
		document.getElementById("confirm").innerText = "";
	}
	$scope.change = function() {
		$scope.initChange();
		$http.put("/api/admin/change", $scope.req).then(function(response) {
			if (response.data.statusCode == "error") {
				document.getElementById("oldPass").innerText = response.data.oldPass;
				document.getElementById("newPass").innerText = response.data.newPass;
				document.getElementById("confirm").innerText = response.data.confirm;
			} else {
				Toast.fire({
					icon: 'success',
					title: response.data.data
				})
				sleep(1000).then(() => {
					window.location.href = "/logout";
				});
			}
		})
	}
}