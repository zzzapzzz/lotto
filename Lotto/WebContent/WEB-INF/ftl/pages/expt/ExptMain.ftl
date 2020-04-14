			<!-- MAIN CONTENT -->
			<div id="content">
				<input type="hidden" id="isPlugin" value="Y" />
				<input type="hidden" id="isLogin" value="${isLogin?if_exists}" />
				<input type="hidden" id="checkMsg" value="${checkMsg?if_exists}" />
				<input type="hidden" id="status" value="${status?if_exists}" />
				<input type="hidden" id="ex_count" value="${ex_count?if_exists}" />

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa ${CurrMenuInfo.lna_element?if_exists}"></i> ${CurrMenuInfo.p_menu_nm?if_exists} <#if CurrMenuInfo.p_menu_id != 0> <span>> ${CurrMenuInfo.menu_nm?if_exists}</span></#if></h1>
					</div>
				</div>
				
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
						<h3><strong>로또 ${ex_count?if_exists}회차 예상번호</strong></h3>
					</div>
				</div>
				
				<!-- widget grid -->
				<section id="widget-grid" class="">

					<!--					
					<div class="well">
					</div>
					-->
					
					<span id="search10" class="btn btn-success" onclick="javascript:search10();"><i class="fa fa-search"></i> 10조합</span>
					
					
					<!-- row -->
					<div class="row">
						<div id="ex_numbers" class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: center;">
							
						</div>
					</div>
					<!-- end row -->

				</section>
				<!-- end widget grid -->
				
			</div>
			<!-- END MAIN CONTENT -->

