[#assign styles]
[/#assign]
[#assign  scripts]
<script type="text/javascript" src="assets/jqueryvalidate/jquery.validate.min.js"></script>
<script type="text/javascript">
$(function(){
    var validate=$("#itemForm").validate({
        rules:{
            loginname:{
                required:true
            },
            password:{
                required:true
            }

        },
        messages:{
            loginname:{
                required:'登录名必填'
            },
            password:{
                required:'密码必填'
            }

        }

    });
});
</script>

[/#assign]

[@admin.layout  styles=styles  scripts=scripts ]
<div class="col-md-12">
    <!-- Horizontal Form -->
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">添加管理员</h3>
        </div>
        <!-- /.box-header -->
        <!-- form start -->
        <form class="form-horizontal" id="itemForm" method="post" action="admin/user/save">
            <div class="box-body">
                <div class="form-group">
                    <label for="inputEmail3" class="col-sm-2 control-label">用户名</label>

                    <div class="col-sm-4">
                        <input type="text" name="loginname" class="form-control" id="loginname" placeholder="用户名">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3"  class="col-sm-2 control-label">昵称</label>

                    <div class="col-sm-4">
                        <input type="text" name="nickname" id="nickname" class="form-control" id="inputPassword3" placeholder="昵称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label" >密码</label>

                    <div class="col-sm-4">
                        <input type="password" name="password" id="password" class="form-control" id="inputPassword3" placeholder="密码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">确认密码</label>

                    <div class="col-sm-4">
                        <input type="password" name="confirmpassword" class="form-control" id="inputPassword3" id="confirmpassword" placeholder="确认密码">
                    </div>
                </div>
            </div>
            <!-- /.box-body -->
            <div class="box-footer">
                <button type="submit" class="btn btn-default">保存</button>
                <button type="submit" class="btn btn-info ">取消</button>
            </div>
            <!-- /.box-footer -->
        </form>
    </div>

</div>
[/@admin.layout]