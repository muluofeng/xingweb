<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <style>
        body {
            text-align: center
        }

        .serach-input {
            width: 50%;
            height: 44px;
            padding: 6px 0 0 16px;
            margin-left: auto;
            margin-right: auto;
            margin-top: 25%;
            border-radius: 2px;
            box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.16), 0 0 0 1px rgba(0, 0, 0, 0.08);
            transition: box-shadow 200ms cubic-bezier(0.4, 0.0, 0.2, 1);
            box-sizing: border-box;
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

    </style>
    <link rel="stylesheet" href="assets/adminLTE/bootstrap/css/bootstrap.min.css">

    <script src="assets/adminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script>
        $(function () {
            $("#search-btn").on("click", function () {
                location.href = "?q=" + $("#search").val();
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
            box.addEventListener("drop",function(e) {
                e.preventDefault(); //取消默认浏览器拖拽效果
                var fileList = e.dataTransfer.files; //获取文件对象
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
            },false);

        })
    </script>
</head>
<body style="">
<div>
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


</body>
</html>