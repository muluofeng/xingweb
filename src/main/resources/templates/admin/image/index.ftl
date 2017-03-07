[#assign prestyles]
<link rel="stylesheet" type="text/css" href="assets/adminLTE/plugins/datatables/dataTables.bootstrap.css">
[/#assign]
[#assign dialog]
<div class="" style="margin-top:10px;text-align: center">
        <div class="box-body">
            <form class="form-horizontal" id="imageForm" action="/admin/image/upload" enctype="multipart/form-data" method="POST">
                <input class="text-center" type="hidden" name="w" id="w"/>
                <input class="text-center" type="hidden" name="h" id="h"/>

                <div class="form-group">
                    <label for="inputEmail3" class="col-sm-2 control-label">图片</label>

                    <div class="col-sm-4">
                        <input type="file" name="file" id="image"/>
                    </div>
                </div>




            </form>
        </div>
</div>
[/#assign]
[#assign  scripts]
<!-- DataTables -->
<script src="assets/adminLTE/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="assets/adminLTE/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="assets/layer/layer.js"></script>
<script src="assets/moment/moment.js"></script>
<script src="assets/js/AjaxFileUpload.js"></script>
<script>
    $(function () {
        //加载datables
        $('#example2').DataTable({
            dom: 'lfrtip',
            bLengthChange: false,
            serverSide: true,  //服务器处理
            ordering: false,
            searching: true,
            ajax: {
                url: 'admin/image/getList',
                type: 'POST',
            },
            language: {
                url: 'assets/datables/i18n/Chinese.json'
            },
            columns: [
                {data: 'name'},
                {data: 'width'},
                {data: 'height'},
                {
                    data: 'lastModified', render: function (value) {
                    return moment(new Date(value)).format("YYYY-MM-DD")
                }
                },
                {
                    data: 'url', render: function (value) {
                    return "<img class='showDiv' width='15px' height='10px' src='" + value + "'/>";
                }
                }
            ],
            columnDefs: [{
                "targets": [5],
                "data": null,
                "render": function (data, type, row, mata) {
                    return "<a href='javascript:void(0)' class='deleteById'  data-id=" + data.id + ">删除|</a>" + "" + "<a href='javascript:void(0)' class='showImage' data-id='" + data.url + "'> 预览</a>";
                }
            }]
        })
        ;

        //预览图片
        $('#example2').on('click', '.showImage', function () {
            var url = $(this).data("id");
            var jsonData = {
                "title": "pic", //相册标题
                "id": 123, //相册id
                "start": 0, //初始显示的图片序号，默认0
                "data": [   //相册包含的图片，数组格式
                    {
                        "alt": "图片名",
                        "pid": 666, //图片id
                        "src": url, //原图地址
                        "thumb": url //缩略图地址
                    }
                ]
            };
            layer.photos({
                photos: jsonData,
            });
        });

        //上传图片
        $("#uploadImage").on("click", function () {
            layer.open({
                type: 1 //此处以iframe举例
                , title: '图片上传'
                , area: ['390px', '330px']
                , closeBtn: 0
                , shade: 0
                , offset: [ //为了演示，随机坐标
                    ($(window).height() / 2 - 330 / 2)
                    , ($(window).width() / 2 - 390 / 2)
                ]
                , content: '${dialog?js_string}'
                , btn: ['保存', '取消'] //只是为了演示
                , yes: function () {
                    var f = document.getElementById("image").files[0];
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        var data = e.target.result;
                        //加载图片获取图片真实宽度和高度
                        var image = new Image();
                        image.onload = function () {
                            var width = image.width;
                            var height = image.height;
                            $("#w").val(width);
                            $("#h").val(height);
                            $("#imageForm").submit();
                        };
                        image.src = data;
                    };
                    reader.readAsDataURL(f);
                }
            });

        })

        //删除管理员
//        $('#example2').on('click', '.deleteById', function () {
//            var id = $(this).data("id");
//            layer.confirm("确认删除", {
//                btn: ["确认", "取消"]
//            }, function () {
//                $.ajax({
//                    url: "/admin/user/delete",
//                    data: "id=" + id,
//                    type: 'post',
//                    dataType: "json",
//                    success: function (re) {
//                        if (re.success) {
//                            layer.msg("删除成功");
//                            location.reload();
//
//                        } else {
//                            layer.msg("删除失败")
//                        }
//                    }
//                });
//            });
//        });


    });
</script>
[/#assign]
[#assign prescript]
[#--<script src="assets/webUploader/js/webuploader.js"></script>
<script src="assets/webUploader/js/upload.js"></script>--]
[/#assign]
[@admin.layout title="图片列表"  menu="admin/image" preScripts=prescript scripts=scripts preStyles=prestyles]
<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title"></h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <div>
                    <a class="btn btn-primary pull-left" id="uploadImage">上传图片</a>
                </div>
                <table id="example2" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>图片Id</th>
                        <th>宽度</th>
                        <th>长度</th>
                        <th>最后更新时间</th>
                        <th>图片地址</th>
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