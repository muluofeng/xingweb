<!DOCTYPE html>
<html>
<head>
    <base href="${request.contextPath}/">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>后台登录</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="assets/adminLTE/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->

    <!-- Theme style -->
    <link rel="stylesheet" href="assets/adminLTE/dist/css/AdminLTE.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="assets/adminLTE/plugins/iCheck/square/blue.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>

    <![endif]-->
    <style>
        .login-page {
            background-image: url('assets/img/login-background.png');
            background-size: cover;
        }
        .login-box-body {
            border-radius: 12px;
        }
    </style>
</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <a href="../../index2.html"><img src="assets/img/xing-2.png" style="height:60px;width:50px"><b></b>xing</a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg text-red">请输入用户名和密码</p>

        <form action="/admin/dologin" method="post"  id="loginForm">
            <div class="form-group has-feedback">
                <input type="text" class="form-control" placeholder="用户名" name="loginname">
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" class="form-control" placeholder="密码" name="password">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                        <label>
                            <input type="checkbox"> <span>记住我</span>
                        </label>
                    </div>
                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                    <button type="button"  id="loginAction" class="btn btn-primary btn-block btn-flat">登录</button>
                </div>
                <!-- /.col -->
            </div>
        </form>

        <#--第三方登录-->
        <#--<div class="social-auth-links text-center">-->
            <#--<p>- OR -</p>-->
            <#--<a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Sign in using-->
                <#--Facebook</a>-->
            <#--<a href="#" class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-google-plus"></i> Sign in using-->
                <#--Google+</a>-->
        <#--</div>-->
        <!-- /.social-auth-links -->

        <a href="#">忘记密码</a><br>


    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.2.3 -->
<script src="assets/adminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="assets/adminLTE/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="assets/adminLTE/plugins/iCheck/icheck.min.js"></script>
<script>
    $(function () {
        $("#loginAction").on('click',function(){
            $.ajax({
                url:$("#loginForm").attr("action"),
                data:$("#loginForm").serialize(),
                type:"post",
                dataType:"json",
                success:function(re){
                    if(re.status==1){
                        location.href="/admin/user";
                    }else{
                        //登录失败
                        $("p.login-box-msg").text("用户名或者密码错误");
                    }
                }
            })
        });

        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
    });
</script>
</body>
</html>
