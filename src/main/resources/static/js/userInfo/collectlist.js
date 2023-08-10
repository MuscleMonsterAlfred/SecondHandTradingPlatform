layui.use(['form', 'element', 'util', 'carousel', 'laypage', 'layer','table'], function () {
    var table = layui.table;
    table.render({
        elem: '#collect'
        , url: basePath+'/user/collect/queryall'
        , page: {
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip']
            , groups: 3
            , limits: [20, 50, 100]
            , limit: 20
        }, cols: [[
            {field: 'qid', title: 'ID',width:60, align:'center'}
            , {field: 'commname', title: 'Name', width: 250, align:'center'}
            , {field: 'commdesc', title: 'Desc', width: 600, align:'center'}
            , {field: 'username', title: 'Publish User', width: 110, align:'center'}
            , {field: 'school', title: 'School', width: 130, align:'center'}
            , {field: 'soldtime', title: 'Collect Time', width: 160, align:'center'}
            , {fixed: 'right', title: 'Opera', toolbar: '#barDemo', width:140, align:'center'}
        ]]
        ,height: 500
        , done: function (res, curr, count) {
            var i=1;
            $("[data-field='qid']").children().each(function () {
                if($(this).text() == 'ID') {
                    $(this).text("ID")
                }else{
                    $(this).text(i++)
                }
            });
        }
    });
    //监听行工具事件
    table.on('tool(test)', function (obj) {
        var data = obj.data;
        if (obj.event === 'xiangqing') {
            window.open(basePath+"/product-detail/"+data.commid)
        }else if (obj.event === 'bianji') {
            layer.open({
                type: 2,
                title: 'Update Product',
                shadeClose: true,
                shade: 0.8,
                maxmin: true,
                area: ['80%', '80%'],
                content: basePath+'/user/editgoods/'+data.commid
            });
        }else if(obj.event === 'quxiaoshoucang'){
            layer.confirm('Confirm cancel？', {
                btn: ['Yes','No'], //按钮
                title:"Cancel",
                offset:"50px"
            }, function(){
                layer.closeAll();
                $.ajax({
                    url: basePath+'/collect/delete/'+data.id,
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
        }else if (obj.event === 'yishou') {
            layer.confirm('Are you sure to set this product as sold？', {
                btn: ['Yes','No'], //按钮
                title:"Sold ",
                offset:"50px"
            }, function(){
                layer.closeAll();
                $.ajax({
                    url: basePath+'/user/changecommstatus/'+data.commid+"/4",
                    data: "",
                    contentType: "application/json;charset=UTF-8", //发送数据的格式
                    type: "get",
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
    });
});