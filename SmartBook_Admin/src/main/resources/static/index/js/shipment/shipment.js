function ctrlShipment ($scope, $http) {
    $scope.sta={s:3}
    $http.get("/api/admin/bill/status").then(function (response) {
        $scope.status = response.data;
    })
    $scope.checkDate = function (index) {
        let today = new Date().setHours(7, 0, 0, 0);
        let chek = new Date($scope.bills[index].createdTime).setHours(7, 0, 0, 0);
        return today - chek;
    }
    $scope.getDate=function(cDate){
        let today = new Date().setHours(7, 0, 0, 0);
        let chek = new Date(cDate).setHours(7, 0, 0, 0);
        return Math.ceil((today - chek)/86400000);
    }
    $scope.formatDate = function (date) {
        let s = date.split("-");
        return s[2] + "/" + s[1] + "/" + s[0];
    }
    $scope.bills = [];
    $http.get("/api/admin/shipment?status="+$scope.sta.s).then(function (response) {
        $scope.bills = response.data;
    })
    $scope.getData = function () {
        $http.get("/api/admin/shipment?status="+$scope.sta.s).then(function (response) {
            $scope.bills = response.data;
        })
    }
    $scope.getDetail = function (index) {
        if ($scope.bills[index].bill == true) {
            $http.get("/api/admin/bill/" + $scope.bills[index].billId).then(function (response) {
                $scope.details = response.data;
                console.log($scope.details)
            })
            $("#detailmodalBill").modal("show");
        } else {
            $http.get("/api/admin/return/" + $scope.bills[index].billId).then(function (response) {
                $scope.details = response.data;
            })
            $("#detailmodal").modal("show");
        }
    }
    $scope.showModal = function (index) {
        $("#modal" + index).modal("hide");
        $("#cancel" + index).modal("show");
    }
    $scope.hideModal = function (index) {
        $("#cancel" + index).modal("hide");
        $("#modal" + index).modal("show");
    }
    $scope.cancel = function (index) {
        let data = {
            id: $scope.bills[index].id,
            message: document.getElementById("message" + index).value,
            status: 2
        }
        $http.put("/api/admin/shipment", data).then(function (response) {
            if (response.data.statusCode == "ok") {
                Toast.fire({
                    icon: 'success',
                    title: "Thành công"
                });
                $scope.getData()
            } else if (response.data.statusCode == "blank") {
                Toast.fire({
                    icon: 'error',
                    title: "Không bỏ trống thông báo"
                });
                $("#modal" + index).modal("show");
            } else if (response.data.statusCode == "amount") {
                Toast.fire({
                    icon: 'warning',
                    title: "Không đủ sách để bù đơn mới"
                });
                $scope.getData()
            } else {
                Toast.fire({
                    icon: 'error',
                    title: "Thất bại"
                });
            }
        })
    }
    $scope.userCancel=function(index){
        let data = {
            id: $scope.bills[index].id,
            message: "",
            status: 5
        }
        $http.put("/api/admin/shipment", data).then(function (response) {
            if (response.data.statusCode == "ok") {
                Toast.fire({
                    icon: 'success',
                    title: "Thành công"
                });
                $scope.getData()
            } else {
                Toast.fire({
                    icon: 'error',
                    title: "Thất bại"
                });
            }
        })
    }
    $scope.submit=function(index){
        let data = {
            id: $scope.bills[index].id,
            message: "",
            status: 4
        }
        $http.put("/api/admin/shipment", data).then(function (response) {
            if (response.data.statusCode == "ok") {
                Toast.fire({
                    icon: 'success',
                    title: "Thành công"
                });
                $scope.getData()
            } else {
                Toast.fire({
                    icon: 'error',
                    title: "Thất bại"
                });
            }
        })
    }
}