		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		
		<script src="${JS_ROOT}/plugin/jqgrid/jquery.jqGrid.min.js"></script>
		<script src="${JS_ROOT}/plugin/jqgrid/grid.locale-en.min.js"></script>

		<script type="text/javascript">
			var isAction = 'N';
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
								// ctx.fillStyle = 'rgb(0, 0, 0)';
								ctx.fillStyle = 'rgb(255, 255, 255)';
	
								var fontSize = 26;
								var fontStyle = 'normal';
								var fontFamily = 'Helvetica Neue';
								ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
	
								// Just naively convert to string for now
								var dataString = dataset.data[index].toString();
								
								if (dataType == "lowhigh"
									|| dataType == "oddeven"
									|| dataType == "colors"
									) {
									dataString += "%";
								}
	
								// Make sure alignment settings are correct
								ctx.textAlign = 'center';
								ctx.textBaseline = 'middle';
	
								var padding = -10;
								var position = element.tooltipPosition();
								// 그래프 우측 배치 (position.x +14 처리)
								ctx.fillText(dataString, position.x, position.y - (fontSize / 2) - padding);
							});
						}
					});
				}
			});
			
			$(document).ready(function() {
				pageSetUp();
				
				dataLayer.push({
				  'pageCategory': 'ColorStatMain',
				  'visitorType': $("#authTask").val()
				});
				
				setChart();
			});
			
			var randomScalingFactor = function() {
				return Math.round(Math.random() * 100);
			};
		
			function setTestChart() {
				var labels = [];
				var data1 = [];
				var data1 = [];
				var backgroundColor1 = [];
				
				// 데이터 설정
				for ( var i = 0 ; i < 5 ; i++) {
					labels.push(i + 1);
					data1.push(20);
				}
				
				// element별 color 설정
				backgroundColor1.push('#fcb721');
				backgroundColor1.push('#3369bd');
				backgroundColor1.push('#d5210d');
				backgroundColor1.push('#464646');
				backgroundColor1.push('#50ba28');
				
				var chartData = {
					labels: labels,
					dataType: 'numbers',
					datasets: [{
						type: 'pie',
						label: '색상별 통계',
						backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
						borderColor: window.chartColors.red,
						data: data1,
						backgroundColor: backgroundColor1
					}]
				};
				
			    var ctx = document.getElementById("chartArea").getContext('2d');
				window.myBar = new Chart(ctx, {
					type: 'pie',
					data: chartData,
					options: {
						responsive: false
						/*
						,
						title: {
							display: true,
							text: 'Chart.js Combo Bar Line Chart'
						}
						*/
					}
				});
			}
			
			function setChart() {
			
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/statistic/colorstatdatas.do",
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
							var data = [];
							var backgroundColor = [];

							// 데이터 설정
							for ( var i = 0 ; i < result.datas.length ; i++) {								
								labels.push(result.datas[i].range);							
								data.push(result.datas[i].appearPercent);
								
								if (i == 0) {
						  			backgroundColor.push('#fcb721');
						  		} else if (i == 1) {
						  			backgroundColor.push('#3369bd');
						  		} else if (i == 2) {
						  			backgroundColor.push('#d5210d');
						  		} else if (i == 3) {
						  			backgroundColor.push('#464646');
						  		} else {
						  			backgroundColor.push('#50ba28');
						  		}
							}
							
							var chartData = {
								labels: labels,
								dataType: 'colors',
								datasets: [
									{
										type: 'pie',
										label: '색상별 통계',
										backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
										borderColor: window.chartColors.black,
										data: data,
										backgroundColor: backgroundColor
									}
								]
							};
							
						    var ctx = document.getElementById("chartArea").getContext('2d');
							window.myBar = new Chart(ctx, {
								type: 'pie',
								data: chartData,
								options: {
									legend: {
										labels: {
											fontSize: 20
										}
									},
									responsive: false,
									scales: {
								        yAxes: [{
								        	display: false,
								            ticks: {								            	
								                fontSize: 16
								            }
								        }],
								        xAxes: [{
								        	display: false,
											ticks:{
												beginAtZero: true
											}
										}]
								    }
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
			
		</script>