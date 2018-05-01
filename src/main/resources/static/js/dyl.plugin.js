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