function adminController($scope, $http, $rootScope) {
	$scope.users = {};
	$scope.user = "1";
	$scope.page = 0;
	$scope.fullname = "";
	$http.get("/api/admin/user?page=0&role=" + $scope.user).then(function(response) {
		$scope.users = response.data.data;
		console.log($scope.users);
	})

	$scope.getUser = function(index) {
		if (index < 0 || index > $scope.users.totalPages - 1) {
			return;
		}
		$scope.page = index;
		$http.get("/api/admin/user?page=" + index + "&role=" + $scope.user + "&fullname=" + $scope.fullname).then(function(response) {
			$scope.users = response.data.data;
		})
	}


	$scope.search = function() {
		$scope.page = 0;
		$scope.fullname = document.getElementById("fullname").value;
		$scope.getUser(0);
	}

	$scope.reset = function() {
		$scope.page = 0;
		$scope.fullname = "";
		document.getElementById("fullname").value = "";
		$scope.getUser(0);
	}

	$scope.disable = function(index, action) {
		let id = $scope.users.content[index].id;
		$http.patch("/api/admin/user/" + id, action).then(function(response) {
			if (response.data.statusCode == "ok") {
				Toast.fire({
					icon: 'success',
					title: response.data.data
				})
				if (action == "D") {
					$scope.users.content[index].status.color = "danger";
					$scope.users.content[index].status.value = "Ngừng hoạt động";
					$scope.users.content[index].status.id = "2";
				} else {
					$scope.users.content[index].status.color = "primary";
					$scope.users.content[index].status.value = "Hoạt động";
					$scope.users.content[index].status.id = "1";
				}
			} else {
				Toast.fire({
					icon: 'error',
					title: response.data.data
				})
			}
		})
	}
};

function guestController($scope, $http) {
	$scope.users = {};
	$scope.user = "0";
	$scope.page = 0;
	$scope.fullname = "";
	$http.get("/api/admin/user?page=0&role=" + $scope.user).then(function(response) {
		$scope.users = response.data.data;
		console.log($scope.users);
	})

	$scope.getUser = function(index) {
		if (index < 0 || index > $scope.users.totalPages - 1) {
			return;
		}
		$scope.page = index;
		$http.get("/api/admin/user?page=" + index + "&role=" + $scope.user + "&fullname=" + $scope.fullname).then(function(response) {
			$scope.users = response.data.data;
		})
	}


	$scope.search = function() {
		$scope.page = 0;
		$scope.fullname = document.getElementById("fullname").value;
		$scope.getUser(0);
	}

	$scope.reset = function() {
		$scope.page = 0;
		$scope.fullname = "";
		document.getElementById("fullname").value = "";
		$scope.getUser(0);
	}

	$scope.disable = function(index, action) {
		let id = $scope.users.content[index].id;
		$http.patch("/api/admin/user/" + id, action).then(function(response) {
			if (response.data.statusCode == "ok") {
				Toast.fire({
					icon: 'success',
					title: response.data.data
				})
				if (action == "D") {
					$scope.users.content[index].status.color = "danger";
					$scope.users.content[index].status.value = "Ngừng hoạt động";
					$scope.users.content[index].status.id = "2";
				} else {
					$scope.users.content[index].status.color = "primary";
					$scope.users.content[index].status.value = "Hoạt động";
					$scope.users.content[index].status.id = "1";
				}
			} else {
				Toast.fire({
					icon: 'error',
					title: response.data.data
				})
			}
		})
	}
};


