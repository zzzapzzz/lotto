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
				<input type="hidden" id="ex_count" value="${ex_count?if_exists}" />
				<input type="hidden" id="last_count" value="${last_count?if_exists}" />

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa ${CurrMenuInfo.lna_element?if_exists}"></i> ${CurrMenuInfo.p_menu_nm?if_exists} <#if CurrMenuInfo.p_menu_id != 0> <span>> ${CurrMenuInfo.menu_nm?if_exists} > 예상번호 분석</span></#if></h1>
						<a href="javascript:cancel();" class="btn btn-labeled btn-default pull-right" style="margin-left: 10px;"> <span class="btn-label"><i class="fa fa-mail-reply"></i></span>뒤로 </a>
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							<h3><strong>로또${ex_count?if_exists}회예상번호 및 분석</strong></h3>
							
							안녕하세요. 심 서방입니다.<br>
							<br>
							이번 주 ${nextAnnounceDate?if_exists}에 발표되는<br>
							#로또${ex_count?if_exists}회예상번호<br>
							<br>							
							로또 당첨번호를 분석하여<br>
							전체 조합수 중에서 당첨번호 조합으로<br>
							잘 출현하지 않는 조합 패턴을 찾아<br>
							제외하는 필터를 적용하여<br>
							번호조합 범위를 줄여<br>							
							예상번호 조합 30개를 예측했습니다.<br>
							<br>
							아래의 예상번호 30조합은<br>
							무작위로 예측한 번호입니다.<br>
							당첨이 될 수도 있고,<br>
							안 될 수도 있으니<br>
							오해 없으시길 바랍니다.<br>														
							<br>
						</div>
						
						<div id="ex_numbers" class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: center;">
							
						</div>
						<br>
						
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							<h3><strong>로또 ${ex_count?if_exists}회 예상번호 분석</strong></h3>

							<br>
							로또 ${ex_count?if_exists}회차 예상번호는<br>
							1~${last_count?if_exists}회 당첨번호들을 분석하여 얻은<br>
							아래의 자료를 참고했습니다.<br>
							<br>
							저고 비율은<br>
							당첨번호 숫자 중<br>
							23 보다 작은 숫자를 낮은 수(저),<br>
							그 외 숫자를 높은 수(고)로<br>
							정의했습니다.<br>
							<br>
							지난 1~${last_count?if_exists}회 당첨번호의<br>
							저고 비율을 분석해 보니<br>
							아래의 그림과 같이 2:4, 3:3, 4:2에서<br>
							당첨번호가 가장 많이 출현했습니다.<br>
							<br>
							지난 ${last_count?if_exists}회 당첨번호 또한<br>
							${last_lowhigh?if_exists}의 비율로 당첨되었죠.<br>
							<br>
							
							<canvas id="barChartLowHigh" height="120"></canvas>
							
							<br>
							최근 10회 동안의<br>
							저고 비율 출현 패턴을<br>
							아래와 같이 분석해봤습니다.<br>
							<br>
							[저고비율 패턴분석 차트]<br>
							<br>
						</div>
						<br>
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">

							<br>
							홀짝 비율은<br>
							당첨번호 숫자 중<br>
							홀수와 짝수의 출현 비율을 의미합니다.<br>
							<br>
							지난 1~${last_count?if_exists}회 당첨번호의<br>
							홀수 짝수 비율을 분석해 보니<br>
							아래의 그림과 같이 2:4, 3:3, 4:2에서<br>
							당첨번호가 가장 많이 출현했습니다.<br>
							<br>
							지난 ${last_count?if_exists}회 역시<br>
							${last_oddeven?if_exists}의 비율로 당첨되었습니다.<br>
							<br>
							
							<canvas id="barChartOddEven" height="120"></canvas>
							
							<br>
							최근 10회 동안의<br>
							홀짝 비율 출현 패턴을<br>
							아래와 같이 분석해봤습니다.<br>
							<br>
							[홀짝비율 패턴분석 차트]<br>
							<br>
						</div>
						<br>
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">

							<br>
							회차합은<br>
							최근 당첨번호로부터 이전 10회 동안<br>
							당첨번호로 출현한<br>
							숫자의 모임을 의미합니다.<br>
							<br>
							최근 10회차 합에서 출현한 번호들은<br>다음과 같습니다.<br>
							<br>
							<div id="contain10List">
							</div>
							<br>
							그리고 10회차 합에서 미출현한 번호들은<br>다음과 같습니다.<br>
							<br>
							<div id="notContain10List">
							</div>
							
							<br>
							최근 10회 동안의<br>
							회차합 출현 패턴을<br>
							아래와 같이 분석해봤습니다.<br>
							<br>
							[회차합 패턴분석 차트]<br>
							<br>
							
						</div>
						<br>
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">

							<br>
							총합은<br>
							보너스 번호를 제외한<br>
							당첨번호 숫자들의 합을 의미합니다.<br>
							<br>
							총합의 예상범위는<br>
							출현 빈도가 낮은 10%를 제외한<br>
							통계치를 측정한 결과<br>
							${total_range?if_exists} 사이에서<br>
							약 90%의 확률로 출현했습니다.<br>
							<br>
							
							<br>
							최근 10회 동안의<br>
							총합 출현 패턴을<br>
							아래와 같이 분석해봤습니다.<br>
							<br>
							[총합 패턴분석 차트]<br>
							<br>
						</div>
						<br>
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">

							<br>
							끝수합은<br>
							당첨번호 숫자의<br>
							1의 자리의 합을 의미합니다.<br>
							<br>
							끝수합의 예상범위는<br>
							출현 빈도가 낮은 10%를 제외한<br>
							통계치를 측정한 결과<br>
							${endnum_range?if_exists} 사이에서<br>
							약 90%의 확률로 출현했습니다.<br>
							<br>
							
							<br>
							최근 10회 동안의<br>
							끝수합 출현 패턴을<br>
							아래와 같이 분석해봤습니다.<br>
							<br>
							[끝수합 패턴분석 차트]<br>
							<br>
						</div>
						<br>
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">

							<br>
							제외수는<br>
							1~${last_count?if_exists}회에 걸쳐 항상 출현하지 않았던<br>
							일정한 규칙에 의해 선정된 숫자입니다.<br>
							<br>
							이번 주의 예상 제외수입니다.<br>
							<br>
							<div id="excludeNumberListNormal">
							</div>
							<br>
							<!--							
							위의 로또 ${ex_count?if_exists}회차 예상번호는<br>
							제외수를 포함하지 않았습니다.<br>
							-->
							<br>
							
							<br>
							위의 제외 <span id="excludeNumberListNormalCnt"></span>수 이외에<br>
							추가로 예상되는 제외수는<br>
							<span id="modiExcludeNum"></span><br>
