			<!-- MAIN CONTENT -->
			<div id="content">
				<input type="hidden" id="isPlugin" value="Y" />

				<div class="row">
					<div class="col-xs-12 col-sm-7 col-md-7 col-lg-4">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa ${CurrMenuInfo.lna_element?if_exists}"></i> ${CurrMenuInfo.p_menu_nm?if_exists} <#if CurrMenuInfo.p_menu_nm != CurrMenuInfo.menu_nm> <span>> ${CurrMenuInfo.menu_nm?if_exists}</span></#if></h1>
					</div>
				</div>
				
				<!-- widget grid -->
				<section id="widget-grid" class="">

					<div class="well">
						<label class="col-md-1 control-label" for="wincount">회차</label>
						<div class="col-lg-2">
							<select class="form-control" id="wincount">
							</select> 
						</div>
						<span id="search" class="btn btn-default btn-primary"><i class="fa fa-search"></i> 조회</span>
					</div>
					
					<!-- row -->
					<div class="row">


					</div>

					<!-- end row -->

				</section>
				<!-- end widget grid -->
				
			</div>
			<!-- END MAIN CONTENT -->

