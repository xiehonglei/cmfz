<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>

<script type="text/javascript">
    var toolbar = [{
        iconCls: 'icon-add',
        text: "添加",
        handler: function () {
            $("#addPicture").dialog("open");
        }
    }, '-', {
        iconCls: 'icon-delete',
        text: "删除",
        handler: function () {
            var data = $("#dg").edatagrid("getSelected");
            if (data == null) {
                $.messager.alert("提示框", "请选中要删除的数据", "warning");
            } else {
                $.messager.confirm("确认框", "确认真的要删除选中的内容吗？", function (result) {
                    var id = data.id;
                    //2发送ajax请求到后台
                    $.ajax({
                        url: "${pageContext.request.contextPath}/deletePicture",
                        //用这中格式传递数据，jquery会自动将数据深度序列化
                        data: {"id": id},
                        //不让jquery做深度序列化
                        traditional: true,
                        success: function (data) {
                            if (data) {
                                $.messager.alert("提示框", "删除成功", "message");
                                $("#dg").edatagrid("reload");
                            } else {
                                $.messager.alert("提示框", "删除失败", "warning");
                            }
                        }
                    });
                    //ajax-----end
                });
            }
        }
    }, '-', {
        text: "修改",
        iconCls: 'icon-edit',
        handler: function () {
            /*获取选中行*/
            var row = $("#dg").edatagrid("getSelected")
            if (row == null) {
                $.messager.show({
                    title: '警告',
                    msg: '请选中修改行。',
                    showType: 'show',
                    style: {
                        right: '',
                        top: document.body.scrollTop + document.documentElement.scrollTop,
                        bottom: ''
                    }
                });
            } else {
                /*将当前行变成可编辑模式*/
                var index = $("#dg").edatagrid("getRowIndex", row);
                $("#dg").edatagrid("editRow", index);
            }

        }
    }, '-', {
        text: "保存",
        iconCls: 'icon-',
        handler: function () {
            $("#dg").edatagrid("saveRow");
        }
    }];

    /*构建数据表格*/
    $(function () {
        $('#dg').edatagrid({
            toolbar: toolbar,
            url: '${pageContext.request.contextPath}/getAllPicture',
            columns: [[
                {field: 'title', title: '名字', width: 100},
                {
                    field: 'status', title: '状态', width: 100, editor: {
                        type: "text",
                        options: {
                            required: true
                        }
                    }
                },
                {field: 'desc', title: '描述', width: 100, align: 'right'}
            ]],
            fit: true,
            fitColumns: true,
            pagination: true,
            pageSize: 3,
            pageList: [3, 6, 9],
            view: detailview,
            detailFormatter: function (rowIndex, rowData) {
                return '<table><tr>' +
                    '<td rowspan=2 style="border:0"><img src="${pageContext.request.contextPath}' + rowData.imagePath + '" style="height:50px;"></td>' +
                    '<td style="border:0">' +
                    '<p>时间: ' + rowData.date + '</p>' +
                    '<p>:描述:' + rowData.desc + '</p>' +
                    '</td>' +
                    '</tr></table>';
            }

        });
        $("#addPicture").dialog({
            title: "添加",
            height: 400,
            width: 300,
            resizable: true,
            closed: true,
            modal: true,
        });

        $(':file').change(function (event) {
            //  HTML5 js 对象的获取
            var files = event.target.files, file;
            if (files && files.length > 0) {
                // 获取目前上传的文件
                file = files[0];
                // 文件的限定类型什么的道理是一样的
                if (file.size > 1024 * 1024 * 2) {
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
    })

    function add() {
        $("#addForm").form("submit", {
            url: "${pageContext.request.contextPath}/addPicture",
            success: function (data) {
                if (data == "true") {
                    $.messager.alert("提示框", "添加成功", "message");
                    $("#addPicture").dialog("close");
                    $('#dg').edatagrid("reload");
                } else {
                    $.messager.alert("提示框", "添加失败", "warning");
                }
            }
        });
    }
</script>
<table id="dg"></table>
<div id="addPicture">
    <form id="addForm" style="margin:30px" method="post" enctype="multipart/form-data">
        标题 <input type="text" value="" name="title"/></table><br/>
        轮播图片
        <div class="lf salebd"><a href="#"><img src="images/<>.jpg" width="100" height="100"/></a></div>
        <input name="myjar" type="file"/><br/>
        描述<input type="text" value="" name="desc"/><br/>
        <input name="" type="button" value="保 存" onclick="add()"/>
    </form>
</div>