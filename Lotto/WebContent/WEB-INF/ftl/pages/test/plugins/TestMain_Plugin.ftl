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
				$("#totalTest").click(totalTest);
				$("#testAppearNumbers").click(appearNumbersTest);
				$("#testNumbersRange").click(numbersRangeTest);
				$("#testZeroCntRange").click(zeroCntRangeTest);
				$("#insertAllAcInfo").click(insertAllAcInfoGo);
				
				// 총합 테스트
				totalTest();
				
				$("#totalTestResult").hide();
			}
			
			//총합
			function totalTest() {
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
			        	$("#totalRange").html("");
			        	$("#totalTestResult").html("");
			        	
			        	$("#totalRange").html(result.totalRange);
			        	
			        	for (var i = 0 ; i < result.rows.length ; i++) {
			        		var msg = result.rows[i];
			        		
			        		$("#totalTestResult").append(msg +'&nbsp;&nbsp;');
			        		
			        		if ((i+1) % 10 == 0) {
					        	$("#totalTestResult").append('<br>');
				        	}
			        	}
			        	
					}
				});
			}
			
			//최대 출현횟수
			function appearNumbersTest() {
				$.ajax({
					type: "POST",
					url: "/test/appearNumbersTest.do",
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
			        	
			        	$("#matchCnt").html("");
			        	$("#matchPer").html("");
			        	
			        	$("#matchCnt").html("매칭개수 : " + result.isMatchCnt);
			        	$("#matchPer").html("일치율 : " + result.isMatchPer);
			        	
					}
				});
			}
			
			//번호간 범위
			function numbersRangeTest() {
				$.ajax({
					type: "POST",
					url: "/test/numbersRangeTest.do",
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
			        	
			        	$("#matchCnt").html("");
			        	$("#matchPer").html("");
			        	
			        	$("#matchCnt").html("매칭개수 : " + result.isMatchCnt);
			        	$("#matchPer").html("일치율 : " + result.isMatchPer);
			        	
					}
				});
			}
			
			//미출현구간
			function zeroCntRangeTest() {
				$.ajax({
					type: "POST",
					url: "/test/zeroCntRangeTest.do",
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
			        	
						$("#matchCnt").html("");
			        	$("#matchPer").html("");
			        	
			        	$("#matchCnt").html("매칭개수 : " + result.isMatchCnt);
			        	$("#matchPer").html("일치율 : " + result.isMatchPer);
			        	
					}
				});
			}
			
			function insertAllAcInfoGo() {
				$.ajax({
					type: "POST",
					url: "/test/insertAllAcInfo.do",
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
			        	
			        	if (result.status == "success") {
			        		showSmallBox(result.msg);
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