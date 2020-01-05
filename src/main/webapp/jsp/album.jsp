<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<style>
    .ui-jqgrid .ui-jqgrid-view input, .ui-jqgrid .ui-jqgrid-view select, .ui-jqgrid .ui-jqgrid-view textarea, .ui-jqgrid .ui-jqgrid-view button {
        margin-left: 100px;
        margin-top: 10px;
    }
     h3{
         margin-top: -20px;
         margin-bottom: 10px;
     }
    .modal-body {
        position: relative;
        padding: 20px 130px;
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
                url:"${path}/album/query",
                datatype:"json",
                editurl:"${path}/album/operation",
                //设置表单适应父容器
                autowidth:true,
                /*设置高度*/
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
                /*caption:"专辑列表",*/
                /*定义多选框并获取值*/
                multiselect : true,
                /*表头*/
                colNames:["id","title","score","author","broadcaster","counts","brief","create_date","status","img"],
                /*表中数据*/
                colModel:[
                    {name:"id",align:"center"},
                    {name:"title",editable:true,align:"center"},
                    {name:"score",editable:true,align:"center"},
                    {name:"author",editable:true,align:"center"},
                    {name:"broadcaster",editable:true,align:"center"},
                    {name:"counts",align:"center"},
                    {name:"brief",editable:true,align:"center"},
                    {name:"create_date",editable:true,align:"center",edittype:"date"},
                    {name:"status",editable:true,align:"center",edittype:"select",editoptions:{
                        value:"展示:展示;不展示:不展示"}
                    },
                    {name:"img",editable:true,align:"center",edittype:"file",
                    formatter:function(cellvalue, options, rowObject){
                        return "<img style='width:100%;height:40px' src='${path}/img/"+cellvalue+"'/>";
                    }}
                ],
                /*开启子表格*/
                subGrid:true,
                subGridRowExpanded:function (subGridId,albumId) {
                    /*添加子表格的方法*/
                    addSubGrid(subGridId,albumId);
                }
            }).jqGrid("navGrid","#pager",
                {search:false,addtext:"添加",edittext:"修改",deltext:"删除"},
                {
                    /*
                    * 修改
                    * */
                    closeAfterEdit:true,
                    afterSubmit:function(response){
                        var id = response.responseJSON.aid;
                            $.ajaxFileUpload({
                                url:"${path}/album/upload",
                                fileElementId:"img",
                                data:{albumId:id},
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
                        var id = response.responseJSON.albumId;
                        if (id != null) {
                            $.ajaxFileUpload({
                                url:"${path}/album/upload",
                                fileElementId:"img",
                                data:{albumId:id},
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
                    afterComplete:function () {
                        $("#tab").trigger("reloadGrid");
                        $("#msgdel").show();
                        setTimeout(function () {
                            $("#msgdel").hide();
                        },3000);
                    }
                });
        });

        function addSubGrid(subGridId, albumId){
            /*动态 table id*/
            var subGridTableId = subGridId+ "table";
            /*动态 div id*/
            var subGridDivId = subGridId+ "div";
            /*动态添加子表格*/
            $("#"+subGridId).html("<table id='"+subGridTableId+"'></table>"+
                                 "<div id='"+subGridDivId+"' style='height:50px;'></div>"
                                 );
            $("#"+subGridTableId).jqGrid({
                url:"${path}/chapter/query?albumId="+albumId,
                datatype:"json",
                editurl:"${path}/chapter/operation",
                //设置表单适应父容器
                autowidth:true,
                /*设置引入样式必须设置的属性*/
                styleUI:"Bootstrap",
                /*自定义工具栏*/
                toolbar:[true,"top"],
                /*分页显示的位置*/
                pager:"#"+subGridDivId,
                /*显示总条数这只的每页展示的数量*/
                rowNum:2,
                rowList:[2,4,5],
                viewrecords:true,
                caption:"章节列表",
                /*表头*/
                colNames:["id","title","sizes","duration","src","status"],
                /*表中数据*/
                colModel:[
                    {name:"id",align:"center"},
                    {name:"title",editable:true,align:"center"},
                    {name:"sizes",align:"center"},
                    {name:"duration",align:"center"},
                    {name: "src",align:'center',width:300,editable: true,edittype:'file',formatter:function (value,option,rows) {
                            return value;
                    }},
                    {name:"status",editable:true,align:"center",edittype:"select",editoptions:{
                        value:"展示:展示;不展示:不展示"
                        }
                    }
                ]
            }).jqGrid("navGrid","#"+subGridDivId,
                {search:false,addtext:"添加",edittext:"修改",deltext:"删除"},
                { /*
                    * 修改
                    * */
                    closeAfterEdit:true,
                    afterSubmit:function(response){
                        var id = response.responseJSON.cid;
                        if (id != null) {
                            $.ajaxFileUpload({
                                url:"${path}/chapter/upload",
                                fileElementId:"src",
                                data:{chapterId:id},
                                type:"post",
                                success:function (data) {
                                    $("#"+subGridDivId).trigger("reloadGrid");
                                    $("#msgupd").show();
                                    setTimeout(function () {
                                        $("#msgupd").hide();
                                    },3000);
                                }
                            });
                        }
                        return response;
                    }
                },{
                    /*
                    * 增加
                    * */
                    closeAfterAdd:true,recreateForm:true,
                    afterSubmit:function(response){
                        var id = response.responseJSON.cid;
                        if (id != null) {
                            $.ajaxFileUpload({
                                url:"${path}/chapter/upload",
                                fileElementId:"src",
                                data:{chapterId:id,albumId:albumId},
                                type:"post",
                                success:function (data) {
                                    $("#"+subGridDivId).trigger("reloadGrid");
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
                        afterComplete:function () {
                            $("#"+subGridDivId).trigger("reloadGrid");
                            $("#msgdel").show();
                            setTimeout(function () {
                                $("#msgdel").hide();
                            },3000);
                        }
                    });

            /*添加按钮*/
            $("#t_"+subGridTableId).html("<button class='btn btn-info' onclick=\"play('"+subGridTableId+"');\" id='playmusic'>播放<span class='glyphicon glyphicon-play-circle'></span></button>"+
                                         "<button class='btn btn-info' onclick=\"download('"+subGridTableId+"');\">下载<span class='glyphicon glyphicon-download'></span></button>");
        }
        
        function play(subGridTableId) {
            /*判断用户是否选中一行*/
            var grid = $("#" + subGridTableId).jqGrid("getGridParam", "selrow");
            if (grid == null) {
                alert("请选择一首音乐！")
            } else {
                var ddd = $("#" + subGridTableId).jqGrid("getRowData", grid);
                var path = "${path}/music/"+ddd.src;
                $("#mymodal").modal('show');
                $("#aud").attr("src",path);
            }
        }

        function download(subGridTableId) {
            /*判断用户是否选中一行*/
            var grid = $("#" + subGridTableId).jqGrid("getGridParam", "selrow");
            if (grid == null) {
                alert("请选择一首音乐！")
            } else {
                var ddd = $("#" + subGridTableId).jqGrid("getRowData", grid);
                location.href="${path}/chapter/download?src="+ddd.src;

            }
        }



    </script>

<!-- Top content -->
<div class="content">
    <div class="page-header">
        <h3>专辑列表</h3>
    </div>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active">
            <a href="#home" aria-controls="home" role="tab" data-toggle="tab">
                专辑列表
            </a>
        </li>
    </ul>
    <div class="tab-content">
        <table id="tab" class="table table-bordered table-striped" style="..."></table>
    </div>
    <div id="pager" style="height: 45px"></div>
    <div class="alert alert-info" style="display:none" id="msgadd">添加成功</div>
    <div class="alert alert-info" style="display:none" id="msgupd">修改成功</div>
    <div class="alert alert-info" style="display:none" id="msgdel">删除成功</div>
    <div class="alert alert-info" style="display:none" id="msgload">下载成功</div>

    <div class="modal fade" id="mymodal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <audio controls loop preload='auto' id='aud'>
                        <%--<source type="audio/mpeg" class="sources">
                        <source  type="audio/ogg" class="sources">--%>
                    </audio>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

</div>