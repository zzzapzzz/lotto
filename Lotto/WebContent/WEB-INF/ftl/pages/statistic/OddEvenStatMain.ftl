			<#assign JS_ROOT = SystemInfo.js_root>
			<#assign IMG_ROOT = SystemInfo.img_root>
			
			<!-- chart.js -->
			<script src="${JS_ROOT}/plugin/chartjs/Chart.min.js"></script>
			<script src="${JS_ROOT}/plugin/chartjs/Chart.bundle.js"></script>
			<script src="${JS_ROOT}/plugin/chartjs/utils.js"></script>
			
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
					<div class="row">
						<div class="content">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<canvas id="chartArea" width="500" height="500"></canvas>
							</div>
						</div>
					</div>
					<!-- end row -->

				</section>
				<!-- end widget grid -->
				
			</div>
			<!-- END MAIN CONTENT -->

