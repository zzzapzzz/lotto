			<#assign JS_ROOT = SystemInfo.js_root>
			<#assign IMG_ROOT = SystemInfo.img_root>
			
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
				
				<!-- widget grid -->
				<section id="widget-grid" class="">

					<div class="well">
					</div>
					
					<!-- row -->
					<div id="range1Row" class="row">
						<div class="content">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<h1 class="txt-color-blueDark" style="text-align: center;">
									<strong>미출현번호</strong>
								</h1>
								<br />
							</div>
						</div>
					</div>
					<!-- end row -->
					
					<!-- row -->
					<div id="range1Row" class="row">
						<div class="content">
							<div class="col-xs-3 col-sm-3 col-md-5 col-lg-5">
								<h1 class="txt-color-blueDark" style="text-align: right;">
									<strong>1~10</strong>
								</h1>
							</div>
							<div id="range1Numbers" class="col-xs-9 col-sm-9 col-md-7 col-lg-7">
								<img src="${IMG_ROOT}/ballnumber/ball_1.png" alt="1"/>
							</div>
						</div>
					</div>
					<!-- end row -->
					
					<!-- row -->
					<div id="range2Row" class="row">
						<div class="content">
							<div class="col-xs-3 col-sm-3 col-md-5 col-lg-5">
								<h1 class="txt-color-blueDark" style="text-align: right;">
									<strong>11~20</strong>
								</h1>
							</div>
							<div id="range2Numbers" class="col-xs-9 col-sm-9 col-md-7 col-lg-7">
								<img src="${IMG_ROOT}/ballnumber/ball_19.png" alt="1"/>
							</div>
						</div>
					</div>
					<!-- end row -->
					
					<!-- row -->
					<div id="range3Row" class="row">
						<div class="content">
							<div class="col-xs-3 col-sm-3 col-md-5 col-lg-5">
								<h1 class="txt-color-blueDark" style="text-align: right;">
									<strong>21~30</strong>
								</h1>
							</div>
							<div id="range3Numbers" class="col-xs-9 col-sm-9 col-md-7 col-lg-7">
								<img src="${IMG_ROOT}/ballnumber/ball_19.png" alt="1"/>
							</div>
						</div>
					</div>
					<!-- end row -->
					
					<!-- row -->
					<div id="range4Row" class="row">
						<div class="content">
							<div class="col-xs-3 col-sm-3 col-md-5 col-lg-5">
								<h1 class="txt-color-blueDark" style="text-align: right;">
									<strong>31~40</strong>
								</h1>
							</div>
							<div id="range4Numbers" class="col-xs-9 col-sm-9 col-md-7 col-lg-7">
								<img src="${IMG_ROOT}/ballnumber/ball_19.png" alt="1"/>
							</div>
						</div>
					</div>
					<!-- end row -->
					
					<!-- row -->
					<div id="range5Row" class="row">
						<div class="content">
							<div class="col-xs-3 col-sm-3 col-md-5 col-lg-5">
								<h1 class="txt-color-blueDark" style="text-align: right;">
									<strong>41~45</strong>
								</h1>
							</div>
							<div id="range5Numbers" class="col-xs-9 col-sm-9 col-md-7 col-lg-7">
								<img src="${IMG_ROOT}/ballnumber/ball_19.png" alt="1"/>
							</div>
						</div>
					</div>
					<!-- end row -->

				</section>
				<!-- end widget grid -->
				
			</div>
			<!-- END MAIN CONTENT -->

