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

				setWinCountList();
			});
			
			function initPlugin() {
				$("#search").click(searchGo);
				
				$("#win_count").change(searchGo);
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
			        	
			        	$("#win_data_title").html("");
			        	$("#win_data_title").append('로또 <font color="red"><b>' + result.data.win_count + '회</b></font>차 당첨번호');
			        	
			        	$("#win_numbers").html("");
			        	$("#win_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.data.num1 +'.png" alt="' + result.data.num1 + '"/>');
			        	$("#win_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.data.num2 +'.png" alt="' + result.data.num2 + '"/>');
			        	$("#win_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.data.num3 +'.png" alt="' + result.data.num3 + '"/>');
			        	$("#win_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.data.num4 +'.png" alt="' + result.data.num4 + '"/>');
			        	$("#win_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.data.num5 +'.png" alt="' + result.data.num5 + '"/>');
			        	$("#win_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.data.num6 +'.png" alt="' + result.data.num6 + '"/>');
			        	$("#win_numbers").append(' + ');
			        	$("#win_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.data.bonus_num +'.png" alt="' + result.data.bonus_num + '"/>');
			        	
			        	$("#low_high").html(result.data.low_high);
			        	$("#odd_even").html(result.data.odd_even);
			        	$("#total").html(result.data.total);
			        	$("#sum_end_num").html(result.data.sum_end_num);
			        	$("#ac").html(result.data.ac);

						$("#count_sum").html(result.countSumInfo.count_sum);
			        	$("#cont_cnt").html(result.countSumInfo.cont_cnt);
			        	$("#not_cont_cnt").html(result.countSumInfo.not_cont_cnt);
			        	
			        	$("#mcMatchedData").html(result.mcMatchedData);
			        	
			        	$("#zeroRange").html(result.zeroRange);
			        				        	
					}
				});
			}
			
		</script>