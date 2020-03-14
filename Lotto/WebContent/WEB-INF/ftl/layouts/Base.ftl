<#assign tiles=JspTaglibs["http://tiles.apache.org/tags-tiles"] />
<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<title><@tiles.getAsString name="title" /></title>
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<meta name="description" content="심서방로또">
		<meta name="author" content="cremazer">
			
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<#assign CSS_ROOT = SystemInfo.css_root>
	    <#assign JS_ROOT = SystemInfo.js_root>
	    <#assign APP_ROOT = SystemInfo.app_root>
	    <#assign IMG_ROOT = SystemInfo.img_root>
	    
		<!-- Basic Styles -->
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/font-awesome.min.css">

		<!-- SmartAdmin Styles : Caution! DO NOT change the order -->
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/smartadmin-production-plugins.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/smartadmin-production.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/smartadmin-skins.min.css">

		<!-- SmartAdmin RTL Support  -->
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/smartadmin-rtl.min.css">

		<!-- We recommend you use "your_style.css" to override SmartAdmin
		     specific styles this will also ensure you retrain your customization with each SmartAdmin update.
		-->
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/your_style.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/simple-popup.css">

		<!-- Demo purpose only: goes with demo.js, you can delete this css when designing your own WebApp -->
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/demo.min.css">

		<!-- FAVICONS -->
		<link rel="shortcut icon" href="${IMG_ROOT}/favicon/favicon.ico" type="image/x-icon">
		<link rel="icon" href="${IMG_ROOT}/favicon/favicon.ico" type="image/x-icon">

		<!-- GOOGLE FONT -->
		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,300,400,700">

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

	</head>
	<!--

	TABLE OF CONTENTS.
	
	Use search to find needed section.
	
	===================================================================
	
	|  01. #CSS Links                |  all CSS links and file paths  |
	|  02. #FAVICONS                 |  Favicon links and file paths  |
	|  03. #GOOGLE FONT              |  Google font link              |
	|  04. #APP SCREEN / ICONS       |  app icons, screen backdrops   |
	|  05. #BODY                     |  body tag                      |
	|  06. #HEADER                   |  header tag                    |
	|  07. #PROJECTS                 |  project lists                 |
	|  08. #TOGGLE LAYOUT BUTTONS    |  layout buttons and actions    |
	|  09. #MOBILE                   |  mobile view dropdown          |
	|  10. #SEARCH                   |  search field                  |
	|  11. #NAVIGATION               |  left panel & navigation       |
	|  12. #RIGHT PANEL              |  right panel userlist          |
	|  13. #MAIN PANEL               |  main panel                    |
	|  14. #MAIN CONTENT             |  content holder                |
	|  15. #PAGE FOOTER              |  page footer                   |
	|  16. #SHORTCUT AREA            |  dropdown shortcuts area       |
	|  17. #PLUGINS                  |  all scripts and plugins       |
	
	===================================================================
	
	-->
	
	<!-- #BODY -->
	<!-- Possible Classes

		* 'smart-style-{SKIN#}'
		* 'smart-rtl'         - Switch theme mode to RTL
		* 'menu-on-top'       - Switch to top navigation (no DOM change required)
		* 'no-menu'			  - Hides the menu completely
		* 'hidden-menu'       - Hides the main menu but still accessable by hovering over left edge
		* 'fixed-header'      - Fixes the header
		* 'fixed-navigation'  - Fixes the main menu
		* 'fixed-ribbon'      - Fixes breadcrumb
		* 'fixed-page-footer' - Fixes footer
		* 'container'         - boxed layout mode (non-responsive: will not work with fixed-navigation & fixed-ribbon)
	-->
	<body class="desktop-detected pace-done smart-style-3">
 
		<@tiles.insertAttribute name="header" />
		<@tiles.insertAttribute name="lna" />
		
		<!-- MAIN PANEL -->
		<div id="main" role="main">
		<@tiles.insertAttribute name="content" />
		</div>
		
		<@tiles.insertAttribute name="footer" />

		<!--================================================== -->

		<!-- PACE LOADER - turn this on if you want ajax loading to show (caution: uses lots of memory on iDevices)-->
		<script data-pace-options='{ "restartOnRequestAfter": true }' src="${JS_ROOT}/plugin/pace/pace.min.js"></script>

		<!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
		<script>
			if (!window.jQuery) {
				document.write('<script src="${JS_ROOT}/libs/jquery-2.1.1.min.js"><\/script>');
			}
		</script>

		<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
		<script>
			if (!window.jQuery.ui) {
				document.write('<script src="${JS_ROOT}/libs/jquery-ui-1.10.3.min.js"><\/script>');
			}
		</script>

		<!-- IMPORTANT: APP CONFIG -->
		<script src="${JS_ROOT}/app.config.js"></script>

		<!-- JS TOUCH : include this plugin for mobile drag / drop touch events-->
		<script src="${JS_ROOT}/plugin/jquery-touch/jquery.ui.touch-punch.min.js"></script> 

		<!-- BOOTSTRAP JS -->
		<script src="${JS_ROOT}/bootstrap/bootstrap.min.js"></script>

		<!-- CUSTOM NOTIFICATION -->
		<script src="${JS_ROOT}/notification/SmartNotification.min.js"></script>

		<!-- JARVIS WIDGETS -->
		<script src="${JS_ROOT}/smartwidgets/jarvis.widget.min.js"></script>

		<!-- EASY PIE CHARTS -->
		<script src="${JS_ROOT}/plugin/easy-pie-chart/jquery.easy-pie-chart.min.js"></script>

		<!-- SPARKLINES -->
		<script src="${JS_ROOT}/plugin/sparkline/jquery.sparkline.min.js"></script>

		<!-- JQUERY VALIDATE -->
		<script src="${JS_ROOT}/plugin/jquery-validate/jquery.validate.min.js"></script>

		<!-- JQUERY MASKED INPUT -->
		<script src="${JS_ROOT}/plugin/masked-input/jquery.maskedinput.min.js"></script>

		<!-- JQUERY SELECT2 INPUT -->
		<script src="${JS_ROOT}/plugin/select2/select2.min.js"></script>

		<!-- JQUERY UI + Bootstrap Slider -->
		<script src="${JS_ROOT}/plugin/bootstrap-slider/bootstrap-slider.min.js"></script>

		<!-- browser msie issue fix -->
		<script src="${JS_ROOT}/plugin/msie-fix/jquery.mb.browser.min.js"></script>

		<!-- FastClick: For mobile devices -->
		<script src="${JS_ROOT}/plugin/fastclick/fastclick.min.js"></script>

		<!--[if IE 8]>

		<h1>브라우저가 오래되었습니다. 브라우저를 업데이트하십시오. www.microsoft.com/download</h1>

		<![endif]-->

		<!-- MAIN APP JS FILE -->
		<script src="${JS_ROOT}/app.min.js?v=0.01"></script>
		
		<!-- SM Lotto COMMON JS FILE -->
		<script src="${JS_ROOT}/common.min.js?v=0.12"></script>
		<script src="${JS_ROOT}/plugin/jquery-form/jquery-form.min.js"></script>
		
		<script>
			$(document).ready(function() {

				// DO NOT REMOVE : GLOBAL FUNCTIONS!
				pageSetUp();

				/*
				 * PAGE RELATED SCRIPTS
				 */

				$(".js-status-update a").click(function() {
					var selText = $(this).text();
					var $this = $(this);
					$this.parents('.btn-group').find('.dropdown-toggle').html(selText + ' <span class="caret"></span>');
					$this.parents('.dropdown-menu').find('li').removeClass('active');
					$this.parent().addClass('active');
				});
				
			});

		</script>

		<!-- PLUGIN PANEL -->
		<div id="plugin">
		<@tiles.insertAttribute name="plugin" />
		</div>
		
		<!-- Your GOOGLE ANALYTICS CODE Below 
		<script type="text/javascript">
			var _gaq = _gaq || [];
			_gaq.push(['_setAccount', 'UA-XXXXXXXX-X']);
			_gaq.push(['_trackPageview']);

			(function() {
				var ga = document.createElement('script');
				ga.type = 'text/javascript';
				ga.async = true;
				ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
				var s = document.getElementsByTagName('script')[0];
				s.parentNode.insertBefore(ga, s);
			})();

		</script>
		-->
		
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