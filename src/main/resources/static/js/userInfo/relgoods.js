var app = new Vue({
    el: '#goodsform',
    data() {
        return {
            listimages: [],
            mainimg: "",
            videourl: ""
        }
    },
    mounted: function () {
        //将vue中的函数设置成全局的
        window.showlistimages = this.showlistimages;
        window.setmainimgurl = this.setmainimgurl;
        window.setvideourl = this.setvideourl;

        window.getlistimages = this.getlistimages;
        window.getmainimgurl = this.getmainimgurl;
        window.getvideourl = this.getvideourl;
    },
    methods: {
        showlistimages: function (imgurl) {
            var that = this;
            if (that.listimages.length !== 3) {
                var object = new Object();
                object["imgsrc"] = imgurl;
                that.listimages.push(object);//向vue数组中添加图片
            }
        },getlistimages: function () {
            var that = this;
            return that.listimages;
        },setmainimgurl: function (imgurl) {
            var that = this;
            that.mainimg = imgurl;
        },getmainimgurl: function () {
            var that = this;
            return that.mainimg;
        },setvideourl: function (videosrc) {
            var that = this;
            that.videourl = videosrc;
        },getvideourl: function () {
            var that = this;
            return that.videourl;
        },delimage: function (ids) {
            var that = this;
            that.listimages.splice(ids, 1);//从vue数组中删除此图
        },mouseOver: function (id) {
            $("#del" + id).show();
        }, mouseLeave: function (id) {
            $("#del" + id).hide();
        },openimg: function (imgsrc) {
            var img = '<img src="' + imgsrc + '" style="width:100%;height:100%">';
            layer.open({
                type: 1,
                title: false,
                shade: 0.6,
                closeBtn: 0, //不显示关闭按钮
                anim: 2,
                shadeClose: true, //开启遮罩关闭
                content: img
            });
        }
    }
});

