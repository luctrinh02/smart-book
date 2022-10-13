function ctrlPublisher($scope, $http) {
	$scope.pagePublisher;
	$scope.listBook = new Map();

	$scope.createInput = "";

	$scope.updateInput = "";
	$scope.deleteInput = "";

	$scope.updateElement = "";
	$scope.deleteElement = "";

	$scope.sortPublisher = "0";
	$scope.pageIndex = 0;
	$scope.getPublisher = "0";
	$scope.keyWord = "";

	$scope.lstId = [];
	$scope.lstIdReturn = [];

	$http.get("/api/publisher/getPage").then(function(response) {
		$scope.pagePublisher = response.data.data;
		$scope.listBook = response.data.listBook;
		console.log(response.data.listBook);
	});

	$scope.getData = function() {
		$http.get("/api/publisher/getPage?pageIndex=" + $scope.pageIndex + "&sortPublisher=" + $scope.sortPublisher + "&keyWord=" + $scope.keyWord + "&getPublisher=" + $scope.getPublisher).then(function(response) {
			$scope.pagePublisher = response.data.data;
			$scope.listBook = response.data.listBook;
		});
	}

	$scope.getDataReset = function() {
		$scope.pageIndex = 0;
		$scope.getData();
	}


	$scope.nextPage = function() {
		if ($scope.pageIndex < $scope.pagePublisher.totalPages - 1) {
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


	$scope.createPublisher = function() {
		$http.post("/api/publisher/create?value=" + $scope.createInput).then(function(response) {
			if (response.data.statusCode == "blank") {
				$scope.createInput = " ";
				Toast.fire({
					icon: 'warning',
					title: 'Vui lòng nhập tên nhà xuất bản!'
				})
			} else if (response.data.statusCode == "invalid") {
				Toast.fire({
					icon: 'warning',
					title: 'Nhà xuất bản này đã tồn tại!'
				})
			} else if (response.data.statusCode == "ok") {
				$scope.pagePublisher = response.data.data;
				$scope.listBook = response.data.listBook;
				$scope.createInput = "";
				$scope.sortPublisher = "0";
				$scope.pageIndex = 0;
				$scope.keyWord = "";
				$scope.getPublisher = "0";
				$scope.maxPage = response.data.maxPage;
				$('#createModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Thêm nhà xuất bản mới thành công!'
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

	$scope.updatePublisher = function() {
		$http.put("/api/publisher/update?value=" + $scope.updateInput + "&element=" + $scope.updateElement + "&pageIndex=" + $scope.pageIndex + "&sortPublisher=" + $scope.sortPublisher + "&keyWord=" + $scope.keyWord + "&getPublisher=" + $scope.getPublisher).then(function(response) {
			if (response.data.statusCode == "blank") {
				$scope.updateInput = " ";
				Toast.fire({
					icon: 'warning',
					title: 'Vui lòng nhập tên nhà xuất bản!'
				})
			} else if (response.data.statusCode == "invalid") {
				Toast.fire({
					icon: 'warning',
					title: 'Nhà xuất bản này đã tồn tại!'
				})
			} else if (response.data.statusCode == "ok") {
				$scope.pagePublisher = response.data.data;
				$scope.listBook = response.data.listBook;
				$scope.updateInput = "";
				$('#updateModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Chỉnh sửa nhà xuất bản thành công!'
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

	$scope.deletePublisher = function() {
		$http.delete("/api/publisher/delete?element=" + $scope.deleteElement + "&pageIndex=" + $scope.pageIndex + "&sortPublisher=" + $scope.sortPublisher + "&keyWord=" + $scope.keyWord + "&getPublisher=" + $scope.getPublisher).then(function(response) {
			if (response.data.statusCode == "ok") {
				$scope.pagePublisher = response.data.data;
				$scope.listBook = response.data.listBook;
				$scope.deleteElement = "";
				$('#deleteModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Xoá nhà xuất bản thành công!'
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
				title: 'Vui lòng chọn nhà xuất bản cần xoá!'
			})
			return;
		}

		$('#deleteListModal').modal('show');
	}

	$scope.deleteListPublisher = function() {
		$http.delete("/api/publisher/deleteList?listId=" + $scope.lstIdReturn + "&pageIndex=" + $scope.pageIndex + "&sortPublisher=" + $scope.sortPublisher + "&keyWord=" + $scope.keyWord + "&getPublisher=" + $scope.getPublisher).then(function(response) {
			if (response.data.statusCode == "ok") {
				$scope.pagePublisher = response.data.data;
				$scope.listBook = response.data.listBook;
				$scope.lstId = [];
				$scope.lstIdReturn = [];
				$('#deleteListModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Xoá nhà xuất bản thành công!'
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