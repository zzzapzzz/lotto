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
								
								if (dataType == "lowhigh"
									|| dataType == "oddeven"
									) {
									dataString += "%";
								}
	
								// Make sure alignment settings are correct
								ctx.textAlign = 'center';
								ctx.textBaseline = 'middle';
	
								var padding = -10;
								var position = element.tooltipPosition();
								// 그래프 우측 배치 (position.x +14 처리)
								ctx.fillText(dataString, position.x + 18, position.y - 16);
							});
						}
					});
				}
			});
			
			$(document).ready(function() {
				pageSetUp();
				
				dataLayer.push({
				  'pageCategory': 'EndNumPtrnMain',
				  'visitorType': $("#authTask").val()
				});
				
				setChart();
			});
			
			function setChart() {
			
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/pattern/endnumptrndatas.do",
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
							var minValue = result.minValue;
							var maxValue = result.maxValue;
							
							// 데이터 설정
							for ( var i = 0 ; i < result.datas.length ; i++) {								
								labels.push(result.datas[i].win_count);
								data.push(result.datas[i].sum_end_num);
								
						  			//backgroundColor.push('#fcb721'); // 연갈색
						  			//backgroundColor.push('#3369bd'); // 파랑
						  			//backgroundColor.push('#d5210d'); // 빨강
						  			//backgroundColor.push('#464646'); // 진한회색
						  			//backgroundColor.push('#50ba28'); // 초록
							}
							
							var chartData = {
								labels: labels,
								dataType: 'oddevenptrn',
								datasets: [
									{
										type: 'line',
										label: '끝수합패턴',
										//backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
										//backgroundColor: backgroundColor,
							            backgroundColor: '#fcb721',
										//borderColor: window.chartColors.red,
										borderColor: '#fcb721',
										data: data,
										pointRadius: 10,
										pointHoverRadius: 15,
							            fill: false,	// 차트 채우기
							            lineTension: 0
									}
								]
							};
							
						    var ctx = document.getElementById("chartArea").getContext('2d');
							window.myBar = new Chart(ctx, {
								type: 'line',
								data: chartData,
								options: {
									responsive: false,
									legend: {
										labels: {
											fontSize: 20
										}
									},
									scales: {
								        yAxes: [{
								            ticks: {
								                fontSize: 20,
								                min: minValue,
											    max: maxValue,
											    stepSize: 10,
											    suggestedMin: 0.5,
											    suggestedMax: 5.5
								            },
								            scaleLabel: {
												display: true,
												fontSize: 20,
												labelString: '끝수합'
											}
								        }],
								        xAxes: [{
											ticks:{
												fontSize: 20
											},
								            scaleLabel: {
												display: true,
												fontSize: 20,
												labelString: '당첨회차'
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