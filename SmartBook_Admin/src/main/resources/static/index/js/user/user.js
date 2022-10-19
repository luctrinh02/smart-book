function adminController($scope, $http) {
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
	$scope.role = $routeParams.role;
	$scope.initError = function() {
		document.getElementById('email').innerText = "";
		document.getElementById('fullname').innerText = "";
		document.getElementById('phoneNumber').innerText = "";
		document.getElementById('ConfirmPassword').innerText = "";
		document.getElementById('password').innerText = "";
		document.getElementById('address').innerText = "";
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
			"role": $scope.role
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
	$scope.create = function() {
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
			"role": $scope.role
		}
		$http.post("/api/admin/user", formData).then(function(response) {
			if (response.data.statusCode == "ok") {
				Toast.fire({
					icon: 'success',
					title: response.data.data
				})
			} else {
				document.getElementById("email").innerText = response.data.data;
			}
		})
	}
};

function userUpdateController($scope, $http, $routeParams) {
	$scope.title = "";
	$scope.userDefault = {
		"email": "",
		"fullname": "",
		"phoneNumber": "",
		"confirm": "",
		"password": "",
		"address": "",
		"role": ""
	}
	$scope.user = angular.copy($scope.userDefault);
	$scope.name = "";
	$scope.role = 1;
	$http.get("/api/admin/user/" + $routeParams.id).then(function(response) {
		$scope.userDefault = response.data.data;
		$scope.user = response.data.data;
		$scope.name = $scope.user.fullname;
		$scope.role = $scope.user.role.id - 1;
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
	$scope.initError = function() {
		document.getElementById('email').innerText = "";
		document.getElementById('fullname').innerText = "";
		document.getElementById('phoneNumber').innerText = "";
		document.getElementById('address').innerText = "";
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
			"role": $scope.role
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
			"id": $scope.user.id
		}
		$http.put("/api/admin/user/" + $scope.user.id, formData).then(function(response) {
			if (response.data.statusCode == "ok") {
				Toast.fire({
					icon: 'success',
					title: response.data.data
				})
				$scope.name = formData.fullname;
			} else {
				document.getElementById("email").innerText = response.data.data;
			}
		})
	}
	$scope.reset = function() {
		$scope.user = angular.copy($scope.userDefault)
	}
};