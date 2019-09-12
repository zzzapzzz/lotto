<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>SM Lotto</title>
	
	<#assign JS_ROOT = SystemInfo.js_root>
	<#assign APP_ROOT = SystemInfo.app_root>
	
</head>

<body>
	<input type="hidden" id="nextUrl" name="nextUrl" value="${nextUrl?if_exists}" />
	
	<!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script> if (!window.jQuery) { document.write('<script src="${JS_ROOT}/libs/jquery-2.1.1.min.js"><\/script>');} </script>

	<!-- Javscript 설정 정보 및 초기화 -->
	<script>
		$(function() {
			//alert('nextUrl=' + $("#nextUrl").val());
			if ($("#nextUrl").val() == '') {
				window.location.href = "/fhrmdlsapdls.do";
			} else {
				window.location.href = $("#nextUrl").val();
			}
		});
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