​							<br>
							추가 제외수는<br>
							분석하고 계시는 번호와 맞춰<br>
							아니라고 생각하시는 번호는<br>
							개인적으로 필터 하시면 좋겠습니다.<br>
							<br>
						</div>
						<br>
					</div>
				</div>
					
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">

							<br>
							궁합수는<br>
							1~${last_count?if_exists}회 당첨번호들의 관계를 분석하여<br>
							가장 많은 조합으로<br>
							당첨번호로 출현한 번호들을<br>
							궁합이 좋은 수로 정의했습니다.<br>
							<br>
							궁합이 잘 맞는 수를 참고하시어<br>
							이번 주 로또 ${ex_count?if_exists}회차 예상번호를<br>
							신중히 선택하시면<br>
							좋은 결과가 있을 것 같습니다.<br>
							<br>
							금주의 궁합 수 참고 자료입니다.<br>
							<br>
							<table class="table table-bordered" style="font-size:15px; text-align: center; margin-left: auto; margin-right: auto;">
								<thead>
									<tr>
										<th style="width:10%;">번호</th>
										<th style="width:23%;">궁합수</th>
										<th style="width:10%;">번호</th>
										<th style="width:23%;">궁합수</th>
										<th style="width:10%;">번호</th>
										<th style="width:24%;">궁합수</th>										
									</tr>
								</thead>
								<tbody id="mcNumberList">
									
								</tbody>
							</table>
							<br>
							<table class="table table-bordered" style="font-size:15px; text-align: center; margin-left: auto; margin-right: auto;">
								<thead>
									<tr>
										<th style="width:15%;">번호</th>
										<th style="width:35%;">궁합수</th>
										<th style="width:15%;">번호</th>
										<th style="width:35%;">궁합수</th>
									</tr>
								</thead>
								<tbody id="mcNumberList2">
									
								</tbody>
							</table>
							<br>
							
							
						</div>
						<br>
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							<br>
							로또는 운이라고 하는 분도 있고,<br>
							확률이라 하는 분도 있습니다.<br>
							<br>
							한 장의 행복을 구입하여<br>
							일주일 동안 희망에 가득한 마음으로<br>
							행복하시기를 바라며,<br>
							#로또${ex_count?if_exists}회예상번호<br>
							분석을 마칩니다.<br>
							<br>
							※후원안내<br>
							<br>
							심서방로또는<br>
							여러분의 따뜻한 격려의 말씀과<br>
							아낌없는 후원으로 운영됩니다.<br>
							<br>
							후원안내 보기 클릭!<br>
							https://blog.naver.com/smlotto/221410541239<br>
							<br>
							이 글을 읽는 모든 분들께<br>
							로또 1등 당첨이 되시길<br>
							진심으로 기원합니다.<br>
							<br>
							제가 분석하는 내용 이외에<br>
							더 좋은 의견이 있으시면<br>
							댓글이나 쪽지를 남겨주세요.<br>
							<br>
							
						</div>
						<br>
					</div>
				</div>
					
			</div>
			<!-- END MAIN CONTENT -->
