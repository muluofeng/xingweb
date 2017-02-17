[#assign styles]
<link rel="stylesheet" type="text/css" href="assets/adminLTE/plugins/datatables/dataTables.bootstrap.css">
[/#assign]
[#assign  scripts]
<!-- DataTables -->
<script src="assets/adminLTE/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="assets/adminLTE/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="assets/layer/layer.js">
<script src="assets/moment/moment.js"></script>
<script>
    $(function () {
        $('#example2').DataTable({
            bLengthChange:false,
            serverSide:true,  //服务器处理
            ordering:false,
            searching:false,
            ajax: {
                url: 'admin/user/userlist',
                type: 'POST',
            },
            language: {
                url: 'assets/datables/i18n/Chinese.json'
            },
            columns:[
                {data:'loginname'},
                {data:'nickname'},
                {data:'status',render:function(value){return value==1?"启用":"禁用";}},
                {data:'ctime',render:function(value){return moment(new Date(value)).format("YYYY-MM-DD") }},
                {data:'super',render:function(value){return  value?"是":"否";}}
            ],
            columnDefs: [{
                "targets": [5],
                "data": null,
                "render":function(data,type,row,mata){
                    return "<a href='javascript:void(0)' class='deleteById'  data-id="+data.id+">删除</a>";
                }
            }]
        });

        //新增管理员
        $("#addUser").on("click",function(){
           location.href="/admin/user/add";
        })

        //删除管理员
        $('#example2').on('click', '.deleteById', function () {
            var id=$(this).data("id");
            layer.confirm("确认删除",{
                btn:["确认","取消"]
            },function(){
                $.ajax({
                    url:"/admin/user/delete",
                    data:"id="+id,
                    type:'post',
                    dataType:"json",
                    success:function (  re) {
                        if(re.success){
                            layer.msg("删除成功");
                            location.reload();

                        }else{
                            layer.msg("删除失败")
                        }
                    }
                });
            });
        });
//        $(".deleteById").on('click',function() {
//                alert(123)
//        });
    });
</script>
[/#assign]
[@admin.layout title="后台首页"  scripts=scripts styles=styles]
<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">管理员列表</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <div>
                    <a class="btn btn-primary pull-right" id="addUser">新增管理员</a>
                </div>
                <table id="example2" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>用户名</th>
                        <th>昵称</th>
                        <th>状态</th>
                        <th>创建时间</th>
                        <th>超级管理员</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                    <tfoot>

                    </tfoot>
                </table>
            </div>
            <!-- /.box-body -->
        </div>
        <!-- /.box -->
    </div>
    <!-- /.col -->
</div>
[/@admin.layout]