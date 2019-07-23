			<!-- MAIN CONTENT -->
			<div id="content">
				<input type="hidden" id="isPlugin" value="Y" />

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa ${CurrMenuInfo.lna_element?if_exists}"></i> ${CurrMenuInfo.p_menu_nm?if_exists} <#if CurrMenuInfo.p_menu_id != 0> <span>> ${CurrMenuInfo.menu_nm?if_exists} > MY로또 필터등록</span></#if></h1>
						${ex_count?if_exists}&nbsp;회차 필터 등록 화면입니다. 원하시는 조건으로 설정하여 번호조합을 등록하세요.
					</div>
				</div>
				
				<!-- section -->
				<section class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<form id="writestep1-form" name="writestep1-form" class="smart-form client-form" >
						<fieldset>
							<div class="row">
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-2">
									<label class="label">저고비율</label>
									<label class="select">
										<select id="lowhigh_type" name="lowhigh_type">
											<option value="" selected>선택</option>
											<option value="0:6">0:6</option>
											<option value="1:5">1:5</option>
											<option value="2:4">2:4</option>
											<option value="3:3">3:3</option>
											<option value="4:2">4:2</option>
											<option value="5:1">5:1</option>
											<option value="6:0">6:0</option>
										</select>
									</label>
								</div>		
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-2">
									<label class="label">홀짝비율</label>
									<label class="select">
										<select id="oddeven_type" name="oddeven_type">
											<option value="" selected>선택</option>
											<option value="0:6">0:6</option>
											<option value="1:5">1:5</option>
											<option value="2:4">2:4</option>
											<option value="3:3">3:3</option>
											<option value="4:2">4:2</option>
											<option value="5:1">5:1</option>
											<option value="6:0">6:0</option>
										</select>
									</label>
								</div>		
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-2 left-pull">
									<label class="label">회차합</label>
									<label class="select">
										<select id="count_sum" name="count_sum">
											<option value="" selected>선택</option>
											<option value="6">6</option>
											<option value="7">7</option>
											<option value="8">8</option>
											<option value="9">9</option>
											<option value="10">10</option>
										</select>
									</label>
								</div>		
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-2 left-pull">
									<label class="label">AC</label>
									<label class="select">
										<select id="ac" name="ac">
											<option value="" selected>선택</option>
											<option value="7">7</option>
											<option value="8">8</option>
											<option value="9">9</option>
											<option value="10">10</option>
										</select>
									</label>
								</div>		
							</div>
							<div class="row">		
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-4">
									<label class="label">총합</label>
									<label class="input">
										<input type="text" style="width: 30%;" id="low_total" name="low_total" />
										<input type="text" style="width: 30%;" id="high_total" name="high_total" />
										<input type="checkbox" id="chk_this_week_rcmd_total" name="chk_this_week_rcmd_total" value="금주의 추천"/>
									</label>
								</div>		
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-2 left-pull">
									<label class="label">번호6</label>
									<label class="input">
										<input type="text" id="num6" name="num6" />
									</label>
								</div>		
							</div>
						</fieldset>
					</form>
					
					<!-- row -->
					<div class="row">
						<div class="content">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<a href="javascript:save();" class="btn btn-labeled btn-success pull-left"> <span class="btn-label"><i class="fa fa-pencil"></i></span>저장 </a>
								<a href="javascript:cancel();" class="btn btn-labeled btn-default pull-left" style="margin-left: 10px;"> <span class="btn-label"><i class="fa fa-mail-reply"></i></span>취소 </a>							
							</div>
						</div>
					</div>				
				</section>									
				<!-- end section -->

			</div>
			<!-- END MAIN CONTENT -->
