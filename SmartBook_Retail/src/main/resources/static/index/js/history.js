function HistoryController($scope, $http) {
	$scope.isShowReturn=false;
	$scope.billIndex=0;
	$scope.returnRequest = [];
	$scope.bills = [];
	$scope.details = [];
	$scope.users = {}
	$scope.page = 0;
	$scope.tranSn = "";
	$scope.comment={
		detail:"",
		rate:"",
		comment:"",
	}
	$scope.formatDate = function(date) {
		let s = date.split("-");
		return s[2] + "/" + s[1] + "/" + s[0];
	}
	$scope.convertText = function(price) {
		var x = Math.ceil(Number(price));
		x = x.toLocaleString('it-IT', {style : 'currency', currency : 'VND'});
		return x;
	}
	$http.get("/api/bill?page=0").then(function(response) {
		$scope.bills = response.data.data.content;
		$scope.users = response.data.data;
	})
	$scope.getDetail = function(id) {
		if ($scope.details.length == 0) {
			$http.get("/api/bill/" + id).then(function(response) {
				$scope.details = response.data.data;
				if($scope.details[0].bill.status.id==5){
					let today = new Date().setHours(7, 0, 0, 0);
					let chek = new Date($scope.details[0].bill.updatedTime).setHours(7, 0, 0, 0);
					if(today-chek<=432000000){
						$scope.isShowReturn=true;
					}else{
						$scope.isShowReturn=false;
					}
				}else{
					$scope.isShowReturn=false;
				}
			});
			document.getElementById('list').classList.add('col-9');
			document.getElementById('list').classList.remove("col-12");
		} else {
			$scope.details = [];
			document.getElementById('list').classList.add('col-12');
			document.getElementById('list').classList.remove("col-9");
		}
	}

	$scope.cancel = function(index,status) {
		$scope.message = " "
		let data={
			message:$scope.message,
			status:status,
			date:$("#scaleDate"+index).val()
		};
		$http.put("/api/bill/" + $scope.bills[index].id, data).then(function(response) {
			if (response.data.statusCode == "ok") {
				Toast.fire({
					icon: 'success',
					title: response.data.data
				});
				$scope.bills[index]=response.data.bill;
			} else {
				Toast.fire({
					icon: 'error',
					title: response.data.data
				});
			}
		})
	}
	$scope.beforeReturn = function() {
		let size = $scope.details.length;
		if (size > 0) {
			$scope.returnRequest.splice(0, size);
		}
		for (let i = 0; i < size; i++) {
			let detail = $scope.details[i];
			let Request = {
				pk: {
					bookId: detail.book.id,
					billId: $scope.details[0].bill.id
				},
				amount: document.getElementById("returnAmount" + i).value
			};
			$scope.returnRequest.push(Request);
		}
		$http.post("/api/return/before", $scope.returnRequest).then(function(response) {
			if (response.data.statusCode == "error") {
				Toast.fire({
					icon: 'error',
					title: response.data.data
				});
			} else {
				$("#returnBillModal").modal("hide");
				$("#confirmModal").modal("show");
			}
		})
	}
	$scope.return = function() {
		$http.post("/api/return", $scope.returnRequest).then(function(response) {
			if (response.data.statusCode == "error") {
				Toast.fire({
					icon: 'error',
					title: response.data.data
				});
			} else {
				Toast.fire({
					icon: 'success',
					title: response.data.data
				});
			}
		})
	}
	$scope.tranSn = ""
	$scope.getData = function(index) {
		$scope.page = index;
		$http.get("/api/bill?page=" + index + "&transn=" + $scope.tranSn).then(function(response) {
			$scope.bills = response.data.data.content;
			$scope.users = response.data.data;
		})
	}
	$scope.search = function() {
		$scope.tranSn = document.getElementById("tranSn").value;
		$scope.getData(0);
	}
	$scope.reset = function() {
		document.getElementById("tranSn").value = "",
		$scope.tranSn = ""
		$scope.getData(0)
	}
	$scope.openComment=function(index){
		$("#commentModal"+index).modal("show");
	}
	$scope.beforeComment=function(index,detail){
		let radioName="rate"+index;
		$scope.comment={
			detailId:{
				bookId:detail.book.id,
				billId:detail.bill.id
			},
			rate:$("input[name='"+radioName+"']:checked").val(),
			content:$("#commentVal"+index).val()
		}
		$http.post("/api/comment/before",$scope.comment).then(function(response){
			if(response.data.statusCode=="ok"){
				$("#commentModal"+index).modal("hide");
				$("#conf").modal("show");
			}else{
				Toast.fire({
					icon: 'error',
					title: "Vui lòng nhập đầy đủ",
				});
			}
		})
	}
	$scope.commentFunc=function(){
		$http.post("/api/comment",$scope.comment).then(function(response){
			if(response.data.statusCode=="ok"){
				Toast.fire({
					icon: 'success',
					title: "Đánh giá thành công",
				});
				document.getElementById('list').classList.add('col-12');
				document.getElementById('list').classList.remove("col-7");
			}else if(response.data.statusCode=="402"){
				Toast.fire({
					icon: 'error',
					title: "Bạn không có quyền đánh giá sản phẩm",
				});
			}else{
				Toast.fire({
					icon: 'error',
					title: "Lỗi dữ liệu",
				});
			}
		})
	}
};