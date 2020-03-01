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
						<label class="col-md-1 control-label" for="ex_count">회차</label>
						<div class="col-lg-2">
							<select class="form-control" id="ex_count">
							</select> 
						</div>						
						<span id="search" class="btn btn-default btn-primary" onclick="javascript:searchGo();"><i class="fa fa-search"></i> 조회</span>
					</div>
					
					<span id="add" class="btn btn-success" onclick="javascript:addGo();"><i class="fa fa-pencil"></i> 일반등록</span>
					<span id="autoAdd" class="btn btn-warning" onclick="javascript:autoAddCheckGo();"><i class="fa fa-edit"></i> 자동등록</span>
					<span id="filterAdd" class="btn btn-info" onclick="javascript:filterAddGo();"><i class="fa fa-edit"></i> 필터조합등록</span>
					<span id="del" class="btn btn-danger" onclick="javascript:delGo();"><i class="fa fa-trash-o"></i> 선택삭제</span>
					<span id="autoAddibm1077" class="btn btn-warning" onclick="javascript:autoAddibm1077();"><i class="fa fa-edit"></i> 태화강 자동등록</span>
					
					
					<!-- row -->
					<div class="row">

						<!-- NEW WIDGET START -->
						<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

							<table id="jqgrid"></table>
							<div id="pjqgrid"></div>

						</article>
						<!-- WIDGET END -->

					</div>
					<!-- end row -->
					
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

