$(function () {
    var autoHeight =  $(window).height() - (51 + 52 + 40 + 52);
    $("div.auto-height").height(autoHeight);

    var rowHeight = $('#buoy-search').height();
    var tableHeight = autoHeight - (40 + rowHeight + 35);

    $('#buoy-table').bootstrapTable({
        height: tableHeight,
        locale: 'zh-CN',
        url: './assets/data/buoy.json',
        method: 'post',
        contentType: "application/x-www-form-urlencoded",
        pagination: true,
        paginationLoop: false,
        pageNumber: 1,
        pageSize: 10,
        pageList: [],
        queryParamsType: '',
        queryParams: function queryParams() {
            var param = {};
            return param;
        },
        columns: [
            {field: 'id', checkbox: true},
            {field: 'name', title: '浮标名称', sortable: true},
            {field: 'number', title: '浮标编号', sortable: true},
            {field: 'gpsLocation', title: '定位状态', sortable: true},
            {field: 'gpsLng', title: '经度', sortable: true},
            {field: 'gpsLat', title: '纬度', sortable: true},
            {field: 'temp', title: '海水温度', sortable: true},
            {field: 'voltage', title: '电压', sortable: true},
            {field: 'gpsTime', title: '采集时间', sortable: true}
        ]
    });

    laydate.render({
        elem: '#gpsTime'
        ,type: 'datetime'
        ,range: true
        ,eventElem: '#btnTime'
        ,trigger: 'click'
    });
});