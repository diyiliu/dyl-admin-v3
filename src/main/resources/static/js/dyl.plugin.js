Dyl = {
    offsetH: function (o1, o2, y) {
        var ph = $(o2).height();
        $(o1).height(ph - y);
    }
};


$(function () {
    var autoHeight =  $(window).height() - (51 + 40 + 40 + 52);
    $("div.auto-height").height(autoHeight);

    var autoHeightHeader =  $(window).height() - (51 + 40 + 52);
    $("div.auto-height-header").height(autoHeightHeader);

    var autoHeightTab=  $(window).height() - (51 + 52 + 40 + 52);
    $("div.auto-height-tab").height(autoHeightTab);
});

function alertTip(type, msg) {
    if ('success' == type){
        toastr.success(msg, '提示', {
            timeOut: 3000,
            "closeButton": true,
            "debug": false,
            "newestOnTop": true,
            "progressBar": true,
            "positionClass": "toast-bottom-right",
            "preventDuplicates": true,
            "onclick": null,
            "showDuration": "300",
            "hideDuration": "1000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut",
            "tapToDismiss": false
        });
        return;
    }

    if ('error' == type){
        toastr.error(msg, '错误', {
            timeOut: 3000,
            "closeButton": true,
            "debug": false,
            "newestOnTop": true,
            "progressBar": true,
            "positionClass": "toast-bottom-right",
            "preventDuplicates": true,
            "onclick": null,
            "showDuration": "300",
            "hideDuration": "1000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut",
            "tapToDismiss": false
        });
        return;
    }
}