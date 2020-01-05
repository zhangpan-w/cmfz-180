<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<style>
    h3{
        margin-top: -20px;
        margin-bottom: 10px;
    }
</style>

<%-- jqGrid核心js --%>
<script src="${path}/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
<%-- jqGrid --%>
<link rel="stylesheet" href="${path}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
<%-- 国际化 --%>
<script src="${path}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
<%-- fileupload --%>
<script src="${path}/boot/js/ajaxfileupload.js"></script>

    <script type="text/javascript">

        $(function() {
            $("#tab").jqGrid({
                url:"${path}/banner/query",
                datatype:"json",
                editurl:"${path}/banner/operation",
                //设置表单适应父容器
                autowidth:true,
                height:300,
                /*设置引入样式必须设置的属性*/
                styleUI:"Bootstrap",
                /*分页显示的位置*/
                pager:"#pager",
                /*显示总条数这只的每页展示的数量*/
                rowNum:3,
                rowList:[3,5,10],
                viewrecords:true,
                /*设置表单名称*/
                /*caption:"轮播图列表",*/
                /*定义多选框并获取值*/
                multiselect : true,
                /*表头*/
                colNames:["id","title","create_date","status","img"],
                /*表中数据*/
                colModel:[
                    {name:"id",align:"center"},
                    {name:"title",editable:true,align:"center"},
                    {name:"create_date",editable:true,align:"center",edittype:"date"},
                    {name:"status",editable:true,align:"center",edittype:"select",editoptions:{
                        value:"展示:展示;不展示:不展示"}
                    },
                    {name:"img",editable:true,align:"center",edittype:"file",
                    formatter:function(cellvalue, options, rowObject){
                        return "<img style='width:100%;height:100px' src='${path}/img/"+cellvalue+"'/>";
                    }}
                ]
            }).jqGrid("navGrid","#pager",
                {search:false,addtext:"添加",edittext:"修改",deltext:"删除"},
                {
                    /*
                    * 修改
                    * */
                    closeAfterEdit:true,
                    afterSubmit:function(response){
                        var id = response.responseJSON.bid;
                        $.ajaxFileUpload({
                            url:"${path}/banner/upload",
                            fileElementId:"img",
                            data:{bannerId:id},
                            type:"post",
                            success:function (data) {
                                $("#tab").trigger("reloadGrid");
                                $("#msgupd").show();
                                setTimeout(function () {
                                    $("#msgupd").hide();
                                },3000);
                            }
                        });
                        return response;
                    }
                },{
                    /*
                    * 增加
                    * */
                    closeAfterAdd:true,recreateForm:true,
                    afterSubmit:function(response){
                        var id = response.responseJSON.bannerId;
                        if (id != null) {
                            $.ajaxFileUpload({
                                url:"${path}/banner/upload",
                                fileElementId:"img",
                                data:{bannerId:id},
                                type:"post",
                                success:function (data) {
                                    $("#tab").trigger("reloadGrid");
                                    $("#msgadd").show();
                                    setTimeout(function () {
                                        $("#msgadd").hide();
                                    },3000);
                                }
                            });
                        }
                        return response;
                    }
                },{
                    /*
                    * 删除
                    * */
                });


            $("#outPoiBanner").click(function () {
                $.ajax({
                    url:"${pageContext.request.contextPath}/banner/outPoi",
                    success:function (data) {
                        alert("导出成功！")
                    }
                })
            })

            $("#inPoiBanner").click(function () {
                $.ajax({
                    url:"${pageContext.request.contextPath}/banner/inPoi",
                    success:function (data) {
                        alert("导出成功！")
                    }
                })
            })
        });

    </script>

<!-- Top content -->
<div class="content">
    <div class="page-header">
        <h3>轮播图列表</h3>
    </div>
    <ul class="nav nav-tabs" role="tablist">
        <li class="active">
            <a href="#home" aria-controls="home" role="tab" data-toggle="tab">
                轮播图列表
            </a>
        </li>
        <%--<li>
            <a data-toggle="tab">
                <div class="btn" id="inPoiBanner">
                    <label class="control-label">导入</label>
                </div>
            </a>
        </li>--%>
        <li>
            <a data-toggle="tab">
                <div class="btn" id="outPoiBanner">
                    <label class="control-label">导出</label>
                </div>
            </a>
        </li>
    </ul>
    <div class="tab-content">
    <table id="tab" class="table table-bordered table-striped" style="..."></table>
    </div>
    <div id="pager" style="height: 45px"></div>
    <div class="alert alert-info" style="display:none" id="msgadd">添加成功</div>
    <div class="alert alert-info" style="display:none" id="msgupd">修改成功</div>

</div>