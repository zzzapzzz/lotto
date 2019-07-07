			<!-- MAIN CONTENT -->
			<div id="content">
				<input type="hidden" id="isPlugin" value="Y" />

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa ${CurrMenuInfo.lna_element?if_exists}"></i> ${CurrMenuInfo.p_menu_nm?if_exists} <#if CurrMenuInfo.p_menu_id != 0> <span>> ${CurrMenuInfo.menu_nm?if_exists}</span></#if></h1>
					</div>
				</div>
				
				<!-- widget grid -->
				<section id="widget-grid" class="">

					<div class="well">
						<label class="col-md-1 control-label" for="win_count">회차</label>
						<div class="col-lg-2">
							<select class="form-control" id="win_count">
							</select> 
						</div>						
						<span id="search" class="btn btn-default btn-primary"><i class="fa fa-search"></i> 조회</span>
					</div>
					
					
					<!-- row -->
					<div class="row well">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<h1 id="win_data_title" class="txt-color-blueDark" style="text-align: center;">
							</h1>
						</div>
						<div id="win_numbers" class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: center;">
						</div>
						
					</div>
					<!-- end row -->
					
					<!-- table1 -->
					<div class="table-responsive">
						<table class="table table-bordered" style="font-size:15px; text-align: center; margin-left: auto; margin-right: auto;">
							<thead>
								<tr>
									<th>저고비율</th>
									<th>홀짝비율</th>
									<th>총합</th>
									<th>끝수합</th>
									<th>AC</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><span id="low_high"></span></td>
									<td><span id="odd_even"></span></td>
									<td><span id="total"></span></td>
									<td><span id="sum_end_num"></span></td>
									<td><span id="ac"></span></td>
								</tr>
							</tbody>
						</table>
						<table class="table table-bordered" style="text-align: center; margin-left: auto; margin-right: auto;">
							<thead>
								<tr>
									<th>회차합</th>
									<th>포함개수</th>
									<th calspan="3">미포함개수</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><span id="count_sum"></span></td>
									<td><span id="cont_cnt"></span></td>
									<td calspan="3"><span id="not_cont_cnt"></span></td>
								</tr>
							</tbody>
						</table>
						<table class="table table-bordered" style="text-align: center; margin-left: auto; margin-right: auto;">
							<thead>
								<tr>
									<th calspan="5">궁합수</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td calspan="5"><span id="mcMatchedData"></span></td>
								</tr>
							</tbody>
						</table>
						<table class="table table-bordered" style="text-align: center; margin-left: auto; margin-right: auto;">
							<thead>
								<tr>
									<th calspan="5">미출현 번호대</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td calspan="5"><span id="zeroRange"></span></td>
								</tr>
							</tbody>
						</table>
					</div>
					
					<div class="row">
						<div class="content">
							<!-- 저고 비율 정보 -->
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
								<div id="lowHighMsg"></div>
							</div>
							
							<!-- 홀짝 비율 정보 -->
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
								<div id="oddEvenMsg"></div>
							</div>
							
							<!-- 총합 정보 -->
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
								<div id="totalMsg"></div>
							</div>
							
							<!-- 끝수합 정보 -->
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
								<div id="endnumMsg"></div>
							</div>
							
							<!-- AC 정보 -->
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
								<div id="acMsg"></div>
							</div>
							<br>
						</div>
					</div>
				</section>
				<!-- end widget grid -->
				
			</div>
			<!-- END MAIN CONTENT -->

