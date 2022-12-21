function ctrlAuthor($scope, $http) {
	$scope.pageAuthor;
	$scope.listBook = new Map();

	$scope.createInput = "";

	$scope.updateInput = "";
	$scope.deleteInput = "";

	$scope.updateElement = "";
	$scope.deleteElement = "";

	$scope.sortAuthor = "0";
	$scope.pageIndex = 0;
	$scope.getAuthor = "0";
	$scope.keyWord = "";

	$scope.lstId = [];
	$scope.lstIdReturn = [];

	$http.get("/api/author/getPage").then(function(response) {
		$scope.pageAuthor = response.data.data;
		$scope.listBook = response.data.listBook;
	});

	$scope.getData = function() {
		$http.get("/api/author/getPage?pageIndex=" + $scope.pageIndex + "&sortAuthor=" + $scope.sortAuthor + "&keyWord=" + $scope.keyWord + "&getAuthor=" + $scope.getAuthor).then(function(response) {
			$scope.pageAuthor = response.data.data;
			$scope.listBook = response.data.listBook;
		});
	}

	$scope.getDataReset = function() {
		$scope.pageIndex = 0;
		$scope.getData();
	}


	$scope.nextPage = function() {
		if ($scope.pageIndex < $scope.pageAuthor.totalPages - 1) {
			$scope.pageIndex++;
		};
		$scope.getData();
	}

	$scope.previousPage = function() {
		if ($scope.pageIndex > 0) {
			$scope.pageIndex--;
		};
		$scope.getData();
	}


	$scope.createAuthor = function() {
		$http.post("/api/author/create?value=" + $scope.createInput.trim()).then(function(response) {
			if (response.data.statusCode == "blank") {
				$scope.createInput = " ";
				Toast.fire({
					icon: 'warning',
					title: 'Vui lòng nhập tên tác giả!'
				})
			} else if (response.data.statusCode == "invalid") {
				Toast.fire({
					icon: 'warning',
					title: 'tác giả này đã tồn tại!'
				})
			} else if (response.data.statusCode == "ok") {
				$scope.pageAuthor = response.data.data;
				$scope.listBook = response.data.listBook;
				$scope.createInput = "";
				$scope.sortAuthor = "0";
				$scope.pageIndex = 0;
				$scope.keyWord = "";
				$scope.getAuthor = "0";
				$scope.maxPage = response.data.maxPage;
				$('#createModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Thêm tác giả mới thành công!'
				})
			} else if (response.data.statusCode == "error") {
				$scope.createInput = "";
				$('#createModal').modal('hide');
				Toast.fire({
					icon: 'error',
					title: 'Hệ thống đang gặp lỗi!'
				})
			}
		});
	}

	$scope.showUpdateModal = function(element) {
		$scope.updateElement = element.id;
		$scope.updateInput = element.name;
		$('#updateModal').modal('show');
	}

	$scope.updateAuthor = function() {
		$http.put("/api/author/update?value=" + $scope.updateInput + "&element=" + $scope.updateElement + "&pageIndex=" + $scope.pageIndex + "&sortAuthor=" + $scope.sortAuthor + "&keyWord=" + $scope.keyWord + "&getAuthor=" + $scope.getAuthor).then(function(response) {
			if (response.data.statusCode == "blank") {
				$scope.updateInput = " ";
				Toast.fire({
					icon: 'warning',
					title: 'Vui lòng nhập tên tác giả!'
				})
			} else if (response.data.statusCode == "invalid") {
				Toast.fire({
					icon: 'warning',
					title: 'Tác giả này đã tồn tại!'
				})
			} else if (response.data.statusCode == "ok") {
				$scope.pageAuthor = response.data.data;
				$scope.listBook = response.data.listBook;
				$scope.updateInput = "";
				$('#updateModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Chỉnh sửa tác giả thành công!'
				})
			} else if (response.data.statusCode == "error") {
				$scope.updateInput = "";
				$('#updateModal').modal('hide');
				Toast.fire({
					icon: 'error',
					title: 'Hệ thống đang gặp lỗi!'
				})
			}
		});
	}

	$scope.showDeleteModal = function(element) {
		$scope.deleteElement = element.id;
		$scope.deleteInput = element.name;
		$('#deleteModal').modal('show');
	}

	$scope.deleteAuthor = function() {
		$http.delete("/api/author/delete?element=" + $scope.deleteElement + "&pageIndex=" + $scope.pageIndex + "&sortAuthor=" + $scope.sortAuthor + "&keyWord=" + $scope.keyWord + "&getAuthor=" + $scope.getAuthor).then(function(response) {
			if (response.data.statusCode == "ok") {
				$scope.pageAuthor = response.data.data;
				$scope.listBook = response.data.listBook;
				$scope.deleteElement = "";
				$('#deleteModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Xoá tác giả thành công!'
				})
			} else if (response.data.statusCode == "error") {
				$scope.deleteElement = "";
				$('#deleteModal').modal('hide');
				Toast.fire({
					icon: 'error',
					title: 'Hệ thống đang gặp lỗi!'
				})
			}
		});
	}


	$scope.showDeleteListModal = function() {
		let chk = document.getElementsByName("chk");
		$scope.lstId = [];
		$scope.lstIdReturn = [];
		for (let i = 0; i < chk.length; i++) {
			if (chk[i].checked == true) {
				$scope.lstId.push(JSON.parse(chk[i].value));
				$scope.lstIdReturn.push(JSON.parse(chk[i].value).id);
			}
		}

		if ($scope.lstId.length == 0) {
			Toast.fire({
				icon: 'warning',
				title: 'Vui lòng chọn tác giả cần xoá!'
			})
			return;
		}

		$('#deleteListModal').modal('show');
	}

	$scope.deleteListAuthor = function() {
		$http.delete("/api/author/deleteList?listId=" + $scope.lstIdReturn + "&pageIndex=" + $scope.pageIndex + "&sortAuthor=" + $scope.sortAuthor + "&keyWord=" + $scope.keyWord + "&getAuthor=" + $scope.getAuthor).then(function(response) {
			if (response.data.statusCode == "ok") {
				$scope.pageAuthor = response.data.data;
				$scope.listBook = response.data.listBook;
				$scope.lstId = [];
				$scope.lstIdReturn = [];
				$('#deleteListModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Xoá tác giả thành công!'
				})
			} else if (response.data.statusCode == "error") {
				$scope.deleteElement = "";
				$('#deleteListModal').modal('hide');
				Toast.fire({
					icon: 'error',
					title: 'Hệ thống đang gặp lỗi!'
				})
			}
		});
	}
	
	
}

