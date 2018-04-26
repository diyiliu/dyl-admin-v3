$(function () {
    var autoHeight =  $(window).height() - (51 + 52 + 40 + 52);
    $("div.auto-height").height(autoHeight);

    var rowHeight = $('#buoy-search').height();
    Dyl.offsetH('#amap', '#panel-trace', 40 + rowHeight + 50);

    laydate.render({
        elem: '#gpsTime'
        ,type: 'datetime'
        ,range: true
        ,eventElem: '#btnTime'
        ,trigger: 'click'
    });


    $("#buoy").completer({
        zIndex: 200,
        suggest: true,
        source: ["PG3711981", "PG3711982", "PG3711983", "PG3711984"]
    });


});