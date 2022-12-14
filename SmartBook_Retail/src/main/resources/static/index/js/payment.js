function PaymentController($scope, $http, $rootScope) {
	console.log($rootScope.cartPKs)
	$scope.citys = [];
	$scope.districts = [];
	$scope.wards = [];
	$scope.city = "";
	$scope.district = "";
	$scope.ward = "";
	$http.get("/api/city").then(function(response) {
		$scope.citys = response.data.citys;
		$scope.districts = response.data.districts;
		$scope.wards = response.data.wards;
	});
}