function ctrlContent($scope, $http) {
	$scope.listContent = [];
	$scope.createInput = "";
	$scope.updateInput = "";
	$scope.deleteInput = "";
	$scope.updateElement = "";
	$scope.deleteElement = "";
	$scope.sortContent = "0";
	$scope.pageIndex = 0;
	$scope.maxPage = 0;
	$scope.getContent = "0";
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
				$scope.lstIdReturn.push(JSON.parse(chk[i].value).content.id);
			}
		}

		if ($scope.lstId.length == 0) {
			Toast.fire({
				icon: 'warning',
				title: 'Vui lòng chọn nội dung cần xoá!'
			})
			return;
		}

		$('#deleteListModal').modal('show');
	}

	$scope.deleteListContent = function() {
		$http.delete("/api/content/deleteList?listId=" + $scope.lstIdReturn + "&pageIndex=" + $scope.pageIndex + "&sortContent=" + $scope.sortContent + "&keyWord=" + $scope.keyWord + "&getContent=" + $scope.getContent).then(function(response) {
			if (response.data.statusCode == "ok") {
				$scope.listContent = response.data.data;
				$scope.maxPage = response.data.maxPage;
				$scope.deleteElement = "";
				$scope.pageIndex = response.data.pageIndex;
				$('#deleteListModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Xoá nội dung thành công!'
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

	$http.get("/api/content/getPage").then(function(response) {
		$scope.listContent = response.data.data;
		$scope.maxPage = response.data.maxPage;
	});

	$scope.getData = function() {
		$http.get("/api/content/getPage?pageIndex=" + $scope.pageIndex + "&sortContent=" + $scope.sortContent + "&keyWord=" + $scope.keyWord + "&getContent=" + $scope.getContent).then(function(response) {
			$scope.listContent = response.data.data;
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


	$scope.createContent = function() {
		$http.post("/api/content/create?value=" + $scope.createInput).then(function(response) {
			if (response.data.statusCode == "blank") {
				$scope.createInput = " ";
				Toast.fire({
					icon: 'warning',
					title: 'Vui lòng nhập tên nội dung!'
				})
			} else if (response.data.statusCode == "invalid") {
				Toast.fire({
					icon: 'warning',
					title: 'Nội dung này đã tồn tại!'
				})
			} else if (response.data.statusCode == "ok") {
				$scope.listContent = response.data.data;
				$scope.createInput = "";
				$scope.sortContent = "0";
				$scope.pageIndex = 0;
				$scope.keyWord = "";
				$scope.getContent = "0";
				$scope.maxPage = response.data.maxPage;
				$('#createModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Thêm nội dung mới thành công!'
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
		$scope.updateElement = $scope.listContent[index].content.id;
		$scope.updateInput = $scope.listContent[index].content.value;
		$('#updateModal').modal('show');
	}

	$scope.updateContent = function() {
		$http.put("/api/content/update?value=" + $scope.updateInput + "&element=" + $scope.updateElement + "&pageIndex=" + $scope.pageIndex + "&sortContent=" + $scope.sortContent + "&keyWord=" + $scope.keyWord + "&getContent=" + $scope.getContent).then(function(response) {
			if (response.data.statusCode == "blank") {
				$scope.updateInput = " ";
				Toast.fire({
					icon: 'warning',
					title: 'Vui lòng nhập tên nội dung!'
				})
			} else if (response.data.statusCode == "invalid") {
				Toast.fire({
					icon: 'warning',
					title: 'Nội dung này đã tồn tại!'
				})
			} else if (response.data.statusCode == "ok") {
				$scope.listContent = response.data.data;
				$scope.updateInput = "";
				$('#updateModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Chỉnh sửa nội dung thành công!'
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
		$scope.deleteElement = $scope.listContent[index].content.id;
		$scope.deleteInput = $scope.listContent[index].content.value;
		$('#deleteModal').modal('show');
	}

	$scope.deleteContent = function() {
		$http.delete("/api/content/delete?element=" + $scope.deleteElement + "&pageIndex=" + $scope.pageIndex + "&sortContent=" + $scope.sortContent + "&keyWord=" + $scope.keyWord + "&getContent=" + $scope.getContent).then(function(response) {
			if (response.data.statusCode == "ok") {
				$scope.listContent = response.data.data;
				$scope.maxPage = response.data.maxPage;
				$scope.lstId = [];
				$scope.lstIdReturn = [];
				$scope.pageIndex = response.data.pageIndex;
				$('#deleteModal').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Xoá nội dung thành công!'
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
