<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img
                    [#if Session.admin_user.avatar]
                            src="${Session.admin_user.avatar}"
                    [#else]
                            src="assets/adminLTE/dist/img/user2-160x160.jpg"
                    [/#if] class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p>${Session.admin_user.username}</p>
                <a href="#"><i class="fa fa-circle text-success"></i> 在线</a>
            </div>
        </div>
        <!-- search form -->
        <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
                <input type="text" name="q" class="form-control" placeholder="Search...">
                <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
            </div>
        </form>
        <!-- /.search form -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">MAIN NAVIGATION</li>
            <li class="[#if menu?index_of("admin/user")!=-1]  [/#if] treeview">
                <a href="#">
                    <i class="fa fa-users"></i> <span>管理员</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu" [#if menu=="admin/user"]style="display: block"[/#if]>
                    <li  [#if menu=="admin/user"]class="activeMine"[/#if]><a href="/admin/user"><i class="fa fa-user"></i>管理员列表</a></li>
                </ul>
            </li>
            <li class="[#if menu?index_of("admin/image")!=-1]  [/#if] treeview">
                <a href="#">
                    <i class="fa fa-file-image-o"></i>
                    <span>图片管理</span>
                    <span class="pull-right-container"></span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu" [#if menu=="admin/image"]style="display: block"[/#if]>
                    <li [#if menu=="admin/image"]class="activeMine"[/#if]><a href="/admin/image"><i class="fa fa-circle-o"></i> 图片列表</a></li>
                </ul>
            </li>

            <li><a href="documentation/index.html"><i class="fa fa-book"></i> <span>文档</span></a></li>
            <li class="header">LABELS</li>
            <li><a href="#"><i class="fa fa-circle-o text-red"></i> <span>Important</span></a></li>
            <li><a href="#"><i class="fa fa-circle-o text-yellow"></i> <span>Warning</span></a></li>
            <li><a href="#"><i class="fa fa-circle-o text-aqua"></i> <span>Information</span></a></li>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>