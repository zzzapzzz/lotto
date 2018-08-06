		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		<#assign IMG_ROOT = SystemInfo.img_root>
		
		<script src="${JS_ROOT}/plugin/jqgrid/jquery.jqGrid.min.js"></script>
		<script src="${JS_ROOT}/plugin/jqgrid/grid.locale-en.min.js"></script>

		<script type="text/javascript">
			var isAction = 'N';
			var isDim = "";
			
			$(document).ready(function() {
				var isAjax = "${isAjax?if_exists}";
				if ("N" == isAjax) {
					initPlugin();
				}
				
				// Validation
				$("#userinput-form").validate({
					// Rules for form validation
					rules : {
						usr_id : {
							required : true,
							minlength : 3,
							maxlength : 10
						},
						usr_nm : {
							required : true,
							minlength : 3,
							maxlength : 50
						},
						store_desc : {
							required : false,
							minlength : 0,
							maxlength : 50
						},
						cost_centre_code : {
							required : false,
							minlength : 0,
							maxlength : 20
						},
						ship_to_code : {
							required : false,
							minlength : 0,
							maxlength : 10
						}
					},

					// Messages for form validation
					messages : {
						usr_id : {
							required : '사번을 입력하세요.'
						},
						usr_nm : {
							required : '직원명을 입력하세요.'
						}
					},

					// Do not change code below
					errorPlacement : function(error, element) {
						error.insertAfter(element.parent());
					}
				});	
				
				$("#searchKey").focus();
				
			});
			
			function initPlugin() {
				pageSetUp();

				jQuery("#jqgrid").jqGrid({
					url: '${APP_ROOT}/sysmng/userList.do',
					datatype: 'json', //Data 형식   'json/xml/local'
		        	jsonReader : {  // 이부분 추가 하셔야 json 쓰시기 편리 합니다.
							page: "page", 
							total: "total", 
							root: "rows",
							records: function(obj){return obj.length;},
							repeatitems: false
					},					
					height : 'auto',
					colNames : [
						'소속구분코드','소속구분','사원명','사원ID','소속','Cost Centre','Ship To','이메일','업무권한코드','업무권한','메뉴권한코드','메뉴권한','사용여부코드', '사용여부'
					],
					colModel : [
						  {name : 'store_type', index : 'store_type', hidden:true}
						, {name : 'store_type_nm',	index : 'store_type_nm'}
						, {name : 'usr_nm',		index : 'usr_nm'}
						, {name : 'usr_id',		index : 'usr_id'}
						, {name : 'store_desc',	index : 'store_desc'}
						, {name : 'cost_centre_code',	index : 'cost_centre_code'}
						, {name : 'ship_to_code',	index : 'ship_to_code'}
						, {name : 'email',		index : 'email', hidden:true}
						, {name : 'auth_task',	index : 'auth_task', hidden:true}
						, {name : 'auth_task_nm',	index : 'auth_task_nm'}
						, {name : 'auth_menu',	index : 'auth_menu', hidden:true}
						, {name : 'auth_menu_nm',	index : 'auth_menu_nm'}
						, {name : 'use_yn',		index : 'use_yn', hidden:true}
						, {name : 'use_yn_nm',		index : 'use_yn_nm'}
					],
					rowNum : 100,
					rowList : [10, 20, 30, 100],
					pager : '#pjqgrid',
					//sortname : 'id',
					toolbarfilter : true,
					viewrecords : true,
					loadComplete: function(data) {		/** 데이터 로딩시 함수 **/
						// 세션에 사용자 정보가 존재하지 않을때 메인으로 이동
						if (data.status == "usernotfound") {
		               		location.href = "/index.do"; 
		            	}
		            	
		            	// 검색결과 없을 시 보여지는 메시지
						var col = $("#jqgrid").jqGrid ('getGridParam','colNames');
						var colLength = col.length;
						if (data.rows.length == 0) {
						  	$("#jqgrid>tbody").append ("<tr><td align='center' colspan='"+colLength+"' style='font-weight:bold;'>검색결과가 없습니다.</td></tr>");
						  	//alert('검색결과가 없습니다.');
						}
					},
					//gridComplete : function() {
					//},
					//caption : "사용자 목록",
					multiselect : true,
					multiboxonly: true,
					autowidth : true

				});
				
				jQuery("#jqgrid").jqGrid('inlineNav', "#pjqgrid");
				/* Add tooltips */
				$('.navtable .ui-pg-button').tooltip({
					container : 'body'
				});

				// remove classes
				$(".ui-jqgrid").removeClass("ui-widget ui-widget-content");
				$(".ui-jqgrid-view").children().removeClass("ui-widget-header ui-state-default");
				$(".ui-jqgrid-labels, .ui-search-toolbar").children().removeClass("ui-state-default ui-th-column ui-th-ltr");
				$(".ui-jqgrid-pager").removeClass("ui-state-default");
				$(".ui-jqgrid").removeClass("ui-widget-content");

				// add classes
				$(".ui-jqgrid-htable").addClass("table table-bordered table-hover");
				$(".ui-jqgrid-btable").addClass("table table-bordered table-striped");

				$(".ui-pg-div").removeClass().addClass("btn btn-sm btn-primary");
				/*				
				$(".ui-icon.ui-icon-plus").removeClass().addClass("fa fa-plus");
				$(".ui-icon.ui-icon-pencil").removeClass().addClass("fa fa-pencil");
				$(".ui-icon.ui-icon-trash").removeClass().addClass("fa fa-trash-o");
				$(".ui-icon.ui-icon-search").removeClass().addClass("fa fa-search");
				$(".ui-icon.ui-icon-refresh").removeClass().addClass("fa fa-refresh");
				$(".ui-icon.ui-icon-disk").removeClass().addClass("fa fa-save").parent(".btn-primary").removeClass("btn-primary").addClass("btn-success");
				$(".ui-icon.ui-icon-cancel").removeClass().addClass("fa fa-times").parent(".btn-primary").removeClass("btn-primary").addClass("btn-danger");
				*/
				
				$(".ui-icon.ui-icon-seek-prev").wrap("<div class='btn btn-sm btn-default'></div>");
				$(".ui-icon.ui-icon-seek-prev").removeClass().addClass("fa fa-backward");

				$(".ui-icon.ui-icon-seek-first").wrap("<div class='btn btn-sm btn-default'></div>");
				$(".ui-icon.ui-icon-seek-first").removeClass().addClass("fa fa-fast-backward");

				$(".ui-icon.ui-icon-seek-next").wrap("<div class='btn btn-sm btn-default'></div>");
				$(".ui-icon.ui-icon-seek-next").removeClass().addClass("fa fa-forward");

				$(".ui-icon.ui-icon-seek-end").wrap("<div class='btn btn-sm btn-default'></div>");
				$(".ui-icon.ui-icon-seek-end").removeClass().addClass("fa fa-fast-forward");
				
				//2017.11.28
				$("#search").click(searchGo);
				$("#add").click(createUserInfoGo);
				$("#modify").click(modifyUserInfoGo);
				$('#del').click(deleteUserInfoGo);
				$("#thwdInit").click(thwdInitGo);
				$("#uploadFile").click(uploadFileGo);
				$("#downloadFile").click(downloadFileGo);
				
				$("#auth_task").change(changeEqualAuth);
				
				$("#searchKey").keydown(function (e) {
			        if(e.keyCode == 13){
			            searchGo();
			        }
			    });
				
				setCodeList('C000000001', 'store_type');
				
				setAuthList();
			}
			
			$(window).on('resize.jqGrid', function() {
				$("#jqgrid").jqGrid('setGridWidth', $("#content").width());
			});
			
			//동일한 권한 변경
			function changeEqualAuth(){
				$('#auth_menu option').each(function() {
					$(this).removeAttr("selected");
				});
				
				var selectedIndex = $("#auth_task option").index($("#auth_task option:selected"));
				$("#auth_menu option:eq("+selectedIndex+")").attr("selected",true);
			}
	
			/*
			function setStoreTypeList() {
				var param = {
					'code_type' : 'C000000001'		
				}
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/common/getCodeList.do",
					dataType: "json",
					data: param,
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
		            	
		            	$('#store_type').append('<option value="" selected>선택</option>');
		            	
		            	for (var i = 0 ; i < result.rows.length ; i++) {
		            		var code_id = result.rows[i].code_id;
		            		var code_nm = result.rows[i].code_nm;
		            		
		            		$('#store_type').append('<option value="'+ code_id +'">'+ code_nm +'</option>');
		            	}
		            	
					}
				});
			}
			*/
			
			function setAuthList() {
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/authCodeList.do",
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
		            	
		            	$('#auth_task').append('<option value="" selected>선택</option>');
		            	$('#auth_menu').append('<option value="" selected>선택</option>');
		            	
		            	for (var i = 0 ; i < result.rows.length ; i++) {
		            		var auth_cd = result.rows[i].auth_cd;
		            		var auth_nm = result.rows[i].auth_nm;
		            		
		            		$('#auth_task').append('<option value="'+ auth_cd +'">'+ auth_nm +'</option>');
		            		$('#auth_menu').append('<option value="'+ auth_cd +'">'+ auth_nm +'</option>');
		            	}
		            	
					}
				});
			}
			
			
			function createUserInfoGo() {
				$("#userInfoTitle").text('사용자정보 등록');
				$("#usr_id").val("");
				$("#usr_id").removeAttr("disabled");
				$("#usr_nm").val("");
				
				$('#store_type option').each(function() {
					$(this).removeAttr("selected");
				});
				$("#store_desc").val("");
				$("#cost_centre_code").val("");
				$("#ship_to_code").val("");
				
				$('#auth_task option').each(function() {
					$(this).removeAttr("selected");
				});
				$('#auth_menu option').each(function() {
					$(this).removeAttr("selected");
				});
				$('#use_yn option').each(function() {
					$(this).removeAttr("selected");
				});
				
				$("label[class*=state-success]").each(function() {
					$(this).removeClass("state-success");
				});
					
				var url = "javascript:createUserInfo();";
				$("#userinput-form").attr("action", url);
				
		        layer_popup('userInfoPopup', 'C');
			}
			
			function createUserInfo() {
		    	
		    	var usr_id = $("#usr_id").val();
				var usr_nm = $("#usr_nm").val();
				var store_type = $("#store_type").val();
				var store_desc = $("#store_desc").val();
				var cost_centre_code = $("#cost_centre_code").val();
				var ship_to_code = $("#ship_to_code").val();
				var auth_task = $("#auth_task").val();
				var auth_menu = $("#auth_menu").val();
				var use_yn = $("#use_yn").val();
				
		    	
		    	var param = {
		    		'usr_id' : usr_id,
		    		'usr_nm' : usr_nm,
		    		'store_type' : store_type,
		    		'store_desc' : store_desc,
		    		'cost_centre_code' : cost_centre_code,
		    		'ship_to_code' : ship_to_code,
		    		'auth_task' : auth_task,
		    		'auth_menu' : auth_menu,
		    		'use_yn' : use_yn
		    	};
		    	
		    	$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/createUserInfo.do",
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
		               		return;
		            	}
						
		            	if (result.status == "success") {
		            		isDim ? $('.dim-layer').fadeOut() : $el.fadeOut();
							searchGo();
		            	}
		            	
	               		alert(result.msg);
		            	
					}
				});
		    }
		    
			function modifyUserInfoGo() {
				var idArray = $("#jqgrid").jqGrid('getDataIDs');		
				var checkCnt = 0;
				var usr_id = "";
				var usr_nm = "";
				var store_type = "";
				var store_desc = "";
				var cost_centre_code = "";
				var ship_to_code = "";
				var auth_task = "";
				var auth_menu = "";
				var use_yn = "";
				
				for(var i=0;i<idArray.length;i++){		
					if( $("input:checkbox[id='jqg_jqgrid_"+idArray[i]+"']").is(":checked") ) {
						var rowdata = $("#jqgrid").getRowData(idArray[i]);
						usr_id = rowdata.usr_id;
						usr_nm = rowdata.usr_nm;
						store_type = rowdata.store_type;
						store_desc = rowdata.store_desc;
						cost_centre_code = rowdata.cost_centre_code;
						ship_to_code = rowdata.ship_to_code;
						auth_task = rowdata.auth_task;
						auth_menu = rowdata.auth_menu;
						use_yn = rowdata.use_yn;
							
						checkCnt = checkCnt + 1;
					}
				}
			
				if (checkCnt == 0) {
					alert("선택 항목이 없습니다.");
					return;			
				} else if (checkCnt > 1) {
					alert("하나만 선택하여야 합니다.");
					return;
				}
		
				$('#store_type option').each(function() {
					$(this).removeAttr("selected");
				});
				$('#auth_task option').each(function() {
					$(this).removeAttr("selected");
				});
				$('#auth_menu option').each(function() {
					$(this).removeAttr("selected");
				});
				$('#use_yn option').each(function() {
					$(this).removeAttr("selected");
				});
				
				$("label[class*=state-success]").each(function() {
					$(this).removeClass("state-success");
				});
				
				$("#userInfoTitle").text('사용자정보 수정');
				$("#usr_id").val(usr_id);
				$("#usr_id").attr("disabled", "disabled");
				$("#usr_nm").val(usr_nm);
				$("#store_type").val(store_type).attr("selected", true);
				$("#store_desc").val(store_desc);
				$("#cost_centre_code").val(cost_centre_code);
				$("#ship_to_code").val(ship_to_code);
				$("#auth_task").val(auth_task).attr("selected", true);
				$("#auth_menu").val(auth_menu).attr("selected", true);
				$("#use_yn").val(use_yn).attr("selected", true);
				
				
				var url = "javascript:modifyUserInfo();";
				$("#userinput-form").attr("action", url);
				
		        layer_popup('userInfoPopup', 'U');
			}
			
			function thwdInitGo() {
				var idArray = $("#jqgrid").jqGrid('getDataIDs');		
				var checkCnt = 0;
				var usr_id = "";
				
				for(var i=0;i<idArray.length;i++){		
					if( $("input:checkbox[id='jqg_jqgrid_"+idArray[i]+"']").is(":checked") ) {
						var rowdata = $("#jqgrid").getRowData(idArray[i]);
						usr_id = rowdata.usr_id;
							
						checkCnt = checkCnt + 1;
					}
				}
			
				if (checkCnt == 0) {
					alert("선택 항목이 없습니다.");
					return;			
				} else if (checkCnt > 1) {
					alert("하나만 선택하여야 합니다.");
					return;
				}
		
				var param = {
					'search_usr_id' : usr_id
				}
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/login/initThwd.do.do",
					data: param,
					dataType: "json",
					async: false,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					error:function(xhr, textStatus, errorThrown){
						alert(xhr.responseText);				
					},
					success: function(result){
	               		alert(result.msg);
	               		
						// 세션에 사용자 정보가 존재하지 않을때 메인으로 이동
						if (result.status == "usernotfound") {
		               		location.href = "/index.do"; 
		            	}
	               		
						if (result.status == "success") {
							searchGo();
		            	}
		            	
					}
				});
			}
			
			function modifyUserInfo() {
		    
		    	var usr_id = $("#usr_id").val();
				var usr_nm = $("#usr_nm").val();
				var store_type = $("#store_type").val();
				var store_desc = $("#store_desc").val();
				var cost_centre_code = $("#cost_centre_code").val();
				var ship_to_code = $("#ship_to_code").val();
				var auth_task = $("#auth_task").val();
				var auth_menu = $("#auth_menu").val();
				var use_yn = $("#use_yn").val();
				
		    	
		    	var param = {
		    		'usr_id' : usr_id,
		    		'usr_nm' : usr_nm,
		    		'store_type' : store_type,
		    		'store_desc' : store_desc,
		    		'cost_centre_code' : cost_centre_code,
		    		'ship_to_code' : ship_to_code,
		    		'auth_task' : auth_task,
		    		'auth_menu' : auth_menu,
		    		'use_yn' : use_yn
		    	};
		    	
		    	$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/modifyUserInfo.do",
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
						
						if (result.status == "success") {
		            		isDim ? $('.dim-layer').fadeOut() : $el.fadeOut();
							searchGo();
		            	}
		            	
	               		alert(result.msg);
					}
				});
		    }
		    
			function deleteUserInfoGo() {
				var idArray = $("#jqgrid").jqGrid('getDataIDs');		
				var checkCnt = 0;
				var usr_id = "";
				var usr_nm = "";
				
				for(var i=0;i<idArray.length;i++){		
					if( $("input:checkbox[id='jqg_jqgrid_"+idArray[i]+"']").is(":checked") ) {
						var rowdata = $("#jqgrid").getRowData(idArray[i]);
						usr_id = rowdata.usr_id;
						usr_nm = rowdata.usr_nm;
						checkCnt = checkCnt + 1;
					}
				}
			
				if (checkCnt == 0) {
					alert("선택 항목이 없습니다.");
					return;			
				} else if (checkCnt > 1) {
					alert("하나만 선택하여야 합니다.");
					return;
				}
		
				if (confirm("[" + usr_nm + "] 사용자를 삭제하시겠습니까?")) {
					var param = {
						'usr_id' : usr_id,
						'usr_nm' : usr_nm
					}
					
					$.ajax({
						type: "POST",
						url: "${APP_ROOT}/sysmng/deleteUserInfo.do",
						data: param,
						dataType: "json",
						async: false,
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						error:function(xhr, textStatus, errorThrown){
							alert(xhr.responseText);				
						},
						success: function(result){
		               		alert(result.msg);
		               		
							// 세션에 사용자 정보가 존재하지 않을때 메인으로 이동
							if (result.status == "usernotfound") {
			               		location.href = "/index.do"; 
			            	}
			            	
							if (result.status == "success") {
								searchGo();
			            	}
			            	
						}
					});
				}
			}
			
			function uploadFileGo() {
				$("#excel").val("");
				
				$("#status").html("");
		        var percentVal = '0%';
		        $(".bar").attr("width", percentVal);
		        $(".percent").html(percentVal);
		        
				var url = "javascript:uploadFileForUser();";
				$("#uploadfile-form").attr("action", url);
				
				layer_popup('uploadPopup', 'C');
			}
			
			function downloadFileGo() {
				location.href = "${APP_ROOT}/download/form/IOS_사용자_업로드.xlsx";
			}
						
			function uploadFileForUser() {
				
				var excel = $("#excel").val();
				var result = checkExcelFile("excel",1);
				if (result) {
					var url = "${APP_ROOT}/sysmng/uploadFileForUser.do";
					$("#uploadfile-form").attr("action", url);
				
					var options = {
						beforeSend: function() {
					        $("#status").html("");
					        var percentVal = '0%';
					        $(".bar").attr("width", percentVal);
					        $(".percent").html(percentVal);
					    },
					    uploadProgress: function(event, position, total, percentComplete) {
					        var percentVal = percentComplete + '%';
					        $(".bar").attr("width", percentVal);
					        $(".percent").html(percentVal);
					    },
                        success : function(data) {
					        
                        	if ("success" == data.status) {
                        		isDim ? $('.dim-layer').fadeOut() : $el.fadeOut();
                            	alert("모든 데이터가 업로드 되었습니다.");
                            	
                            	searchGo();
                        	} else {
                        		alert(data.msg);
                        	}
                        },            
                        error: function(request, errordata, errorObject) { 
                        	alert(errorObject.toString()); 
                        },
                        type : "POST",
                        dataType: 'json'
                    };
                    $("#uploadfile-form").ajaxSubmit(options);
				}

			}
			
			function popup_add() {
				//팝업 화면 중앙으로 배치
				var wid = 800;
				var hei = 300;
				
				//스크린의 크기
				var sw = screen.availWidth;
				var sh = screen.availHeight;
				//포지션 계산
				var px = (sw-wid)/2;
				var py = (sh-hei)/2;
				
				window.open('${APP_ROOT}/sysmng/popupAddWindata.do','add','width='+wid+', height='+hei+',top='+py+', left='+px+', scrollbars=no, status=no, toolbar=no, resizable=yes, location=no, menu=no');
			}
			
			function popup_modify() {
				var idArray = $("#jqgrid").jqGrid('getDataIDs');		
				var checkCnt = 0;
				var win_count = "";
				
				for(var i=0;i<idArray.length;i++){		
					if( $("input:checkbox[id='jqg_jqgrid_"+idArray[i]+"']").is(":checked") ) {
						checkCnt = checkCnt+1;
					}
				}
			
				if(checkCnt==0){
					alert("선택 항목이 없습니다.");
					return;			
				}
				
				if(checkCnt>1){
					alert("하나만 선택하여야 합니다.");
					return;			
				}
				
				for(var i=0;i<idArray.length;i++){				
					if( $("input:checkbox[id='jqg_jqgrid_"+idArray[i]+"']").is(":checked") ){						
						var rowdata = $("#jqgrid").getRowData(idArray[i]);
						win_count = rowdata.win_count;
					}
				}
		
				//팝업 화면 중앙으로 배치
				var wid = 800;
				var hei = 300;
				
				//스크린의 크기
				var sw = screen.availWidth;
				var sh = screen.availHeight;
				//포지션 계산
				var px = (sw-wid)/2;
				var py = (sh-hei)/2;
				
				window.open('${APP_ROOT}/sysmng/popupModifyWindata.do?win_count='+win_count,'modify','width='+wid+', height='+hei+',top='+py+', left='+px+', scrollbars=no, status=no, toolbar=no, resizable=yes, location=no, menu=no');
			
			}
			
			function searchGo() {
				var param = {		
					searchKey : $("#searchKey").val()
				};	
				
				var page = 1;
				// isAction = Y 인 경우 현재 페이지 유지, N 인 경우 1페이지로 표시
				if (isAction == 'Y') {
					page = jQuery("#jqgrid").getGridParam("page");
					isAction = 'N';
				}
		
				$("#jqgrid").setGridParam({	url: '${APP_ROOT}/sysmng/userList.do', datatype:'json', postData: param, page: page, sortname: '', sortorder: ''   }).trigger("reloadGrid");
			}
			
			function layer_popup(el, type){
				
				if ("userInfoPopup" == el) {
					$("#uploadPopup").addClass("hide");
					$("#userInfoPopup").removeClass("hide");
				} else {
					$("#userInfoPopup").addClass("hide");
					$("#uploadPopup").removeClass("hide");
				}
			
		        var $el = $("#"+el);        //레이어의 id를 $el 변수에 저장
		        //isDim = $el.prev().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수
		        isDim = $(".dim-layer div:first").hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수
		
		        isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();
		
		        var $elWidth = ~~($el.outerWidth()),
		            $elHeight = ~~($el.outerHeight()),
		            docWidth = $(document).width(),
		            docHeight = $(document).height();
				
		        // 화면의 중앙에 레이어를 띄운다.
		        if ($elHeight < docHeight || $elWidth < docWidth) {
		            $el.css({
		                marginTop: -$elHeight /2,
		                marginLeft: -$elWidth/2
		            })
		        } else {
		            $el.css({top: 0, left: 0});
		        }
				
		        $('#btnClose').click(function(){
		            isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
		            return false;
		        });
		        
		        $('.btn-layerClose').click(function(){
		            isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
		            return false;
		        });
		        
		        $('#btnUploadClose').click(function(){
		            isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
		            return false;
		        });
		
		        $('.layer .dimBg').click(function(){
		            $('.dim-layer').fadeOut();
		            return false;
		        });
		        
		        if ("C" == type) {
		        	$("#usr_id").focus();
		        } else if ("U" == type) {
		        	$("#usr_nm").focus();
		        }
		    }
		</script>