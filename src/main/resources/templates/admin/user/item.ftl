[#assign styles]
<style type="text/css">
    span.error {
        font-size: 12px;
        color: red;
        margin: 4px 8px;
        line-height: 24px;
        display: inline-block;
        float: left;
    }
</style>
[/#assign]
[#assign  scripts]
<script type="text/javascript" src="assets/jqueryvalidate/jquery.validate.min.js"></script>
<script type="text/javascript">
//    $(function () {
//        var validate = $("#itemForm").validate({
//            errorElement: "span",
//            errorPlacement: function (error, element) {
//                error.appendTo(element.parent().parent());
//            },
//            rules: {
//                loginname: {required: true,},
//                nickname: {required: true},
//                password: {required: true, rangelength: [6,11]},
//                confirmpassword: {equalTo: "#password"}
//            },
//            messages: {
//                loginname: {
//                    required: "登录名必填",
//                },
//                nickname: {
//                    required: "昵称必填",
//                },
//                password: {
//                    required: "密码必填",
//                    rangelength: "密码必须6-11位字符",
//                },
//                confirmpassword: {
//                    equalTo: "两次密码不一致"
//                }
//            }
//        });
//
//        //ajax提交
//
//    });
</script>
[/#assign]
[@admin.layout  title=title menu="admin/user/add" styles=styles  scripts=scripts ]
<div class="col-md-12">
    <!-- Horizontal Form -->
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title"></h3>
        </div>
        <!-- /.box-header -->
        <!-- form start -->
        <form class="form-horizontal" id="itemForm" method="post" action="admin/user/save">
            <div class="box-body">
                [#if adminObj.id]
                    <div  class="alert-warning col-md-offset-1" style="margin-bottom: 8px">提示：密码为空则保存为原密码</div>
                    <input type="hidden" name="id" value="${adminObj.id}">
                [/#if]
                <div  class="alert-warning col-md-offset-1" style="margin-bottom: 8px">${message}</div>
                <div class="form-group">
                    <label for="inputEmail3" class="col-sm-2 control-label">用户名</label>

                    <div class="col-sm-4">
                        [#if adminObj.id]
                            <div class="form-control">${adminObj.loginname}</div>
                        [#else]
                            <input type="text" name="loginname" class="form-control" id="loginname" placeholder="用户名" value="${adminObj.loginname}" >
                        [/#if]
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">昵称</label>

                    <div class="col-sm-4">
                        <input type="text" name="nickname" id="nickname" class="form-control" id="inputPassword3" placeholder="昵称" value="${adminObj.nickname}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>

                    <div class="col-sm-4">
                        <input type="password" name="password" id="password" class="form-control" id="inputPassword3"
                               placeholder="密码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">确认密码</label>

                    <div class="col-sm-4">
                        <input type="password" name="confirmpassword" class="form-control" id="inputPassword3"
                               id="confirmpassword" placeholder="确认密码">
                    </div>
                </div>

            </div>
            <!-- /.box-body -->
            <div class="box-footer col-md-offset-2">
                <button type="submit"   class="btn btn-primary">保存</button>
                <button type="reset" class="btn btn-default">取消</button>
            </div>
            <!-- /.box-footer -->
        </form>
    </div>

</div>
[/@admin.layout]