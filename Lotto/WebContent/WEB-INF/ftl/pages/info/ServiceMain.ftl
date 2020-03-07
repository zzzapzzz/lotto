			<#assign APP_ROOT = SystemInfo.app_root>
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
				
				<div lass="row">
					<div class="col-sm-12">
						<div id="info" class="well well-light">
							<h1>서비스 유형<small> </small></h1>
							
							<div class="row">
								
						        <div class="col-xs-12 col-sm-6 col-md-3">
						            <div class="panel panel-success pricing-big">
						            	
						                <div class="panel-heading">
						                    <h3 class="panel-title">방문자</h3>
						                </div>
						                <div class="panel-body no-padding text-align-center">
						                    <div class="the-price">
						                        <h1>무료</h1>
						                    </div>
											<div class="price-features">
												<ul class="list-unstyled text-left">
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>당첨결과</strong> 조회 서비스</li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>예상번호</strong> 조회 서비스 (10조합)</li>
										        	<li><i class="fa fa-times text-danger font-md"></i> <span class="subscript font-sm">SM 예상번호 조회 서비스</span></li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>통계자료</strong> 조회 서비스</li>
										        	<li><i class="fa fa-times text-danger font-md"></i> <span class="subscript font-sm">패턴분석 조회 서비스</span></li>
										        	<li><i class="fa fa-times text-danger font-md"></i> <span class="subscript font-sm">MY로또 관리 서비스</span></li>
										        	<li><i class="fa fa-times text-danger font-md"></i> <span class="subscript font-sm">MY로또 필터 조합 서비스</span></li>
										        </ul>
											</div>
						                </div>
						                <div class="panel-footer text-align-center">
						                    <a href="javascript:void(0);" class="btn btn-success btn-block" role="button"> 무료!<span> </span></a>
						                	<div>
						                		일반 서비스만 이용할 수 있습니다.
						                	</div>
						                </div>
						            </div>
						        </div>
						        
						        <div class="col-xs-12 col-sm-6 col-md-3">
						            <div class="panel panel-teal pricing-big">
						            	
						                <div class="panel-heading">
						                    <h3 class="panel-title">일반 회원</h3>
						                </div>
						                <div class="panel-body no-padding text-align-center">
						                    <div class="the-price">
						                        <h1>무료</h1>
						                    </div>
											<div class="price-features">
												<ul class="list-unstyled text-left">
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>당첨결과</strong> 조회 서비스</li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>예상번호</strong> 조회 서비스 (10,20,30조합)</li>
										        	<li><i class="fa fa-times text-danger font-md"></i> <span class="subscript font-sm">SM 예상번호 조회 서비스</span></li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>통계자료</strong> 조회 서비스</li>
										        	<li><i class="fa fa-times text-danger font-md"></i> <span class="subscript font-sm">패턴분석 조회 서비스</span></li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>MY로또</strong> 관리 서비스</li>
										        	<li><i class="fa fa-times text-danger font-md"></i> <span class="subscript font-sm">MY로또 필터 조합 서비스</span></li>
										        </ul>
											</div>
						                </div>
						                <div class="panel-footer text-align-center">
						                	<#if UserInfo.isLogin?if_exists == "N">
						                    <a href="/fhrmdlsapdls.do" class="btn btn-primary btn-block" role="button">회원가입 <span></span></a>
						                	<div>회원가입 후 이용할 수 있습니다.</a>
						                	</div>
						                	</#if>
						                	<#if UserInfo.isLogin?if_exists == "Y">
						                	<a href="javascript:void(0);" class="btn btn-primary btn-block" role="button">회원가입 <span></span></a>
						                	<div>기본 서비스를 이용할 수 있습니다.</a>
						                	</div>
						                	</#if>
						                </div>
						            </div>
						        </div>
						        
						        <div class="col-xs-12 col-sm-6 col-md-3">
						            <div class="panel panel-primary pricing-big">
						            	<img src="img/ribbon.png" class="ribbon" alt="">
						                <div class="panel-heading">
						                    <h3 class="panel-title">유료 회원</h3>
						                </div>
						                <div class="panel-body no-padding text-align-center">
						                    <div class="the-price">
						                        <h1><strong>9,900</strong>원<span class="subscript">/ 1개월</span></h1>
						                    </div>
											<div class="price-features">
												<ul class="list-unstyled text-left">
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>당첨결과</strong> 조회 서비스</li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>예상번호</strong> 조회 서비스 (10,20,30조합)</li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>SM 예상번호</strong> 조회 서비스</li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>통계자료</strong> 조회 서비스</li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>패턴분석</strong> 조회 서비스</li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>MY로또</strong> 관리 서비스</li>
										        	<li><i class="fa fa-times text-danger font-md"></i> <span class="subscript font-sm">MY로또 필터 조합 서비스</span></li>
										        </ul>
											</div>
						                </div>
						                <div class="panel-footer text-align-center">
						                    <#if UserInfo.isLogin?if_exists == "N">
						                    <a href="/fhrmdlsapdls.do" class="btn btn-primary btn-block" role="button">회원가입 <span></span></a>
						                	<div>회원가입 후 이용할 수 있습니다.</a>
						                	</div>
						                	</#if>
						                	<#if UserInfo.isLogin?if_exists == "Y">
						                	<a href="javascript:apply(1);" class="btn btn-primary btn-block" role="button">신청하기 <span></span></a>
						                	<div>유료 서비스를 이용할 수 있습니다.</a>
						                	</div>
						                	</#if>
						                </div>
						            </div>
						        </div>
						        
						        <div class="col-xs-12 col-sm-6 col-md-3">
						            <div class="panel panel-darken pricing-big">
						            	
						                <div class="panel-heading">
						                    <h3 class="panel-title">프리미엄 회원</h3>
						                </div>
						                <div class="panel-body no-padding text-align-center">
						                    <div class="the-price">
						                        <h1><strong>19,900</strong>원<span class="subscript">/ 1개월</span></h1>
						                    </div>
											<div class="price-features">
												<ul class="list-unstyled text-left">
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>당첨결과</strong> 조회 서비스</li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>예상번호</strong> 조회 서비스 (10,20,30조합)</li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>SM 예상번호</strong> 조회 서비스</li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>통계자료</strong> 조회 서비스</li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>패턴분석</strong> 조회 서비스</li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>MY로또</strong> 관리 서비스</li>
										        	<li><i class="fa fa-check text-success font-md"></i> <strong>MY로또 <span class="txt-color-red">필터 조합</span></strong> 서비스 (무제한)</li>
										        </ul>
											</div>
						                </div>
						                <div class="panel-footer text-align-center">
						                	<#if UserInfo.isLogin?if_exists == "N">
						                	<a href="/fhrmdlsapdls.do" class="btn btn-primary btn-block" role="button">회원가입 <span></span></a>
						                	<div>회원가입 후 이용할 수 있습니다.</a>
						                	</div>
						                	</#if>
						                	<#if UserInfo.isLogin?if_exists == "Y">
						                	<a href="javascript:apply(2);" class="btn btn-primary btn-block" role="button">신청하기 <span></span></a>
						                	<div>유료 서비스를 포함한 나만의 <strong><span class="txt-color-red">필터조합</span></strong> 서비스를<br /> 무제한 이용할 수 있습니다.</a>
						                	</div>
						                	</#if>
						                </div>
						            </div>
						        </div>		    	
				    		</div>
				
							
							<hr>
						</div>
						
						<div id="apply_result" class="well well-light hide">
							<div class="row">
						        <div class="col-xs-12 col-sm-12 col-md-12">
									<br>
									<h1 class="text-center text-success"><strong><i class="fa fa-check fa-lg"></i> 신청 완료!</strong></h1>
									<br>
									
									<div class="text-align-center">
										<img src="${IMG_ROOT}/info/account.png" alt="무통장입금" />
									</div>
									
									<br>
									<br>
									<div class="text-align-center">
									위의 계좌로 9,900원을 입금하시면, 확인 후 서비스를 이용할 수 있습니다.
									</div>
									<br>
								</div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
			<!-- END MAIN CONTENT -->