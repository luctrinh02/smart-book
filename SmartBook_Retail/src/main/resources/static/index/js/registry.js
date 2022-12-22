function registryController($scope, $http) {
	$scope.citys = [];
	$scope.districts = [];
	$scope.wards = [];
	$scope.file = {};
	$scope.city = "";
	$scope.district = "";
	$scope.ward = "";
	$http.get("/api/city").then(function(response) {
		$scope.citys = response.data.citys;
		$scope.districts = response.data.districts;
		$scope.wards = response.data.wards;
	});

	$scope.initError = function() {
		document.getElementById('email').innerText = "";
		document.getElementById('fullname').innerText = "";
		document.getElementById('phoneNumber').innerText = "";
		document.getElementById('ConfirmPassword').innerText = "";
		document.getElementById('password').innerText = "";
		document.getElementById('address').innerText = "";
		document.getElementById('ward').innerText = "";
		document.getElementById('city').innerText = "";
		document.getElementById('district').innerText = "";
	}
	$scope.check = function() {
		$scope.initError();
		let email = document.getElementsByName("email")[0].value;
		let fullname = document.getElementsByName("fullname")[0].value;
		let phoneNumber = document.getElementsByName("phoneNumber")[0].value;
		let confirm = document.getElementsByName("confirm")[0].value;
		let password = document.getElementsByName("password")[0].value;
		let address = document.getElementsByName("address")[0].value;
		let formData = {
			"email": email,
			"fullname": fullname,
			"phoneNumber": phoneNumber,
			"confirm": confirm,
			"password": password,
			"address": address,
			"city": $scope.city,
			"district": $scope.district,
			"ward": $scope.ward,
		}
		$http.post("/api/user/before", formData).then(function(response) {
			if (response.data.statusCode == "ok") {
				$("#exampleModal").modal("show");
			} else {
				let data = response.data.data;
				for (let i = 0; i < data.length; i++) {
					try {
						document.getElementById(data[i].field).innerText = data[i].defaultMessage;
					} catch (error) {
						document.getElementById(data[i].code).innerText = data[i].defaultMessage;
					}
				}
			}
		})
	}
	$scope.reset = function() {
		document.getElementsByName("email")[0].value = "";
		document.getElementsByName("fullname")[0].value = "";
		document.getElementsByName("phoneNumber")[0].value = "";
		document.getElementsByName("confirm")[0].value = "";
		document.getElementsByName("password")[0].value = "";
		document.getElementsByName("address")[0].value = "";
		$scope.city = "";
		$scope.district = "";
		$scope.ward = "";
	}
	$scope.create = function() {
		let myForm = new FormData();
		var config = {
			"transformRequest": angular.identity,
			"transformResponse": angular.identity,
			"headers": {
				'Content-Type': undefined
			}
		}
		myForm.append("email", document.getElementsByName("email")[0].value);
		myForm.append("fullname", document.getElementsByName("fullname")[0].value);
		myForm.append("phoneNumber", document.getElementsByName("phoneNumber")[0].value)
		myForm.append("confirm", document.getElementsByName("confirm")[0].value)
		myForm.append("password", document.getElementsByName("password")[0].value)
		myForm.append("address", document.getElementsByName("address")[0].value)
		myForm.append("base64", "smartbook");

		myForm.append("ward", $scope.ward);
		myForm.append("city", $scope.city);
		myForm.append("district", $scope.district);

		$http.post("/api/user", myForm, config).then(function(response) {
			if (response.data == 0) {
				Toast.fire({
					icon: 'success',
					title: "Đăng ký thành công!!!"
				})
				sleep(1500).then(() => {
					window.location.href = "/smart-book/login"
				})
			} else if (response.data == 1) {
				document.getElementById("email").innerText = "Email trùng lặp";
			} else {
				Toast.fire({
					icon: 'error',
					title: "Lỗi dữ liệu"
				})
			}
		})
	}
}