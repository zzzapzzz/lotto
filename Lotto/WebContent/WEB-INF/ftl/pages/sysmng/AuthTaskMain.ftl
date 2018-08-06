			<#assign CSS_ROOT = SystemInfo.css_root>
			
			<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/tree_noicon.min.css" />
			
			<!-- MAIN CONTENT -->
			<div id="content">
				<input type="hidden" id="isPlugin" value="Y" />

				<div class="row">
					<div class="col-xs-12 col-sm-7 col-md-7 col-lg-4">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa ${CurrMenuInfo.lna_element?if_exists}"></i> ${CurrMenuInfo.p_menu_nm?if_exists} <#if CurrMenuInfo.p_menu_nm != CurrMenuInfo.menu_nm> <span>> ${CurrMenuInfo.menu_nm?if_exists}</span></#if></h1>
					</div>
				</div>
				
				<div class="col-md-12">
		            <div class="well">
						<section class="col col-6">
							<input id="searchKey" type="text" placeholder="Search..." />
							<span id="search" class="btn btn-default btn-primary"><i class="fa fa-search"></i> 조회</span>
							<span id="add" class="btn btn-success"><i class="fa fa-pencil"></i> 등록</span>
							<span id="modify" class="btn btn-warning"><i class="fa fa-edit"></i> 수정</span>
							<span id="del" class="btn btn-danger"><i class="fa fa-trash-o"></i> 삭제</span>
						</section>
					</div>
				</div>
						
				<!-- widget grid -->
				<section id="widget-grid" class="">
				
					<!-- row -->
					<div class="row">
				
						<!-- NEW WIDGET START -->
						<article id="article1" class="col-sm-12 col-md-12 col-lg-6">
							
							<table id="jqgrid"></table>
							<div id="pjqgrid"></div>
							
				
						</article>
						<!-- WIDGET END -->
				
						<!-- NEW WIDGET START -->
						<article id="article2" class="col-sm-12 col-md-12 col-lg-6">
							
							<div id="tree" ></div>
							<span id="save" class="btn btn-success"><i class="fa fa-pencil"></i> 권한설정 저장</span>
										
						</article>
						<!-- WIDGET END -->
				
					</div>
					<!-- end row -->
				
				</section>
				<!-- end widget grid -->
				
				<!-- dim-layer -->
				<div class="dim-layer">
				    <div class="dimBg"></div>
				    
				    <div id="authCodePopup" class="pop-layer">
				        <div class="pop-container">
				            <div class="pop-conts">
				                <!--content //-->
				                <form action="" id="authinput-form" class="smart-form client-form">
									<header>
										<span id="authCodeTitle"></span>
									</header>
	
									<fieldset>
										<section>
											<label class="label">권한코드</label>
											<label id="lbl_auth_cd" class="input">
												<input type="text" id="auth_cd" name="auth_cd">
												<input type="hidden" id="bf_auth_cd" name="bf_auth_cd">
												<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 권한코드를 입력하세요.</b>
											</label>
										</section>
	
										<section>
											<label class="label">권한명</label>
											<label class="input">
												<input type="text" id="auth_nm" name="auth_nm">
												<input type="hidden" id="bf_auth_nm" name="bf_auth_nm">
												<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 권한명을 입력하세요.</b> 
											</label>
										</section>
									</fieldset>
									<footer>
										<button type="submit" class="btn btn-primary">
											저장
										</button>
										<button id="btnClose" class="btn btn-default">
											취소
										</button>
									</footer>
								</form>
				                <!--// content-->
				            </div>
				        </div>
				    </div>
				    
				</div>
				<!-- end dim-layer -->
				
			</div>
			<!-- END MAIN CONTENT -->
