function ctrlModule($scope, $http ,$rootScope) {
	$scope.listModule = [];
	$scope.listSubModule = new Map();
	$http.get("/api/module/getModule").then(function(response) {
		$scope.listModule = response.data.data;
		$scope.listSubModule = response.data.listSubModule;
	});

	$http.get("/api/admin/pricipal").then(function(response){
		$rootScope.authen=response.data;
	})
}
