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

					<!-- row -->
					<div class="row well">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<span id="totalTest" class="btn btn-info"><i class="fa fa-bug"></i> 총합</span>
							<span id="testAppearNumbers" class="btn btn-info"><i class="fa fa-edit"></i> 최대 출현횟수</span>
							<span id="testNumbersRange" class="btn btn-info"><i class="fa fa-edit"></i> 번호간 범위 </span>
							<span id="testZeroCntRange" class="btn btn-info"><i class="fa fa-edit"></i> 미출현구간 </span>
							<span id="insertAllAcInfo" class="btn btn-success"><i class="fa fa-edit"></i> AC정보 등록</span>
							<span id="sendEmailTest" class="btn btn-success"><i class="fa fa-edit"></i> email발송</span>
						</div>
						<div id="totalRange" class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: center;">
						</div>
						<div id="totalTestResult" class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: center;">
						</div>
						<div id="matchCnt" class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: center;">
						</div>
						<div id="matchPer" class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: center;">
						</div>
						
					</div>
					<!-- end row -->
					
					<!-- row -->
					<div class="row well">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<h1 id="win_data_title" class="txt-color-blueDark" style="text-align: center;">
								나대길 가설 검증
							</h1>
							<span id="testTheory" class="btn btn-info" onclick="javascript:testTheory();"><i class="fa fa-bug"></i> 가설</span>
							<input type="text" style="width: 10%;" id="testTheoryCount" name="testTheoryCount" placeholder="검증 가설번호" />
							<input type="text" style="width: 10%;" id="fromCheckCount" name="fromCheckCount" placeholder="확인할 전회차수" />
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: left;">
								<br />
								<br />
								<ol>
									<li>가설1. 10차이나는 동일한 수 사이에 다른 숫자가 있으면, 큰 수에서 중간수를 뺀 끝수가 출현한다.</li>
									<li>가설2. 단번대 멸 & 첫수가 10번대 라면, 당첨숫자 4, 5, 6번째 중 1개가 이월된다.</li>
									<li>가설3. 20번대(20~29) 2개 & 30번대(30~39) 2개가 나오면, 다음 회차에서는 0끝수가 1개 이상 출현한다.</li>
									<li>가설4. 42번이 나오면 42의 앞 2번째 수 -1이 출현한다.</li>
									<li>가설5. 28번이 출현하면, 4끝수가 출현한다.</li>
									<li>가설6. 19번이 출현하면, 0끝수가 출현한다. 뒤에 (1구간 3수)가 나오면 뒤에는 0끝이 출현하지 않는다. (2020.02.29)</li>
									<li>가설7. 쌍수가 생겼는데 부작용이 생겨, 다시 한 번 자기번호나 가족번호(끝수가 같은)가 출현한다.</li>
									<li>가설8. 8번이 출현하면, 8배수가 출현한다.</li>
									<li>가설9. 3연속수가 출현하면, 1구간 3수가 출현한다.</li>
									<li>가설10. 40번대 멸 이면, 당첨번호의 앞뒤 바뀐수가 출현한다.(예: 21 <-> 12)</li>
									<li>가설11. 10구간 3수 출현하면, 마지막 수 합의 배수 출현(예: 18 -> 18, 27, 36, 45)</li>
									<li>가설12. 38번이 출현하면, 26, 29가 출현한다.</li>
									<li>가설13. 4회차 전부터 1씩 감소하는 수가 4회 출현 후 다음 2 적은수가 출현하면, 다음 회차에서 빠진수가 출현한다.</li>
									<li>가설14. 3회차 전부터 1씩 감소하는 수가 3회동안 출현하면, 다음 회차에서 8끝수가 출현한다.</li>
									<li>가설15. 28, 42가 출현하면 40번대가 3회연속 출현하지 않음. 보너스 볼까지도 안나옴.4번째 40번대 출현한다.</li>
									<li>가설16. 28, 42가 출현하면 40번대가 3회연속 출현하지 않음. 보너스 볼까지도 안나옴.4번째 쌍수가 출현한다.</li>
									<li>가설17. 단번대가 1개씩 3회연속 출현하면,4회째에 단번대의 배수가 출현한다.</li>
									<li>가설18. 5와 8끝수가 출현하면, 다음회 6끝수가 출현한다.</li>
									<li>가설19. 마지막 회차의 2회전 단번대 출현, 최근 회차에서 단번대 출현하면, 3끝수가 출현한다.</li>
									<li>가설20. 1회차 전에서의 10번대, 20번대 번호가 최근 회차에서 연번으로 출현하면, 1회차 전의 번호 중 1개가 다시 출현한다.</li>
									<li>가설21. 24번호 출현시(보너스 포함), 2,8,7 끝수가 출현한다.</li>
									<!-- 22,23은 다른 가설로 사용 -->
									<li>가설24. 20구간 3개 출현시, 8,2,4 끝수가 출현한다.</li>
									<li>가설25. 20구간 3개 출현하고 8,2,4 끝수가 출현하면 다음회차에 8,2,4끝수가 출현하지 않는다.</li>
									<li>가설26. 20구간 3개 출현하면, 단번대(1~9)가 출현하지 않는다.</li>
									<li>가설27. 10번대 연번이 출현하면, 1끝수가 출현한다.</li>
									<li>가설28. 15번이 출현하면, 19번이 출현한다.</li>
									<li>가설29. 28번이 출현하면, 28번이 이월되지 않는다.</li>
									<li>가설30. 40번대(40~45) 보너스가 출현하면, 구간수 3개가 출현한다.</li>
								</ol>
							</div>
						</div>
						<div id="theoryResult" class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: center;">
							가설<br /> 
							전체출현횟수 = <span id="theoryAllAppearCnt">100</span>, 일치횟수 = <span id="theoryMatchedCnt">100</span>, 정확도 <span id="theoryMatchedPer">100</span>%<br />
						</div>
					</div>
					<!-- end row -->
					
					<!-- row -->
					<div class="row well">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<span id="testExclude" class="btn btn-info"><i class="fa fa-bug"></i> 전체 제외수 테스트</span>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: left;">
								<br />
								<strong>제외수의 궁합수를 포함한 번호들을 제외수로 설정할 수 있는지 테스트</strong><br />
								<br />
								<ol>
									<li>제외수의 궁합수를 포함한 번호들을 제외번호들로 설정.</li>
									<li>제외수 설정 시작회차(12회차)부터 비교하여 적중률(%)을 계산.</li>
									<li>전체 회차의 적중률을 평균치를 계산하여 기준률(90%)을 비교.</li>
									<li>1.의 목록에서 당첨번호가 출현했다면, 이전 회차의 무엇과 연관성이 있는지 추가분석.</li>
									<li>4.를 통해 연광성 있는 번호는 1.의 목록에서 remove 처리.</li>
									<li>1~3.의 과정을 반복.</li>
								</ol>
							</div>
							
							<input type="text" style="width: 40%;" id="ex_count" name="ex_count" placeholder="미입력 시 금주회차" />회차
							<span id="testExcludeCount" class="btn btn-info"><i class="fa fa-bug"></i> 제외수 테스트</span>
						</div>
					</div>
					<!-- end row -->
					
					<!-- row -->
					<div class="row well">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<span id="user001" class="btn btn-info"><i class="fa fa-bug"></i> 태화강 조합</span>
						</div>
					</div>
					<!-- end row -->
					
				</section>
				<!-- end widget grid -->
				
			</div>
			<!-- END MAIN CONTENT -->