function shipperController($scope, $http) {
	$scope.users = {};
	$scope.user = "2";
	$scope.page = 0;
	$scope.fullname = "";
	$http.get("/api/admin/user?page=0&role=" + $scope.user).then(function(response) {
		$scope.users = response.data.data;
		console.log($scope.users);
	})

	$scope.getUser = function(index) {
		if (index < 0 || index > $scope.users.totalPages - 1) {
			return;
		}
		$scope.page = index;
		$http.get("/api/admin/user?page=" + index + "&role=" + $scope.user + "&fullname=" + $scope.fullname).then(function(response) {
			$scope.users = response.data.data;
		})
	}


	$scope.search = function() {
		$scope.page = 0;
		$scope.fullname = document.getElementById("fullname").value;
		$scope.getUser(0);
	}

	$scope.reset = function() {
		$scope.page = 0;
		$scope.fullname = "";
		document.getElementById("fullname").value = "";
		$scope.getUser(0);
	}

	$scope.disable = function(index, action) {
		let id = $scope.users.content[index].id;
		$http.patch("/api/admin/user/" + id, action).then(function(response) {
			if (response.data.statusCode == "ok") {
				Toast.fire({
					icon: 'success',
					title: response.data.data
				})
				if (action == "D") {
					$scope.users.content[index].status.color = "danger";
					$scope.users.content[index].status.value = "Ngừng hoạt động";
					$scope.users.content[index].status.id = "2";
				} else {
					$scope.users.content[index].status.color = "primary";
					$scope.users.content[index].status.value = "Hoạt động";
					$scope.users.content[index].status.id = "1";
				}
			} else {
				Toast.fire({
					icon: 'error',
					title: response.data.data
				})
			}
		})
	}
};


function userCreateController($scope, $http, $routeParams) {
	$scope.title = "";
	$scope.citys = [];
	$scope.districts = [];
	$scope.wards = [];
	$scope.city = "";
	$scope.district = "";
	$scope.ward = "";
	$scope.file = {};
	switch ($routeParams.role) {
		case "0":
			$scope.title = "Khách hàng";
			break;
		case "1":
			$scope.title = "Quản trị viên";
			break;
		case "2":
			$scope.title = "Giao hàng";
			break;
	}
	$http.get("/api/city").then(function(response) {
		$scope.citys = response.data.citys;
		$scope.districts = response.data.districts;
		$scope.wards = response.data.wards;
	});
	$scope.role = $routeParams.role;
	$scope.initError = function() {
		document.getElementById('email').innerText = "";
		document.getElementById('fullname').innerText = "";
		document.getElementById('phoneNumber').innerText = "";
		document.getElementById('ConfirmPassword').innerText = "";
		document.getElementById('password').innerText = "";
		document.getElementById('address').innerText = "";
		document.getElementById('file').innerText = "";
		document.getElementById('ward').innerText = "";
		document.getElementById('city').innerText = "";
		document.getElementById('district').innerText = "";
	}

	$scope.check = function() {
		$scope.initError();
		let email = document.getElementsByName("email")[0].value;
		let fullname = document.getElementsByName("fullname")[0].value;
		let phoneNumber = document.getElementsByName("phoneNumber")[0].value;
		let address = document.getElementsByName("address")[0].value;
		let password = document.getElementsByName("password")[0].value;
		let confirm = document.getElementsByName("confirm")[0].value;
		let formData = {
			"email": email,
			"fullname": fullname,
			"phoneNumber": phoneNumber,
			"confirm": confirm,
			"password": password,
			"address": address,
			"role": $scope.role,
			"city": $scope.city,
			"district": $scope.district,
			"ward": $scope.ward,
		}
		$http.post("/api/admin/user/validation", formData).then(function(response) {
			if (response.data.statusCode == "ok") {
				if ($('#abc').val() == '') {
					document.getElementById('file').innerText = "Vui lòng chọn hình ảnh người dùng";
				} else {
					$("#exampleModal").modal("show");
					document.getElementById('file').innerText = "";
				}
			} else {
				let data = response.data.data;
				for (let i = 0; i < data.length; i++) {
					try {
						document.getElementById(data[i].field).innerText = data[i].defaultMessage;
					} catch (error) {
						document.getElementById(data[i].code).innerText = data[i].defaultMessage;
					}
				}
				if ($('#abc').val() == '') {
					document.getElementById('file').innerText = "Vui lòng chọn hình ảnh người dùng";
				} else {
					document.getElementById('file').innerText = "";
				}
			}
		})
	}
	$scope.create = function() {
		$scope.initError();
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
		myForm.append("file", $scope.file);
		myForm.append("role", $scope.role);

		myForm.append("ward", $scope.ward);
		myForm.append("city", $scope.city);
		myForm.append("district", $scope.district);

		$http.post("/api/admin/user", myForm, config).then(function(response) {
			if (response.data == 0) {
				Toast.fire({
					icon: 'success',
					title: "Thêm thành công"
				})
				switch ($routeParams.role) {
					case "0":
						window.location.href = "/admin/smart-book#/guest";
						break;
					case "1":
						window.location.href = "/admin/smart-book#/admin";

						break;
					case "2":
						window.location.href = "/admin/smart-book#/shipper";

						break;
				}
			} else {
				document.getElementById("email").innerText = "Email trùng lặp";
			}
		})
	}
};

