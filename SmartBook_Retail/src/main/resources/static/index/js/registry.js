function registryController($scope, $http) {
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
    $scope.reset=function(){
        document.getElementsByName("email")[0].value="";
		document.getElementsByName("fullname")[0].value="";
		document.getElementsByName("phoneNumber")[0].value="";
		document.getElementsByName("confirm")[0].value="";
		document.getElementsByName("password")[0].value="";
		document.getElementsByName("address")[0].value="";
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
		}
		$http.post("/api/user", formData).then(function(response) {
			if (response.data.statusCode == "ok") {
				Toast.fire({
					icon: 'success',
					title: response.data.data
				})
                sleep(1500).then(() => {
                    window.location.href="/smart-book/login"
                })
			} else if(response.data.statusCode == "email") {
				document.getElementById("email").innerText = response.data.data;
			}else{
				Toast.fire({
					icon: 'error',
					title: "Lỗi dữ liệu"
				})
			}
		})
	}
}