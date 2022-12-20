function ctrlHome($scope, $http) {
	$scope.home={};
    $http.get("/api/home").then(function(response){
        $scope.home=response.data;
    })
    $scope.convertText = function(price) {
		var x = Math.ceil(Number(price));
		x = x.toLocaleString('it-IT', {style : 'currency', currency : 'VND'});
		return x;
	}
}