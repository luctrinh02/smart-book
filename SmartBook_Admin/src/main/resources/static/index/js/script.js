const Toast = Swal.mixin({
	toast: true,
	position: 'top-end',
	showConfirmButton: false,
	timer: 1500,
	timerProgressBar: true,
	didOpen: (toast) => {
		toast.addEventListener('mouseenter', Swal.stopTimer)
		toast.addEventListener('mouseleave', Swal.resumeTimer)
	}
});


function selectAll() {
	var chkAll = document.getElementById("chkAll");
	var chk = document.getElementsByName("chk");
	if (chkAll.checked == true) {
		for (var i = 0; i < chk.length; i++) {
			chk[i].checked = true;
		}
	} else {
		for (var i = 0; i < chk.length; i++) {
			chk[i].checked = false;
		}
	}
	document.getElementsByClassName("chk")

}

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
		$http.post("/api/type/create?value=" + $scope.createInput).then(function(response) {
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
		console.log(response.data.listBook);
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
		$http.post("/api/author/create?value=" + $scope.createInput).then(function(response) {
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
					title: 'tác giả này đã tồn tại!'
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


