function MyController($scope, $http) {
	$scope.books = [];
	$http.get("/api/book/suggest").then(function(response) {
		$scope.books = response.data;
	});
	$scope.search=function(){
		let search=document.getElementById("searchText").value;
		$http.get("/api/book/search?key="+search).then(function(response) {
			$scope.books = response.data;
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
	$scope.convertText = function(price) {
		let newString = "";
		let oldString = price.toString();
		while(oldString.length > 3){
			newString += "." + oldString.substring(oldString.length-3);
			oldString = oldString.slice(0, oldString.length-3);
		}
		oldString += newString;
		return oldString;
	}
	
};