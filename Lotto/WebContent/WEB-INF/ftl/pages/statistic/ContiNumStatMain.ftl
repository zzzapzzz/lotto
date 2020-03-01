			<#assign JS_ROOT = SystemInfo.js_root>
			<#assign IMG_ROOT = SystemInfo.img_root>
			
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
					</div>
					
					<!-- row -->
					<div id="range1Row" class="row">
						<div class="content">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<h1 class="txt-color-blueDark" style="text-align: center;">
									<strong>연속번호출현</strong>
								</h1>
								<br />
							</div>
						</div>
					</div>
					<!-- end row -->
					
					<!-- row -->
					<#list winDataList as winData>
					<#if winData_index &gt;9> <#break> </#if>
					<div class="row">
						<div class="content">
							<div class="col-xs-3 col-sm-3 col-md-5 col-lg-5">
								<h1 class="txt-color-blueDark" style="text-align: right;">
									<strong>${winData.win_count}회</strong>
								</h1>
							</div>
							<div id="range1Numbers" class="col-xs-9 col-sm-9 col-md-7 col-lg-7">
								<img src="${IMG_ROOT}/ballnumber/ball_${winData.num1}<#if winData.num2 - winData.num1 != 1>_w</#if>.png" alt="${winData.num1}"/>
								<img src="${IMG_ROOT}/ballnumber/ball_${winData.num2}<#if winData.num3 - winData.num2 == 1 || winData.num2 - winData.num1 == 1><#else>_w</#if>.png" alt="${winData.num2}"/>
								<img src="${IMG_ROOT}/ballnumber/ball_${winData.num3}<#if winData.num4 - winData.num3 == 1 || winData.num3 - winData.num2 == 1><#else>_w</#if>.png" alt="${winData.num3}"/>
								<img src="${IMG_ROOT}/ballnumber/ball_${winData.num4}<#if winData.num5 - winData.num4 == 1 || winData.num4 - winData.num3 == 1><#else>_w</#if>.png" alt="${winData.num4}"/>
								<img src="${IMG_ROOT}/ballnumber/ball_${winData.num5}<#if winData.num6 - winData.num5 == 1 || winData.num5 - winData.num4 == 1><#else>_w</#if>.png" alt="${winData.num5}"/>
								<img src="${IMG_ROOT}/ballnumber/ball_${winData.num6}<#if winData.num6 - winData.num5 != 1>_w</#if>.png" alt="${winData.num6}"/>
								<img src="${IMG_ROOT}/ballnumber/ball_plus.png" alt="+"/>
								<img src="${IMG_ROOT}/ballnumber/ball_${winData.bonus_num}_w.png" alt="${winData.bonus_num}"/>
							</div>
						</div>
					</div>
					
					</#list>
					<!-- end row -->
				</section>
				<!-- end widget grid -->
				
			</div>
			<!-- END MAIN CONTENT -->

