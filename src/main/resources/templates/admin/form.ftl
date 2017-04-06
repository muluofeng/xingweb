[#assign styles]
<link rel="stylesheet"  href="assets/jquery-upload-file/uploadfile.css"/>
[/#assign]
[#assign scripts]
<script src="assets/jquery-upload-file/jquery.uploadfile.min.js"></script>
<script type="text/javascript">
    $(document).ready(function()
    {
        $("#fileuploader").uploadFile({
            url:"admin/image/upload",
            fileName:"file",
            showDone: true,                     //是否显示"Done"(完成)按钮
            showDelete: true,                  //是否显示"Delete"(删除)按钮
            allowedTypes: 'jpg,jpeg,gif',  //允许上传的文件式
        });
    });
</script>
[/#assign]
[@admin.layout title="form" menu="admin/form"   styles=styles scripts=scripts]
<div id="fileuploader">Upload</div>
[/@admin.layout]