layui.use(['form', 'element', 'util', 'carousel', 'laypage', 'layer','table'], function () {
    var table = layui.table;
    table.render({
        elem: '#adminsold'
        , url: basePath+'/soldrecord/queryall'
        , page: {
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip']
            , groups: 3
            , limits: [20, 50, 100]
            , limit: 20
        }, cols: [[
            {field: 'ordernumber', title: 'Order No',width:180, align:'center'}
            , {field: 'commname', title: 'Name', width: 300, align:'center'}
            , {field: 'commdesc', title: 'Desc', width: 639, align:'center'}
            , {field: 'kdstatus', title: 'Status', width: 150, align:'center'}
            , {field: 'thinkmoney', title: 'Price', width: 90, align:'center'}
            , {field: 'soldtime', title: 'Date', width: 160,sort: true, align:'center'}
            , {fixed: 'right', title: 'Opera', toolbar: '#barDemo', width:200, align:'center'}
        ]]
        , done: function (res, curr, count) {
            $("[data-field='kdstatus']").children().each(function () {
                if($(this).text() == '0') {
                    $(this).text("To be shipped")
                }else if($(this).text() == '1'){
                    $(this).text("Shipped")
                }else if($(this).text() == '2'){
                    $(this).text("Confirm receipt")
                }
            });
        }
        ,height: 500
    });
    //监听行工具事件
    table.on('tool(test)', function (obj) {
        var data = obj.data;
        if (obj.event === 'xiangqing') {
            window.open(basePath+"/product-detail/"+data.commid)
        }
    });
});