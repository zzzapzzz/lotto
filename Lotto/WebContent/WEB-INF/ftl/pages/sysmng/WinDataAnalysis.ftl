			<#assign JS_ROOT = SystemInfo.js_root>
			<#assign IMG_ROOT = SystemInfo.img_root>
			
			<!-- MAIN CONTENT -->
			<div id="content">
				<input type="hidden" id="isPlugin" value="Y" />
				<input type="hidden" id="win_count" value="${last_count?if_exists}" />
				<input type="hidden" id="last_count" value="${last_count?if_exists}" />

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa ${CurrMenuInfo.lna_element?if_exists}"></i> ${CurrMenuInfo.p_menu_nm?if_exists} <#if CurrMenuInfo.p_menu_nm != CurrMenuInfo.menu_nm> <span>> ${CurrMenuInfo.menu_nm?if_exists} > 당첨번호 분석</span></#if></h1>
						<a href="javascript:cancel();" class="btn btn-labeled btn-default pull-right" style="margin-left: 10px;"> <span class="btn-label"><i class="fa fa-mail-reply"></i></span>뒤로 </a>
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<!-- row -->
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
								<h3><strong>로또${last_count?if_exists}회당첨번호 및 분석</strong></h3>

								안녕하세요. 심 서방입니다.<br>
								<br>
								지난 로또 ${last_count?if_exists}회차 당첨번호는<br>
								${winNumbers?if_exists}<br>
								보너스 ${bonusNumber?if_exists}입니다.<br>
								<br>
								1등은 총 00명이 당첨되었고,<br>
								당첨금은 약 00억 0천만 원입니다.<br>
								<br>
								지난주 로또 ${last_count?if_exists}회차 당첨번호를<br>
								아래와 같이 간략하게 분석해 보았습니다.<br>
								<br>
								<br>
							</div>
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
						
						<!-- 제외수 정보 -->
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							<div id="excludeMsg"></div>
						</div>
						
						<!-- 출현 번호 정보 -->
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							<div id="containMsg"></div>
						</div>
						
						<!-- 미출현 번호 정보 -->
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							<div id="notContainMsg"></div>
						</div>
						
						<!-- 궁합수 정보 -->
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							<div id="mcMatchedMsg"></div>
						</div>
						
						<!-- 미출현 번호대 구간 정보 -->
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							<div id="zeroRangeMsg"></div>
						</div>
						<br>
						
						<!-- row -->
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
								
								<br>								
								다음 포스트는<br>
								${ex_count?if_exists}회 로또예상번호<br>
								분석 내용으로 찾아뵙겠습니다.<br>
								<br>
								이웃을 신청하시면,<br>
								심 서방이 분석하는 로또 정보를<br>
								네이버 웹 또는 앱으로<br>
								알림 받을 수 있어요.<br>
								<br>
								감사합니다.<br>
								<br>
								<br>
								태그<br>
								<br>
								로또${last_count?if_exists}회당첨번호<br>
								로또${last_count?if_exists}회당첨결과<br>
								${last_count?if_exists}회로또당첨번호<br>
								${last_count?if_exists}회로또당첨결과<br>
								로또분석<br>
								심서방로또<br>
								동행복권<br>
							</div>
						</div>
						
					</div>
				</div>
					
			</div>
			<!-- END MAIN CONTENT -->
