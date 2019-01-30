			<!-- MAIN CONTENT -->
			<div id="content">
				<input type="hidden" id="isPlugin" value="Y" />

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa ${CurrMenuInfo.lna_element?if_exists}"></i> ${CurrMenuInfo.p_menu_nm?if_exists} <#if CurrMenuInfo.p_menu_nm != CurrMenuInfo.menu_nm> <span>> ${CurrMenuInfo.menu_nm?if_exists}</span></#if></h1>
					</div>
				</div>
				
				<!-- widget grid -->
				<section id="widget-grid" class="">

					<!-- row -->
					<div class="row well">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<span id="totalTest" class="btn btn-info"><i class="fa fa-bug"></i> 총합</span>
							<span id="testAppearNumbers" class="btn btn-info"><i class="fa fa-edit"></i> 최대 출현횟수</span>
							<span id="testNumbersRange" class="btn btn-info"><i class="fa fa-edit"></i> 번호간 범위 </span>
							<span id="insertAllAcInfo" class="btn btn-success"><i class="fa fa-edit"></i> AC정보 등록</span>
						</div>
						<div id="totalRange" class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: center;">
						</div>
						<div id="totalTestResult" class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: center;">
						</div>
						
					</div>
					<!-- end row -->
					
				</section>
				<!-- end widget grid -->
				
			</div>
			<!-- END MAIN CONTENT -->

