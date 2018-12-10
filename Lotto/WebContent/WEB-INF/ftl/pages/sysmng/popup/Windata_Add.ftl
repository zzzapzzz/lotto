<#assign tiles=JspTaglibs["http://tiles.apache.org/tags-tiles"] />
<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<title>당첨번호 등록</title>

		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<meta name="description" content="심서방로또">
		<meta name="author" content="심서방">
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign IMG_ROOT = SystemInfo.img_root>
		<#assign CSS_ROOT = SystemInfo.css_root>
		<#assign JS_ROOT = SystemInfo.js_root>

		<!-- Basic Styles -->
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/font-awesome.min.css">

		<!-- SmartAdmin Styles : Caution! DO NOT change the order -->
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/smartadmin-production-plugins.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/smartadmin-production.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/smartadmin-skins.min.css">

		<!-- SmartAdmin RTL Support  -->
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/smartadmin-rtl.min.css">

		<!-- We recommend you use "your_style.css" to override SmartAdmin
		     specific styles this will also ensure you retrain your customization with each SmartAdmin update.
		-->
		<link rel="stylesheet" type="text/css" media="screen" href="${CSS_ROOT}/your_style.css">
	</head>
		
	<body class="">
		
			<div id="content">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa fa-pencil fa-fw "></i> ${NextCnt?if_exists}회 당첨번호 등록</h1>
					</div>
				</div>
				
				<!-- widget grid -->
				<section id="widget-grid" class="">
					<!-- row -->
					<div class="row">
						<!-- NEW WIDGET START -->
						<article class="col-sm-12">
							<!-- Widget ID (each widget will need unique ID)-->
							<div class="jarviswidget well" id="wid-id-0a" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
								<!-- widget div-->
								<div>
									<a href="javascript:insertWinData();" class="btn btn-success"> 저장</a>
	        						<a href="javascript:self.close();" class="btn btn-default"> 취소</a>
									<!-- widget edit box -->
									<div class="jarviswidget-editbox">
										<!-- This area used as dropdown edit box -->
									</div>
									
									<!-- widget content -->
									<div class="widget-body">
										<div class="table-responsive">
											<table class="table table-bordered">
												<thead>
													<tr>
														<th>번호1</th>
														<th>번호2</th>
														<th>번호3</th>
														<th>번호4</th>
														<th>번호5</th>
														<th>번호6</th>
														<th>보너스</th>
													</tr>
												</thead>
												<tbody>
													<tr>
										                <td><input type="text" size="5" id="num1" /></td>
										                <td><input type="text" size="5" id="num2" /></td>
										                <td><input type="text" size="5" id="num3" /></td>
										                <td><input type="text" size="5" id="num4" /></td>
										                <td><input type="text" size="5" id="num5" /></td>
										                <td><input type="text" size="5" id="num6" /></td>
										                <td><input type="text" size="5" id="bonus_num" onkeypress="if(event.keyCode==13) insertWindata();"/></td>
													</tr>
												</tbody>	
											</table>
										</div>	
									</div>
								</div>
							</div>
						</article>
					</div>
				</section>
		</div>
		
		<script type="text/javascript">
			$(document).ready(function(){
				$("#num1").focus();	
			});
			
			function insertWindata() {	
				var num1 = $("#num1").val();
				var num2 = $("#num2").val();
				var num3 = $("#num3").val();
				var num4 = $("#num4").val();
				var num5 = $("#num5").val();
				var num6 = $("#num6").val();
				var bonus_num = $("#bonus_num").val();
			        
			    if( num1 == ""){
					alert("번호1을 입력해 주세요");
					$("#num1").focus();
					return;			
				}
				
			    if( num2 == ""){
					alert("번호2를 입력해 주세요");
					$("#num2").focus();
					return;			
				}
				
			    if( num3 == ""){
					alert("번호3을 입력해 주세요");
					$("#num3").focus();
					return;			
				}
				
			    if( num4 == ""){
					alert("번호4를 입력해 주세요");
					$("#num4").focus();
					return;			
				}
				
			    if( num5 == ""){
					alert("번호5를 입력해 주세요");
					$("#num5").focus();
					return;			
				}
				
			    if( num6 == ""){
					alert("번호6을 입력해 주세요");
					$("#num6").focus();
					return;			
				}
				
			    if( bonus_num == ""){
					alert("보너스번호를 입력해 주세요");
					$("#bonus_num").focus();
					return;			
				}
				
				
				
				var param = {
					win_count :'${NextCnt?if_exists}', 
					num1    : num1,
					num2    : num2,
					num3    : num3,
					num4    : num4,
					num5    : num5,
					num6    : num6,
					bonus_num    : bonus_num
				};	
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/insertWinData.do",			
					data: param,
					dataType: "json",
			        async: true,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			        error:function(data, a, b){
			            alert('서버의 접속상태가 원활하지 않습니다. 잠시 후 다시 시도해 주세요.');
			        },
			        success: function(result){
			            alert(result.msg);	
			        	if(result.status=="success"){
				            self.close() ;	
				            opener.searchGo();
			        	} 
			        }
				});
			}
		</script>
	</body>
</html>
