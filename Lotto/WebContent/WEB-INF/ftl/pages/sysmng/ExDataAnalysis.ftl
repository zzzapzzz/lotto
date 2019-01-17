			<#assign JS_ROOT = SystemInfo.js_root>

			<!-- chart.js -->
			<script src="${JS_ROOT}/plugin/chartjs/Chart.min.js"></script>
			<script src="${JS_ROOT}/plugin/chartjs/Chart.bundle.js"></script>
			<script src="${JS_ROOT}/plugin/chartjs/utils.js"></script>
					
			<!-- MAIN CONTENT -->
			<div id="content">
				<input type="hidden" id="isPlugin" value="Y" />
				<input type="hidden" id="ex_count" value="${ex_count?if_exists}" />
				<input type="hidden" id="last_count" value="${last_count?if_exists}" />

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa ${CurrMenuInfo.lna_element?if_exists}"></i> ${CurrMenuInfo.p_menu_nm?if_exists} <#if CurrMenuInfo.p_menu_nm != CurrMenuInfo.menu_nm> <span>> ${CurrMenuInfo.menu_nm?if_exists} > 예상번호 분석</span></#if></h1>
						<a href="javascript:cancel();" class="btn btn-labeled btn-default pull-right" style="margin-left: 10px;"> <span class="btn-label"><i class="fa fa-mail-reply"></i></span>뒤로 </a>
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							<h3><strong>로또 ${ex_count?if_exists}회 로또예상번호</strong></h3>
							
							안녕하세요. 심 서방입니다.<br>
							<br>
							이번 주 ${nextAnnounceDate?if_exists}에 발표되는<br>
							로또 ${ex_count?if_exists}회차 예상번호를 예측해 봤습니다.<br>
							<br>
							1~${ex_count?if_exists}회 로또 당첨번호를 분석하여<br>
							일정한 패턴을 찾아<br>
							가장 당첨 확률이 높은 규칙을 적용하여,<br>
							100만 번의 랜덤 번호 중 가장 높은 확률로 출현한<br>
							예상번호 조합 30개를 예측했습니다.<br>
							<br>
							예상번호 30조합은<br>
							심 서방 로또에서 예측한 번호입니다.<br>
							번호 그대로 선택하셔도 좋고,<br>
							마음에 드는 번호만 선택하셔도 좋습니다.<br>
							1등 조합이 선택된다면<br>
							그것은 여러분의 운입니다.<br>
							당첨이 될 수도 있고,<br>
							안 될 수도 있으니<br>
							아래 조합들에 대한<br>
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
							
							<h4><strong>저고비율</strong></h4>
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
						</div>
						<br>
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							<h4><strong>홀짝비율</strong></h4>
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
						</div>
						<br>
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							<h4><strong>회차합</strong></h4>
							<br>
							회차합은<br>
							최근 당첨번호로부터 이전 10회 동안<br>
							당첨번호로 출현한<br>
							숫자의 모임을 의미합니다.<br>
							<br>
							최근 10회차 합에서 출현한 번호들은 다음과 같습니다.<br>
							<br>
							<div id="contain10List">
							</div>
							<br>
							그리고 10회차 합에서 미출현한 번호들은 다음과 같습니다.<br>
							<br>
							<div id="notContain10List">
							</div>
							
							<br>
							
						</div>
						<br>
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							<h4><strong>총합</strong></h4>
							<br>
							총합은<br>
							보너스 번호를 제외한<br>
							당첨번호 숫자들의 합을 의미합니다.<br>
							<br>
							총합의 예상범위는<br>
							출현빈도가 낮은 10%를 제외한<br>
							통계치를 측정한 결과<br>
							${total_range?if_exists} 사이에서<br>
							약 90%의 확률로 출현했습니다.<br>
							<br>
							
						</div>
						<br>
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							<h4><strong>끝수합</strong></h4>
							<br>
							끝수합은<br>
							당첨번호 숫자의<br>
							1의 자리의 합을 의미합니다.<br>
							<br>
							끝수합의 예상범위는<br>
							출현빈도가 낮은 10%를 제외한<br>
							통계치를 측정한 결과<br>
							${endnum_range?if_exists} 사이에서<br>
							약 90%의 확률로 출현했습니다.<br>
							<br>
							
						</div>
						<br>
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							<h4><strong>제외수</strong></h4>
							<br>
							제외수는<br>
							1~${last_count?if_exists}회에 걸쳐 항상 출현하지 않았던<br>
							일정한 규칙에 의해 선정된 숫자입니다.<br>
							<br>
							이번 주의 예상 제외수입니다.<br>
							<br>
							<div id="excludeNumberList">
							</div>
							<br>
							위의 로또 ${ex_count?if_exists}회차 예상번호는<br>
							제외수를 포함하지 않았습니다.<br>
							<br>
							
						</div>
						<br>
					</div>
				</div>
					
			</div>
			<!-- END MAIN CONTENT -->
