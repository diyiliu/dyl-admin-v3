$(function () {
    var autoHeightHeader = $("div.auto-height-header").height();
    var tableHeight = autoHeightHeader - (40 + 35);

    var $table = $('#menu-table');
    $table.bootstrapTable({
        height: tableHeight,
        sidePagination: 'server',
        url: './assets/data/menu.json',
        toolbar: '#toolbar',
        // 条纹
        striped: true,
        idField: 'id',
        columns: [
            {
                field: 'id',
                checkbox: true
            },
            {
                field: 'name',
                title: '菜单名称'
            },
            {
                field: 'type',
                title: '类型',
                sortable: true,
                align: 'center',
                formatter: 'typeFormatter'
            },
            {
                field: 'controller',
                title: '控制器'
            },
            {
                field: 'view',
                title: '视图'
            },
            {
                field: 'isMenu',
                title: '导航菜单'
            },
            {
                field: 'sort',
                title: '排序'
            },
            {
                field: 'iconCss',
                title: '图标'
            }
        ],
        treeShowField: 'name',
        parentIdField: 'pid',
        onLoadSuccess: function() {
            $table.treegrid({
                treeColumn: 1,
                onChange: function() {
                    $table.bootstrapTable('resetWidth');
                }
            });
        }
    });
});

// 格式化类型
function typeFormatter(value, row, index) {
    if (value === 'menu') {
        return '菜单';
    }
    if (value === 'button') {
        return '按钮';
    }
    if (value === 'node') {
        return '节点';
    }
    return '-';
}

// 格式化状态
function statusFormatter(value, row, index) {
    if (value === 1) {
        return '<span class="label label-success">正常</span>';
    } else {
        return '<span class="label label-default">锁定</span>';
    }
}