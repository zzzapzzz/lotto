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
								ctx.fillText(dataString, position.x, position.y - fontSize + 5);
							});
						}
					});
				}
			});

			$(document).ready(function() {
				dataLayer.push({
				  'pageCategory': 'ExDataAnalysis',
				  'visitorType': $("#authTask").val()
				});
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
				
				//궁합수 조회
				setMcNumberList();
			}
			
			function setExpectData30List() {
				var param = {
					ex_count : ex_count 
				};
				
				$.ajax({
					type: "POST",
					// url: "${APP_ROOT}/sysmng/getExData30List.do",
					url: "${APP_ROOT}/sysmng/getExData30NewList.do",
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
							if (result.excludeNumberListNormal.length > 0) {
								$("#excludeNumberListNormal").html("");
								
								for (var i = 0 ; i < result.excludeNumberListNormal.length ; i++) {
									if (i > 0) {
							        	$("#excludeNumberListNormal").append('&nbsp;');
									}
									$("#excludeNumberListNormal").append('<img src="${IMG_ROOT}/ballnumber/ball_' + result.excludeNumberListNormal[i] +'.png" alt="' + result.excludeNumberListNormal[i] + '"/>');
								}
							} else {
								$("#excludeNumberListNormal").html("제외수 목록이 없습니다.");
							}
			    			
			    			// 제외수 설정
			    			$("#excludeNumberListNormalCnt").html(result.excludeNumberListNormalCnt);
			    			// 개선된 제외수 목록
			    			$("#modiExcludeNum").html(result.modiExcludeNum);
			    			
		            	} else {
		            		alert(result.msg);
		            	}
					}
				});
			}
			
			function setMcNumberList() {
				var param = {
					win_count : last_count
				};
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/getMcNumberList.do",
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

						$("#mcNumberList").html("");
						$("#mcNumberList2").html("");
						if (result.status == "success") {
							if (result.mcNumList.length > 0) {
								
								for (var i = 0 ; i < 10 ; i++) {
									var mcNumberList = "";
									mcNumberList += '<tr>';
									
									for (var j = 0 ; j < 5 - 2 ; j++) {
										mcNumberList += '<td>';
										if (i > 4 && j == 4) {
											mcNumberList += '';
										} else {
											mcNumberList += result.mcNumList[i + (j*10)].num;
										}
										mcNumberList += '</td><td style="text-align: left;">';
										if (result.mcNumList[i].mc_num.length > 0) {
											if (i > 4 && j == 4) {
												mcNumberList += '';
											} else {
												mcNumberList += result.mcNumList[i + (j*10)].mc_num;
											} 
										} else {
											mcNumberList += '궁합수가 없습니다.';
										}
										mcNumberList += '</td>';
									}
									
									mcNumberList += '</tr>';
									$("#mcNumberList").append(mcNumberList);
									
									var mcNumberList2 = "";
									mcNumberList2 += '<tr>';
									for (var j = 0 + 3 ; j < 5 ; j++) {
										mcNumberList2 += '<td>';
										if (i > 4 && j == 4) {
											mcNumberList2 += '';
										} else {
											mcNumberList2 += result.mcNumList[i + (j*10)].num;
										}
										mcNumberList2 += '</td><td style="text-align: left;">';
										if (result.mcNumList[i].mc_num.length > 0) {
											if (i > 4 && j == 4) {
												mcNumberList2 += '';
											} else {
												mcNumberList2 += result.mcNumList[i + (j*10)].mc_num;
											} 
										} else {
											mcNumberList2 += '궁합수가 없습니다.';
										}
										mcNumberList2 += '</td>';
									}
									
									mcNumberList2 += '</tr>';
									$("#mcNumberList2").append(mcNumberList2);
								}
								
								
								
								
								
								/*
								for (var i = 0 ; i < result.mcNumList.length ; i++) {
									var mcNumberList = "";
									mcNumberList += '<tr><td>';
									// mcNumberList += '<img src="${IMG_ROOT}/ballnumber/ball_' + result.mcNumList[i].num +'.png" alt="' + result.mcNumList[i].num + '"/>';
									mcNumberList += result.mcNumList[i].num;
									mcNumberList += '</td><td style="text-align: left;">';
									
									if (result.mcNumList[i].mc_num.length > 0) {
										
										mcNumberList += result.mcNumList[i].mc_num; 
										if (result.mcNumList[i].mc_num.indexOf(",") > -1) {
											var mcNum = result.mcNumList[i].mc_num.split(",");
											for (var j = 0 ; j < mcNum.length ; j++) {
												mcNumberList += '<img src="${IMG_ROOT}/ballnumber/ball_' + mcNum[j] +'.png" alt="' + mcNum[j] + '"/>&nbsp;&nbsp;';	
											}			
										} else {
											mcNumberList += '<img src="${IMG_ROOT}/ballnumber/ball_' + result.mcNumList[i].mc_num +'.png" alt="' + result.mcNumList[i].mc_num + '"/>';											
										} 
									} else {
										mcNumberList += '궁합수가 없습니다.';
									}
									mcNumberList += '</td></tr>';
									$("#mcNumberList").append(mcNumberList);
								}
								*/
							} else {
								var mcNumberList = "";
			            		mcNumberList += '<tr><td>';
								mcNumberList += 'X';
								mcNumberList += '</td><td style="text-align: left;">';
								mcNumberList += '궁합수 목록이 없습니다.';
								mcNumberList += '</td></tr>';
								$("#mcNumberList").html(mcNumberList);
							}
			    			
		            	} else {
		            		var mcNumberList = "";
		            		mcNumberList += '<tr><td>';
							mcNumberList += 'X';
							mcNumberList += '</td><td style="text-align: left;">';
							mcNumberList += '궁합수 목록이 없습니다.';
							mcNumberList += '</td></tr>';
							$("#mcNumberList").html(mcNumberList);
		            	}
					}
				});
			}
			
			function cancel() {
				var cid = $("#currCid").val(); 
				openContent(cid);
			}
			
		</script>