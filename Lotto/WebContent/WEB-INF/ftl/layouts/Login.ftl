<#assign tiles=JspTaglibs["http://tiles.apache.org/tags-tiles"] />
<!DOCTYPE html>
<html lang="en-us" id="extr-page">
	<head>
		<meta charset="utf-8">
		<title><@tiles.getAsString name="title" /></title>
		
		<meta name="description" content="심서방로또">
		<meta name="author" content="cremazer">
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		
		<#assign CSS_ROOT = SystemInfo.css_root>
	    <#assign JS_ROOT = SystemInfo.js_root>
	    <#assign APP_ROOT = SystemInfo.app_root>
	    <#assign IMG_ROOT = SystemInfo.img_root>
		
		<!-- #CSS Links -->
		<!-- Basic Styles -->
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/font-awesome.min.css">

		<!-- SmartAdmin Styles : Caution! DO NOT change the order -->
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/smartadmin-production-plugins.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/smartadmin-production.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/smartadmin-skins.min.css">

		<!-- SmartAdmin RTL Support -->
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/smartadmin-rtl.min.css"> 

		<!-- We recommend you use "your_style.css" to override SmartAdmin
		     specific styles this will also ensure you retrain your customization with each SmartAdmin update.
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/your_style.css"> -->

		<!-- Demo purpose only: goes with demo.js, you can delete this css when designing your own WebApp -->
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/demo.min.css">

		<!-- #FAVICONS -->
		<link rel="shortcut icon" href="${IMG_ROOT}/favicon/favicon.ico" type="image/x-icon">
		<link rel="icon" href="${IMG_ROOT}/favicon/favicon.ico" type="image/x-icon">

		<!-- #GOOGLE FONT -->
		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,300,400,700">

		<!-- #APP SCREEN / ICONS -->
		<!-- Specifying a Webpage Icon for Web Clip 
			 Ref: https://developer.apple.com/library/ios/documentation/AppleApplications/Reference/SafariWebContent/ConfiguringWebApplications/ConfiguringWebApplications.html -->
		<link rel="apple-touch-icon" href="${IMG_ROOT}/splash/sptouch-icon-iphone.png">
		<link rel="apple-touch-icon" sizes="76x76" href="${IMG_ROOT}/splash/touch-icon-ipad.png">
		<link rel="apple-touch-icon" sizes="120x120" href="${IMG_ROOT}/splash/touch-icon-iphone-retina.png">
		<link rel="apple-touch-icon" sizes="152x152" href="${IMG_ROOT}/splash/touch-icon-ipad-retina.png">
		
		<!-- iOS web-app metas : hides Safari UI Components and Changes Status Bar Appearance -->
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		
		<!-- Startup image for web apps -->
		<link rel="apple-touch-startup-image" href="${IMG_ROOT}/splash/ipad-landscape.png" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:landscape)">
		<link rel="apple-touch-startup-image" href="${IMG_ROOT}/splash/ipad-portrait.png" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:portrait)">
		<link rel="apple-touch-startup-image" href="${IMG_ROOT}/splash/iphone.png" media="screen and (max-device-width: 320px)">

		<!-- login image -->
		<style>
	    input.img-button-naver {
		    background: url( "${IMG_ROOT}/sclogin/Naver_Perfect_Green.PNG" ) no-repeat;
		    border: none;
		    width: 210px;
		    cursor: pointer;
	    }	
	        
	    input.kor {
	    	ime-mode:active;
	    }
	    </style>
	</head>
	
	<body class="animated fadeInDown">

		<header id="header">

			<div id="logo-group">
				<span id="logo"> <img src="${IMG_ROOT}/logo.png" alt="Logo Image"> </span>
			</div>
			
			<!--
			<span id="extr-page-header-space"> <span class="hidden-mobile hiddex-xs">아이디가 없으세요?</span> <a href="javascript:alert('준비중입니다.');" class="btn btn-danger">회원가입</a> </span>
			-->

		</header>

		<div id="main" role="main">

			<!-- MAIN CONTENT -->
			<div id="content" class="container">
				
				<input type="hidden" id="isLogin" name="isLogin" value="${isLogin?if_exists}">
				
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-7 col-lg-8 hidden-xs hidden-sm">
						<h1 class="txt-color-red login-header-big">SM Lotto</h1>
						<div class="hero">

							<div class="pull-left login-desc-box-l">
								<h4 class="paragraph-header">
								심서방 로또입니다.
							</div>
							
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-12 col-md-5 col-lg-4">
						<div class="well no-padding">
						
							<!-- 로그인 -->
							<form action="javascript:loginProc();" id="login-form" class="smart-form client-form">
								<header>
									<i class="fa fa-sign-in"></i> 로그인
								</header>

								<fieldset>
									
									<section>
										<label class="label">아이디</label>
										<label class="input"> <i class="icon-append fa fa-envelope-o"></i>
											<input type="email" id="email" name="email" placeholder="이메일을 입력하세요." >
											<!--
											<input type="text" id="usr_id" name="usr_id">
											-->
											<b class="tooltip tooltip-top-right"><i class="fa fa-envelope txt-color-teal"></i>이메일을 입력하세요.</b>
										</label>
									</section>

									<section>
										<label class="label">비밀번호</label>
										<label class="input"> <i class="icon-append fa fa-lock"></i>
											<input type="password" id="thwd" name="thwd" placeholder="비밀번호를 입력하세요.">
											<b class="tooltip tooltip-top-right"><i class="fa fa-lock txt-color-teal"></i> 비밀번호를 입력하세요.</b> </label>
										<div class="note">
											<a href="javascript:forgetProcGo();">비밀번호를 잊어버렸나요?</a>
										</div>
									</section>

									<section>
										<label class="checkbox">
											<input type="checkbox" name="remember" checked="">
											<i></i>로그인 유지
										</label>
									</section>
								</fieldset>
								<footer>
									<button type="submit" class="btn btn-primary">
										로그인
									</button>
									<input type="button" id="join" class="btn btn-primary" value="회원가입" />
								</footer>
								<footer>
									<ul class="list-inline text-center">
										<li>
											<!-- Naver Login Button -->
											<div id="naverIdLogin"></div>
										</li>
										<li>
											<!-- Kakao Login Button -->
											<div id="kakaoIdLogin"><a id="kakao-login-btn"></a></div>
										</li>
									</ul>
								</footer>
							</form>
							
							<!-- end 로그인 -->
							
							<!-- 비밀번호 변경 -->
							<form action="javascript:authenticationProc();" id="authentication-form" class="smart-form client-form hide">
								<header>
									비밀번호 변경
								</header>

								<fieldset>
									
									<section>
										<div class="note">
											아래의 질문에 대한 답변을 입력하고, 비밀번호를 변경하세요.
										</div>
										<input type="hidden" id="change_usr_id" name="change_usr_id">
									</section>
									
									<section>
										<label class="label">비밀번호 확인질문</label>
										<label class="input"> <i class="icon-append fa fa-comment"></i>
											<input type="text" id="thwd_question" name="thwd_question">											
										</label>
									</section>

									<section>
										<label class="label">답변</label>
										<label class="input"> <i class="icon-append fa fa-edit"></i>
											<input type="text" id="thwd_answer" name="thwd_answer">
											<b class="tooltip tooltip-top-right"><i class="fa fa-edit txt-color-teal"></i> 답변을 입력하세요.</b> </label>
									</section>
									
									<section>
										<label class="label">새 비밀번호</label>
										<label class="input"> <i class="icon-append fa fa-lock"></i>
											<input type="password" id="change_thwd" name="change_thwd" placeholder="Password">	
											<b class="tooltip tooltip-top-right"><i class="fa fa-lock txt-color-teal"></i> 새 비밀번호를 입력하세요.</b> </label>										
										</label>
									</section>
									
									<section>
										<label class="label">새 비밀번호 확인</label>
										<label class="input"> <i class="icon-append fa fa-lock"></i>
											<input type="password" id="change_thwd_confirm" name="change_thwd_confirm" placeholder="Confirm password">
											<b class="tooltip tooltip-top-right"><i class="fa fa-lock txt-color-teal"></i> 새 비밀번호를 다시 한 번 입력하세요.</b> </label>
										</label>
									</section>
								</fieldset>
								<footer>
									<button type="submit" class="btn btn-primary">
										비밀번호 변경
									</button>
								</footer>
							</form>
							<!-- end 비밀번호 변경 -->
							
							<!-- 사용자정보 설정 -->
							<form action="javascript:resetProc();" id="reset-form" class="smart-form client-form hide">
								<header>
									사용자정보 설정
								</header>

								<fieldset>
									
									<section>
										<div class="note">
											새로운 비밀번호를 입력하고, 비밀번호를 찾기위해 본인만 아는 질문과 답을 입력하세요.
										</div>
										<input type="hidden" id="set_usr_id" name="set_usr_id">
									</section>
									
									<section>
										<label class="label">새 비밀번호</label>
										<label class="input"> <i class="icon-append fa fa-lock"></i>
											<input type="password" id="new_thwd" name="new_thwd" placeholder="Password">	
											<b class="tooltip tooltip-top-right"><i class="fa fa-lock txt-color-teal"></i> 새 비밀번호를 입력하세요.</b> </label>										
										</label>
									</section>
									
									<section>
										<label class="label">새 비밀번호 확인</label>
										<label class="input"> <i class="icon-append fa fa-lock"></i>
											<input type="password" id="thwd_confirm" name="thwd_confirm" placeholder="Confirm password">
											<b class="tooltip tooltip-top-right"><i class="fa fa-lock txt-color-teal"></i> 새 비밀번호를 다시 한 번 입력하세요.</b> </label>
										</label>
									</section>
									
									<section>
										<label class="label">비밀번호 확인질문</label>
										<label class="input"> <i class="icon-append fa fa-comment"></i>
											<input type="text" id="thwd_q" name="thwd_q">
											<b class="tooltip tooltip-top-right"><i class="fa fa-comment txt-color-teal"></i> 비밀번호 확인을 위한 질문을 입력하세요.</b> </label>											
										</label>
									</section>

									<section>
										<label class="label">비밀번호 확인답변</label>
										<label class="input"> <i class="icon-append fa fa-edit"></i>
											<input type="text" id="thwd_a" name="thwd_a">
											<b class="tooltip tooltip-top-right"><i class="fa fa-edit txt-color-teal"></i> 비밀번호 확인을 위한 답변을 입력하세요.</b> </label>
									</section>

								</fieldset>
								<footer>
									<button type="submit" class="btn btn-primary">
										설정내용 저장
									</button>
								</footer>
							</form>
							<!-- end 사용자정보 설정 -->
							
							<!-- 비밀번호 찾기 -->
							<form action="javascript:forgetProc();" id="forget-form" class="smart-form client-form hide">
								<header>
									비밀번호 초기화
								</header>

								<fieldset>
									
									<section>
										<div class="note">
											비밀번호를 잊어버리셨나요? 사원ID를 입력하고, 비밀번호 초기화 버튼을 클릭하세요.
										</div>
									</section>
									
									<section>
										<label class="label">사원ID</label>
										<label class="input"> <i class="icon-append fa fa-user"></i>
											<input type="text" id="search_usr_id" name="search_usr_id">
											<b class="tooltip tooltip-top-right"><i class="fa fa-user txt-color-teal"></i>사원ID를 입력하세요.</b>
										</label>
									</section>

								</fieldset>
								<footer>
									<button type="submit" class="btn btn-primary">
										비밀번호 초기화
									</button>
								</footer>
							</form>
							<!-- end 비밀번호 찾기 -->
							
							<!-- 회원가입 -->
							<form action="javascript:joinProc();" id="join-form" class="smart-form client-form hide">
								<header>
									<i class="fa fa-edit"></i> 회원가입
								</header>

								<fieldset>
									
									<section>
										<div class="note">
											심서방 로또에 가입하셔서 더 많은 정보를 얻으시고, 꼭 당첨되세요!!
										</div>
									</section>
									
									<section>
										<label class="label">아이디</label>
										<label class="input"> <i class="icon-append fa fa-envelope-o"></i>
											<input type="text" id="join_email" name="join_email" placeholder="이메일">
											<b class="tooltip tooltip-top-right"><i class="fa fa-envelope txt-color-teal"></i>이메일을 입력하세요.</b>
										</label>
									</section>

									<section>
										<label class="label">닉네임(별명)</label>
										<label class="input"> <i class="icon-append fa fa-tag"></i>
											<input type="text" class="kor" id="join_nickname" name="join_nickname" placeholder="닉네임(별명)">
											<b class="tooltip tooltip-top-right"><i class="fa fa-tag txt-color-teal"></i> 닉네임(별명)을 입력하세요.</b> </label>
										</label>
									</section>
									
									<section>
										<label class="label">비밀번호</label>
										<label class="input"> <i class="icon-append fa fa-lock"></i>
											<input type="password" id="join_thwd" name="join_thwd" placeholder="비밀번호">	
											<b class="tooltip tooltip-top-right"><i class="fa fa-lock txt-color-teal"></i> 비밀번호를 입력하세요.</b> </label>										
										</label>
									</section>
									
									<section>
										<label class="label">비밀번호 확인</label>
										<label class="input"> <i class="icon-append fa fa-lock"></i>
											<input type="password" id="join_thwd_confirm" name="join_thwd_confirm" placeholder="비밀번호 확인">
											<b class="tooltip tooltip-top-right"><i class="fa fa-lock txt-color-teal"></i> 비밀번호를 다시 한 번 입력하세요.</b> </label>
										</label>
									</section>
								</fieldset>
								<footer>
									<button type="submit" class="btn btn-primary">
										회원가입
									</button>
								</footer>
							</form>
							<!-- end 회원가입 -->
							
						</div>
					</div>
				</div>
			</div>

		</div>

		<!--================================================== -->	

		<!-- PACE LOADER - turn this on if you want ajax loading to show (caution: uses lots of memory on iDevices)-->
		<script src="${JS_ROOT}/plugin/pace/pace.min.js"></script>

	    <!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
	    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
		<script> if (!window.jQuery) { document.write('<script src="${JS_ROOT}/libs/jquery-2.1.1.min.js"><\/script>');} </script>

	    <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
		<script> if (!window.jQuery.ui) { document.write('<script src="${JS_ROOT}/libs/jquery-ui-1.10.3.min.js"><\/script>');} </script>

		<!-- IMPORTANT: APP CONFIG -->
		<script src="${JS_ROOT}/app.config.js"></script>

		<!-- JS TOUCH : include this plugin for mobile drag / drop touch events 		
		<script src="${JS_ROOT}/plugin/jquery-touch/jquery.ui.touch-punch.min.js"></script> -->

		<!-- BOOTSTRAP JS -->		
		<script src="${JS_ROOT}/bootstrap/bootstrap.min.js"></script>
		
		<!-- CUSTOM NOTIFICATION -->
		<script src="${JS_ROOT}/notification/SmartNotification.min.js"></script>

		<!-- JQUERY VALIDATE -->
		<script src="${JS_ROOT}/plugin/jquery-validate/jquery.validate.min.js"></script>
		
		<!-- JQUERY MASKED INPUT -->
		<script src="${JS_ROOT}/plugin/masked-input/jquery.maskedinput.min.js"></script>
		
		<!-- 소셜로그인 설정 -->
		<!-- NAVER LOGIN -->
		<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>
		
		<!-- KAKAO -->
		<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
		<!-- 소셜로그인 설정 끝-->
		
		<!--[if IE 8]>
			
			<h1>브라우저가 최신 버전이 아닙니다. <a href="http://www.microsoft.com/download" target="_blank">www.microsoft.com/download</a>에서 브라우저를 업데이트하십시오.</h1>
			
		<![endif]-->

		<!-- MAIN APP JS FILE -->
		<script src="${JS_ROOT}/app.min.js"></script>
		<script src="${JS_ROOT}/common.min.js"></script>

		<script type="text/javascript">
			runAllForms();

			Kakao.init('1342d57c2672941f535211c80bf2b025');

			// 네이버 로그인 버튼 생성
			var naverLogin = new naver.LoginWithNaverId(
				{
					clientId: "_vZ13kySrEVrBIV2C9Di",
					// callbackUrl: "http://smlotto.com/api/naver/member/oauth2c.do",
					callbackUrl: "http://127.0.0.1/api/naver/member/oauth2c.do",
					isPopup: false, /* 팝업을 통한 연동처리 여부 */
					loginButton: {color: "green", type: 3, height: 48} /* 로그인 버튼의 타입을 지정 */
				}
			);
			
			// 카카오 로그인 버튼 생성
		    Kakao.Auth.createLoginButton({
		    	container: '#kakao-login-btn',
		      	success: function(authObj) {
     				// Kakao.Auth.createLoginButton에서 불러온 결과값 json형태로 출력
		        	console.log('kakao login success=', JSON.stringify(authObj));
		        	window.location.href = "/api/kakao/member/oauth2c.do";
		      	},
		      	fail: function(err) {
		        	console.log('kakao login fail=',JSON.stringify(err));
		      	}
		    });
		    
	
			$(function() {
				dataLayer.push({
				  'pageCategory': 'Login'
				});
			
				// Validation
				$("#login-form").validate({
					// Rules for form validation
					rules : {
						email : {
							required : true,
							email : true,
							minlength : 7,
							maxlength : 100
						},
						thwd : {
							required : true,
							minlength : 3,
							maxlength : 50
						}
					},

					// Messages for form validation
					messages : {
						email : {
							required : '사원ID를 입력하세요.'
						},
						thwd : {
							required : '비밀번호를 입력하세요.'
						}
					},

					// Do not change code below
					errorPlacement : function(error, element) {
						error.insertAfter(element.parent());
					}
				});
				
				$("#authentication-form").validate({
					// Rules for form validation
					rules : {
						thwd_answer : {
							required : true,
							minlength : 3,
							maxlength : 100
						},
						change_thwd : {
							required : true,
							minlength : 3,
							maxlength : 50
						},
						change_thwd_confirm : {
							required : true,
							minlength : 3,
							maxlength : 50,
							equalTo : '#change_thwd'
						}
					},

					// Messages for form validation
					messages : {
						thwd_answer : {
							required : '사용자 인증확인을 위한 답변을 입력하세요.'
						},
						change_thwd : {
							required : '새 비밀번호를 입력하세요.'
						},
						change_thwd_confirm : {
							required : '새 비밀번호를 다시 한 번 입력하세요.',
							equalTo : '입력한 새 비밀번호와 일치하지 않습니다.'
						}
					},

					// Do not change code below
					errorPlacement : function(error, element) {
						error.insertAfter(element.parent());
					}
				});
				
				$("#reset-form").validate({
					// Rules for form validation
					rules : {
						new_thwd : {
							required : true,
							minlength : 7,
							maxlength : 50
						},
						thwd_confirm : {
							required : true,
							minlength : 7,
							maxlength : 50,
							equalTo : '#new_thwd'
						},
						thwd_q : {
							required : true,
							minlength : 3,
							maxlength : 200
						},
						thwd_a : {
							required : true,
							minlength : 3,
							maxlength : 100
						}
					},

					// Messages for form validation
					messages : {
						new_thwd : {
							required : '새 비밀번호를 입력하세요.'
						},
						thwd_confirm : {
							required : '새 비밀번호를 다시 한 번 입력하세요.',
							equalTo : '입력한 새 비밀번호와 일치하지 않습니다.'
						},
						thwd_q : {
							required : '사용자 인증을 위한 질문을 입력하세요.'
						},
						thwd_a : {
							required : '사용자 인증확인을 위한 답변을 입력하세요.'
						}
					},
					
					// Ajax form submition
					/*
					submitHandler : function(form) {
						$("#reset-form").ajaxSubmit({
							type : "POST",
	                        dataType: 'json',
							success : function() {
								$("#reset-form").addClass('submited');
							}
						});
					},
					*/

					// Do not change code below
					errorPlacement : function(error, element) {
						error.insertAfter(element.parent());
					}
				});
				
				$("#join-form").validate({
					// Rules for form validation
					rules : {
						join_email : {
							required : true,
							email : true,
							minlength : 3,
							maxlength : 100
						},
						join_thwd : {
							required : true,
							minlength : 3,
							maxlength : 50
						},
						join_thwd_confirm : {
							required : true,
							minlength : 3,
							maxlength : 50,
							equalTo : '#join_thwd'
						},
						join_nickname : {
							required : true,
							minlength : 3,
							maxlength : 50
						}
					},

					// Messages for form validation
					messages : {
						join_email : {
							required : '이메일을 입력하세요.',
							email : '이메일 형식이 아닙니다.'
						},
						join_thwd : {
							required : '비밀번호를 입력하세요.'
						},
						join_thwd_confirm : {
							required : '비밀번호를 다시 한 번 입력하세요.',
							equalTo : '입력한 비밀번호와 일치하지 않습니다.'
						},
						join_nickname : {
							required : '닉네임(별명)을 입력하세요.'
						}
					},

					// Do not change code below
					errorPlacement : function(error, element) {
						error.insertAfter(element.parent());
					}
				});
				
				$("#new_thwd").keypress(function (e) {
			        if(e.keyCode == 13){
			        	$("#thwd_confirm").focus();
			        }
			    });
				
				$("#thwd_confirm").keypress(function (e) {
			        if(e.keyCode == 13){
			        	$("#thwd_q").focus();
			        }
			    });
				
				$("#thwd_q").keypress(function (e) {
			        if(e.keyCode == 13){
			        	$("#thwd_a").focus();
			        }
			    });
				
				/*
				$("#thwd_a").keypress(function (e) {
			        if(e.keyCode == 13){
			        	$("#reset-form").submit();
			        }
			    });
				*/
				
				$("#thwd_answer").keypress(function (e) {
			        if(e.keyCode == 13){
			        	$("#change_thwd").focus();
			        }
			    });
				
				$("#change_thwd").keypress(function (e) {
			        if(e.keyCode == 13){
			        	$("#change_thwd_confirm").focus();
			        }
			    });
			    
			    
				$("#join_email").keypress(function (e) {
			        if(e.keyCode == 13){
			        	$("#join_nickname").focus();
			        }
			    });
			    
				$("#join_nickname").keypress(function (e) {
			        if(e.keyCode == 13){
			        	$("#join_thwd").focus();
			        }
			    });
				
				$("#join_thwd").keypress(function (e) {
			        if(e.keyCode == 13){
			        	$("#join_thwd_confirm").focus();
			        }
			    });
			    
				/*
				$("#change_thwd_confirm").keypress(function (e) {
			        if(e.keyCode == 13){
			        	$("#authentication-form").submit();
			        }
			    });
				*/

				/* 소셜로그인 설정정보를 초기화하고 연동을 준비 */
				naverLogin.init();
							
				$("#join").click(function () {
					joinProcGo();
			    });
				
				//TEST
				$("#email").val("smlotto@naver.com");
				$("#thwd").val("Qudrkfl!3023");
				
				// 로그인체크 메세지 표시
				if ("N" == $("#isLogin").val()) {
					showSmallBox("로그인이 필요합니다.");
				}
				
			});
			
			function loginProc() {
				dataLayer.push({'event': 'login-click'});
				
				var paramMap = {};
				var email      = '';
				var thwd     = '';
				
				email  = $.trim($('#email').val());
				
				thwd = $.trim($('#thwd').val());
				
				if ((email == "") && (thwd == "")) {
					return;
				}
				
				if (email == "") {
					alert("이메일을 입력하여 주십시요.");
					return;
				}
				
				if (thwd == "" || thwd=="비밀번호") {
					alert("비밀번호를 입력하여 주십시요.");
					return;
				}
				
				paramMap = {'email':email, 'thwd':thwd};
		        $.ajax({
		            type: "POST",
		            url: "${APP_ROOT}/login/loginProc.do",
		            data: paramMap,
		            dataType: "json",
		            async: true,
		            error:function(data, a, b){
		            	alert('서버와의 연결이 원활하지 않습니다.');
		            },
		            success: function(result){
		            	console.log('result.status=' + result.status);
		            	if(result.status=="success"){
		            		if (result.result == "T") {
	        					window.location.href = "/";
	        					//window.location.href = result.goto;
		            		} else {
	            				alert(result.msg);
		            		}
		            	} else {
		            		if (result.result == "F05") {
		            		
		            			if (confirm(result.msg)) {
		            				//authenticationProcGo(result);
		            				forgetProcGo();
		            			}
		            			
		        			} else if (result.result == "T01") {
		        				
		        				alert(result.msg);
		        				
		        				resetProcGo();
		        				
		        			} else if (result.result == "failover") {
		        				//비밀번호 5회 초과 시 재설정여부 확인
		            			//alert(result.msg);
		        				if (confirm(result.msg)) {
		        					alert('SMS인증 팝업');
		        					
		        					//smsAuthPopup();
		        					
		        				} else {
		        					alert('취소되었습니다.');
		        				}
		        				
		        			} else {           	
		        				if (result.msg) {
		            				alert(result.msg);
		            			} else {
		            				alert(result.errorMsg);
		            			}
		            		}
		            	}
		            }
		        });	
			}
			
			function resetProc() {
				dataLayer.push({'event': 'user-init-click'});
				
				var setUsrId      = $.trim($('#set_usr_id').val());
				var newThwd    = $.trim($('#new_thwd').val());
				var thwdQ      = $.trim($('#thwd_q').val());
				var thwdA      = $.trim($('#thwd_a').val());
				
				var param = {
					'set_usr_id' : setUsrId,
					'new_thwd' : newThwd,
					'thwd_q' : thwdQ,
					'thwd_a' : thwdA
				};
				
		        $.ajax({
		            type: "POST",
		            url: "${APP_ROOT}/login/setUserInfo.do",
		            data: param,
		            dataType: "json",
		            async: true,
		            error:function(data, a, b){
		            	alert('서버와의 연결이 원활하지 않습니다.');
		            },
		            success: function(result){
		            	alert(result.msg);
						
						if ("success" == result.status) {
							$("#login-form").removeClass("hide");
					        $("#reset-form").addClass("hide");
					        
					        $("#thwd").val("");
							$("#thwd").focus();
						}
		            }
		        });	
			}
			
			function forgetProc() {
				var searchUsrId      = $.trim($('#search_usr_id').val());
				
				var param = {
					'search_usr_id' : searchUsrId
				};
				
		        $.ajax({
		            type: "POST",
		            url: "${APP_ROOT}/login/initThwd.do",
		            data: param,
		            dataType: "json",
		            async: true,
		            error:function(data, a, b){
		            	alert('서버와의 연결이 원활하지 않습니다.');
		            },
		            success: function(result){
		            	alert(result.msg);
						
						if ("success" == result.status) {
							$("#usr_id").val(searchUsrId);
							
							$("#forget-form").addClass("hide");
					        $("#login-form").removeClass("hide");
					        
					        $("#thwd").val("");
							$("#thwd").focus();
							
						}
		            }
		        });	
			}
			
			function authenticationProc() {
				var changeUsrId      = $.trim($('#change_usr_id').val());
				var thwdAnswer    = $.trim($('#thwd_answer').val());
				var changeThwd      = $.trim($('#change_thwd').val());
				
				var param = {
					'change_usr_id' : changeUsrId,
					'thwd_a' : thwdAnswer,
					'change_thwd' : changeThwd
				};
				
		        $.ajax({
		            type: "POST",
		            url: "${APP_ROOT}/login/changeThwd.do",
		            data: param,
		            dataType: "json",
		            async: true,
		            error:function(data, a, b){
		            	alert('서버와의 연결이 원활하지 않습니다.');
		            },
		            success: function(result){
		            	alert(result.msg);
						
						if ("success" == result.status) {
							$("#login-form").removeClass("hide");
					        $("#authentication-form").addClass("hide");
					        
					        $("#usr_id").val(changeUsrId);
					        
					        $("#thwd").val("");
							$("#thwd").focus();
						} else {
							$("#thwd_a").focus();							
						}
		            }
		        });	
			}
			
			function joinProc() {
				var joinEmail = $.trim($('#join_email').val());
				var joinThwd = $.trim($('#join_thwd').val());
				var joinNickname = $.trim($('#join_nickname').val());
				
				var param = {
					'join_email' : joinEmail,
					'join_thwd' : joinThwd,
					'join_nickname' : joinNickname
				};
				
		        $.ajax({
		            type: "POST",
		            url: "${APP_ROOT}/login/join.do",
		            data: param,
		            dataType: "json",
		            async: true,
		            error:function(data, a, b){
		            	alert('서버와의 연결이 원활하지 않습니다.');
		            },
		            success: function(result){
				        $("#join-form").addClass("hide");
						$("#login-form").removeClass("hide");
						
						if ("success" == result.status) {
							showSmallBox(result.msg);
					        $("#email").val(joinEmail);
						}
		            }
		        });	
			}
			
			function authenticationProcGo(result) {
				$("#change_usr_id").val($("#usr_id").val());
				
				if (result.question) {
					$("#thwd_question").val(result.question);
					$("#thwd_question").attr("disabled", "disabled");
					
				}
				$("#login-form").addClass("hide");
		        $("#authentication-form").removeClass("hide");
		        
				$("#thwd_answer").focus();
			}
			
			function resetProcGo() {
				
				$("#set_usr_id").val($("#usr_id").val());
				
				$("#login-form").addClass("hide");
		        $("#reset-form").removeClass("hide");
		        
				$("#new_thwd").focus();
			}
			
			function forgetProcGo() {
				dataLayer.push({'event': 'find-pw-click'});
				
				$("#search_usr_id").val($("#usr_id").val());
				
				$("#login-form").addClass("hide");
		        $("#forget-form").removeClass("hide");
		        
				$("#search_usr_id").focus();
			}
			
			function joinProcGo() {
				dataLayer.push({'event': 'join-click'});
				
				$("#login-form").addClass("hide");
		        $("#join-form").removeClass("hide");
			}
		</script>

		<!-- Global site tag (gtag.js) - Google Analytics -->
		<script async src="https://www.googletagmanager.com/gtag/js?id=G-YRLXXYRZXT"></script>
		<script>
		  window.dataLayer = window.dataLayer || [];
		  function gtag(){dataLayer.push(arguments);}
		  gtag('js', new Date());
		
		  gtag('config', 'G-YRLXXYRZXT');
		</script>
	</body>
</html>