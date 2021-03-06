$(function () {
    Dyl.offsetH('#side-menu', '#panel-buoy', 75);
    $('#side-menu').metisMenu();

    var map = new AMap.Map('amap',{
        resizeEnable: true,
        zoom: 6
    });
    AMap.plugin(["AMap.ToolBar", "AMap.Scale"], function() {
        map.addControl(new AMap.ToolBar());
        map.addControl(new AMap.Scale());
    });

    $("#buoy").completer({
        zIndex: 200,
        suggest: true,
        source: ["PG3711981", "PG3711982", "PG3711983", "PG3711984"]
    });
});