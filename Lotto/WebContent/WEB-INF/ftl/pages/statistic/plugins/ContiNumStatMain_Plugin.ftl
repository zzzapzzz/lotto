		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		<#assign IMG_ROOT = SystemInfo.img_root>
		
		<script src="${JS_ROOT}/plugin/jqgrid/jquery.jqGrid.min.js"></script>
		<script src="${JS_ROOT}/plugin/jqgrid/grid.locale-en.min.js"></script>

		<script type="text/javascript">
			var isAction = 'N';
			
			$(document).ready(function() {
				pageSetUp();
				
				dataLayer.push({
				  'pageCategory': 'ContiNumStatMain',
				  'visitorType': $("#authTask").val()
				});
				
			});
			
			function setContiNumStat() {
			
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/statistic/continumstatdatas.do",
					dataType: "json",
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					error:function(xhr, textStatus, errorThrown){
						alert(xhr.responseText);				
					},
					success: function(result){
						// 세션에 사용자 정보가 존재하지 않을때 메인으로 이동
						if (result.status == "usernotfound") {
		               		location.href = "/index.do";
		               		return;
		            	}
		            	
						if (result.status == "success") {
							// 데이터 설정
							// console.log('result.datas=', result.datas);
							for ( var i = 0 ; i < result.datas.length - (result.datas.length - 10) ; i++) {								
								
								$("#widget-grid").append('<div class="row">');
								$("#widget-grid").append('<div class="content">');
								$("#widget-grid").append('<div class="col-xs-3 col-sm-3 col-md-5 col-lg-5">');
								$("#widget-grid").append('<h1 class="txt-color-blueDark" style="text-align: right;">');
								$("#widget-grid").append('<strong>' + result.datas[i].win_count + '회</strong>');
								$("#widget-grid").append('</h1>');
								$("#widget-grid").append('</div>');
								
								$("#widget-grid").append('<div class="col-xs-9 col-sm-9 col-md-7 col-lg-7">');
								
								$("#widget-grid").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.datas[i].num1 +'.png" alt="' + result.datas[i].num1 + '"/>');
								$("#widget-grid").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.datas[i].num2 +'.png" alt="' + result.datas[i].num2 + '"/>');
								$("#widget-grid").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.datas[i].num3 +'.png" alt="' + result.datas[i].num3 + '"/>');
								$("#widget-grid").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.datas[i].num4 +'.png" alt="' + result.datas[i].num4 + '"/>');
								$("#widget-grid").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.datas[i].num5 +'.png" alt="' + result.datas[i].num5 + '"/>');
								$("#widget-grid").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.datas[i].num6 +'.png" alt="' + result.datas[i].num6 + '"/>');
								
								$("#widget-grid").append('</div>');
								$("#widget-grid").append('</div>');
								$("#widget-grid").append('</div>');
							}
							
		            	} else {
		            		alert(result.msg);
		            	}
					}
				});
			}
		</script>