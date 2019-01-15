		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		<#assign IMG_ROOT = SystemInfo.img_root>
		
		<script src="${JS_ROOT}/plugin/jqgrid/jquery.jqGrid.min.js"></script>
		<script src="${JS_ROOT}/plugin/jqgrid/grid.locale-en.min.js"></script>

		<script type="text/javascript">
			var isAction = 'N';
			
			$(document).ready(function() {
				var isAjax = "${isAjax?if_exists}";
				if ("N" == isAjax) {
					initPlugin();
				}
			});
			
			function initPlugin() {
				$("#totalTest").click(totalTestGo);
				
				// 총합 테스트
				totalTestGo();
			}
			
			function totalTestGo() {
				$.ajax({
					type: "POST",
					url: "/test/totalTest.do",
					dataType: "json",
					async: false,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					error:function(xhr, textStatus, errorThrown){
						alert(xhr.responseText);				
					},
					success: function(result){
						// 세션에 사용자 정보가 존재하지 않을때 메인으로 이동
						if (result.status == "usernotfound") {
			           		location.href = "/index.do"; 
			        	}
			        	
			        	for (var i = 0 ; i < result.rows.length ; i++) {
			        		var msg = result.rows[i].msg;
			        		
			        		$("#totalTestResult").append(msg +'&nbsp;&nbsp;');
			        		
			        		if ((i+1) % 10 == 0) {
					        	$("#totalTestResult").append('<br>');
				        	}
			        	}
			        	
					}
				});
			}
			
			function testGo() {
				$("#testResult").html("테스트 결과");
			}
			
			function setWinCountList() {
				var param = {
					page: '1'
				};
				$.ajax({
					type: "POST",
					url: "/sysmng/getWinDataList.do",
					data: param,
					dataType: "json",
					async: false,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					error:function(xhr, textStatus, errorThrown){
						alert(xhr.responseText);				
					},
					success: function(result){
						// 세션에 사용자 정보가 존재하지 않을때 메인으로 이동
						if (result.status == "usernotfound") {
			           		location.href = "/index.do"; 
			        	}
			        	
			        	for (var i = 0 ; i < result.rows.length ; i++) {
			        		var win_count = result.rows[i].win_count;
			        		
			        		$("#win_count").append('<option value="'+ win_count +'">'+ win_count +'</option>');
			        	}
			        	
			        	searchGo();
					}
				});
			}
			
			function searchGo() {
				var param = {		
					win_count : Number($("#win_count").val())
				};	
				
				$.ajax({
					type: "POST",
					url: "/win/getWinData.do",
					data: param,
					dataType: "json",
					async: false,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					error:function(xhr, textStatus, errorThrown){
						alert(xhr.responseText);				
					},
					success: function(result){
						// 세션에 사용자 정보가 존재하지 않을때 메인으로 이동
						if (result.status == "usernotfound") {
			           		location.href = "/index.do"; 
			        	}
			        				        	
					}
				});
			}
			
		</script>