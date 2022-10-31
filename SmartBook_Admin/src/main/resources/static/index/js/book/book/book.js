function ctrlBook($scope, $http, $rootScope) {
	$scope.listType = [];
	$scope.listContent = [];
	$scope.listCharactor = [];
	$scope.listAuthor = [];
	$scope.listPublisher = [];
	$scope.listBookStatus = [];
	$scope.pageBook = {};
	$scope.typeInBook = new Map();
	$scope.charactorInBook = new Map();
	$scope.contentInBook = new Map();
	$scope.showInfo = [];
	$scope.newInfo = "";
	$scope.showType = [];
	$scope.newType = "";
	$scope.showContent = [];
	$scope.newContent = "";
	$scope.showCharactor = [];
	$scope.newCharactor = "";
	$scope.titleInfo = "";
	$scope.statusUpdate = 0;
	$scope.elementUpdate;
	$scope.params = {
		"page": 0,
		"sortBy": "id",
		"keyWord": "",
		"type": "",
		"publisher": "",
		"status": "",
		"author": "",
		"evaluate": 0
	};
	$http.post("/api/book/getBooks", $scope.params).then(function(response) {
		$scope.listType = response.data.listType;
		$scope.listContent = response.data.listContent;
		$scope.listCharactor = response.data.listCharactor;
		$scope.listAuthor = response.data.listAuthor;
		$scope.listPublisher = response.data.listPublisher;
		$scope.listBookStatus = response.data.listBookStatus;
		$scope.pageBook = response.data.pageBook;
		$scope.typeInBook = response.data.typeInBook;
		$scope.charactorInBook = response.data.charactorInBook;
		$scope.contentInBook = response.data.contentInBook;
		autocomplete(document.getElementById("newType"), $scope.listType);
		autocomplete(document.getElementById("newContent"), $scope.listContent);
		autocomplete(document.getElementById("newCharactor"), $scope.listCharactor);
	});
	$scope.getData = function(index) {
		if (index < 0 || index > $scope.pageBook.totalPages - 1 && index != 0) {
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
	$scope.showModalUpdate = function(index) {
		$scope.elementUpdate = $scope.pageBook.content[index];
		$scope.statusUpdate = $scope.elementUpdate.status.id - 1 + '';
		$('#updateStatusModal').modal('show');
	};
	$scope.updateStatus = function() {
		$scope.elementUpdate.status = $scope.listBookStatus[$scope.statusUpdate];
		$http.put("/api/book/update", $scope.elementUpdate).then(function(response) {
			$scope.pageBook = response.data.pageBook;
			if (response.data.statusCode == "ok") {
				Toast.fire({
					icon: 'success',
					title: 'Cập nhật trạng thái thành công!'
				})
			} else if (response.data.statusCode == "error") {
				Toast.fire({
					icon: 'error',
					title: 'Hệ thống đang gặp lỗi!'
				})
			}
		});
	};
	$scope.getIds = function(list){
		$scope.ids = "";
		for (let i = 0; i < list.length; i++) {
			$scope.ids += list[i].id + ',';
		}
		return $scope.ids;
	}
	$scope.updateInfo = function() {
		if ($scope.showInfo.length == 0) {
			Toast.fire({
				icon: 'warning',
				title: 'Vui lòng chọn ít nhất một ' + $scope.titleInfo.toLocaleLowerCase() + '!'
			});
			document.getElementById("newInfo").focus();
			return;
		}
		
		switch ($scope.titleInfo) {
			case "Thể loại":
				$scope.elementUpdate.type = $scope.getIds($scope.showInfo);
				break;
			case "Nội dung":
				$scope.elementUpdate.content = $scope.getIds($scope.showInfo);
				break;
			default:
				$scope.elementUpdate.charactor = $scope.getIds($scope.showInfo);
		}
		$http.put("/api/book/update", $scope.elementUpdate).then(function(response) {
			$scope.pageBook = response.data.pageBook;
			if (response.data.statusCode == "ok") {
				$scope.pageBook = response.data.pageBook;
				$scope.typeInBook = response.data.typeInBook;
				$scope.charactorInBook = response.data.charactorInBook;
				$scope.contentInBook = response.data.contentInBook;
				$('#modalInfo').modal('hide');
				Toast.fire({
					icon: 'success',
					title: 'Cập nhật thông tin thành công!'
				})
			} else if (response.data.statusCode == "error") {
				Toast.fire({
					icon: 'error',
					title: 'Hệ thống đang gặp lỗi!'
				})
			}
		});
	};
	$scope.showInfoModal = function(bookIndex, index) {
		$scope.showInfo = [];
		$scope.newInfo = "";
		$scope.titleInfo = "";
		$scope.elementUpdate = $scope.pageBook.content[bookIndex];
		if (index == -1) {
			$scope.showInfo = angular.copy($scope.typeInBook[$scope.elementUpdate.id]);
			$scope.titleInfo = "Thể loại";
			autocomplete(document.getElementById("newInfo"), $scope.listType);
		} else if (index == 0) {
			$scope.showInfo = angular.copy($scope.contentInBook[$scope.elementUpdate.id]);
			$scope.titleInfo = "Nội dung";
			autocomplete(document.getElementById("newInfo"), $scope.listContent);
		} else {
			$scope.showInfo = angular.copy($scope.charactorInBook[$scope.elementUpdate.id]);
			$scope.titleInfo = "Nhân vật";
			autocomplete(document.getElementById("newInfo"), $scope.listCharactor);
		}
		$('#modalInfo').modal('show');
	};

	$scope.checkNewInfo = function(inputId, listAdd) {
		$scope.listCheck = [];
		switch ($scope.titleInfo) {
			case "Thể loại":
				$scope.listCheck = $scope.listType;
				break;
			case "Nội dung":
				$scope.listCheck = $scope.listContent;
				break;
			default:
				$scope.listCheck = $scope.listCharactor;
		}
		if (!$scope.checkAddList(inputId, listAdd, $scope.listCheck, $scope.titleInfo)) {
			$('#modalInfo').modal('hide');
		}
	};

	$scope.checkAddList = function(inputId, listAdd, listCheck, title) {
		$scope.titleInfo = title;
		if (document.getElementById(inputId).value.trim() == "") {
			Toast.fire({
				icon: 'warning',
				title: 'Không được bỏ trống!'
			});
			document.getElementById(inputId).value = "";
			document.getElementById(inputId).focus();
			return true;
		}
		for (var i = 0; i < listCheck.length; i++) {
			if (document.getElementById(inputId).value.trim().toUpperCase() == listCheck[i].value.toUpperCase()) {
				for (var j = 0; j < listAdd.length; j++) {
					if (listAdd[j].id == listCheck[i].id) {
						Toast.fire({
							icon: 'error',
							title: 'Đã tồn tại!'
						});
						document.getElementById(inputId).value = "";
						document.getElementById(inputId).focus();
						return true;
					}
				}
				listAdd.push(listCheck[i]);
				Toast.fire({
					icon: 'success',
					title: 'Thêm mới thành công!'
				});
				document.getElementById(inputId).value = "";
				document.getElementById(inputId).focus();
				return true;
			}
		}
		$('#modalConfirm').modal('show');
	}

	$scope.remove = function(index, list, focus) {
		list.splice(index, 1);
		Toast.fire({
			icon: 'success',
			title: 'Xoá thành công!'
		});
		document.getElementById(focus).focus();
	};

	$scope.removeAll = function(list, focus) {
		list.splice(0, list.length);
		document.getElementById(focus).focus();
	}

	$scope.createNewInfo = function() {
		$scope.createElementType('index');
		$('#modalInfo').modal('show');
	};

	$scope.createElementType = function(temp) {
		switch (temp) {
			case "index":
				$scope.createElement('newInfo', $scope.showInfo);
				break;
			default:
				switch ($scope.titleInfo) {
					case 'Thể loại':
						$scope.createElement("newType", $scope.showType);
						break;
					case 'Nội dung':
						$scope.createElement("newContent", $scope.showContent);
						break;
					default:
						$scope.createElement("newCharactor", $scope.showCharactor);
				}
		}
	}

	$scope.createElement = function(inputId, list) {
		switch ($scope.titleInfo) {
			case "Thể loại":
				$http.post("/api/book/createType", document.getElementById(inputId).value).then(function(response) {
					if (response.data.statusCode == "ok") {
						$scope.listType = response.data.listType;
						list.push($scope.listType[$scope.listType.length - 1]);
						autocomplete(document.getElementById(inputId), $scope.listType);
						Toast.fire({
							icon: 'success',
							title: 'Thêm thể loại mới thành công!'
						})
					} else if (response.data.statusCode == "error") {
						Toast.fire({
							icon: 'error',
							title: 'Hệ thống đang gặp lỗi!'
						})
					}
				});
				break;
			case "Nội dung":
				$http.post("/api/book/createContent", document.getElementById(inputId).value).then(function(response) {
					if (response.data.statusCode == "ok") {
						$scope.listContent = response.data.listContent;
						list.push($scope.listContent[$scope.listContent.length - 1]);
						autocomplete(document.getElementById(inputId), $scope.listContent);
						Toast.fire({
							icon: 'success',
							title: 'Thêm nội dung mới thành công!'
						})
					} else if (response.data.statusCode == "error") {
						Toast.fire({
							icon: 'error',
							title: 'Hệ thống đang gặp lỗi!'
						})
					}
				});
				break;
			default:
				$http.post("/api/book/createCharactor", document.getElementById(inputId).value).then(function(response) {
					if (response.data.statusCode == "ok") {
						$scope.listCharactor = response.data.listCharactor;
						list.push($scope.listCharactor[$scope.listCharactor.length - 1]);
						autocomplete(document.getElementById(inputId), $scope.listCharactor);
						Toast.fire({
							icon: 'success',
							title: 'Thêm nhân vật mới thành công!'
						})
					} else if (response.data.statusCode == "error") {
						Toast.fire({

							icon: 'error',
							title: 'Hệ thống đang gặp lỗi!'
						})
					}
				});
		}
		document.getElementById(inputId).value = "";
		document.getElementById(inputId).focus();
	};

	/*	Create book	 */
	$scope.book = {
		"id": 0,
		"ISBN": "",
		"amount": "",
		"charactor": $scope.getIds($scope.showCharactor),
		"content": $scope.getIds($scope.showContent),
		"createdTime": null,
		"description": "",
		"discount": 0,
		"evaluate": 0,
		"image": "",
		"name": "",
		"numOfPage": "",
		"point": 0,
		"price": "",
		"saleAmount": 0,
		"type": "",
		"updatedTime": null,
		"author": "",
		"createdBy": $rootScope.authen,
		"publisher": "",
		"status": "",
	};
	$scope.showBook = function(){
		$scope.book.type = $scope.getIds($scope.showType);
		$scope.book.content = $scope.getIds($scope.showContent);
		$scope.book.charactor = $scope.getIds($scope.showCharactor);
		$scope.book.createdTime = new Date();
		console.log($scope.book);
	}
	
}



