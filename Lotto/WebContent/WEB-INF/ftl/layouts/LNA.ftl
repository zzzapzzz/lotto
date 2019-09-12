		<#assign CSS_ROOT = SystemInfo.css_root>
	    <#assign JS_ROOT = SystemInfo.js_root>
	    <#assign APP_ROOT = SystemInfo.app_root>
	    <#assign IMG_ROOT = SystemInfo.img_root>
	    
	    <script type="text/javascript">	
	    	function menu_onClick(mtype, pid, mid) {
	    		if ('P' == mtype) {
	    			//현재 active인 대메뉴 찾기
	    			//console.log(pid + ', ' + mid);
	    			
	    			if (pid == '0') {
	    				//console.log('pid = 0');
	    				var class_by_id = $('#'+mid).attr('class');
	    				if (class_by_id) {
	    					//no event
	    				} else {
	    					//기존 active를 찾아 없애고 active open 추가
	    					//console.log('기존 active를 찾아 없애고 active open 추가');
	    					//console.log('length='+$("li[name=p_menu]").length);
	    					var pm_size = $("li[name=p_menu]").length;
	    					for( i=0 ; i < pm_size ; i++){
				                var id = $("li[name='p_menu']").eq(i).attr("id");
	    						$('#'+id).removeClass('active');	
	    						$('#'+id).removeClass('open');	
				            }
				            
				            var cm_size = $("li[name=c_menu]").length;
				            for( i=0 ; i < cm_size ; i++){
				                var id = $("li[name='c_menu']").eq(i).attr("id");
	    						$('#'+id).removeClass('active');	
				            }
				            
	    					$('#'+mid).addClass('active open');
	    					
	    					//하위 첫번째 메뉴 active 설정
	    					var cid = $("#"+mid+">ul>li[name='c_menu']").eq(0).attr("id");
	    					//console.log(cid);
	    					$('#'+cid).addClass('active');
	    					//화면 호출
	    					
	    					openContent(cid);
	    				}
	    			}
	    		} else {
	    			//console.log(pid + ', ' + mid);
	    			
	    			var cm_size = $("#"+pid+">ul>li[name=c_menu]").length;
	    			//console.log('cm_size='+cm_size);
		            for( i=0 ; i < cm_size ; i++){
		                var id = $("#"+pid+">ul>li[name=c_menu]").eq(i).attr("id");
		                
		    			//console.log('removeClass id='+id);
						$('#'+id).removeClass('active');	
		            }
		            $('#'+mid).addClass('active');
		            //화면 호출
		            
		            openContent(mid);
	    		}	
	    	}
	    	
	    	function openContent(cid) {
	    		var url = $("#"+cid+"_url").val();
	    		$("#currCid").val(cid);	//2018.02.01 set current cid
	    		$("#currUrl").val(url);	//2018.02.03 set current url
	    		
	    		$.ajax({
					type: "POST",
					url: url,
					dataType: "html",
					async: false,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					error:function(xhr, textStatus, errorThrown){
						//alert(xhr.responseText);
						window.location.href = "/error.do";
					},
					success: function(result){
						$('#main').html(result);
						
						if ($('#isLogin').val() == 'Y') {
							var isPlugin = $("#isPlugin").val();
							if ("Y" == isPlugin) {
								openPlugin(cid);
							}
						} else {
							showSmallBox($('#checkMsg').val());
							window.location.href = "/fhrmdlsapdls.do?isLogin=" + $('#isLogin').val();
						}
						
					}
				});
	    	}
	    	
	    	//2018.02.01 change content view
	    	function changeContent(url) {
	    		var cid = $("#currCid").val();	//2018.02.01 set current cid
	    		$("#currUrl").val(url);	//2018.02.03 set current url
	    		
	    		var idx = $("#currIdx").val();
	    		var usr_id = $("#currUsrId").val();
	    		var param = {
	    			idx : idx,
	    			usr_id : usr_id
	    		}
	    		
	    		$.ajax({
					type: "POST",
					url: url,
					data: param,
					dataType: "html",
					async: false,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					error:function(xhr, textStatus, errorThrown){
						alert(xhr.responseText);				
					},
					success: function(result){
						$('#main').html(result);
						
						if ($('#isLogin').val() == 'Y') {
							var isPlugin = $("#isPlugin").val();
							if ("Y" == isPlugin) {
								openPlugin(cid);
							}
						} else {
							showSmallBox($('#checkMsg').val());
							window.location.href = "/fhrmdlsapdls.do?isLogin=" + $('#isLogin').val();
						}
						
					}
				});
	    	}
	    	
	    	//2018.12.10 change content view
	    	function changeContent(url, param) {
	    		var cid = $("#currCid").val();	//2018.02.01 set current cid
	    		$("#currUrl").val(url);	//2018.02.03 set current url
	    		
	    		$.ajax({
					type: "POST",
					url: url,
					data: param,
					dataType: "html",
					async: false,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					error:function(xhr, textStatus, errorThrown){
						alert(xhr.responseText);				
					},
					success: function(result){
						$('#main').html(result);
						
						
						var isPlugin = $("#isPlugin").val();
						if ("Y" == isPlugin) {
							openPlugin(cid);
						}
						
					}
				});
	    	}
	    	
	    	function openPlugin(cid) {
	    		//var url = $("#"+cid+"_url").val();
	    		var url = $("#currUrl").val();	//2018.02.03 set current url
	    		url = url.replace("ajax", "plugin");
	    		
	    		var idx = $("#currIdx").val();
	    		var usr_id = $("#currUsrId").val();
	    		var param = {
	    			idx : idx,
	    			usr_id : usr_id
	    		}
	    		
	    		$.ajax({
					type: "POST",
					url: url,
					data: param,
					dataType: "html",
					async: false,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					error:function(xhr, textStatus, errorThrown){
						alert(xhr.responseText);				
					},
					success: function(result){
						$('#plugin').html(result);
						
						initPlugin();
					}
				});
	    	}

	    	// 2018.06.23
			// 콤마찍기
			function numberWithCommas(x) {
				return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			}
	    	
	    </script>
	    
		<!-- Left panel : Navigation area -->
		<!-- Note: This width of the aside area can be adjusted through LESS variables -->
		<aside id="left-panel">

			<#if UserInfo.isLogin?if_exists == "Y">
			<!-- User info -->
			<div class="login-info">
				<span> <!-- User image size is adjusted inside CSS, it should stay as it --> 
					<a href="javascript:void(0);" id="show-shortcut" data-action="toggleShortcut">
						<!-- user img
						<img src="img/avatars/sunny.png" alt="me" class="online" />
						--> 
						<span>
							${UserInfo.nickname?if_exists}님 접속중
						</span>
					</a> 
				</span>
			</div>
			<!-- end user info -->
			</#if>

			<!-- 2018.02.01 set current cid -->
			<input type="hidden" id="currCid" value="" />
			<!-- 2018.02.03 set current url -->
			<input type="hidden" id="currUrl" value="" />
			
			<!-- 2018.02.10 set promotion -->
			<input type="hidden" id="currIdx" value="" />
			<input type="hidden" id="currUsrId" value="" />
			<input type="hidden" id="currItemBrand" value="" /> <!-- 2018.04.01 최초 1회 설정 -->
			<input type="hidden" id="currItemBrandNm" value="" /> <!-- 아이템 추가할 때 검색조건 -->
			<input type="hidden" id="currOrderType" value="" />
			<input type="hidden" id="currRemarkType" value="" />
			<input type="hidden" id="currProcStatus" value="" />
			
			<!-- 2018.03.12 current interval id -->
			<input type="hidden" id="currIntervalId" value="" />
			
			<!-- 2018.06.09 current freegood master idx -->
			<input type="hidden" id="currFGMstIdx" value="" />
			<!-- 2018.06.14 current employeesale master idx -->
			<input type="hidden" id="currSSMstIdx" value="" />
				
			<input type="hidden" id="authTask" value="${UserInfo.auth_task?if_exists}" />
			
			<!-- NAVIGATION : This navigation is also responsive-->
			<nav>
				<ul>
					<#assign bf_m_type = "P">
                    <#assign bf_p_mid = 0>
                    	
					<#list GnbMenuList as gnbmenu>
					
						<#if gnbmenu.m_type == "P">
							<#if (bf_m_type == "C") && (bf_p_mid != 0)>
						</ul>
					</li>	
							</#if>
							
							<#assign bf_m_type = gnbmenu.m_type>
							<#assign bf_p_mid = gnbmenu.p_menu_id>
							
					<li id="${gnbmenu.menu_id?if_exists}" name="p_menu" <#if gnbmenu.current == "Y"> class="active"</#if>>
						<input type="hidden" id="${gnbmenu.menu_id?if_exists}_url" value="${APP_ROOT?if_exists}${gnbmenu.menu_url?if_exists}ajax.do" />
						<!--
						<a href="${APP_ROOT?if_exists}${gnbmenu.menu_url?if_exists}.do" title="${gnbmenu.menu_nm?if_exists}"><i class="fa fa-lg fa-fw ${gnbmenu.lna_element?if_exists}"></i> <span class="menu-item-parent">${gnbmenu.menu_nm?if_exists}</span></a>
						-->
						<a href="javascript:menu_onClick('P', '${gnbmenu.p_menu_id?if_exists}', '${gnbmenu.menu_id?if_exists}');" title="${gnbmenu.menu_nm?if_exists}"><i class="fa fa-lg fa-fw ${gnbmenu.lna_element?if_exists}"></i> <span class="menu-item-parent">${gnbmenu.menu_nm?if_exists}</span></a>
						
							<#if gnbmenu.haschild == "Y">
						<ul>
							<#else>
					</li>	
							</#if>
						
						<#else>
						
							<#assign bf_m_type = gnbmenu.m_type>
							<#assign bf_p_mid = gnbmenu.p_menu_id>
						
							<li id="${gnbmenu.menu_id?if_exists}" name="c_menu" <#if gnbmenu.current == "Y"> class="active"</#if>>
								<input type="hidden" id="${gnbmenu.menu_id?if_exists}_url" value="${APP_ROOT?if_exists}${gnbmenu.menu_url?if_exists}ajax.do" />
								<!--
								<a href="${APP_ROOT?if_exists}${gnbmenu.menu_url?if_exists}.do" ><i class="fa fa-lg fa-fw ${gnbmenu.lna_element?if_exists}"></i>${gnbmenu.menu_nm?if_exists}</a>
								-->
								<a href="javascript:menu_onClick('C', '${gnbmenu.p_menu_id?if_exists}', '${gnbmenu.menu_id?if_exists}');" ><i class="fa fa-lg fa-fw ${gnbmenu.lna_element?if_exists}"></i>${gnbmenu.menu_nm?if_exists}</a>
							</li>	
						
						</#if>
						
						
					</#list>
						</ul>
					</li>
				</ul>
			</nav>
			<span class="minifyme" data-action="minifyMenu"> 
				<i class="fa fa-arrow-circle-left hit"></i> 
			</span>

		</aside>
		<!-- END NAVIGATION -->
		
		<script type="text/javascript">
			//document.addEventListener("DOMContentLoaded", function(){
			//	alert('dom ready');
			//});
		</script>