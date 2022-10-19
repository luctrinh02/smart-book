function ctrlModule($scope, $http) {
	$scope.listModule = [];
	$scope.listSubModule = new Map();
	
	$http.get("/api/module/getModule").then(function(response) {
		$scope.listModule = response.data.data;
		$scope.listSubModule = response.data.listSubModule;
	});
}