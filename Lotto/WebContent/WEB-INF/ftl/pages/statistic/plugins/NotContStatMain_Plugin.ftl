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
				  'pageCategory': 'NotContStatMain',
				  'visitorType': $("#authTask").val()
				});

				setNotContStat();
			});
			
			function setNotContStat() {
			
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/statistic/notcontstatdatas.do",
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
		            	
		            	$("#range1Numbers").html("");
		            	$("#range2Numbers").html("");
		            	$("#range3Numbers").html("");
		            	$("#range4Numbers").html("");
		            	$("#range5Numbers").html("");

						if (result.status == "success") {
							// 데이터 설정
							console.log('result.datas=', result.datas);
							for ( var i = 0 ; i < result.datas.length ; i++) {								
								if (result.datas[i] <= 10) {
						  			$("#range1Numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.datas[i] +'.png" alt="' + result.datas[i] + '"/>');
						  		} else if (result.datas[i] <= 20) {
						  			$("#range2Numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.datas[i] +'.png" alt="' + result.datas[i] + '"/>');
						  		} else if (result.datas[i] <= 30) {
						  			$("#range3Numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.datas[i] +'.png" alt="' + result.datas[i] + '"/>');
						  		} else if (result.datas[i] <= 40) {
						  			$("#range4Numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.datas[i] +'.png" alt="' + result.datas[i] + '"/>');
						  		} else {
						  			$("#range5Numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.datas[i] +'.png" alt="' + result.datas[i] + '"/>');
						  		}
							}
							
		            	} else {
		            		alert(result.msg);
		            	}
					}
				});
			}
		</script>