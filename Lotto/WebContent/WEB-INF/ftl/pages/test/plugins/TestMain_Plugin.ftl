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
				
				dataLayer.push({
				  'pageCategory': 'TestMain',
				  'visitorType': $("#authTask").val()
				});
			});
			
			function initPlugin() {
				$("#totalTest").click(totalTest);
				$("#testAppearNumbers").click(appearNumbersTest);
				$("#testNumbersRange").click(numbersRangeTest);
				$("#testZeroCntRange").click(zeroCntRangeTest);
				$("#insertAllAcInfo").click(insertAllAcInfoGo);
				$("#testExclude").click(excludeTest);
				$("#testExcludeCount").click(excludeCountTest);
				$("#sendEmailTest").click(sendEmailTest);
				
				/*
				$("#testTheory1").click(function(){
					testTheory(1);
				});
				$("#testTheory2").click(testTheory(2));
				$("#testTheory3").click(testTheory(3));
				$("#testTheory4").click(testTheory(4));
				$("#testTheory5").click(testTheory(5));
				$("#testTheory6").click(testTheory(6));
				$("#testTheory7").click(testTheory(7));
				$("#testTheory8").click(testTheory(8));
				$("#testTheory9").click(testTheory(9));
				$("#testTheory10").click(testTheory(10));
				*/
				
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
			
			// 임시추출 테스트
			function user001Test() {
				$.ajax({
					type: "POST",
					url: "/test/user001Test.do",
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
			
			// 제외수 테스트
			function excludeTest() {
				$.ajax({
					type: "POST",
					url: "/test/testExclude.do",
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
			
			// 특정회차 제외수 테스트
			function excludeCountTest() {
				var param = {
					ex_count : $("#testExcludeCount").val()
				};
				
				$.ajax({
					type: "POST",
					url: "/test/testExcludeCount.do",
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
			
			// 이메일 발송 테스트
			function sendEmailTest() {
				$.ajax({
					type: "POST",
					url: "/test/sendEmail.do",
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
			
			// 나대길 검증
			function testTheory(num) {
				var param = {
					fromCheckCount : $("#fromCheckCount").val()
				};
					
				var url = "/test/testTheory" + num + ".do";
				var allAppearCnt = "theory" + num + "AllAppearCnt"; 
				var matchedCnt = "theory" + num + "MatchedCnt"; 
				var matchedPer = "theory" + num + "MatchedPer"; 
				
				$.ajax({
					type: "POST",
					url: url,
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
			        	
			        	$("#"+allAppearCnt).text(result.allAppearCnt);
			        	$("#"+matchedCnt).text(result.matchedCnt);
			        	$("#"+matchedPer).text(result.matchedPer);
					}
				});
			}
		</script>