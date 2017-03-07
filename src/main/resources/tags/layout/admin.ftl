[#macro layout title="" menu="index"   preStyles="" preScripts="" styles="" scripts="" ]
<!DOCTYPE html>
<html>
<head>
    <base href="${request.contextPath}/">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${title}</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="assets/adminLTE/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="assets/adminLTE/cdn/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="assets/adminLTE/cdn/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="assets/adminLTE/dist/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="assets/adminLTE/dist/css/skins/_all-skins.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="assets/adminLTE/plugins/iCheck/flat/blue.css">
    <!-- Morris chart -->
    <link rel="stylesheet" href="assets/adminLTE/plugins/morris/morris.css">

    <!-- Date Picker -->
    <link rel="stylesheet" href="assets/adminLTE/plugins/datepicker/datepicker3.css">
    <!-- Daterange picker -->
    <link rel="stylesheet" href="assets/adminLTE/plugins/daterangepicker/daterangepicker.css">
    <!-- bootstrap wysihtml5 - text editor -->
    <link rel="stylesheet" href="assets/adminLTE/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">

    <!-- jQuery 2.2.3 -->
    <script src="assets/adminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <!-- Bootstrap 3.3.6 -->
    <script src="assets/adminLTE/bootstrap/js/bootstrap.min.js"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="assets/adminLTE/cdn/js/html5shiv.min.js"></script>
    <script src="assets/adminLTE/cdn/js/respond.min.js"></script>
    <![endif]-->
    ${preStyles}
    ${preScripts}
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    [#include  "*/layout/top.ftl"/]
    <!-- Left side column. contains the logo and sidebar -->
    [#include  "*/layout/sidebar.ftl"/]

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                <small>${title}</small>
                <small>${secondtitle}</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 主页</a></li>
                <li class="active">${title}</li>
            </ol>
        </section>
        <section class="content">
            [#nested /]
        </section>

    </div>
    <!-- /.content-wrapper -->
    [#include "*/layout/footer.ftl"/]


</div>
<!-- ./wrapper -->

<!-- jQuery 2.2.3 -->
<script src="assets/adminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="assets/adminLTE/bootstrap/js/bootstrap.min.js"></script>




<!-- Sparkline -->
<script src="assets/adminLTE/plugins/sparkline/jquery.sparkline.min.js"></script>

<!-- jQuery Knob Chart -->
<script src="assets/adminLTE/plugins/knob/jquery.knob.js"></script>
<!-- daterangepicker -->
<script src="assets/moment/moment.js"></script>
<script src="assets/adminLTE/plugins/daterangepicker/daterangepicker.js"></script>
<!-- datepicker -->
<script src="assets/adminLTE/plugins/datepicker/bootstrap-datepicker.js"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="assets/adminLTE/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
<!-- Slimscroll -->
<script src="assets/adminLTE/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="assets/adminLTE/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="assets/adminLTE/dist/js/app.min.js"></script>
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
[#--<script src="assets/adminLTE/dist/js/pages/dashboard.js"></script>--]
<!-- AdminLTE for demo purposes -->
<script src="assets/adminLTE/dist/js/demo.js"></script>

      ${styles}
       ${scripts}
</body>
</html>


[/#macro]