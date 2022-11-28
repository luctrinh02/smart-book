function MyController($scope, $http, $rootScope) {
	$rootScope.books = [];
	$http.get("/api/book/suggest").then(function(response) {
		$rootScope.books = response.data;
	});
	$scope.search = function() {
		let search = document.getElementById("searchText").value;
		$http.get("/api/book/search?key=" + search).then(function(response) {
			$rootScope.books = response.data;
		});
	}
	$scope.addToCart = function(id) {
		$http.post("/api/book/" + id + "?amount=1").then(function(response) {
			if (response.data.statusCode == "error") {
				Toast.fire({
					icon: 'error',
					title: response.data.data
				})
			} else {
				Toast.fire({
					icon: 'success',
					title: response.data.data
				})
			}
		});
	};
	$scope.detail = function(id){
		window.location.href = "/smart-book#/book/"+id;
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

};