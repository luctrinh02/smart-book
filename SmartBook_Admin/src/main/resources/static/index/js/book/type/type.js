function ctrlType($scope, $http) {
	$scope.listType = [];
	$scope.createInput = "";
	$scope.updateInput = "";
	$scope.deleteInput = "";
	$scope.updateElement = "";
	$scope.deleteElement = "";
	$scope.sortType = "0";
	$scope.pageIndex = 0;
	$scope.maxPage = 0;
	$scope.getType = "0";
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
				$scope.lstIdReturn.push(JSON.parse(chk[i].value).type.id);
			}
		}

		if ($scope.lstId.length == 0) {
			Toast.fire({
				icon: 'warning',
				title: 'Vui lòng chọn thể loại cần xoá!'
			})
			return;
		}

		$('#deleteListModal').modal('show');
	}

	$scope.deleteListType = function() {
		$http.delete("/api/type/deleteList?listId=" + $scope.lstIdReturn + "&pageIndex=" + $scope.pageIndex + "&sortType=" + $scope.sortType + "&keyWord=" + $scope.keyWord + "&getType=" + $scope.getType).then(function(response) {
			if (response.data.statusCode == "ok") {
				$scope.listType = response.data.data;
				$scope.maxPage = response.data.maxPage;
				$scope.deleteElement = "";
				$scope.pageIndex = response.data.pageIndex;
				$('#deleteListModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Xoá thể loại thành công!'
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

	$http.get("/api/type/getPage").then(function(response) {
		$scope.listType = response.data.data;
		$scope.maxPage = response.data.maxPage;
	});

	$scope.getData = function() {
		$http.get("/api/type/getPage?pageIndex=" + $scope.pageIndex + "&sortType=" + $scope.sortType + "&keyWord=" + $scope.keyWord + "&getType=" + $scope.getType).then(function(response) {
			$scope.listType = response.data.data;
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


	$scope.createType = function() {
		$http.post("/api/type/create?value=" + $scope.createInput.trim()).then(function(response) {
			if (response.data.statusCode == "blank") {
				$scope.createInput = " ";
				Toast.fire({
					icon: 'warning',
					title: 'Vui lòng nhập tên thể loại!'
				})
			} else if (response.data.statusCode == "invalid") {
				Toast.fire({
					icon: 'warning',
					title: 'Thể loại này đã tồn tại!'
				})
			} else if (response.data.statusCode == "ok") {
				$scope.listType = response.data.data;
				$scope.createInput = "";
				$scope.sortType = "0";
				$scope.pageIndex = 0;
				$scope.keyWord = "";
				$scope.getType = "0";
				$scope.maxPage = response.data.maxPage;
				$('#createModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Thêm thể loại mới thành công!'
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
		$scope.updateElement = $scope.listType[index].type.id;
		$scope.updateInput = $scope.listType[index].type.value;
		$('#updateModal').modal('show');
	}

	$scope.updateType = function() {
		$http.put("/api/type/update?value=" + $scope.updateInput + "&element=" + $scope.updateElement + "&pageIndex=" + $scope.pageIndex + "&sortType=" + $scope.sortType + "&keyWord=" + $scope.keyWord + "&getType=" + $scope.getType).then(function(response) {
			if (response.data.statusCode == "blank") {
				$scope.updateInput = " ";
				Toast.fire({
					icon: 'warning',
					title: 'Vui lòng nhập tên thể loại!'
				})
			} else if (response.data.statusCode == "invalid") {
				Toast.fire({
					icon: 'warning',
					title: 'Thể loại này đã tồn tại!'
				})
			} else if (response.data.statusCode == "ok") {
				$scope.listType = response.data.data;
				$scope.updateInput = "";
				$('#updateModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Chỉnh sửa thể loại thành công!'
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
		$scope.deleteElement = $scope.listType[index].type.id;
		$scope.deleteInput = $scope.listType[index].type.value;
		$('#deleteModal').modal('show');
	}

	$scope.deleteType = function() {
		$http.delete("/api/type/delete?element=" + $scope.deleteElement + "&pageIndex=" + $scope.pageIndex + "&sortType=" + $scope.sortType + "&keyWord=" + $scope.keyWord + "&getType=" + $scope.getType).then(function(response) {
			if (response.data.statusCode == "ok") {
				$scope.listType = response.data.data;
				$scope.maxPage = response.data.maxPage;
				$scope.lstId = [];
				$scope.lstIdReturn = [];
				$scope.pageIndex = response.data.pageIndex;
				$('#deleteModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Xoá thể loại thành công!'
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
