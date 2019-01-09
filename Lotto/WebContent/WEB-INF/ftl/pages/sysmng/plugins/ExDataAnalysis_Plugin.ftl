		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		<#assign IMG_ROOT = SystemInfo.img_root>
		
		<script type="text/javascript">
			var ex_count = Number($("#ex_count").val());
			var last_count = Number($("#last_count").val());
			
			var color = Chart.helpers.color;
			
			// Define a plugin to provide data labels
			Chart.plugins.register({
				afterDatasetsDraw: function(chart) {
					var ctx = chart.ctx;
					var dataType = chart.data.dataType;
					
					chart.data.datasets.forEach(function(dataset, i) {
						var meta = chart.getDatasetMeta(i);
						if (!meta.hidden) {
							meta.data.forEach(function(element, index) {
								// Draw the text in black, with the specified font
								ctx.fillStyle = 'rgb(0, 0, 0)';
	
								var fontSize = 16;
								var fontStyle = 'normal';
								var fontFamily = 'Helvetica Neue';
								ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
	
								// Just naively convert to string for now
								var dataString = dataset.data[index].toString();
								
								if (dataType == "lowhigh"
									|| dataType == "oddeven"
									) {
									dataString += "%";
								}
	
								// Make sure alignment settings are correct
								ctx.textAlign = 'center';
								ctx.textBaseline = 'middle';
	
								var padding = -15;
								var position = element.tooltipPosition();
								ctx.fillText(dataString, position.x, position.y - (fontSize / 2) - padding);
							});
						}
					});
				}
			});

			// plugin 화면 초기화
			function initPlugin() {
				//예상번호 30조합 조회
				setExpectData30List();
				
				//최근 당첨회차 저고비율 조회
				setLowHighChart();
				
				//최근 당첨회차 홀짝비율 조회
				setOddEvenChart();
				
				//회차합 조회
				setCountSumDataList();
				
				//제외수 조회
				setExcludeNumberList();
			}
			
			function setExpectData30List() {
				var param = {
					ex_count : ex_count 
				};
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/getExData30List.do",
					data: param,
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
							if (result.ex_numbers_cnt > 0) {
								$("#ex_numbers").html("");
								
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
								$("#ex_numbers").html("<font color='red'><b>예측번호 목록이 없습니다.</b></font>");
							}
			    			
		            	} else {
		            		alert(result.msg);
		            	}
					}
				});
			}
			
			function setLowHighChart() {
			
				var param = {
					win_count : last_count
				};
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/getLowHighData.do",
					data: param,
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
							var labels = [];
							var data1 = [];

							// 데이터 설정
							for ( var i = 0 ; i < result.lowHighDataList.length ; i++) {
								labels.push(result.lowHighDataList[i].lowhigh_type);
								data1.push(parseFloat(result.lowHighDataList[i].ratio));
							}
							
							var barChartData = {
								labels: labels,
								dataType: 'lowhigh',
								datasets: [{
									type: 'bar',
									label: '저고비율',
									backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
									borderColor: window.chartColors.red,
									data: data1
								}]
							};
							
						    var ctx = document.getElementById("barChartLowHigh").getContext('2d');
							window.myBar = new Chart(ctx, {
								type: 'bar',
								data: barChartData,
								options: {
									responsive: true
									/*
									,
									title: {
										display: true,
										text: 'Chart.js Combo Bar Line Chart'
									}
									*/
								}
							});
							
		            	} else {
		            		alert(result.msg);
		            	}
					}
				});
			}
			
			function setOddEvenChart() {
			
				var param = {
					win_count : last_count
				};
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/getOddEvenData.do",
					data: param,
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
							var labels = [];
							var data1 = [];

							// 데이터 설정
							for ( var i = 0 ; i < result.oddEvenDataList.length ; i++) {
								labels.push(result.oddEvenDataList[i].oddeven_type);
								data1.push(parseFloat(result.oddEvenDataList[i].ratio));
							}
							
							var barChartData = {
								labels: labels,
								dataType: 'oddeven',
								datasets: [{
									type: 'bar',
									label: '홀짝비율',
									backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
									borderColor: window.chartColors.red,
									data: data1
								}]
							};
							
						    var ctx = document.getElementById("barChartOddEven").getContext('2d');
							window.myBar = new Chart(ctx, {
								type: 'bar',
								data: barChartData,
								options: {
									responsive: true
									/*
									,
									title: {
										display: true,
										text: 'Chart.js Combo Bar Line Chart'
									}
									*/
								}
							});
							
		            	} else {
		            		alert(result.msg);
		            	}
					}
				});
			}
			
			function setCountSumDataList() {			
				var param = {
					win_count : last_count
				};
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/getCountSumDataList.do",
					data: param,
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
							if (result.contain10List.length > 0) {
								$("#contain10List").html("");
								
								var bf10Div = 0;	// 10의 자리수
								
								for (var i = 0 ; i < result.contain10List.length ; i++) {
									// 줄변경 처리
									var div10 = parseInt(Number(result.contain10List[i]) / 10);
					        		if (Number(result.contain10List[i]) % 10 != 0 
					        			&& bf10Div < div10
					        			) {
							        	$("#contain10List").append('<br>');
					        			bf10Div = div10;
					        		}
						        		
									$("#contain10List").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.contain10List[i] +'.png" alt="' + result.contain10List[i] + '"/>');
						        	$("#contain10List").append('&nbsp;');

									// 줄변경 처리
									if (Number(result.contain10List[i]) % 10 == 0) {
							        	$("#contain10List").append('<br>');
							        	bf10Div = parseInt(parseInt(result.contain10List[i]) / 10);
						        	}
								}
							} else {
								$("#contain10List").html("10회차 포함번호 목록이 없습니다.");
							}
							
							if (result.notContain10List.length > 0) {
								$("#notContain10List").html("");
								
								var bf10Div = 0;	// 10의 자리수
								
								for (var i = 0 ; i < result.notContain10List.length ; i++) {
									// 줄변경 처리
									var div10 = parseInt(Number(result.notContain10List[i]) / 10);
					        		if (Number(result.notContain10List[i]) % 10 != 0 
					        			&& bf10Div < div10
					        			) {
							        	$("#notContain10List").append('<br>');
					        			bf10Div = div10;
					        		}
					        		
									$("#notContain10List").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.notContain10List[i] +'.png" alt="' + result.notContain10List[i] + '"/>');
						        	$("#notContain10List").append('&nbsp;');
						        	
						        	// 줄변경 처리
									if (Number(result.notContain10List[i]) % 10 == 0) {
							        	$("#notContain10List").append('<br>');
							        	bf10Div = parseInt(parseInt(result.notContain10List[i]) / 10);
						        	}
								}
							} else {
								$("#notContain10List").html("10회차 미포함번호 목록이 없습니다.");
							}
			    			
		            	} else {
		            		alert(result.msg);
		            	}
					}
				});
			}
			
			function setExcludeNumberList() {
				var param = {
					ex_count : ex_count
				};
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/getExcludeNumberList.do",
					data: param,
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
							if (result.excludeNumberList.length > 0) {
								$("#excludeNumberList").html("");
								
								for (var i = 0 ; i < result.excludeNumberList.length ; i++) {
									if (i > 0) {
							        	$("#excludeNumberList").append('&nbsp;');
									}
									$("#excludeNumberList").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.excludeNumberList[i] +'.png" alt="' + result.excludeNumberList[i] + '"/>');
								}
							} else {
								$("#excludeNumberList").html("제외수 목록이 없습니다.");
							}
			    			
		            	} else {
		            		alert(result.msg);
		            	}
					}
				});
			}
			
			function cancel() {
				var cid = $("#currCid").val(); 
				openContent(cid);
			}
			
		</script>