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
								ctx.fillStyle = 'rgb(0, 0, 0)';
								// ctx.fillStyle = 'rgb(255, 255, 255)';
	
								var fontSize = 16;
								var fontStyle = 'normal';
								var fontFamily = 'Helvetica Neue';
								ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
	
								// Just naively convert to string for now
								var dataString = dataset.data[index].toString();
	
								// Make sure alignment settings are correct
								ctx.textAlign = 'center';
								ctx.textBaseline = 'middle';
	
								var padding = -10;
								var position = element.tooltipPosition();
								// 그래프 우측 배치 (position.x +14 처리)
								// 그래프 좌측 배치 (position.x -16 처리)
								ctx.fillText(dataString, position.x + 14, position.y - (fontSize / 2) - padding);
							});
						}
					});
				}
			});
			
			$(document).ready(function() {
				pageSetUp();
				
				dataLayer.push({
				  'pageCategory': 'OddEvenStatMain',
				  'visitorType': $("#authTask").val()
				});
				
				setChart();
			});
			
			function setChart() {
			
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/statistic/oddevenstatdatas.do",
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
							var axesMaxValue = result.axesMaxValue;
							
							// 데이터 설정
							for ( var i = 0 ; i < result.datas.length ; i++) {								
								labels.push(result.datas[i].oddeven_type);
								data.push(result.datas[i].appearCnt);
								
								if (i == 0) {
						  			backgroundColor.push('#fcb721');
						  		} else if (i == 1) {
						  			backgroundColor.push('#3369bd');
						  		} else if (i == 2) {
						  			backgroundColor.push('#d5210d');
						  		} else if (i == 3) {
						  			backgroundColor.push('#464646');
						  		} else if (i == 4) {
						  			backgroundColor.push('#50ba28');
						  		} else if (i == 5) {
						  			backgroundColor.push('#e8c695');
						  		} else {
						  			backgroundColor.push('#e595e8');
						  		}
							}
							
							var chartData = {
								labels: labels,
								dataType: 'oddeven',
								datasets: [
									{
										type: 'horizontalBar',
										label: '홀짝비율 통계(건)',
										backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
										borderColor: window.chartColors.red,
										data: data,
										backgroundColor: backgroundColor
									}
								]
							};
							
						    var ctx = document.getElementById("chartArea").getContext('2d');
							window.myBar = new Chart(ctx, {
								type: 'horizontalBar',
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
								            ticks: {								            	
								                fontSize: 20
								            }
								        }],
								        xAxes: [{
											ticks:{
												beginAtZero: true,
								                max: axesMaxValue
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