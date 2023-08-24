layui.use(['form', 'element', 'util', 'carousel', 'laypage', 'layer','table'], function () {
    var table = layui.table;
    table.render({
        elem: '#sold'
        , url: basePath+'/orderecord/lookuser'
        , page: {
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip']
            , groups: 3
            , limits: [20, 50, 100]
            , limit: 20
        }, cols: [[
            {field: 'ordernumber', title: 'Order no',width:180, align:'center'}
            , {field: 'commname', title: 'Product name', width: 280, align:'center'}
            , {field: 'price', title: 'Price', width: 90, align:'center'}
            , {field: 'username', title: 'Consignee', width: 150, align:'center'}
            , {field: 'mobilephone', title: 'Consignee mobile', width: 150, align:'center'}
            , {field: 'kdstatus', title: 'Shipment status', width: 150, align:'center'}
            , {field: 'kdnumber', title: 'Delivery number', width: 150, align:'center'}
            , {field: 'ordertime', title: 'Sold time', width: 160, sort: true, align:'center'}
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
        }else if(obj.event === 'shouhuo'){
            layer.confirm('Are you sure to receive the goods？', {
                btn: ['Yes','No'], //按钮
                title:"Confirm",
                offset:"50px"
            }, function(){
                layer.closeAll();
                $.ajax({
                    url: basePath+'/soldrecord/change/'+data.ordernumber,
                    data: "",
                    contentType: "application/json;charset=UTF-8", //发送数据的格式
                    type: "put",
                    dataType: "json", //回调
                    beforeSend: function () {
                        layer.load(1, { //icon支持传入0-2
                            content: 'loading...',
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
                        console.log(data)
                        if(data.status===200){
                            layer.msg(data.message, {
                                time: 1000,
                                icon: 1,
                                offset: '50px'
                            }, function () {
                                location.reload();
                            });
                        }else {
                            layer.msg(data.message, {
                                time: 1000,
                                icon: 2,
                                offset: '50px'
                            });
                        }
                    }
                });
            }, function(){
            });
        }
        else if(obj.event === 'toPay'){
            window.open(basePath+"/shop/preorder/"+data.ordernumber)
        }
    });
});