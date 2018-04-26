$(function () {
    $("#buoy").completer({
        zIndex: 200,
        suggest: true,
        source: ["PG3711981", "PG3711982", "PG3711983", "PG3711984"]
    });

    var autoHeight =  $(window).height() - (51 + 40 + 40 + 52);
    $("div.auto-height").height(autoHeight);

    Dyl.offsetH('#list-buoy', '#panel-buoy', 75);
});