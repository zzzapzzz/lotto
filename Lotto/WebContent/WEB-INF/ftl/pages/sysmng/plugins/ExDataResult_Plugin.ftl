		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		<#assign IMG_ROOT = SystemInfo.img_root>
		
		<script type="text/javascript">
			var ex_count = Number($("#ex_count").val());
			var last_count = Number($("#last_count").val());
			
			// plugin 화면 초기화
			function initPlugin() {
			
			}
			
			function cancel() {
				var cid = $("#currCid").val(); 
				openContent(cid);
			}
		</script>