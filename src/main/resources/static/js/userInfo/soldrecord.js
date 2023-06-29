layui.use(['form', 'element', 'util', 'carousel', 'laypage', 'layer','table'], function () {
    var table = layui.table;
    table.render({
        elem: '#sold'
        , url: basePath+'/soldrecord/lookuser'
        , page: {
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip']
            , groups: 3
            , limits: [20, 50, 100]
            , limit: 20
        }, cols: [[
            {field: 'ordernumber', title: '订单号',width:180, align:'center'}
            , {field: 'commname', title: '名称', width: 280, align:'center'}
            , {field: 'commdesc', title: '描述', width: 600, align:'center'}
            , {field: 'kdstatus', title: '发货状态', width: 90, align:'center'}
            , {field: 'price', title: '售价', width: 90, align:'center'}
            , {field: 'ordertime', title: '售出时间', width: 160, sort: true, align:'center'}
            , {fixed: 'right', title: '操作', toolbar: '#barDemo', width:160, align:'center'}
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
        }else if(obj.event === 'fahuo'){
            layer.prompt({title: '请请填写快递编号', formType: 3}, function(pass, index){
                layer.closeAll();
                var object = new Object();
                object["ordernumber"] = data.ordernumber;
                object["kdnumber"] = pass;
                var jsonData = JSON.stringify(object);
                $.ajax({
                    url:basePath+'/order/kdnumber',
                    data: jsonData,
                    contentType: "application/json;charset=UTF-8", //发送数据的格式
                    type: "post",
                    dataType: "text", //回调
                    beforeSend: function () {
                        layer.load(1, { //icon支持传入0-2
                            content: '请稍等',
                            success: function (layero) {
                                layero.find('.layui-layer-content').css({
                                    'padding-top': '39px',
                                    'width': '60px'
                                });
                            }
                        });
                    },
                    complete: function () {
                        layer.closeAll('loading');
                    },
                    success: function (data) {
                        layer.msg('操作成功', {
                            time: 1000,
                            icon: 1,
                            offset: '50px'
                        }, function () {
                            location.reload()
                        });
                    },
                    error: function (data) {
                        layer.msg('系统错误', {
                            time: 1000,
                            icon: 2,
                            offset: '50px'
                        });
                    }
                });

            });
        }
    });
});