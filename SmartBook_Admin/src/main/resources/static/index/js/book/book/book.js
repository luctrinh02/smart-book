function ctrlBook($scope, $http) {
	$scope.listType = {};
	$scope.listAuthor = {};
	$scope.listPublisher = {};
	$scope.listBookStatus = {};
	$scope.pageBook = {};
	
	$scope.params = {
		"page": 0,
		"sortBy": "id",
		"keyWord": "",
		"type": "",
		"publisher": null,
		"status": null,
		"author": null,
		"evaluate": 0
	};

	$http.post("/api/book/getBooks", $scope.params).then(function(response) {
		$scope.listType = response.data.listType;
		$scope.listAuthor = response.data.listAuthor;
		$scope.listPublisher = response.data.listPublisher;
		$scope.listBookStatus = response.data.listBookStatus;
		$scope.pageBook = response.data.pageBook;
		
	});
	
	$scope.getData = function(index){
		if (index < 0 || index > $scope.pageBook.totalPages - 1) {
			return;
		}
		$scope.params.page = index;
		$http.post("/api/book/getBooks", $scope.params).then(function(response) {
		$scope.listType = response.data.listType;
		$scope.listAuthor = response.data.listAuthor;
		$scope.listPublisher = response.data.listPublisher;
		$scope.listBookStatus = response.data.listBookStatus;
		$scope.pageBook = response.data.pageBook;
		});
	};
	
	$scope.updateStatus = function(index){
		cosole.log(index);
	};
	
	
	

	
}

function ctrlCreateBook($scope, $http) {


}

function ctrlUpdateBook($scope, $http) {


}