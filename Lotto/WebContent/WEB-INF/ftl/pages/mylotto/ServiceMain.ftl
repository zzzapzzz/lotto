			<!-- MAIN CONTENT -->
			<div id="content">
				<input type="hidden" id="isPlugin" value="Y" />
				<input type="hidden" id="isLogin" value="${isLogin?if_exists}" />
				<input type="hidden" id="checkMsg" value="${checkMsg?if_exists}" />
				<input type="hidden" id="status" value="${status?if_exists}" />

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa ${CurrMenuInfo.lna_element?if_exists}"></i> ${CurrMenuInfo.p_menu_nm?if_exists} <#if CurrMenuInfo.p_menu_id != 0> <span>> ${CurrMenuInfo.menu_nm?if_exists}</span></#if></h1>
					</div>
				</div>
				
				<div class="row">
					<!-- NEW COL START -->
					<article class="col-sm-12 col-md-12 col-lg-6">
						<!-- Widget ID (each widget will need unique ID)-->
						<div class="jarviswidget" id="serviceInfomation" data-widget-editbutton="false" data-widget-custombutton="false">
							<!-- widget options:
								usage: <div class="jarviswidget" id="wid-id-0" data-widget-editbutton="false">
								
								data-widget-colorbutton="false"	
								data-widget-editbutton="false"
								data-widget-togglebutton="false"
								data-widget-deletebutton="false"
								data-widget-fullscreenbutton="false"
								data-widget-custombutton="false"
								data-widget-collapsed="true" 
								data-widget-sortable="false"
								
							-->
							<header>
								<span class="widget-icon"> <i class="fa fa-info"></i> </span>
								<h2>서비스정보</h2>
								
							</header>
			
							<!-- widget div-->
							<div>
								
								<!-- widget edit box 
								<div class="jarviswidget-editbox">
								-->
									<!-- This area used as dropdown edit box 
								</div>
									-->
								<!-- end widget edit box -->
								
								<!-- widget content -->
								<div class="widget-body no-padding">
									
									<form id="review-form" class="smart-form">
										<header>
											<strong>서비스 상세정보</strong>
										</header>
			
										<#if ServiceApply??>
										<fieldset>
											<section>
												<label class="label">서비스명</label>
												<label class="input">
													<#if ServiceApply??>
													${ServiceApply.svc_nm?if_exists} 
													</#if>
												</label>
											</section>
											
											<section>
												<label class="label">서비스상태</label>
												<label class="input">
													<#if ServiceApply??>
													${ServiceApply.svc_stat?if_exists} 
													</#if>
												</label>
											</section>
											
											<section>
												<label class="label">시작일자</label>
												<label class="input">
													<#if ServiceApply??>
													${ServiceApply.start_date?if_exists} 
													</#if>
												</label>
											</section>
			
											<section>
												<label class="label">종료일자</label>
												<label class="input">
													<#if ServiceApply??>
													${ServiceApply.end_date?if_exists} 
													</#if>
												</label>
											</section>
			
										</fieldset>

										<footer>
											<button type="submit" class="btn btn-danger">
												해지 신청
											</button>
										</footer>
										
										<#else>
										
										<fieldset>
											<section>
												이용중인 서비스가 없습니다.<br>
												<br>
												아래의 "<a href="/info/service.do">이용안내</a>"를 클릭하여<br>
												이용 가능한 서비스를 확인하고 신청하세요.<br>
												<br>
												서비스 이용 신청중이라면<br>
												조금만 기다려주세요.<br>
												승인이 완료되면 이용하실 수 있습니다.
											</section>
										</fieldset>
										
										<footer>
											<a href="/info/service.do" class="btn btn-success btn-block" role="button"> 이용안내<span> </span></a>
										</footer>
										
										</#if>
									</form>						
									
								</div>
								<!-- end widget content -->
								
							</div>
							<!-- end widget div -->
							
						</div>
						<!-- end widget -->	
					</article>
					
					<!-- NEW COL START -->
					<article class="col-sm-12 col-md-12 col-lg-6">
						<!-- Widget ID (each widget will need unique ID)-->
						<div class="jarviswidget" id="serviceInfomation" data-widget-editbutton="false" data-widget-custombutton="false">
							<!-- widget options:
								usage: <div class="jarviswidget" id="wid-id-0" data-widget-editbutton="false">
								
								data-widget-colorbutton="false"	
								data-widget-editbutton="false"
								data-widget-togglebutton="false"
								data-widget-deletebutton="false"
								data-widget-fullscreenbutton="false"
								data-widget-custombutton="false"
								data-widget-collapsed="true" 
								data-widget-sortable="false"
								
							-->
							<header>
								<span class="widget-icon"> <i class="fa fa-list"></i> </span>
								<h2>서비스 신청목록</h2>
								
							</header>
			
							<!-- widget div-->
							<div>
								
								<!-- widget edit box 
								<div class="jarviswidget-editbox">
								-->
									<!-- This area used as dropdown edit box 
								</div>
									-->
								<!-- end widget edit box -->
								
								<!-- widget content -->
								<div class="widget-body no-padding">
								
									<table id="jqgrid"></table>
									<div id="pjqgrid"></div>
								
								</div>
								<!-- end widget content -->
								
							</div>
							<!-- end widget div -->
							
						</div>
						<!-- end widget -->	
					</article>		
				</div>
				<!-- end row -->	
				
			</div>
			<!-- END MAIN CONTENT -->