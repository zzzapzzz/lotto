			<#assign JS_ROOT = SystemInfo.js_root>

			<!-- MAIN CONTENT -->
			<div id="content">
				<input type="hidden" id="isPlugin" value="Y" />
				<input type="hidden" id="isLogin" value="${isLogin?if_exists}" />
				<input type="hidden" id="checkMsg" value="${checkMsg?if_exists}" />
				<input type="hidden" id="last_count" value="${last_count?if_exists}" />

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa ${CurrMenuInfo.lna_element?if_exists}"></i> ${CurrMenuInfo.p_menu_nm?if_exists} <#if CurrMenuInfo.p_menu_id != 0> <span>> ${CurrMenuInfo.menu_nm?if_exists} > 전회차 예상번호 당첨결과</span></#if></h1>
						<a href="javascript:cancel();" class="btn btn-labeled btn-default pull-right" style="margin-left: 10px;"> <span class="btn-label"><i class="fa fa-mail-reply"></i></span>뒤로 </a>
					</div>
				</div>
				
				<div class="row">
					<div class="content">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							<h3><strong>로또 ${last_count?if_exists}회 예상번호 당첨결과</strong></h3>
						</div>
						
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 tac">
							1등 : ${win1Result?if_exists}<br>
							2등 : ${win2Result?if_exists}<br>
							3등 : ${win3Result?if_exists}<br>
							4등 : ${win4Result?if_exists}<br>
							5등 : ${win5Result?if_exists}<br>
							<br>
							조합개수 : ${exDataListCnt?if_exists}<br>							
						</div>
						<br>
						
					</div>
				</div>
					
			</div>
			<!-- END MAIN CONTENT -->
