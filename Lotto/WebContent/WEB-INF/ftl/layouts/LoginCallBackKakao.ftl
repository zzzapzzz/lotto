<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Kakao Login</title>
	
	<meta name="description" content="심서방로또">
	<meta name="author" content="cremazer">
		
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
    input.kor {
    	ime-mode:active;
    }
    </style>
</head>

<body>
	<input type="hidden" id="user_id" name="user_id" />
	<input type="hidden" id="email" name="email" />
	<input type="hidden" id="email_varify_yn" name="email_varify_yn" />
	<input type="hidden" id="nickname" name="nickname" />
	<input type="hidden" id="thumbnail_image" name="thumbnail_image" />
	
	<header id="header">

		<div id="logo-group">
			<span id="logo"> <img src="${IMG_ROOT}/logo.png" alt="Logo Image"> </span>
		</div>

	</header>

	<div id="main" role="main">

		<!-- MAIN CONTENT -->
		<div id="content" class="container hide">

			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-4 pull-left">
					<div class="well no-padding">
					
						<!-- 회원가입 -->
						<form action="javascript:joinProc();" id="join-form" class="smart-form client-form">
							<header>
								<i class="fa fa-edit"></i> 추가정보 입력
							</header>

							<fieldset>
								<section>
									<div class="note">
										추가정보 입력이 필요합니다.
									</div>
								</section>
								
								<section>
									<div id="msgSection" class="alert alert-danger fade in hide">
										<button class="close" data-dismiss="alert">
											×
										</button>
										<i class="fa-fw fa fa-times"></i>
										<div id="msg"></div>
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
									로그인
								</button>
							</footer>
						</form>
						<!-- end 회원가입 -->
						
					</div>
				</div>
			</div>
		</div>

	</div>
	
	
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


	<!-- KAKAO -->
	<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>

	<!--[if IE 8]>
			
		<h1>브라우저가 최신 버전이 아닙니다. <a href="http://www.microsoft.com/download" target="_blank">www.microsoft.com/download</a>에서 브라우저를 업데이트하십시오.</h1>
		
	<![endif]-->

	<!-- Javscript 설정 정보 및 초기화 -->
	<script>

		Kakao.init('1342d57c2672941f535211c80bf2b025');
		
		/* Callback의 처리.  */
		Kakao.API.request({
			url: '/v1/user/me',
			success: function(res) {
				// kakao.api.request 에서 불러온 결과값 json형태로 출력
 				console.log('kakao get user info success=', JSON.stringify(res));
 				
 				// 콘솔 로그에 id 정보 출력(id는 res안에 있기 때문에  res.id 로 불러온다)
 				console.log('id=',res.id);
 				// 콘솔 로그에 email 정보 출력
 				console.log('email=',res.kaccount_email);
				// 콘솔 로그에 닉네임 출력(properties에 있는 nickname 접근, res.properties.nickname으로도 접근 가능)
 				console.log('nickname=',res.properties['nickname']);
 				// 썸네일 이미지
             	console.log(res.properties['thumbnail_image']);
             	
             	$("#user_id").val(res.id);
             	if (res.kaccount_email_verified) {
	             	$("#email_varify_yn").val('Y');
             	} else {
	             	$("#email_varify_yn").val('N');
             	}
             	$("#nickname").val(res.properties['nickname']);
             	$("#thumbnail_image").val(res.properties['thumbnail_image']);
             	
             	// kakao not valid email check
             	if (res.kaccount_email) {
	             	$("#email").val(res.kaccount_email);
	             	
	             	loginProc();
             	} else {
	             	$("#join_nickname").val(res.properties['nickname']);
             		
             		$("#content").removeClass("hide");
             		$("#join_email").focus();
             	}
			}
		});
		
		$(function() {
			// Validation
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
		    
		});
		
		function loginProc() {
			var paramMap = {
				'email':$("#email").val(), 
				'email_varify_yn':$("#email_varify_yn").val(),
				'user_id':$("#user_id").val(),
				'nickname':$("#nickname").val(),
				'thumbnail_image':$("#thumbnail_image").val(),
				'sns_type':'kakao'
			};
			
	        $.ajax({
	            type: "POST",
	            url: "${APP_ROOT}/login/snsLoginProc.do",
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
        				if (result.msg) {
            				alert(result.msg);
            			} else {
            				alert(result.errorMsg);
            			}
            			window.location.href = "/fhrmdlsapdls.do";
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
					if ("success" == result.status) {
				        $("#email").val($("#join_email").val());
				        
				        $("#content").addClass("hide");
				        $("#msgSection").addClass("hide");
				        
				        loginProc();
					} else {
						//TODO 이메일 본인인증 연동 또는
						// 재입력 처리 필요.							
				        $("#msgSection").removeClass("hide");
				        $("#msg").text(result.msg);
					}
	            }
	        });	
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