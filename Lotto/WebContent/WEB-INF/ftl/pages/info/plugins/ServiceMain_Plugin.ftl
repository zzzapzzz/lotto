		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		
		<script src="${JS_ROOT}/plugin/jqgrid/jquery.jqGrid.min.js"></script>
		<script src="${JS_ROOT}/plugin/jqgrid/grid.locale-en.min.js"></script>

		<script type="text/javascript">
			var isAction = 'N';
			
			$(document).ready(function() {
				pageSetUp();
				
				dataLayer.push({
				  'pageCategory': 'InfoService',
				  'visitorType': $("#authTask").val()
				});
			});
			
			function apply(type) {
				console.log('type=',type);
				var param = {
					applyType : type
				};
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/info/applyService.do",
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
						
	            		showSmallBox(result.msg);
	            		
	            		if (result.status == "success") {
							$("#info").addClass("hide");
							$("#apply_result").removeClass("hide");
            			}
					}
				});
			}
		</script>