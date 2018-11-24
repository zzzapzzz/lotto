		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		<#assign IMG_ROOT = SystemInfo.img_root>
		
		<script src="${JS_ROOT}/plugin/jqgrid/jquery.jqGrid.min.js"></script>
		<script src="${JS_ROOT}/plugin/jqgrid/grid.locale-en.min.js"></script>
		
		<script src="${JS_ROOT}/plugin/jquery-tree/jquery.tree.min.js"></script>

		<script type="text/javascript">
			var isAction = 'N';
			var isDim = "";
			
			$(document).ready(function() {
				var isAjax = "${isAjax?if_exists}";
				if ("N" == isAjax) {
					initPlugin();
				}
				
				// Validation
				$("#authinput-form").validate({
					// Rules for form validation
					rules : {
						auth_cd : {
							required : true,
							minlength : 3,
							maxlength : 50
						},
						auth_nm : {
							required : true,
							minlength : 3,
							maxlength : 50
						}
					},

					// Messages for form validation
					messages : {
						auth_cd : {
							required : '권한코드를 입력하세요.',
						},
						auth_nm : {
							required : '권한명을 입력하세요.'
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
					url: '${APP_ROOT}/sysmng/authCodeList.do',
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
						'권한코드','권한명','사용여부'
					],
					colModel : [
						  {name : 'auth_cd', 	index : 'auth_cd', align:'center', editable:true}
						, {name : 'auth_nm',	index : 'auth_nm', align:'center', editable:true}
						, {name : 'use_yn',		index : 'use_yn' , align:'center', sortable:false, hidden:true}
					],
					rowNum : 1000,
					rowList : [10, 20, 30, 100, 1000],
					//pager : '#pjqgrid',
					//sortname : 'id',
					toolbarfilter : true,
					viewrecords : true,
					sortorder : "asc",
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
					onSelectRow: function(id){		/** 로우 선택시 함수 **/
						var rowdata = $("#jqgrid").getRowData(id);		
						getAuthTaskInfoList(rowdata.auth_cd);
					},
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
				
				/*
				jQuery("#jqgrid").jqGrid('setGroupHeaders', {
					useColSpanStyle: true, 
					groupHeaders:[
						  {startColumnName: 'usr_c', numberOfColumns: 4, titleText: '<strong>사용자관리</strong>'}
						, {startColumnName: 'sc_c', numberOfColumns: 4, titleText: '<strong>매장/고객정보관리</strong>'}
						, {startColumnName: 'es_ex', numberOfColumns: 1, titleText: '<strong>직원세일관리</strong>'}
						, {startColumnName: 'fg_ex', numberOfColumns: 1, titleText: '<strong>프리굳관리</strong>'}
						, {startColumnName: 'od_mng', numberOfColumns: 1, titleText: '<strong>오더관리</strong>'}
					]
				});
				*/
				
				
				$("#searchKey").bind('keypress', function(e) {
					if (e.keyCode == 13){
						searchGo();
					}
				});
				
				$("#search").click(searchGo);
				$('#add').click(createAuthCodeGo);
				$('#modify').click(modifyAuthCodeGo);
				$('#del').click(deleteAuthCodeGo);
				$('#save').click(saveTaskAuthInfo);
				
				//$("#addforfile").click(proc_addforfile);

			    getAuthTaskInfoList('');
			}
			
			$(window).on('resize.jqGrid', function() {
				$("#jqgrid").jqGrid('setGridWidth', $("#article1").width());
			});

			function getAuthTaskInfoList(auth_cd){
				var param = {
					'auth_cd' : auth_cd
				};
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/authTaskInfoList.do",
					data : param,
					dataType: "json",
					async: false,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					error:function(xhr, textStatus, errorThrown){
						alert(xhr.responseText);				
					},
					success: function(result){
						// 세션에 사용자 정보가 존재하지 않을때 메인으로 이동
						/*
						if (result.status == "usernotfound") {
		               		location.href = "/index.do"; 
		            	}
		            	*/
		            	
		            	
		            	var o = {
							datatype: "json",
							showcheck: true,
							cbiconpath: "${IMG_ROOT}/tree/icons/"
						};
						
				        o.data = result;
        				$("#tree").treeview(o);
        				
        				/*
        				$('.tree > ul').attr('role', 'tree').find('ul').attr('role', 'group');
						$('.tree').find('li:has(ul)').addClass('parent_li').attr('role', 'treeitem').find(' > span').attr('title', 'Collapse this branch').on('click', function(e) {
							var children = $(this).parent('li.parent_li').find(' > ul > li');
							if (children.is(':visible')) {
								children.hide('fast');
								$(this).attr('title', 'Expand this branch').find(' > i').removeClass().addClass('fa fa-lg fa-plus-circle');
							} else {
								children.show('fast');
								$(this).attr('title', 'Collapse this branch').find(' > i').removeClass().addClass('fa fa-lg fa-minus-circle');
							}
							e.stopPropagation();
						});	
						*/
						
					}
				});
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
			
			function proc_addforfile() {
				var tmpUsr="";
				var s=$("#tree").getCheckedNodes();
				
				if (s.length > 0) {
					for(var i=0 ; i <s.length ; i++){
						if(s[i].indexOf('C')== 0 && s[i].indexOf('1')== 1)
						{
							//tmpOrg=tmpOrg+s[i]+","
						}else{
							if(i < s.length-1){
								tmpUsr=tmpUsr+s[i]+"@"
							}else{
								tmpUsr=tmpUsr+s[i]
							}
						}
						
					}
				}
				
				/*
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/procAddForFile.do",
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
		            		alert('처리되었습니다.');
		            	} else {
		            		alert('실패하였습니다.');
		            	}
		            	
						searchGo();
					}
				});
				*/
			}
			
			function popup_modify() {
				var idArray = $("#jqgrid").jqGrid('getDataIDs');		
				var checkCnt = 0;
				var win_count = "";
				
				for(var i = 0 ; i < idArray.length ; i++){		
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
				
				for(var i = 0 ; i < idArray.length ; i++){				
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
		
				$("#jqgrid").setGridParam({	url: '${APP_ROOT}/sysmng/authCodeList.do', datatype:'json', postData: param, page: page, sortname: '', sortorder: ''   }).trigger("reloadGrid");
			}
			
			function createAuthCodeGo() {
				
				$("#authCodeTitle").text('권한 등록');
				$("#auth_cd").val("");
				$("#auth_nm").val("");
				$("#bf_auth_cd").val("");
				$("#bf_auth_nm").val("");
				
				$("#auth_cd").removeAttr("disabled");
				$("#lbl_auth_cd").removeClass("state-disabled");
				
				var url = "javascript:createAuthCode();";
				$("#authinput-form").attr("action", url);
				
				$("label[class*=state-success]").each(function() {
					$(this).removeClass("state-success");
				});
				
		        layer_popup('authCodePopup', 'C');
			}
			
			function createAuthCode() {
		    	
		    	var auth_cd = $("#auth_cd").val();
		    	var auth_nm = $("#auth_nm").val();
		    	
		    	var param = {
		    		'auth_cd' : auth_cd,
		    		'auth_nm' : auth_nm
		    	};
		    	
		    	$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/createAuthCode.do",
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
						if (result.status == "isDuplicate") {
		               		alert(result.msg);
		               		$("#auth_cd").focus();
		            	} else {
			            	if (result.status == "success") {
			            		isDim ? $('.dim-layer').fadeOut() : $el.fadeOut();
			            		alert('등록 되었습니다.');
			            	} else {
			            		alert('등록에 실패했습니다.');
			            	}
		            	}
		            	
						searchGo();
					}
				});
		    }
		    
		    function modifyAuthCodeGo() {
				var idArray = $("#jqgrid").jqGrid('getDataIDs');		
				var checkCnt = 0;
				var auth_cd = "";
				var auth_nm = "";
				var bf_auth_cd = "";
				var bf_auth_nm = "";
				
				for(var i = 0 ; i < idArray.length ; i++){		
					if( $("input:checkbox[id='jqg_jqgrid_"+idArray[i]+"']").is(":checked") ) {
						var rowdata = $("#jqgrid").getRowData(idArray[i]);
						if(auth_cd=="") {
							auth_cd = rowdata.auth_cd;
							auth_nm = rowdata.auth_nm;
							bf_auth_cd = rowdata.auth_cd;
							bf_auth_nm = rowdata.auth_nm;
						}
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
				
				$("label[class*=state-success]").each(function() {
					$(this).removeClass("state-success");
				});
				
				$("#authCodeTitle").text('권한 수정');
				$("#auth_cd").val(auth_cd);
				$("#auth_nm").val(auth_nm);
				$("#bf_auth_cd").val(bf_auth_cd);
				$("#bf_auth_nm").val(bf_auth_nm);
				
				$("#auth_cd").attr("disabled", "disabled");
				$("#lbl_auth_cd").addClass("state-disabled");
				
				var url = "javascript:modifyAuthCode();";
				$("#authinput-form").attr("action", url);
				
		        layer_popup('authCodePopup', 'U');
			}
		    
		    function modifyAuthCode() {
		    
		    	var auth_cd = $("#auth_cd").val();
		    	var auth_nm = $("#auth_nm").val();
		    	var bf_auth_cd = $("#bf_auth_cd").val();
		    	var bf_auth_nm = $("#bf_auth_nm").val();
		    	
		    	var param = {
		    		'auth_cd' : auth_cd,
		    		'auth_nm' : auth_nm,
		    		'bf_auth_cd' : bf_auth_cd,
		    		'bf_auth_nm' : bf_auth_nm
		    	};
		    	
		    	$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/modifyAuthCode.do",
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
						if (result.status == "notequalauthcode") {
		               		alert(result.msg);
		               		$("#auth_nm").focus();
		            	} else {
			            	if (result.status == "success") {
			            		isDim ? $('.dim-layer').fadeOut() : $el.fadeOut();
			            		alert('수정 되었습니다.');
			            	} else {
			            		alert('수정에 실패했습니다.');
			            	}
		            	}
		            	
						searchGo();
					}
				});
		    }
		    
		    function deleteAuthCodeGo() {
				var idArray = $("#jqgrid").jqGrid('getDataIDs');		
				var checkCnt = 0;
				var auth_cd = "";
				var auth_nm = "";
				
				for(var i = 0 ; i < idArray.length ; i++){		
					if( $("input:checkbox[id='jqg_jqgrid_"+idArray[i]+"']").is(":checked") ) {
						var rowdata = $("#jqgrid").getRowData(idArray[i]);
						if(auth_cd=="") {
							auth_cd = rowdata.auth_cd;
							auth_nm = rowdata.auth_nm;
						}
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
		
				if (confirm("[" + auth_nm + "] 권한을 삭제하시겠습니까?")) {
					var param = {
						'auth_cd' : auth_cd,
						'auth_nm' : auth_nm
					}
					
					$.ajax({
						type: "POST",
						url: "${APP_ROOT}/sysmng/deleteAuthCode.do",
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
			            	
							if (result.status == "isbaseauth") {
			               		alert(result.msg);
			            	} else {
				            	if (result.status == "success") {
				            		alert('삭제 되었습니다.');
				            	} else {
				            		alert('삭제에 실패했습니다.');
				            	}
			            	}
			            	
							searchGo();
						}
					});
				}
			}
		    
		    function saveTaskAuthInfo(){
		    	var idArray = $("#jqgrid").jqGrid('getDataIDs');		
				var checkCnt = 0;
				var auth_cd = "";
				
				for(var i = 0 ; i < idArray.length ; i++){		
					if( $("input:checkbox[id='jqg_jqgrid_"+idArray[i]+"']").is(":checked") ) {
						var rowdata = $("#jqgrid").getRowData(idArray[i]);
						if(auth_cd=="") {
							auth_cd = rowdata.auth_cd;
						}
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
				
				
				var tmp_auth_cd="";
				var checkedNodes = $("#tree").getCheckedNodes();
				if (checkedNodes.length == 0) {
					alert("설정한 권한이 없습니다.");
					return;
				}
				if (checkedNodes.length > 0) {
					for (var i = 0 ; i < checkedNodes.length ; i++) {
						if (checkedNodes[i].indexOf('C')== 0 && checkedNodes[i].indexOf('1')== 1) {
							//tmpOrg=tmpOrg+s[i]+","
						} else {
							if(i > 0){
								tmp_auth_cd = tmp_auth_cd + "@";
							}							
							tmp_auth_cd = tmp_auth_cd + checkedNodes[i];
							
						}
						
					}
				}
				
				var param = {
		    		'auth_cd' : auth_cd,
		    		'tmp_auth_cd' : tmp_auth_cd
		    	};
		    	
		    	$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/saveTaskAuthInfo.do",
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
		            		alert('저장 되었습니다.');
		            	} else {
		            		alert('저장에 실패했습니다.');
		            	}
						
					}
				});
		    }
		    
		    function layer_popup(el, type){
		        var $el = $("#"+el);        //레이어의 id를 $el 변수에 저장
		        isDim = $el.prev().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수
		
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
		
		        $('.layer .dimBg').click(function(){
		            $('.dim-layer').fadeOut();
		            return false;
		        });
		        
		        if ("C" == type) {
		        	$("#auth_cd").focus();
		        } else if ("U" == type) {
		        	$("#auth_nm").focus();
		        }
		        
		    }
		    
		    
		    
		    
		</script>