		<#assign CSS_ROOT = SystemInfo.css_root>
	    <#assign JS_ROOT = SystemInfo.js_root>
	    <#assign APP_ROOT = SystemInfo.app_root>
	    <#assign IMG_ROOT = SystemInfo.img_root>
	    
		<!-- HEADER -->
		<header id="header">
			<div id="logo-group">

				<span id="logo"> <a href="/"><img src="${IMG_ROOT}/logo.png" alt="LOCCITANE Logo"></a> </span>

			</div>

			<!-- pulled right: nav area -->
			<div class="pull-right">
				
				<!-- collapse menu button -->
				<div id="hide-menu" class="btn-header pull-right">
					<span> <a href="javascript:void(0);" data-action="toggleMenu" title="Collapse Menu"><i class="fa fa-reorder"></i></a> </span>
				</div>
				<!-- end collapse menu -->
				
				<!-- logout button -->
				<div id="logout" class="btn-header transparent pull-right">
					<span> <a href="/logoutProc.do" title="Sign Out" data-action="userLogout" data-logout-msg="이 브라우저를 닫아서 로그아웃 한 후에 보안을 강화할 수 있습니다."><i class="fa fa-sign-out"></i></a> </span>
				</div>
				<!-- end logout button -->
			</div>
			<!-- end pulled right: nav area -->

		</header>
		<!-- END HEADER -->