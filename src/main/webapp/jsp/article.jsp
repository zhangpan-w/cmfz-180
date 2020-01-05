<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%-- jqGrid核心js --%>
<script src="${path}/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
<%-- jqGrid --%>
<link rel="stylesheet" href="${path}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
<%-- 国际化 --%>
<script src="${path}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
<%-- fileupload --%>
<script src="${path}/boot/js/ajaxfileupload.js"></script>
<%--kindeditor--%>
<script charset="UTF-8" src="${path}/kindeditor/kindeditor-all-min.js"></script>
<script charset="UTF-8" src="${path}/kindeditor/lang/zh-CN.js"></script>

    <script type="text/javascript">

        $(function() {
                editor = KindEditor.create("#editor_id",{
                width:500,
                //resizeType:0,      //使文本框不能随意拉伸
                allowFileManager:true,
                filePostName:'img',
                uploadJson:'${path}/article/uploadImg',
                fileManagerJson:'${path}/article/getImgs',
                afterBlur:function () {
                    this.sync();
                }
            });

            editorupd = KindEditor.create("#edit_id",{
                width:500,
                //resizeType:0,      //使文本框不能随意拉伸
                allowFileManager:true,
                filePostName:'img',
                uploadJson:'${path}/article/uploadImg',
                fileManagerJson:'${path}/article/getImgs',
                afterBlur:function () {
                    this.sync();
                },afterChange:function(){
                    this.sync()
                },
            });

            $("#tab").jqGrid({
                url:"${path}/article/query",
                datatype:"json",
                editurl:"${path}/article/del",
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
                /*caption:"文章列表",*/
                /*定义多选框并获取值*/
                multiselect : true,
                /*表头*/
                colNames:["id","title","author","content","create_date","status","操作"],
                /*表中数据*/
                colModel:[
                    {name:"id",align:"center"},
                    {name:"title",editable:true,align:"center"},
                    {name:"author",editable:true,align:"center"},
                    {name:"content",editable:true,align:"center"},
                    {name:"create_date",editable:true,edittype:"date",align:"center"},
                    {name:"status",editable:true,align:"center",edittype:"select",editoptions:{
                        value:"展示:展示;不展示:不展示"}
                    },
                    {name:"operation",align:"center",
                        formatter:function(cellvalue, options, rowObject){
                            return "<a class='btn' onclick=\"selectContent();\"><span class='glyphicon glyphicon-th-list'></span></a>" +
                                "&nbsp;&nbsp;&nbsp;&nbsp;"+
                                "<a class='btn' onclick=\"update();\"><span class='glyphicon glyphicon-pencil'></span></a>";
                        }
                    }
                ]
            }).jqGrid("navGrid","#pager",
                {add:false,edit:false,deltext:"删除"},
                {
                    /*
                    * 修改
                    * */
                    closeAfterEdit:true
                },{
                    /*
                    * 增加
                    * */
                    closeAfterAdd:true
                },{
                    /*
                    * 删除
                    * */
                });

            $("#insert").click(function () {
               $("#insertmodal").modal('show');
            });

            $("#insertsub").click(function () {
                var ff = $("#myForm").serialize();
                $.ajax({
                    url:"${path}/article/insert",
                    data:ff,
                    datatype:"json",
                    success:function (data) {
                        $("#tab").trigger("reloadGrid");
                        $("#insertmodal").modal('hide');
                        $("#msgadd").show();
                        setTimeout(function () {
                            $("#msgadd").hide();
                        },3000);
                    }
                })
            })

            $("#updsub").click(function () {
                $.ajax({
                    url:"${path}/article/update",
                    data:$("#myupdForm").serialize(),
                    datatype:"json",
                    success:function (data) {
                        $("#tab").trigger("reloadGrid");
                        $("#updmodal").modal('hide');
                        $("#msgupd").show();
                        setTimeout(function () {
                            $("#msgupd").hide();
                        },3000);
                    }
                })
            })

            $("#insertmodal").on("hidden.bs.modal",function () {
                document.getElementById("myForm").reset()
            })

            $("#updmodal").on("hidden.bs.modal",function () {
                document.getElementById("myupdForm").reset()
            })




        });


        function update() {
            var grid = $("#tab").jqGrid("getGridParam", "selrow");
            if (grid != null) {
                $("#updmodal").modal('show');
                $("#id2").val(grid);
                var ddd = $("#tab").jqGrid("getRowData", grid);
                $("#title2").val(ddd.title);
                $("#author2").val(ddd.author);
                $("#status2").val(ddd.status);
                editorupd.html(ddd.content);
            }else{
                alert("请选择要修改的记录")
            }
        }

        function selectContent(){
            var grid = $("#tab").jqGrid("getGridParam", "selrow");
            if (grid != null) {
                $("#tabsel").empty();
                var con = $("#tab").jqGrid("getRowData", grid);
                $("#selmodal").modal("show");
                var tab = $("<tr><td>"+con.content+"</td></tr>");
                $("#tabsel").append(tab);
            }else{
                alert("请选择要修改的记录")
            }
        }
    </script>