let player = null;
layui.use(['form', 'upload', 'element'], function () {
    var upload = layui.upload;
    var form=layui.form;
    upload.render({
        elem: '#test3'
        , url: basePath + '/relgoods/images'
        , accept: 'images' //图片
        , size: 1024 * 20
        , exts: 'png|jpg'
        , multiple: true
        , number: 3
        , choose: function (obj) {
            //上传前判断已经上传了多少张图片
            var imgs = document.getElementById("otherimages").getElementsByTagName("img");
            if (imgs.length === 6) {
                layer.msg('Upload up to three images');
                layer.close(obj);//报错让其停止上传
            }
        }
        , progress: function (n) {
            var percent = n + '%';
            layer.msg(percent, {
                icon: 16
                , shade: 0.01
            });
        }
        , done: function (res) {
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('Error');
            } else {
                layer.closeAll('loading');
                layer.msg('Success', {
                    time: 1000,
                    icon: 1,
                    offset: '150px'
                });
                showlistimages(res.data.src[0]);
                $("#otherimages").show();
            }
        }
        , error: function () {
            layer.closeAll('loading');
            layer.msg('Error', {
                time: 1000,
                icon: 2,
                offset: '150px'
            });
        }
    });
    upload.render({
        elem: '#test2'
        , url: basePath + '/relgoods/video'
        , accept: 'images' //图片
        , size: 1024 * 20
        , exts: 'png|jpg'
        , progress: function (n) {
            var percent = n + '%'; //获取进度百分比
            layer.msg(percent, {
                icon: 16
                , shade: 0.01
            });
        }
        , done: function (res) {
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('Error');
            } else {
                layer.closeAll('loading');
                layer.msg('Success', {
                    time: 1000,
                    icon: 1,
                    offset: '150px'
                });
                setmainimgurl(res.data.src);
                $("#mainimage").show();
            }
        }
        , error: function () {
            layer.closeAll('loading');
            layer.msg('Error', {
                time: 1000,
                icon: 2,
                offset: '150px'
            });
        }
    });
    upload.render({
        elem: '#test1'
        , url: basePath + '/relgoods/video'
        , accept: 'video' //视频
        , size: 1024 * 400
        , exts: 'mp4'
        , progress: function (n) {
            var percent = n + '%'; //获取进度百分比
            layer.msg(percent, {
                icon: 16
                , shade: 0.01
            });
        }
        , done: function (res) {
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('Error');
            } else {
                layer.closeAll('loading');
                layer.msg('Success', {
                    time: 1000,
                    icon: 1,
                    offset: '150px'
                });
                $("#videosamplex").show();
                var videoObject = {
                    container: '.videosamplex',
                    variable: 'player',
                    mobileCkControls:true,
                    mobileAutoFull:false,
                    h5container:'#videoplayer',
                    loop:true,
                    volume:0.5,
                    video:basePath + res.data.src
                };
                var player=new ckplayer(videoObject);
                setvideourl(res.data.src);
            }
        }
        , error: function () {
            layer.closeAll('loading');
            layer.msg('Error', {
                time: 1000,
                icon: 2,
                offset: '150px'
            });
        }
    });

    form.on('submit(demo1)', function (data) {
        var vuevideo=getvideourl();
        var vuemainimg=getmainimgurl();
        var vuelistimages=getlistimages();
        if(vuemainimg.length===0){
            layer.msg('Upload Images', {
                time: 1000,
                icon: 2,
                offset: '150px'
            });
            return false;
        }
        if(vuelistimages.length===0){
            layer.msg('Upload Other Images', {
                time: 1000,
                icon: 2,
                offset: '150px'
            });
            return false;
        }
        $("#demo1").addClass("layui-btn-disabled");
        $("#demo1").attr("disabled", true);
        var rellistimgs=new Array();
        for (var i=0;i<vuelistimages.length;i++){
            rellistimgs[i]=vuelistimages[i].imgsrc;
        }
        if(data.field.commname.length>200){
            layer.msg('Product name too long', {
                time: 1000,
                icon: 2,
                offset: '150px'
            });
            return false;
        }
        var object = new Object();
        object["commname"] = data.field.commname;
        object["commdesc"] = data.field.commdesc;
        object["videourl"] = vuevideo;
        object["orimoney"] = data.field.orimoney;
        object["thinkmoney"] = data.field.thinkmoney;
        object["category"] = data.field.category;
        object["common"] = data.field.common;
        object["common2"] = data.field.common2;
        object["image"] = vuemainimg;
        object["otherimg"] = rellistimgs;
        object["goodposition"] = data.field.goodPosition;
        var jsonData = JSON.stringify(object);
        $.ajax({
            url: basePath + "/product/add",
            data: jsonData,
            contentType: "application/json;charset=UTF-8",
            type: "post",
            dataType: "json",
            beforeSend: function () {
                layer.load(1, {
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
                layer.msg("Successfully published, please wait for review", {
                    time: 1000,
                    icon: 1,
                    offset: '100px'
                }, function () {
                    //location.href=basePath+"/";
                    window.location.reload();
                });
            },error:function () {
                layer.msg('error', {
                    time: 1000,
                    icon: 2,
                    offset: '150px'
                });
            }
        });
        return false;
    });

    function activeMap(position, map) {
        var pos = {
            lat: position.coords.latitude,
            lng: position.coords.longitude,
            name: position.coords.name
        };

        // 在地图上添加标记
        var marker = new google.maps.Marker({
            position: pos,
            map: map
        });

        // 将位置信息显示在信息窗口中
        var infowindow = new google.maps.InfoWindow({
            content: 'Current location: ' + pos.name
        });

        marker.addListener('click', function () {
            infowindow.open(map, marker);
        });

        // 将地图中心移动到当前位置
        map.setCenter(pos);

        $("#goodPosition").val(JSON.stringify(pos));
    }

    function initMap() {
        // 创建地图
        var map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: -34.397, lng: 150.644}, // 设置地图中心坐标
            zoom: 8 // 设置地图缩放级别
        });

        // 获取当前位置
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                activeMap(position, map);
            }, function() {
                handleLocationError(true, infoWindow, map.getCenter());
            });
        } else {
            // 浏览器不支持地理定位
            handleLocationError(false, infoWindow, map.getCenter());
        }
        var input = document.getElementById('address-input');
        var autocomplete = new google.maps.places.Autocomplete(input);

        autocomplete.addListener('place_changed', function() {
            var place = autocomplete.getPlace();

            if (!place.geometry) {
                window.alert("Invalid address, please reselect");
                return;
            }

            var latitude = place.geometry.location.lat();
            var longitude = place.geometry.location.lng();
            var sPos={coords:{latitude:latitude,longitude:longitude,name:place.name}};
            activeMap(sPos, map);
        });

    }

    function handleLocationError(browserHasGeolocation, infoWindow, pos) {
        infoWindow.setPosition(pos);
        infoWindow.setContent(browserHasGeolocation ?
            'Error：Unable to obtain current location。' :
            'Error：Your browser does not support geolocation。');
        infoWindow.open(map);
    }

    initMap();
});