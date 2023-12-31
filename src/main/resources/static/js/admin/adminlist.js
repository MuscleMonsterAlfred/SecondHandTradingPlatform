layui.use(['form', 'element', 'util', 'carousel', 'laypage', 'layer','table'], function () {
    var table = layui.table;
    table.render({
        elem: '#adminlist'
        , url: basePath+'/admin/userlist' + '/2' + '/1'
        , page: {
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip']
            , groups: 3
            , limits: [20, 50, 100]
            , limit: 20
        }, cols: [[
            {field: 'uimage', title: 'Avatar', templet: '<div><img src="{{d.uimage}}" class="layui-nav-img"></div>',width:170}
            , {field: 'username', title: 'Nick name', width: 120, align:'center'}
            , {field: 'mobilephone', title: 'Mobile', width: 160, align:'center'}
            , {field: 'email', title: 'Email', width: 200, align:'center'}
            , {field: 'sex', title: 'Sex', width: 80, align:'center'}
            , {field: 'school', title: 'University', width: 200, align:'center'}
            , {field: 'faculty', title: 'College', width: 160, align:'center'}
        ]], done: function (res, curr, count) {

        }
        ,height: 500
    });
    //监听行工具事件
    table.on('tool(test)', function (obj) {
        var data = obj.data;
        if (obj.event === 'gerenzhuye') {
            //window.open(basePath+"/product-detail/"+data.commid)
        }else if(obj.event === 'setuser'){
            layer.confirm('确认设置为成员吗？', {
                btn: ['确定','算了'], //按钮
                title:"用户封号",
                offset:"50px"
            }, function(){
                layer.closeAll();
                $.ajax({
                    url: basePath+'/admin/set/administrator/'+data.userid+"/1",
                    data: "",
                    contentType: "application/json;charset=UTF-8", //发送数据的格式
                    type: "put",
                    dataType: "json", //回调
                    beforeSend: function () {
                        layer.load(1, { //icon支持传入0-2
                            content: '请稍等...',
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