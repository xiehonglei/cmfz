<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户管理</title>
    <script type="text/javascript">
        var toolbar = [{
            iconCls: 'icon-add',
            text: "导入数据",
            handler: function () {
                $("#importUser").dialog("open");
            }
        }, '-', {
            iconCls: 'icon-add',
            text: "导出数据",
            handler: function () {
                location.href = "${pageContext.request.contextPath}/emportUser"
            }
        }, '-', {
            text: "选择导出",
            iconCls: 'icon-add',
            handler: function () {

            }
        }];

        $(function () {
            $("#dguser").datagrid({
                toolbar: toolbar,
                url: "${pageContext.request.contextPath}/getAllUser",
                columns: [[
                    {title: '名字', field: 'username', width: 180},
                    {title: '电话', field: 'phoneNum', width: 180},
                    {title: '法号', field: 'dharmalName', width: 180},
                    {title: '省份', field: 'province', width: 180},
                    {title: '性别', field: 'sex', width: 180},
                    {title: '个性签名', field: 'sign', width: 180},
                ]],
                fit: true,
                fitColumns: true,
                pagination: true,
            });
            $("#importUser").dialog({
                title: '导入',
                width: 400,
                height: 200,
                closed: true,
            })
        });

        function add() {
            $("#userForm").form("submit", {
                url: "${pageContext.request.contextPath}/importUser",
                success: function (data) {
                    if (data == "true") {
                        $.messager.alert("提示框", "导入成功", "message");
                        $("#importUser").dialog("close");
                        $("#dguser").datagrid("reload");
                    } else {
                        $.messager.alert("提示框", "导入失败", "warning");
                    }
                }
            })
        }
    </script>
</head>
<table id="dguser"></table>
<div id="importUser">
    <form id="userForm" method="post" enctype="multipart/form-data">
        请选择文件<input id="userfile" type="file" name="userfile">
        <input type="button" value="导入" onclick="add()"/>
    </form>
</div>
</html>
