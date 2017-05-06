<!doctype html>
<html lang="en">
<head>
    <link rel="shortcut icon" href="assets/img/logo/feji_16x16huishe.ico" type="image/x-icon"/>

    <meta charset="UTF-8">
    <title>书签检索</title>
    <style>
        body {
            text-align: center
        }

        .oauth {
            position: relative;
            width: 100%;
            height: 50px;
        }

        .oauth-weibo {
            position: absolute;
            top: 25px;
            right: 100px;
            background-image: url("assets/img/login-third-party.png");
            background-position: 0 0;
            width: 32px;
            height: 32px;
        }

        .oauth-div {
            position: absolute;
            top: 25px;
            right: 50px;
            width: 100px;
            height: 40px;
        }

        .oauth-qq {

            background-image: url("assets/img/login-third-party.png");
            background-position: -112px 0;
            width: 32px;
            height: 32px;
            display: block;
        }

        .serach-input {
            width: 50%;
            height: 44px;
            padding: 6px 0 0 16px;
            margin-left: auto;
            margin-right: auto;
            margin-top: 10%;
            border-radius: 2px;
            box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.16), 0 0 0 1px rgba(0, 0, 0, 0.08);
            transition: box-shadow 200ms cubic-bezier(0.4, 0.0, 0.2, 1);
            box-sizing: border-box;
            position: relative;
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
            top: 0;
            right: 0;
            position: absolute;
            outline: none;
        }

        .submitBtn:hover {
            box-sizing: border-box;
        }

        .upload {
            width: 500px;
            margin: 50px auto;
        }

        #drop_area {
            width: 100%;
            height: 100px;
            border: 3px dashed silver;
            line-height: 100px;
            text-align: center;
            font-size: 36px;
            color: #d3d3d3;
        }

        .logoImg {
            position: relative;
        }

        .img {
            width:200px;
            height:180px;
            margin:0 auto;
        }

        .beian {
            margin-top: 150px;
            font-size: small;
            color: #757575;
        }
    </style>
    <link rel="stylesheet" href="assets/adminLTE/bootstrap/css/bootstrap.min.css">

    <script src="assets/adminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="assets/layer/layer.js"></script>
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
            //阻止浏览器默认行。

            $(document).on({
                dragleave: function (e) {    //拖离
                    e.preventDefault();
                },
                drop: function (e) {  //拖后放
                    e.preventDefault();
                },
                dragenter: function (e) {    //拖进
                    e.preventDefault();
                },
                dragover: function (e) {    //拖来拖去
                    e.preventDefault();
                }
            });
            var box = document.getElementById('drop_area'); //拖拽区域
            box.addEventListener("drop", function (e) {
                e.preventDefault(); //取消默认浏览器拖拽效果
                var fileList = e.dataTransfer.files; //获取文件对象
                var isLogin=[#if isLogin==true]true[#else]false[/#if];
//                if(isLogin==false){
//                    layer.msg('您还未登录,请先登录');
//                    return false;
//                }
                //检测是否是拖拽文件到页面的操作
                if (fileList.length == 0) {
                    return false;
                }
                //检测文件是不是图片
                if (fileList[0].type.indexOf('html') === -1) {
                    alert("您拖的不是html！");
                    return false;
                }
                //上传
                xhr = new XMLHttpRequest();
                xhr.open("post", "/uploadhtml", true);
                xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");

                var fd = new FormData();
                fd.append('file', fileList[0]);

                xhr.send(fd);
            }, false);

        })
    </script>
</head>
<body style="">
<div class="oauth">
[#--<a href="#" class="oauth-weibo"></a>--]
    <div class="oauth-div">
    [#if user?exists]
        <img src="${userImg}"/>
        <span id="userId" data-id="${openid}">${nickname}</span>
    [#else ]
        <a href="/oauth/qq" class="oauth-qq"></a>
    [/#if]
    </div>

</div>
<div class="logoImg">

</div>
<div>
    <div class="img">
        <img src="assets/img/logo/feiji_200x200_huishe.png">
    </div>
    <div class="serach-input">
        <input name="q" type="text" maxlength="2048" id="search" autocomplete="off"/>
        <button type="submit" class="submitBtn" id="search-btn">
            <span class="glyphicon glyphicon-search"></span>
        </button>
    </div>
    <div class="upload">
        <div id="drop_area">将书签拖拽到此区域</div>
    </div>
</div>

<div class="footer">
    <div class="beian"> 皖ICP备16006468号-1</div>
</div>
</body>
</html>