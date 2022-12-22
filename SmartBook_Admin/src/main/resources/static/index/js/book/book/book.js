function ctrlBook($scope, $http, $rootScope, $routeParams) {
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
			console.log(response.data)
			$scope.listType = response.data.listType;
			$scope.listAuthor = response.data.listAuthor;
			$scope.listPublisher = response.data.listPublisher;
			$scope.listBookStatus = response.data.listBookStatus;
			$scope.pageBook = response.data.pageBook;
			$scope.typeInBook = response.data.typeInBook;
			$scope.charactorInBook = response.data.charactorInBook;
			$scope.contentInBook = response.data.contentInBook;
		});
	};
	$scope.convertText = function(price) {
		var x = Math.ceil(Number(price));
		x = x.toLocaleString('it-IT', { style: 'currency', currency: 'VND' });
		return x;
	}
	$scope.showModalUpdate = function(index) {
		$scope.elementUpdate = $scope.pageBook.content[index];
		$scope.statusUpdate = $scope.elementUpdate.status.id - 1 + '';
		$('#updateStatusModal').modal('show');
	};
	$scope.updateStatus = function() {
		$scope.elementUpdate.status = $scope.listBookStatus[$scope.statusUpdate];
		console.log($scope.elementUpdate);

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
	$scope.getIds = function(list) {
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
			if (document.getElementById(inputId).value.trim().toUpperCase() == listCheck[i].value.trim().toUpperCase()) {
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
				$http.post("/api/book/createType", document.getElementById(inputId).value.trim()).then(function(response) {
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
				$http.post("/api/book/createContent", document.getElementById(inputId).value.trim()).then(function(response) {
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
				$http.post("/api/book/createCharactor", document.getElementById(inputId).value.trim()).then(function(response) {
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
		"isbn": "",
		"amount": "",
		"charactor": "",
		"content": "",
		"createdTime": null,
		"description": "",
		"discount": 0,
		"evaluate": 0,
		"image": {},
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
		"height": "",
		"length": "",
		"weight": "",
		"year": "",
		"width": "",
		"saleTime": null,
	};

	$scope.statusChangeCreate = function() {
		if ($scope.book.status == 3 || $scope.book.status == "3") {
			document.getElementById("rowSaleTime").classList.remove("d-none");
			document.getElementById("rowSaleTime").classList.add("d-inline");
		} else {
			document.getElementById("rowSaleTime").classList.add("d-none");
			document.getElementById("rowSaleTime").classList.remove("d-inline");
		}
	}
	$scope.init = function() {
		document.getElementById("name").innerText = "";
		document.getElementById("isbn").innerText = "";
		document.getElementById("numOfPage").innerText = "";
		document.getElementById("width").innerText = "";
		document.getElementById("length").innerText = "";
		document.getElementById("height").innerText = "";
		document.getElementById("weight").innerText = "";
		document.getElementById("year").innerText = "";
		document.getElementById("author").innerText = "";
		document.getElementById("publisher").innerText = "";
		document.getElementById("charactor").innerText = "";
		document.getElementById("content").innerText = "";
		document.getElementById("status").innerText = "";
		document.getElementById("price").innerText = "";
		document.getElementById("amount").innerText = "";
		document.getElementById("type").innerText = "";
		document.getElementById("saleTime").innerText = "";
	}
	$scope.check = function() {
		$scope.init();
		$scope.book.type = $scope.getIds($scope.showType);
		$scope.book.content = $scope.getIds($scope.showContent);
		$scope.book.charactor = $scope.getIds($scope.showCharactor);
		$scope.book.createdTime = new Date();
		$http.post("/api/book/before", $scope.book).then(function(response) {
			if (response.data.statusCode == "ok") {
				$("#addConf").modal("show");
			} else {
				let data = response.data.data;
				for (let i = 0; i < data.length; i++) {
					document.getElementById(data[i].field).innerText = data[i].defaultMessage;
				}
				if ($scope.book.status == 3 && $scope.book.saleTime == null) {
					document.getElementById("saleTime").innerText = "Vui lòng nhập ngày ra mắt"
				} else {
					document.getElementById("saleTime").innerText = ""
				}
			}
		})
	}
	$scope.insertBook = function() {
		let myForm = new FormData();
		var config = {
			"transformRequest": angular.identity,
			"transformResponse": angular.identity,
			"headers": {
				'Content-Type': undefined
			}
		}
		if ($routeParams.id != null) {
			myForm.append("id", $scope.book.id)
		}
		myForm.append("isbn", $scope.book.isbn)
		myForm.append("amount", $scope.book.amount)
		myForm.append("charactor", $scope.book.charactor)
		myForm.append("content", $scope.book.content)
		myForm.append("description", $scope.book.description)
		myForm.append("discount", $scope.book.discount)
		if ($("#abc").val() != "") {
			myForm.append("file", $scope.book.image)
		}
		myForm.append("name", $scope.book.name)
		myForm.append("numOfPage", $scope.book.numOfPage)
		myForm.append("price", $scope.book.price)
		myForm.append("type", $scope.book.type)
		myForm.append("author", $scope.book.author)
		myForm.append("publisher", $scope.book.publisher)
		myForm.append("height", $scope.book.height)
		myForm.append("weight", $scope.book.weight)
		myForm.append("width", $scope.book.width)
		myForm.append("year", $scope.book.year)
		myForm.append("length", $scope.book.length)
		if ($scope.book.saleTime != null) {
			myForm.append("saleTime", $scope.book.saleTime.toLocaleDateString("en-US"))
		} else {
			myForm.append("saleTime", new Date().toLocaleDateString("en-US"))
		}
		myForm.append("status", $scope.book.status)


		$http.post("/api/book", myForm, config).then(function(response) {
			console.log(response)
			if (response.status == 200) {
				Toast.fire({
					icon: 'success',
					title: "Lưu dữ liệu thành công"
				})
				$scope.init();
				window.location.href = "/admin/smart-book#/book";
			} else {
				Toast.fire({
					icon: 'error',
					title: "Lỗi dữ liệu"
				})
			}
		})
	};
	/* Update Book */
	$scope.getShows = function(ids, list) {
		let listReturn = [];
		let id = ids.split(",");
		for (let i = 0; i < id.length; i++) {
			for (let j = 0; j < list.length; j++) {
				if (list[j].id == id[i]) {
					listReturn.push(list[j]);
				}
			}
		}
		return listReturn;
	}


	if ($routeParams.id != null) {
		$http.get("/api/book/" + $routeParams.id).then(function(response) {
			$scope.book = response.data;
			$scope.book.author = $scope.book.author.id.toString();
			$scope.book.publisher = $scope.book.publisher.id.toString();
			$scope.book.status = $scope.book.status.id - 1 + "";
			$scope.statusChangeCreate();
			$scope.book.saleTime = new Date($scope.book.saleTime);
			$http.post("/api/book/getBooks", $scope.params).then(function(response) {
				$scope.listType = response.data.listType;
				$scope.listContent = response.data.listContent;
				$scope.listCharactor = response.data.listCharactor;
				$scope.showType = $scope.getShows($scope.book.type, $scope.listType);
				$scope.showContent = $scope.getShows($scope.book.content, $scope.listContent);
				$scope.showCharactor = $scope.getShows($scope.book.charactor, $scope.listCharactor);
			});
		});
	}

	$scope.excel = {};
	$scope.upload = function() {
		document.getElementById("load").classList.remove("d-none");
		$("#excelError").val("");
		let myForm = new FormData();
		var config = {
			"transformRequest": angular.identity,
			"transformResponse": angular.identity,
			"headers": {
				'Content-Type': undefined
			}
		}
		if ($("#excel").val() != "") {
			myForm.append("file", $scope.excel)
		}
		$http.post("/api/book/upload", myForm, config).then(function(response) {
			switch (Number(response.data)) {
				case 0:
					document.getElementById("load").classList.add("d-none");
					Toast.fire({
						icon: 'success',
						title: "Thành công"
					})
					$("#excelModal").modal("hide");
					$("#excelModal").val("");
					sleep(1000).then(() => {
						location.reload();
					})
					break;
				case 2:
					Toast.fire({
						icon: 'error',
						title: "File không tồn tại"
					})
					$("#excelModal").modal("hide");
					$("#excelModal").val("");
					break;
				case 4:
					$("#excelError").html("File lỗi");
					break;
				case 3:
					$("#excelError").html("File lỗi");
					break;
				default:
					Toast.fire({
						icon: 'error',
						title: "Lỗi hệ thống"
					})
					$("#excelModal").modal("hide");
					$("#excelModal").val("");
					break;
			}
		})
	}
}



