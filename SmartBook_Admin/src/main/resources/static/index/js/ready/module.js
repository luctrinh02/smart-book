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
	$rootScope.getInstance=function(isAll){
		let role=$rootScope.authen.role.id;
		if(role==2 && isAll==null){
			return false;
		}
		if(role==3 && isAll==null){
			return true;
		}else{
			return role==2?true:isAll;
		}
	}
}
