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
								<h2>회원 정보</h2>
								
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
									
									<form id="userinfo-form" class="smart-form">
										<input type="hidden" name="user_no" value="${MyProfile.user_no?if_exists}" />
										<header>
											<strong>회원 정보</strong>
										</header>
			
										<#if MyProfile??>
										<fieldset>
											<section>
												<div class="row">
													<label class="label col col-2">회원번호</label>
													<div class="col col-10">
														<label class="input">
															${MyProfile.user_key?if_exists}
														</label>
													</div>
												</div>
											</section>
											
											<section>
												<div class="row">
													<label class="label col col-2">닉네임</label>
													<div class="col col-10">
														<label class="input">
															<input id="nickname" name="nickname" type="text" value="${MyProfile.nickname?if_exists}" />
														</label>
													</div>
												</div>
											</section>
											
											<section>
												<div class="row">
													<label class="label col col-2">이메일</label>
													<div class="col col-10">
														<label class="input">
															<input id="email" name="email" type="text" value="${MyProfile.email?if_exists}" />
														</label>
													</div>
												</div>
											</section>
											
											<section>
												<div class="row">
													<label class="label col col-2">연락처(휴대폰)</label>
													<div class="col col-10">
														<label class="input">
															<input id="mbtlnum" name="mbtlnum" type="text" value="${MyProfile.mbtlnum?if_exists}" placeholder="- 제외. 예) 01012345678" />
														</label>
													</div>
												</div>
											</section>
			
										</fieldset>
										
										<footer>
											<button type="submit" class="btn btn-primary">
												정보 수정하기
											</button>
										</footer>

										<#else>
										
										<fieldset>
											<section>
												사용자 정보가 없습니다.<br>
												회원으로 가입하세요.
											</section>
										</fieldset>
										
										<footer>
											<a href="/fhrmdlsapdls.do?isLogin=N" class="btn btn-success btn-block" role="button"> 로그인 화면이동<span> </span></a>
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
					
				</div>
				<!-- end row -->
				
			</div>
			<!-- END MAIN CONTENT -->