<!-- Top content -->
<div class="content">
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active">
            <a data-toggle="tab">
                文章列表
            </a>
        </li>
        <li role="presentation">
            <a href="#profile" data-toggle="tab" id="insert">
                添加文章
            </a>
        </li>
    </ul>
    <div class="tab-content">
    <table id="tab" class="table table-bordered table-striped" style="..."></table>
    </div>
    <div id="pager" style="height: 45px"></div>
    <div class="alert alert-info" style="display:none" id="msgadd">添加成功</div>
    <div class="alert alert-info" style="display:none" id="msgupd">修改成功</div>

    <div class="modal fade" id="insertmodal">
        <div class="modal-dialog" role="document">
            <div class="modal-content" style="width:850px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">添加文章</h4>
                </div>
                <div class="modal-body">
                    <form action="" class="form-horizontal" id="myForm">
                        <div class="form-group">
                            <label for="title1" class="col-sm-2 control-label">标题:</label>
                            <div class="col-sm-6">
                                <input type="text" id="title1" name="title" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="tea1" class="col-sm-2 control-label">上师ID:</label>
                            <div class="col-sm-6">
                                <input type="text" id="tea1" name="guru_id" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="author1" class="col-sm-2 control-label">作者:</label>
                            <div class="col-sm-6">
                                <input type="text" id="author1" name="author" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="status1" class="col-sm-2 control-label">状态:</label>
                            <div class="col-sm-6">
                                <select id="status1" name="status" class="form-control">
                                    <option value="展示">展示</option>
                                    <option value="不展示">不展示</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-10 col-sm-offset-1">
                                <textarea id="editor_id" name="content" rows="14" cols="65" title="内容"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="insertsub">添加</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div class="modal fade" id="updmodal">
        <div class="modal-dialog" role="document">
            <div class="modal-content" style="width:850px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">修改文章</h4>
                </div>
                <div class="modal-body">
                    <form action="" class="form-horizontal" id="myupdForm">
                        <div class="form-group">
                            <label for="title2" class="col-sm-2 control-label">标题:</label>
                            <div class="col-sm-6">
                                <input type="hidden" id="id2" name="id"/>
                                <input type="text" id="title2" name="title" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="author2" class="col-sm-2 control-label">作者:</label>
                            <div class="col-sm-6">
                                <input type="text" id="author2" name="author" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="status2" class="col-sm-2 control-label">状态:</label>
                            <div class="col-sm-6">
                                <select id="status2" name="status" class="form-control">
                                    <option value="展示">展示</option>
                                    <option value="不展示">不展示</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-10 col-sm-offset-1">
                                <textarea id="edit_id" name="content" rows="14" cols="65" title="内容"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="updsub">修改</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div class="modal fade" id="selmodal">
        <div class="modal-dialog" role="document">
            <div class="modal-content" style="width:850px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">查看内容</h4>
                </div>
                <div class="modal-body">
                    <table id="tabsel" class="table table-striped">

                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

</div>