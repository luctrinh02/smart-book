function ctrlCharactor($scope, $http) {
	$scope.listCharactor = [];
	$scope.createInput = "";
	$scope.updateInput = "";
	$scope.deleteInput = "";
	$scope.updateElement = "";
	$scope.deleteElement = "";
	$scope.sortCharactor = "0";
	$scope.pageIndex = 0;
	$scope.maxPage = 0;
	$scope.getCharactor = "0";
	$scope.keyWord = "";
	$scope.lstId = [];
	$scope.lstIdReturn = [];
	$scope.showDeleteListModal = function() {
		let chk = document.getElementsByName("chk");

		$scope.lstId = [];
		$scope.lstIdReturn = [];
		for (let i = 0; i < chk.length; i++) {
			if (chk[i].checked == true) {
				$scope.lstId.push(JSON.parse(chk[i].value));
				$scope.lstIdReturn.push(JSON.parse(chk[i].value).charactor.id);
			}
		}

		if ($scope.lstId.length == 0) {
			Toast.fire({
				icon: 'warning',
				title: 'Vui lòng chọn nhân vật cần xoá!'
			})
			return;
		}

		$('#deleteListModal').modal('show');
	}

	$scope.deleteListCharactor = function() {
		$http.delete("/api/charactor/deleteList?listId=" + $scope.lstIdReturn + "&pageIndex=" + $scope.pageIndex + "&sortCharactor=" + $scope.sortCharactor + "&keyWord=" + $scope.keyWord + "&getCharactor=" + $scope.getCharactor).then(function(response) {
			if (response.data.statusCode == "ok") {
				$scope.listCharactor = response.data.data;
				$scope.maxPage = response.data.maxPage;
				$scope.deleteElement = "";
				$scope.pageIndex = response.data.pageIndex;
				$('#deleteListModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Xoá nhân vật thành công!'
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

	$http.get("/api/charactor/getPage").then(function(response) {
		$scope.listCharactor = response.data.data;
		$scope.maxPage = response.data.maxPage;
	});

	$scope.getData = function() {
		$http.get("/api/charactor/getPage?pageIndex=" + $scope.pageIndex + "&sortCharactor=" + $scope.sortCharactor + "&keyWord=" + $scope.keyWord + "&getCharactor=" + $scope.getCharactor).then(function(response) {
			$scope.listCharactor = response.data.data;
			$scope.maxPage = response.data.maxPage;
		});
	}

	$scope.getDataReset = function() {
		$scope.pageIndex = 0;
		$scope.getData();
	}


	$scope.nextPage = function() {
		if ($scope.pageIndex < $scope.maxPage) {
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


	$scope.createCharactor = function() {
		$http.post("/api/charactor/create?value=" + $scope.createInput.trim()).then(function(response) {
			if (response.data.statusCode == "blank") {
				$scope.createInput = " ";
				Toast.fire({
					icon: 'warning',
					title: 'Vui lòng nhập tên nhân vật!'
				})
			} else if (response.data.statusCode == "invalid") {
				Toast.fire({
					icon: 'warning',
					title: 'Nhân vật này đã tồn tại!'
				})
			} else if (response.data.statusCode == "ok") {
				$scope.listCharactor = response.data.data;
				$scope.createInput = "";
				$scope.sortCharactor = "0";
				$scope.pageIndex = 0;
				$scope.keyWord = "";
				$scope.getCharactor = "0";
				$scope.maxPage = response.data.maxPage;
				$('#createModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Thêm nhân vật mới thành công!'
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

	$scope.showUpdateModal = function(index) {
		$scope.updateElement = $scope.listCharactor[index].charactor.id;
		$scope.updateInput = $scope.listCharactor[index].charactor.value;
		$('#updateModal').modal('show');
	}

	$scope.updateCharactor = function() {
		$http.put("/api/charactor/update?value=" + $scope.updateInput + "&element=" + $scope.updateElement + "&pageIndex=" + $scope.pageIndex + "&sortCharactor=" + $scope.sortCharactor + "&keyWord=" + $scope.keyWord + "&getCharactor=" + $scope.getCharactor).then(function(response) {
			if (response.data.statusCode == "blank") {
				$scope.updateInput = " ";
				Toast.fire({
					icon: 'warning',
					title: 'Vui lòng nhập tên nhân vật!'
				})
			} else if (response.data.statusCode == "invalid") {
				Toast.fire({
					icon: 'warning',
					title: 'Nhân vật này đã tồn tại!'
				})
			} else if (response.data.statusCode == "ok") {
				$scope.listCharactor = response.data.data;
				$scope.updateInput = "";
				$('#updateModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Chỉnh sửa nhân vật thành công!'
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

	$scope.showDeleteModal = function(index) {
		$scope.deleteElement = $scope.listCharactor[index].charactor.id;
		$scope.deleteInput = $scope.listCharactor[index].charactor.value;
		$('#deleteModal').modal('show');
	}

	$scope.deleteCharactor = function() {
		$http.delete("/api/charactor/delete?element=" + $scope.deleteElement + "&pageIndex=" + $scope.pageIndex + "&sortCharactor=" + $scope.sortCharactor + "&keyWord=" + $scope.keyWord + "&getCharactor=" + $scope.getCharactor).then(function(response) {
			if (response.data.statusCode == "ok") {
				$scope.listCharactor = response.data.data;
				$scope.maxPage = response.data.maxPage;
				$scope.lstId = [];
				$scope.lstIdReturn = [];
				$scope.pageIndex = response.data.pageIndex;
				$('#deleteModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Xoá nhân vật thành công!'
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
	
	

}
