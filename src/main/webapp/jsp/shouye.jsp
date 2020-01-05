<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set var="app" value="${pageContext.request.contextPath}"/>
<head>

    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%--引入jquery.js--%>
    <link rel="stylesheet" href="${app}/boot/css/bootstrap.min.css">
    <script src="${app}/js/jquery-3.3.1.min.js"></script>
    <script src="${app}/boot/js/bootstrap.min.js"></script>
    <title>Document</title>
    <script type="text/javascript">

        $(function () {
           $.ajax({
               url:"${app}/banner/selectzs",
               datatype:"json",
               success:function (data) {
                 for(var i=0;i<data.length;i++){
                     if(i==0){
                         var play1 = $("<li class='active' data-target='#slidershow' data-slide-to='"+0+"' ></li>");
                         var plays1 = $("<div class='item active'> <a href='#'><img style='width:100%;height:350px;' src='../img/"+data[0].img+"'></a> </div>");
                         $("#sortplay").append(play1);
                         $("#play").append(plays1);
                     }else {
                         var play2 = $("<li data-target='#slidershow' data-slide-to='"+i+"'></li>");
                         var plays2 = $("<div class='item'> <a href='#'><img style='width:100%;height:350px;' src='../img/"+data[i].img+"'></a> </div>");
                         $("#sortplay").append(play2);
                         $("#play").append(plays2);
                     }
                 }
               }
           });

           $("#outPoi").click(function () {
               $.ajax({
                   url:"${pageContext.request.contextPath}/admin/outPoi",
                   success:function (data) {
                       alert("导出成功！")
                   }
               })
           })

            $("#inPoi").click(function () {
                $.ajax({
                    url:"${pageContext.request.contextPath}/admin/inPoi",
                    success:function (data) {
                        alert("导入成功！")
                    }
                })
            })
        });

    </script>
</head>
<body>
<div class="container-fluid">
    <nav class="nav navbar-default">
        <div class="navbar-left col-sm-3">
            <a class="navbar-brand">持明法洲管理系统</a>
        </div>
        <div class="navbar-right">
            <%--<a class="navbar-brand">
                <div class="btn" id="inPoi">
                    <label class="control-label">导入</label>
                </div>
            </a>--%>
            <a class="navbar-brand">
                <div class="btn" id="outPoi">
                    <label class="control-label">导出</label>
                </div>
            </a>
            <a class="navbar-brand">欢迎:
                <label class="control-label">${username}</label>
            </a>
            <a class="navbar-brand" style="margin-right: 25px;">退出登陆
                <span class="glyphicon glyphicon-log-out"></span>
            </a>
        </div>
    </nav>

    <div class="col-sm-2">
        <br/>
            <div class="panel-group" id="parent">
                <div class="panel panel-default">
                    <div class="panel-heading" id="headingOne">
                        <h4 class="panel-title">
                            <center><a data-toggle="collapse" data-parent="#parent" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                用户管理
                            </a></center>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body ">
                            <a class="btn btn-danger form-control">用户列表</a>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading" id="headingTwo">
                        <h4 class="panel-title">
                            <center><a data-toggle="collapse" data-parent="#parent" href="#collapseTwo" aria-expanded="true" aria-controls="collapseOne">
                                上师管理
                            </a></center>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body ">
                            <a class="btn btn-danger form-control">上师列表</a>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading" id="headingThree">
                        <h4 class="panel-title">
                            <center><a data-toggle="collapse" data-parent="#parent" href="#collapseThree" aria-expanded="true" aria-controls="collapseOne">
                                文章管理
                            </a></center>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                        <div class="panel-body ">
                            <a class="btn btn-danger form-control" href="javascript:$('#content').load('article.jsp');">文章列表</a>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading" id="headingFour">
                        <h4 class="panel-title">
                            <center><a data-toggle="collapse" data-parent="#parent" href="#collapseFour" aria-expanded="true" aria-controls="collapseOne">
                                专辑管理
                            </a></center>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                        <div class="panel-body ">
                            <a class="btn btn-danger form-control" href="javascript:$('#content').load('album.jsp');">专辑列表</a>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading" id="headingFive">
                        <h4 class="panel-title">
                            <center><a data-toggle="collapse" data-parent="#parent" href="#collapseFive" aria-expanded="true" aria-controls="collapseOne">
                                轮播图管理
                            </a></center>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
                        <div class="panel-body ">
                            <a href="javascript:$('#content').load('banner.jsp');" class="btn btn-danger form-control">轮播图列表</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <div class="col-sm-10" id="content">
        <br/>
        <div class="jumbotron" >
            <h3 style="margin-left: 100px;">欢迎来到持明法洲后台管理系统</h3>
        </div>
        <div id="slidershow" class="carousel slide" data-ride="carousel" data-interval = 2000 data-pause = "hover" data-wrap="true">
            <!-- 设置图片轮播的顺序 -->
            <ol class="carousel-indicators" id="sortplay"></ol>
            <!--设置轮播的图片-->
            <div class="carousel-inner" id="play"></div>
            <a class="left carousel-control" href="#slidershow" role="button" data-slide="prev" >
                <span class="glyphicon glyphicon-chevron-left"></span>
            </a>
            <a class="right carousel-control" href="#slidershow" role="button" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right"></span>
            </a>
        </div>
    </div>
    </div>

    <div class="container-fluid" style="margin-top: 40px;">
        <nav class="navbar navbar-default navbar-fixed-bottom">
            <div class="container" style="margin-top: 20px;text-align: center;">
                @百知教育 baizhi@zparkir.com.cn
            </div>
        </nav>
    </div>
</body>
