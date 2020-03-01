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
				  'pageCategory': 'ExptMain',
				  'visitorType': $("#authTask").val()
				});
				
				search10();
				
				$("#search10").click(search10);
				$("#search20").click(search20);
				$("#search30").click(search30);
			});

			function initPlugin() {
			}
			
			function search10() {
				search(10);
			}
			function search20() {
				search(20);
			}
			function search30() {
				search(30);
			}
			function search(count) {
				var param = {
					ex_count : $("#ex_count").val(),
					search_count : count
				};
				
				$.ajax({
					type: "POST",
					url: "/expt/getExDataList.do",
					data: param,
					dataType: "json",
					async: false,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					error:function(xhr, textStatus, errorThrown){
						alert(xhr.responseText);				
					},
					success: function(result){
			        	
			        	// 이미지로 표시영역 초기화
						$("#ex_numbers").html("");
			        	if (result) {
			        		
			        		// 이미지로 표시
				        	$("#ex_numbers").append('<br>');
							for (var i = 0 ; i < result.ex_numbers_cnt ; i++) {
								$("#ex_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.ex_numbers[i].num1 +'.png" alt="' + result.ex_numbers[i].num1 + '"/>');
					        	$("#ex_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.ex_numbers[i].num2 +'.png" alt="' + result.ex_numbers[i].num2 + '"/>');
					        	$("#ex_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.ex_numbers[i].num3 +'.png" alt="' + result.ex_numbers[i].num3 + '"/>');
					        	$("#ex_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.ex_numbers[i].num4 +'.png" alt="' + result.ex_numbers[i].num4 + '"/>');
					        	$("#ex_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.ex_numbers[i].num5 +'.png" alt="' + result.ex_numbers[i].num5 + '"/>');
					        	$("#ex_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.ex_numbers[i].num6 +'.png" alt="' + result.ex_numbers[i].num6 + '"/>');
					        	$("#ex_numbers").append('<br>');
					        	
					        	if ((i+1) % 5 == 0) {
						        	$("#ex_numbers").append('<br>');
					        	}
							}
							
			        	} else {
			        		$("#ex_numbers").append('결과가 없습니다.');
			        	}
					}
				});
			}
		</script>