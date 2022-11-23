function ctrlCoupon($scope,$http){
    $scope.listCoupon= [];
    $scope.createInput = "";
    $scope.createInput2 = "";
    $scope.createInput3 = "";
    $scope.createInput4 = "";
    $scope.createInput5 = "";
    $scope.createInput6 = "";
    $scope.updateInput = "";
    $scope.updateInput2 = "";
    $scope.updateInput3 = "";
    $scope.updateInput4 = "";
    $scope.updateInput5 = "";
    $scope.updateInput6 = "";
    $scope.deleteInput = "";
    $scope.updateElement = "";
    $scope.deleteElement = "";
    $scope.sortCoupon = "0";
    $scope.pageIndex = 0;
    $scope.maxPage = 0;
    $scope.getCoupon = "0";
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
                title: 'Vui lòng chọn khuyến mãi cần xoá!'
            })
            return;
        }

        $('#deleteListModal').modal('show');
    }

    $scope.deleteListCoupon = function() {
        $http.delete("/api/coupon/deleteList?listId=" + $scope.lstIdReturn + "&pageIndex=" + $scope.pageIndex + "&sortCoupon=" + $scope.sortContent + "&keyWord=" + $scope.keyWord + "&getContent=" + $scope.getContent).then(function(response) {
            if (response.data.statusCode == "ok") {
                $scope.listCoupon = response.data.data;
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

    $http.get("/api/coupon/getPage").then(function(response) {
        $scope.listCoupon = response.data.data;
        // $scope.maxPage = response.data.maxPage;
        console.log(response.data.data)
    });

    $scope.getData = function() {
        $http.get("/api/coupon/getPage?pageIndex=" + $scope.pageIndex + "&sortCoupon=" + $scope.sortCoupon + "&keyWord=" + $scope.keyWord + "&getCoupon=" + $scope.getCoupon).then(function(response) {
            $scope.listCoupon = response.data.data;
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
    $scope.createCoupon = function() {
        $http.post("/api/coupon/create?value=" + $scope.createInput+"&startDate="+$scope.createInput2+"&endDate="
            +$scope.createInput3+"&discount="+$scope.createInput4+"&min_money="+$scope.createInput5+"&money="+$scope.createInput6).then(function(response) {
            if (response.data.statusCode == "blank") {
                $scope.createInput = " ";
                $scope.createInput2 = " ";
                $scope.createInput3 = " ";
                $scope.createInput4 = " ";
                $scope.createInput5 = " ";
                $scope.createInput6 = " ";
                Toast.fire({
                    icon: 'warning',
                    title: 'Vui lòng nhập đầy đủ thông tin!'
                })
            } else if (response.data.statusCode == "invalid") {
                Toast.fire({
                    icon: 'warning',
                    title: 'Khuyến mãi đã tồn tại!'
                })
            } else if (response.data.statusCode == "ok") {
                $scope.listCoupon = response.data.data;
                // $scope.listBook = response.data.listBook;
                $scope.createInput = "";
                $scope.createInput2 = "";
                $scope.createInput3 = "";
                $scope.createInput4 = "";
                $scope.sortCoupon = "0";
                $scope.pageIndex = 0;
                $scope.keyWord = "";
                $scope.getCoupon = "0";
                $scope.maxPage = response.data.maxPage;
                console.log(response.data.data);
                $('#createModal').modal('hide');
                Toast.fire({
                    icon: 'success',
                    title: 'Thêm khuyến mãi mới thành công!'
                })
            } else if (response.data.statusCode == "error") {
                $scope.createInput = "";
                $scope.createInput2 = "";
                $scope.createInput3 = "";
                $scope.createInput4 = "";
                $scope.createInput5 = "";
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
        $scope.updateInput2 = element.startDate;
        $scope.updateInput3 = element.endDate;
        $scope.updateInput4 = element.discount;
        $scope.updateInput5 = element.minMoney;
        $scope.updateInput6 = element.money;
        $('#updateModal').modal('show');
    }

    $scope.updateCoupon = function() {
        $http.put("/api/coupon/update?value=" + $scope.updateInput
            +"&startDate="+$scope.updateInput2
            +"&endDate="+$scope.updateInput3
            +"&discount="+$scope.updateInput4
            +"&min_money="+$scope.updateInput5
            +"&money="+$scope.updateInput6
            + "&element=" + $scope.updateElement + "&pageIndex=" + $scope.pageIndex + "&sortCoupon=" + $scope.sortCoupon + "&keyWord=" + $scope.keyWord + "&getCoupon=" + $scope.getCoupon).then(function(response) {
            if (response.data.statusCode == "blank") {
                $scope.updateInput = " ";
                $scope.updateInput2 = " ";
                $scope.updateInput3 = " ";
                $scope.updateInput4 = " ";
                $scope.updateInput5 = " ";
                $scope.updateInput6 = " ";
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
                $scope.listCoupon = response.data.data;
                // $scope.listBook = response.data.listBook;
                $scope.updateInput = "";
                $scope.updateInput2 = "";
                $scope.updateInput3 = "";
                $scope.updateInput4 = "";
                $scope.updateInput5 = "";
                $scope.updateInput6 = "";
                $('#updateModal').modal('hide');
                Toast.fire({
                    icon: 'success',
                    title: 'Chỉnh sửa khuyến mãi thành công!'
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

    $scope.deleteCoupon = function() {
        $http.delete("/api/coupon/delete?element=" + $scope.deleteElement + "&pageIndex=" + $scope.pageIndex + "&sortCoupon=" + $scope.sortCoupon + "&keyWord=" + $scope.keyWord + "&getCoupon=" + $scope.getCoupon).then(function(response) {
            if (response.data.statusCode == "ok") {
                $scope.listCoupon = response.data.data;
                // $scope.listBook = response.data.listBook;
                $scope.deleteElement = "";
                $('#deleteModal').modal('hide');
                Toast.fire({
                    icon: 'success',
                    title: 'Xoá khuyến mãi thành công!'
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


}