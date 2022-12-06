function BookController($scope, $http, $routeParams) {
	$scope.book = {};
	$scope.star = 0;
	$scope.types = {};
	$http.get("/api/book/" + $routeParams.id).then(function(response) {
		$scope.book = response.data;
		$scope.star = Math.round($scope.book.point / $scope.book.evaluate * 2) / 2;
		if ($scope.star % 1 > 0) {
			for (let i = 0; i < $scope.star - 1; i++) {
				document.getElementById('star').innerHTML += '<i class="bi bi-star-fill text-warning me-1"></i>';
			}
			document.getElementById('star').innerHTML += '<i class="bi bi-star-half text-warning me-1"></i>';
		} else if ($scope.star % 1 == 0) {
			for (let i = 0; i < $scope.star; i++) {
				document.getElementById('star').innerHTML += '<i class="bi bi-star-fill text-warning me-1"></i>';
			}
		}
		document.getElementById('star').innerHTML += '<span class="text small"> ('+$scope.book.evaluate+' đánh giá)</span>';
	});
	
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
	
	$scope.showModal = function(id) {
		$(id).modal("show");
	}

}