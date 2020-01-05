<%@ page isELIgnored="false" contentType="text/html;UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/echarts/china.js"></script>
    <script type="text/javascript">
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BS-172bf1a3efa1482d9364f61e2923bb62" //替换为您的应用appkey
        });
        $(function () {
            /*基于准备好的dom，初始化echarts实例*/
            var myChart = echarts.init(document.getElementById("main"));
            var myChart2 = echarts.init(document.getElementById("main2"));

            /*指定图表的配置项和数据*/
            /*var option = {
                title : {
                    text: '用户地理分布图',
                    left: 'center'
                },
                tooltip : {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data:['女士','男士']
                },
                visualMap: {
                    min: 0,
                    max: 2500,
                    left: 'left',
                    top: 'bottom',
                    text:['高','低'],           // 文本，默认为数值文本
                    calculable : true
                },
                toolbox: {
                    show: true,
                    orient : 'vertical',
                    left: 'right',
                    top: 'center',
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                series : [
                    {
                        name: '女士',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        }
                    },
                    {
                        name: '男士',
                        type: 'map',
                        mapType: 'china',
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        }
                    }
                ]
            };*/
            var option = {
                title: {
                    text: '最近七天上师注册量'
                },
                tooltip: {},
                legend: {
                    data:['数量']
                },
                xAxis: {
                    data:["第一天","第二天","第三天"]
                },
                yAxis: {},
                series: [{
                    name: '数量',
                    type: 'bar'
                }]
            };
            var option2 = {
                title: {
                    text: '1-12月上师注册量'
                },
                tooltip: {},
                legend: {
                    data:['数量']
                },
                xAxis: {
                    data: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
                },
                yAxis: {},
                series: [{
                    name: '数量',
                    type: 'line',
                }]
            };
            /*使用刚指定的配置项和数据显示图标*/
            myChart.setOption(option);
            myChart2.setOption(option2);
            $.ajax({
                url:"${pageContext.request.contextPath}/goEasy/action",
                datatype:"json",
                success:function (data) {
                    myChart.setOption({
                        xAxis: {
                            data:data["name"]
                        },
                        series: [{
                            data: data["value"]
                        }]
                    })
                }
            });
            $.ajax({
                url:"${pageContext.request.contextPath}/goEasy/monthselect",
                datatype:"json",
                success:function (data) {
                    console.log(data)
                    myChart2.setOption({
                        xAxis: {
                            data:data["name"]
                        },
                        series: [{
                            data: data["value"]
                        }]
                    })
                }
            });
            goEasy.subscribe({
                channel: "goeasy_7", //替换为您自己的channel
                onMessage: function (message) {
                    console.log("Channel:" + message.channel + " content:" + message.content);
                    var parse = JSON.parse(message.content);
                    myChart.setOption({
                        series: [{
                            data: parse["value"]
                        }]
                    })
                }
            });

        });

    </script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 1000px;height:400px;"></div>
<div id="main2" style="width: 1000px;height:400px;"></div>
</body>
</html>