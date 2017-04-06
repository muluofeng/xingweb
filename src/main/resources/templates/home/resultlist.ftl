[#assign styles]
<style>

</style>
[/#assign]
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <style>
        body {
            text-align: center;
        }

        .serach-input {
            width: 50%;
            height: 44px;
            padding: 6px 0 0 16px;
            position: relative;
            left: 150px;
            margin-top: 19px;
            border-radius: 2px;
            box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.16), 0 0 0 1px rgba(0, 0, 0, 0.08);
            transition: box-shadow 200ms cubic-bezier(0.4, 0.0, 0.2, 1);
            box-sizing: border-box;
            background: #fff;
        }

        .serach-input > input {
            width: 100%;
            line-height: 34px;
            border: none;
            padding: 0;
            margin: 0;
            outline: none;
            font-size: 16px;
        }

        .serach-input:hover {
            box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.2), 0 0 0 1px rgba(0, 0, 0, 0.08);
        }

        .submitBtn {
            width: 44px;
            height: 44px;
            float: right;
            border: none;
            background: transparent;
            top: -37px;
            right: 0;
            position: relative;
            outline: none;
        }

        .submitBtn:hover {
            box-sizing: border-box;
        }

        .logocont {
            left: 0;
            position: absolute;
        }

        #logocont {
            /*z-index: 1*/
            /*padding-top: 7px;*/
            padding-left: 13px;
            /*padding-right: 12px;*/
            /*margin-top: 17px;*/
            position: relative;
        }

        #logo {
            display: block;
            overflow: hidden;
            position: absolute;

        }

        #logo img {
            border: 0;
        }

        .nav {
            background-color: #fafafa;
        }

        .nav-bottom {
            width: 100%;
            height: 58px;
            color: #666;
            border-bottom: 1px solid #ebebeb;
            outline-width: 0;
            outline: none;
            position: relative;
        }

        .login-div {
            position: absolute;
            width: 100px;
            height: 50px;
            top: 25px;
            right: 0;
        }

        .login-qq {
            display: inline-block;
            background-position: -112px 0;
            background-image: url(assets/img/login-third-party.png);
            background-repeat: no-repeat;
            margin-right: 5px;
            width: 32px;
            height: 32px;
        }

        .content {
            min-height: 513px;  //没有数据的情况下撑开
        }

        .data {
            margin-top: 20px;
            margin-left: 150px;
            width: 632px;
            margin-right: 264px;
            position: relative;

        }

        .item {
            text-align: left;
            margin-top: 0;
            margin-bottom: 26px;
            padding-left: 16px;

        }

        .item-url {
            color: #006621;
            font-style: normal;
            overflow: hidden;
        }

        .item-title {
            font-size: 18px;
            line-height: 1.2;

        }

        .item-content {
            line-height: 1.4;
            word-wrap: break-word;
        }

        .resultStatus {
            font-size: small;
            line-height: 43px;
            color: #808080;
            padding-left: 16px;
            padding-top: 0;
            padding-bottom: 0;
            padding-right: 8px;
            margin-left: 150px;
            text-align: left;

        }

        .page {
            text-align: left;
            margin-left: 15px;
        }

        .footer {
            margin-top: 40px;
            padding-top: 20px;
            background: #f2f2f2;
            border-top: 1px solid #e4e4e4;
            line-height: 40px;
            height: 100px;
            width: 100%;
            text-align: center;
            font-size: 12px;
            color: #757575;

        }

    </style>
    <link rel="stylesheet" href="assets/adminLTE/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/laypage/skin/laypage.css">
    <script src="assets/adminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="assets/laypage/laypage.js"></script>
    <script>
        $(function () {
            $("#search-btn").on("click", function () {
                if ($("#search").val() != "") {
                    location.href = "?q=" + $("#search").val();
                }
            });
            $("#search").on("keydown", function (e) {
                if ($("#search").val() != "" && e.keyCode == 13) {
                    location.href = "?q=" + $("#search").val();
                }
            });
            laypage({
                cont: document.getElementById('page2'), //容器。值支持id名、原生dom对象，jquery对象,
                pages: ${pages}, //总页数
                skin: '#1E9FFF', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
                groups: 10,//连续显示分页数
                curr:${current},
                jump: function (obj, first) {
                    //得到了当前页，用于向服务端请求对应数据
                    if (!first) { //一定要加此判断，否则初始时会无限刷新
                        location.href = "?q=" + $("#search").val() + "&page=" + obj.curr;
                    }
                }
            });

        })
    </script>
</head>
<body style="">
<div class="nav">
    <div>
        <div class="logocont" id="logocont">
            <a href="/" id="logo">
                <img width="55" height="55" src="assets/img/logo/xing-logo44x44.png">
            </a>
        </div>
        <div class="nav-input">
            <div class="serach-input">

                <input name="q" type="text" value="${keyword}" maxlength="2048" id="search" autocomplete="off"/>
                <button type="submit" class="submitBtn" id="search-btn">
                    <span class="glyphicon glyphicon-search"></span>
                </button>

            </div>
        </div>
        <div class="login-div">
            <div class="login-qq"></div>
        </div>
    </div>
    <div class="nav-bottom">

    </div>
</div>


<div class="content">
    <div class="resultStatus">找到约 ${num} 条结果 （用时 ${time?string('0.000')} 秒）
    </div>
    <div class="data">
    [#list bookmarks as bookmark]
        <div class="item">
            <h3 class="item-title">
                <a href="${bookmark.url}">${bookmark.title}</a>
            </h3>
            <div>
                <div class="item-url">${bookmark.url}</div>
                <span class="item-content">${bookmark.content?replace("<!--","&lt;!--")}</span>
            </div>
        </div>
    [/#list]
        <div class="page">
            <div id="page2"></div>
        </div>
    </div>


</div>


<div class="footer">
    粤ICP备16037375号-1
</div>
</body>
</html>