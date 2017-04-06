[#assign styles]
<style>
#countTable>tbody>tr>th,.border>tbody>tr>td{
    border: 1px solid #cdcdcd;
    text-align: center;
}
/*#countTable>tr>th,.table>tr>td{*/
    /*border-top: 1px solid #967070;*/
/*}*/
</style>
[/#assign]
[#assign scripts]
<script type="text/javascript" src="assets/echarts/echarts.js" charset="UTF-8"></script>
<script type="text/javascript" src="assets/echarts/map/china.js" charset="UTF-8"></script>
<script>
    /**
     * 为空地区补充数据
     * @param seriesData
     */
    function replaceNullAreaValue(seriesData) {
        var features = echarts.getMap('china').geoJson.features;
        features.filter(function (area) {  //过滤数据，true保留，false不保留
            return !seriesData.some(function (d) {  //some 测试数组中的某些元素是否通过了指定函数的测试,找到返回true
                return d.name === area.properties.name;
            })
        }).forEach(function (area) {
            seriesData.push({name: area.properties.name, value: 0});
        })
    }
    var myChart = echarts.init(document.getElementById('main'));
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '会员省份分布图',
            left: 'center'
        },
        visualMap: {
            min: 0,
            max: 0,
            left: '3%',
            top: '15%',
            type: 'continuous',
            text: ['高', '低'],
            calculable: true,
            inRange: {
                color: ['#e0ffff', '#77bbef', '#006edd']
            }
        },
        //提示框
        tooltip: {
            trigger: 'item'//数据项图形触发
        },
        toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
                dataView: {
                    readOnly: false
                },
                restore: {},
                saveAsImage: {}
            }
        },
        series: [{
            name: '会员数量',
            type: 'map',
            mapType: 'china',
            data: []
        }]
    };
    // 通过ajax获取数据
    $.ajax({
        type: "post",
//        async: false, //同步执行
        url: "/admin/getCountByProvince",
        dataType: "json", //返回数据形式为json
        data: {rnd: Math.random()},
        success: function (result) {
            if (result) {
                var reducer = function add(sum, item) {
                    return sum += item.value;
                }
                var total = result.reduce(reducer, 0);
                result.forEach(function (item) {
                    var percent = item.value / total * 100;
                    var color = "";
                    if (percent > 90) {
                        color = "red";
                    } else if (percent > 70) {
                        color = "yellow";
                    } else if (percent > 50) {
                        color = "yellow";
                    } else if (percent > 30) {
                        color = "light-blue";
                    } else if (percent > 10) {
                        color = "green";
                    } else {
                        color = "";
                    }
                    $('#countTable').append(`<tr>
                            <td>
                             ${r'${item.name}'}
                            </td>
                            <td>
                                ${r'${item.value}'}
                            </td>
                            <td>
                                <span class='badge bg-${r'${color}'}'> ${r'${percent}'}%</span></td>
                            </tr>`);
                })

                option.visualMap.max = result[0].value  //最大值
                replaceNullAreaValue(result)   //给无数据的省份初始化值
                option.series[0].data = result;
                myChart.setOption(option);
            }
        },
        error: function (errorMsg) {
            console.log("图表请求数据失败啦!");
        }
    });

</script>
[/#assign]
[@admin.layout title="后台首页" menu="admin/index"   preStyles="" preScripts="" styles=styles scripts=scripts ]
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 500px;height:500px;margin-left:40px;float: left"></div>
<div style="float:right;width: 30%;margin-top: 30px">
    <div class="box-body">
        <table class="table border" id="countTable" >
            <tr>
                <th>省份</th>
                <th>数量</th>
                <th>百分比</th>
            </tr>

        </table>
    </div>
</div>
[/@admin.layout]