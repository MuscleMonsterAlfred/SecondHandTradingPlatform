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
            {field: 'ordernumber', title: 'Order No',width:180, align:'center'}
            , {field: 'commname', title: 'Name', width: 280, align:'center'}
            , {field: 'commdesc', title: 'Desc', width: 600, align:'center'}
            , {field: 'kdstatus', title: 'Status', width: 150, align:'center'}
            , {field: 'price', title: 'Price', width: 90, align:'center'}
            , {field: 'ordertime', title: 'Date', width: 160, sort: true, align:'center'}
            , {fixed: 'right', title: 'Opera', toolbar: '#barDemo', width:260, align:'center'}
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
        }else if(obj.event === 'fahuo'){
            layer.prompt({title: 'Please fill in the courier number', formType: 3}, function(pass, index){
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
                        layer.msg('success', {
                            time: 1000,
                            icon: 1,
                            offset: '50px'
                        }, function () {
                            location.reload()
                        });
                    },
                    error: function (data) {
                        layer.msg('error', {
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