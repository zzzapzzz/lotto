			<!-- MAIN CONTENT -->
			<div id="content">
				<input type="hidden" id="isPlugin" value="Y" />
				<input type="hidden" id="isLogin" value="${isLogin?if_exists}" />
				<input type="hidden" id="checkMsg" value="${checkMsg?if_exists}" />
				<input type="hidden" id="status" value="${status?if_exists}" />

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa ${CurrMenuInfo.lna_element?if_exists}"></i> ${CurrMenuInfo.p_menu_nm?if_exists} <#if CurrMenuInfo.p_menu_id != 0> <span>> ${CurrMenuInfo.menu_nm?if_exists} > ${sub_menu_nm?if_exists}</span></#if></h1>
					</div>
				</div>
				
				<div class="row">
					<!-- NEW COL START -->
					<article class="col-sm-12 col-md-12 col-lg-6">
			
						<!-- Widget ID (each widget will need unique ID)-->
						<div class="jarviswidget" id="wid-id-1" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-custombutton="false">
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
								<span class="widget-icon"> <i class="fa fa-edit"></i> </span>
								<h2>${sub_menu_nm?if_exists}</h2>
							</header>
							
							<!-- widget div start -->
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
			
									<form id="writestep1-form" class="smart-form">
										<header>기본 정보</header>
			
										<fieldset>
											
											<section>
												<label class="label">서비스구분</label>
												<label class="select">
													<select id="svc_type" name="svc_type" class="input-sm">
														<option value="B2C">B2C</option>
														<option value="B2B">B2B</option>
													</select> <i></i> </label>
											</section>
											
											<section>
												<label class="label">서비스코드</label>
												<label class="input">
													<input id="svc_cd" name="svc_cd" type="text" class="input-xs">
												</label>
											</section>
											
											<section>
												<label class="label">서비스명</label>
												<label class="input">
													<input id="svc_nm" name="svc_nm" type="text" class="input-xs">
												</label>
											</section>
											
											<section>
												<label class="label">서비스기준</label>
												<label class="select">
													<select id="svc_base_type" name="svc_base_type" class="input-sm">
														<option value="M">월</option>
														<option value="C">건</option>
													</select> <i></i> </label>
											</section>
											
											<section>
												<label class="label">서비스기준값</label>
												<label class="input">
													<input id="svc_base_val" name="svc_base_val" type="text" class="input-xs">
												</label>
											</section>
											
											<section>
												<label class="label">금액</label>
												<label class="input">
													<input id="amount" name="amount" type="text" class="input-xs">
												</label>
											</section>
											
											<section>
												<label class="label">프로모션</label>
												<label class="select">
													<select id="prmt_cd" name="prmt_cd" class="input-sm">
													</select> <i></i> </label>
											</section>
											
											<section>
												<label class="label">사용여부</label>
												<label class="select">
													<select id="use_yn" name="use_yn" class="input-sm">
														<option value="Y">사용</option>
														<option value="N">미사용</option>
													</select> <i></i> </label>
											</section>
										</fieldset>
										
										<footer>
											<button type="submit" class="btn btn-primary">
												등록
											</button>
											<button type="button" class="btn btn-default" onclick="javascript:cancel();">
												취소
											</button>
										</footer>
											
									</form>
									
								</div>
								<!-- end widget content -->
								
							</div>
							<!-- end widget div -->
							
						</div>
						<!-- end widget -->
						
					</article>
					<!-- END COL -->
					
				</div>
				<!-- END ROW -->
				
			</div>
			<!-- END MAIN CONTENT -->