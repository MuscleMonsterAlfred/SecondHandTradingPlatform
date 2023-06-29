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
            {field: 'ordernumber', title: '订单号',width:180, align:'center'}
            , {field: 'commname', title: '名称', width: 300, align:'center'}
            , {field: 'commdesc', title: '描述', width: 639, align:'center'}
            , {field: 'kdstatus', title: '发货状态', width: 100, align:'center'}
            , {field: 'thinkmoney', title: '售价', width: 80, align:'center'}
            , {field: 'soldtime', title: '售出时间', width: 160,sort: true, align:'center'}
            , {fixed: 'right', title: '操作', toolbar: '#barDemo', width:100, align:'center'}
        ]]
        , done: function (res, curr, count) {
            $("[data-field='kdstatus']").children().each(function () {
                if($(this).text() == '0') {
                    $(this).text("待发货")
                }else if($(this).text() == '1'){
                    $(this).text("已发货")
                }else if($(this).text() == '2'){
                    $(this).text("已确认收货")
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