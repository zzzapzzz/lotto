			<!-- MAIN CONTENT -->
			<div id="content">
				<input type="hidden" id="isPlugin" value="Y" />
				<input type="hidden" id="isLogin" value="${isLogin?if_exists}" />
				<input type="hidden" id="checkMsg" value="${checkMsg?if_exists}" />
				<input type="hidden" id="status" value="${status?if_exists}" />
				<input type="hidden" id="nextWinCount" value="${nextWinCount?if_exists}" />
				<input type="hidden" id="nextAnnounceDate" value="${nextAnnounceDate?if_exists}" />

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa ${CurrMenuInfo.lna_element?if_exists}"></i> ${CurrMenuInfo.p_menu_nm?if_exists} <#if CurrMenuInfo.p_menu_id != 0> <span>> ${CurrMenuInfo.menu_nm?if_exists}</span></#if></h1>
					</div>
				</div>
				
				<!-- widget grid -->
				<section id="widget-grid" class="">

					<div class="well">
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<h2 class="page-title txt-color-blueDark">
									로또 ${nextWinCount?if_exists}회 예상번호 (${nextAnnounceDate?if_exists} 발표)
								</h2>
								<#if isUnregistered?if_exists == "Y">
								<h2 class="page-title txt-color-red">
									<b>미입력된 당첨번호가 존재합니다.</b><br>
									(마지막 입력 회차 : ${lastWinCount?if_exists})
								</h2>
								</#if>
							</div>
						</div>
					</div>
					
					<span id="exDataExtraction" class="btn btn-success"><i class="fa fa-edit"></i> 예상번호 추출</span>
					<span id="exDataNewExtraction" class="btn btn-success"><i class="fa fa-edit"></i> 예상번호 NEW 추출</span>
					<span id="beforeExDataResult" class="btn btn-warning"><i class="fa fa-edit"></i> 전회차 매칭결과</span>
					<span id="exDataAnalysis" class="btn btn-info"><i class="fa fa-pencil"></i> 예상번호 분석</span>
					
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

				</section>
				<!-- end widget grid -->
				
			</div>
			<!-- END MAIN CONTENT -->

