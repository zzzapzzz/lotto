			<style type="text/css">
			/* inputbox */
			.smart-form .input_va_m input {
			    box-sizing: border-box;
			    -moz-box-sizing: border-box;
			    width: 100%;
			    height: 32px;
			    line-height: 32px;
			    padding: 5px 10px;
			    outline: 0;
			    border-width: 1px;
			    border-style: solid;
			    border-radius: 0;
			    background: #fff;
			    font: 13px/16px 'Open Sans', Helvetica, Arial, sans-serif;
			    color: #404040;
			    appearance: normal;
			    -moz-appearance: none;
			    -webkit-appearance: none
			}
			
			.smart-form .checkbox {
    			font-weight: 400
			}
			</style>
			
			<!-- MAIN CONTENT -->
			<div id="content">
				<input type="hidden" id="isPlugin" value="Y" />
				<input type="hidden" id="isLogin" value="${isLogin?if_exists}" />
				<input type="hidden" id="checkMsg" value="${checkMsg?if_exists}" />

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
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-3">
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
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-3">
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
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-3 left-pull">
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
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-3 left-pull">
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
							<br />
							<div class="row">		
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-6">
									<label class="label">총합</label>
									<label class="input_va_m">
										<input type="text" style="width: 40%;" id="low_total" name="low_total" />&nbsp;~&nbsp;
										<input type="text" style="width: 40%;" id="high_total" name="high_total" />
									</label>
									<input type="checkbox" id="chk_this_week_rcmd_total" name="chk_this_week_rcmd_total" value="금주의 추천"/>금주의 추천
								</div>
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-6">
									<label class="label">끝수합</label>
									<label class="input_va_m">
										<input type="text" style="width: 40%;" id="low_endsum" name="low_total" />&nbsp;~&nbsp;
										<input type="text" style="width: 40%;" id="high_endsum" name="high_total" />
									</label>
									<input type="checkbox" id="chk_this_week_rcmd_endsum" name="chk_this_week_rcmd_endsum" value="금주의 추천"/>금주의 추천
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