function userUpdateController($scope, $http, $routeParams) {
	$scope.title = "";
	$scope.citys = [];
	$scope.districts = [];
	$scope.wards = [];
	$scope.city = "";
	$scope.district = "";
	$scope.ward = "";
	$scope.file = {};

	$scope.userDefault = {
		"email": "",
		"fullname": "",
		"phoneNumber": "",
		"confirm": "",
		"password": "",
		"address": "",
		"role": "",
		"img": "",
		"ward": {}
	}

	$scope.user = angular.copy($scope.userDefault);
	$scope.name = "";
	$scope.role = 1;
	$http.get("/api/admin/user/" + $routeParams.id).then(function(response) {
		$scope.userDefault = response.data.data;
		$scope.user = response.data.data;
		$scope.name = $scope.user.fullname;
		$scope.role = $scope.user.role.id - 1;
		$scope.city = $scope.user.ward.district.city.id.toString();
		$scope.district = $scope.user.ward.district.id.toString();
		$scope.ward = $scope.user.ward.id.toString();

		$http.get("/api/city").then(function(response) {
			$scope.citys = response.data.citys;
			$scope.districts = response.data.districts;
			$scope.wards = response.data.wards;
		});
		switch ($scope.role) {
			case 0:
				$scope.title = "Khách hàng";
				break;
			case 1:
				$scope.title = "Quản trị viên";
				break;
			case 2:
				$scope.title = "Giao hàng";
				break;
		}
	})
	$scope.resetAbc = function() {
		$scope.user = angular.copy($scope.userDefault);
		$scope.city = angular.copy($scope.userDefault.ward.district.city.id.toString());
		$scope.district = angular.copy($scope.userDefault.ward.district.id.toString());
		$scope.ward = angular.copy($scope.userDefault.ward.id.toString());
	}
	$scope.initError = function() {
		document.getElementById('email').innerText = "";
		document.getElementById('fullname').innerText = "";
		document.getElementById('phoneNumber').innerText = "";
		document.getElementById('address').innerText = "";
		document.getElementById('file').innerText = "";
		document.getElementById('ward').innerText = "";
		document.getElementById('city').innerText = "";
		document.getElementById('district').innerText = "";
	}
	$scope.check = function() {
		$scope.initError();
		let email = document.getElementsByName("email")[0].value;
		let fullname = document.getElementsByName("fullname")[0].value;
		let phoneNumber = document.getElementsByName("phoneNumber")[0].value;
		let address = document.getElementsByName("address")[0].value;

		let formData = {
			"email": email,
			"fullname": fullname,
			"phoneNumber": phoneNumber,
			"confirm": "smartbook",
			"password": "smartbook",
			"address": address,
			"role": $scope.role,
			"city": $scope.city,
			"district": $scope.district,
			"ward": $scope.ward,
		}
		$http.post("/api/admin/user/validation", formData).then(function(response) {
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

	$scope.update = function() {
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
		myForm.append("confirm", "smartbook")
		myForm.append("password", "smartbook")
		myForm.append("address", document.getElementsByName("address")[0].value)
		if ($('#abc').val() == '') {
			myForm.append("base64", $scope.user.img);
		} else {
			myForm.append("file", $scope.file);
		}
		myForm.append("role", $scope.role);
		myForm.append("ward", $scope.ward);
		myForm.append("city", $scope.city);
		myForm.append("district", $scope.district);

		myForm.append("id", $scope.user.id);

		$http.put("/api/admin/user/" + $scope.user.id, myForm, config).then(function(response) {
			if (response.data == 0) {
				Toast.fire({
					icon: 'success',
					title: "Chỉnh sửa thành công"
				})
				switch ($scope.role.toString()) {
					case "0":
						window.location.href = "/admin/smart-book#/guest";
						break;
					case "1":
						window.location.href = "/admin/smart-book#/admin";

						break;
					case "2":
						window.location.href = "/admin/smart-book#/shipper";

						break;
				}
				$scope.name = formData.fullname;
			} else {
				document.getElementById("email").innerText = "Email trùng lặp";
			}
		})
	}
};