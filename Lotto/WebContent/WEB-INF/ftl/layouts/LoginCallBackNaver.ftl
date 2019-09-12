<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Naver Login</title>
	
	<#assign JS_ROOT = SystemInfo.js_root>
	<#assign APP_ROOT = SystemInfo.app_root>
	
</head>

<body>

	화면으로 로딩중입니다.

	<input type="hidden" id="email" name="email" />
	<input type="hidden" id="user_id" name="user_id" />

	<!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script> if (!window.jQuery) { document.write('<script src="${JS_ROOT}/libs/jquery-2.1.1.min.js"><\/script>');} </script>


	<!-- (1) LoginWithNaverId Javscript SDK -->
	<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>


	<!-- (2) LoginWithNaverId Javscript 설정 정보 및 초기화 -->
	<script>
		var naverLogin = new naver.LoginWithNaverId(
			{
				clientId: "_vZ13kySrEVrBIV2C9Di",
				callbackUrl: "http://127.0.0.1/fhrmdlsapdls.do",
				isPopup: false,
				callbackHandle: true
				/* callback 페이지가 분리되었을 경우에 callback 페이지에서는 callback처리를 해줄수 있도록 설정합니다. */
			}
		);

		/* (3) 네아로 로그인 정보를 초기화하기 위하여 init을 호출 */
		naverLogin.init();

		/* (4) Callback의 처리. 정상적으로 Callback 처리가 완료될 경우 main page로 redirect(또는 Popup close) */
		window.addEventListener('load', function () {
			naverLogin.getLoginStatus(function (status) {
				if (status) {
				
					console.log('naver get user info success=', JSON.stringify(naverLogin.user));
				
					/* (5) 필수적으로 받아야하는 프로필 정보가 있다면 callback처리 시점에 체크 */
					var email = naverLogin.user.getEmail();
					if( email == undefined || email == null) {
						alert("이메일은 필수정보입니다. 정보제공을 동의해주세요.");
						/* (5-1) 사용자 정보 재동의를 위하여 다시 네아로 동의페이지로 이동함 */
						naverLogin.reprompt();
						return;
					}
										
					console.log('email=',naverLogin.user.getEmail());
					console.log('user_id=',naverLogin.user.getId());

					$("#email").val(naverLogin.user.getEmail());
					$("#user_id").val(naverLogin.user.getId());

					loginProc();
				} else {
					console.log("callback 처리에 실패하였습니다.");
					
					window.location.href = "/";
				}
			});
		});
		
		function loginProc() {
				var paramMap = {
					'email':$("#email").val(), 
					'user_id':$("#user_id").val(),
					'sns_type':'naver'
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