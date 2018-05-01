$(function () {
    var rowHeight = $('#buoy-search').height();
    Dyl.offsetH('#amap', '#panel-trace', 40 + rowHeight + 50);

    var map = new AMap.Map('amap',{
        resizeEnable: true,
        zoom: 6
    });
    AMap.plugin(["AMap.ToolBar", "AMap.Scale"], function() {
        map.addControl(new AMap.ToolBar());
        map.addControl(new AMap.Scale());
    });

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