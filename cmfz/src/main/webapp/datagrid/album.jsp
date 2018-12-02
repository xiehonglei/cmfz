<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>章节管理</title>
    <script type="text/javascript">

        var toolbar = [{
            iconCls: 'icon-search',
            text: "专辑详情",
            handler: function () {
                /*展示专辑相关的信息*/

                /*把专辑信息填充到dialog*/
                var row = $("#dgalbum").treegrid("getSelected");
                if (row == null) {
                    $.messager.alert("提示框", "请先选中专辑", "info");
                } else {
                    if (row.score != null) {

                        $("#album_ff").form("load", row);
                        $("#album_img").prop("src", "${pageContext.request.contextPath}" + row.coverimg);
                        $('#album').dialog("open");
                    } else {
                        $.messager.alert("提示框", "请先选中专辑", "info");
                    }
                }
            }
        }, '-', {
            iconCls: 'icon-add',
            text: "添加专辑",
            handler: function () {
                $("#addalbum").dialog("open")
            }
        }, '-', {
            text: "添加章节",
            iconCls: 'icon-add',
            handler: function () {
                var row = $("#dgalbum").treegrid("getSelected");
                if (row == null) {
                    $.messager.alert("提示框", "请先选中专辑", "info");
                } else {
                    if (row.score != null) {
                        $("#addchapter").dialog("open");
                        $("#p_id").val(row.id);
                    } else {
                        $.messager.alert("提示框", "请先选中专辑", "info");
                    }
                }

            }
        }, '-', {
            text: "下载音频",
            iconCls: 'icon-download',
            handler: function () {
                var row = $("#dgalbum").treegrid("getSelected");
                alert(row.title)
                if (row != null) {
                    if (row.size != null) {
                        location.href = "${pageContext.request.contextPath}/downloadCapter?url=" + row.downPath + "&title=" + row.title

                    }
                }
            }
        }];

        $(function () {

            $(':file').change(function (event) {
                //  HTML5 js 对象的获取
                var files = event.target.files, file;
                if (files && files.length > 0) {
                    // 获取目前上传的文件
                    file = files[0];
                    // 文件的限定类型什么的道理是一样的
                    if (file.size > 1024 * 1024 * 10) {
                        alert('图片大小不能超过 2MB!');
                        return false;
                    }
                    // file对象生成可用的图片
                    var URL = window.URL || window.webkitURL;
                    // 通过 file 生成目标 url
                    var imgURL = URL.createObjectURL(file);

                    $("img").attr('src', imgURL);
                }
            });

            $("#dgalbum").treegrid({
                url: '${pageContext.request.contextPath}/getAllAlbum',
                idField: 'id',
                treeField: 'title',
                toolbar: toolbar,
                columns: [[
                    {title: '名字', field: 'title', width: 180},
                    {field: 'downPath', title: '下载路径', width: 60, align: 'right'},
                    {field: 'size', title: '章节大小', width: 80},
                    {field: 'duration', title: '章节时长', width: 80}
                ]],
                fit: true,
                fitColumns: true,
                pagination: true,
                pageSize: 3,
                pageList: [3, 6, 9],

                onDblClickRow: function (row) {
                    $("#audio").dialog("open");
                    $("#audio_id").prop("src", "${pageContext.request.contextPath}" + row.downPath);
                },
            });
            $('#album').dialog({
                title: '专辑详情',
                width: 400,
                height: 400,
                closed: true,
            });
            $('#addalbum').dialog({
                title: '添加专辑',
                width: 400,
                height: 400,
                closed: true,
            });

            $("#addchapter").dialog({
                title: '添加章节',
                width: 400,
                height: 400,
                closed: true,
                buttons: [{
                    text: '保存',
                    handler: function () {
                        addChapter();
                    }
                }, {
                    text: '关闭',
                    handler: function () {
                        $("#addchapter").dialog("close")
                    }
                }],
            });

            $("#audio").dialog({
                title: '播放',
                width: 400,
                height: 200,
                closed: true,
            });

            if ($("#audio").parent().is(":hidden")) {
                $("#audio_id").prop("src", " ");
            }

        });

        function addAlbum() {
            $("#addalbumForm").form("submit", {
                url: "${pageContext.request.contextPath}/addAlbum",
                success: function (data) {
                    if (data == "true") {
                        $.messager.alert("提示框", "添加成功", "message");
                        $("#addalbum").dialog("close");
                        $("#dgalbum").treegrid("reload");
                    } else {
                        $.messager.alert("提示框", "添加失败", "warning");
                    }
                }
            });
        }

        function addChapter() {
            $('#addchapterForm').form('submit', {
                url: "${pageContext.request.contextPath}/addChapter",
                success: function (data) {
                    if (data == "true") {
                        $.messager.alert("提示框", "添加成功", "message");
                        $("#addchapter").dialog("close");
                        $("#dgalbum").treegrid("reload");
                    } else {
                        $.messager.alert("提示框", "添加失败", "warning");
                    }
                }
            })
        }
    </script>
</head>
<table id="dgalbum"></table>
<div id="album">

    <form id="album_ff" method="post">
        <div>
            标题<input id="name" class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            作者<input class="easyui-validatebox" type="text" name="author" data-options="required:true"/>
        </div>
        <div>
            简介<input class="easyui-validatebox" type="text" name="brief" data-options="required:true"/>
        </div>
        <div>
            <img src="" id="album_img">
        </div>
    </form>
</div>
<div id="addalbum">
    <form id="addalbumForm" method="post" enctype="multipart/form-data">
        标题<input name="title" type="text"/><br/>
        集数<input name="count" type="text"/><br/>
        分数<input name="score" type="text"/><br/>
        作者<input name="author" type="text"/><br/>
        播音员<input name="broadCast" type="text"/><br/>
        简介<input name="brief" type="text"/><br/>
        背景图片
        <div class="lf salebd"><a href="#"><img src="images/<>.jpg" width="100" height="100"/></a></div>
        <input name="myjar" type="file"/><br/>
        <input name="" type="button" value="保 存" onclick="addAlbum()"/>
    </form>
</div>
<div id="addchapter">
    <form id="addchapterForm" method="post" enctype="multipart/form-data">
        <div>
            标题:<input class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            请选择音频文件:<input type="file" name="chapter" style="width:300px">
        </div>
        <div>
            <input type="hidden" name="album_id" id="p_id" value="" style="width:300px">
        </div>
    </form>
</div>

<div id="audio">
    <audio id="audio_id" src="" autoplay="autoplay" controls="controls" loop="loop"></audio>
</div>
</html>
