		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		
		<script src="${JS_ROOT}/plugin/jqgrid/jquery.jqGrid.min.js"></script>
		<script src="${JS_ROOT}/plugin/jqgrid/grid.locale-en.min.js"></script>

		<script type="text/javascript">
			var isAction = 'N';
			
			$(document).ready(function() {
				var isAjax = "${isAjax?if_exists}";
				if ("N" == isAjax) {
					initPlugin();
				}
				
				dataLayer.push({
				  'pageCategory': 'ServiceMain',
				  'visitorType': $("#authTask").val()
				});

			});
			
			function initPlugin() {
				jQuery("#jqgrid").jqGrid({
					url: '${APP_ROOT}/sysmng/getServiceInfoList.do',
					//postData: param,
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
						'서비스코드','서비스명','서비스구분','서비스기준','서비스기준값','금액','프로모션코드','사용여부'
					],
					colModel : [
						  {name:'svc_cd',index:'svc_cd',width:100, align:'center'}
						, {name:'svc_nm',index:'svc_nm',width:100, align:'center'}
						, {name:'svc_type',index:'svc_type',width:100, align:'center'}
						, {name:'svc_base_type',index:'svc_base_type',width:100, align:'center'}
						, {name:'svc_base_val',index:'svc_base_val',width:100, align:'center'}
						, {name:'amount',index:'amount',width:100, align:'right', formatter:'currency', formatoptions:{thousandsSeparator:",", decimalPlaces: 0}}
						, {name:'prmt_cd',index:'prmt_cd',width:100, align:'center'}
						, {name:'use_yn',index:'use_yn',width:100,align:'center'}
					],
					rowNum : 100,
					rowList : [10, 20, 30, 100],
					pager : '#pjqgrid',
					sortname : 'svc_cd',
					sortorder : 'ASC',
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
					ondblClickRow: function(rowId) {
						modifyLastStepGo(rowId);
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
				
				$(".ui-icon.ui-icon-seek-prev").wrap("<div class='btn btn-sm btn-default'></div>");
				$(".ui-icon.ui-icon-seek-prev").removeClass().addClass("fa fa-backward");

				$(".ui-icon.ui-icon-seek-first").wrap("<div class='btn btn-sm btn-default'></div>");
				$(".ui-icon.ui-icon-seek-first").removeClass().addClass("fa fa-fast-backward");

				$(".ui-icon.ui-icon-seek-next").wrap("<div class='btn btn-sm btn-default'></div>");
				$(".ui-icon.ui-icon-seek-next").removeClass().addClass("fa fa-forward");

				$(".ui-icon.ui-icon-seek-end").wrap("<div class='btn btn-sm btn-default'></div>");
				$(".ui-icon.ui-icon-seek-end").removeClass().addClass("fa fa-fast-forward");
				
				$("#search").click(searchGo);
				$("#add").click(changeWriteGo);
				$("#modify").click(changeModifyGo);
				$('#del').click(deleteGo);
				
				$("#searchKey").keydown(function (e) {
			        if(e.keyCode == 13){
			            searchGo();
			        }
			    });
				
			}
			
			function searchGo() {
				var param = {		
				};	
				var page = 1;
				// isAction = Y 인 경우 현재 페이지 유지, N 인 경우 1페이지로 표시
				if (isAction == 'Y') {
					page = jQuery("#jqgrid").getGridParam("page");
					isAction = 'N';
				}
		
				$("#jqgrid").setGridParam({	url: '${APP_ROOT}/sysmng/getServiceList.do', datatype:'json', postData: param, page: page, sortname: '', sortorder: ''   }).trigger("reloadGrid");
			}
			
			function changeWriteGo() {
				var url = "${APP_ROOT}/sysmng/writeServiceInfoajax.do";
				changeContent(url);
			}
			
			function changeModifyGo() {
				var idArray = $("#jqgrid").jqGrid('getDataIDs');		
				var checkCnt = 0;
				var svc_cd = "";
				
				for(var i = 0 ; i < idArray.length ; i++){		
					if( $("input:checkbox[id='jqg_jqgrid_"+idArray[i]+"']").is(":checked") ) {
						var rowdata = $("#jqgrid").getRowData(idArray[i]);
						svc_cd = rowdata.svc_cd;
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
					svc_cd : svc_cd
				}
				var url = "${APP_ROOT}/sysmng/modifyServiceInfoajax.do";
				changeContent(url, param);
			}
			
			function deleteGo() {
				var idArray = $("#jqgrid").jqGrid('getDataIDs');		
				var checkCnt = 0;
				var svc_cd = "";
				var svc_nm = "";
				
				for(var i = 0 ; i < idArray.length ; i++){		
					if( $("input:checkbox[id='jqg_jqgrid_"+idArray[i]+"']").is(":checked") ) {
						var rowdata = $("#jqgrid").getRowData(idArray[i]);
						svc_cd = rowdata.svc_cd;
						svc_nm = rowdata.svc_nm;
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
		
				showMessageBox('서비스정보 삭제', "선택한 [" + svc_nm + "]를 삭제하시겠습니까?", 'F', 'deleteProc');
			}
			
			function deleteProc() {
				var idArray = $("#jqgrid").jqGrid('getDataIDs');		
				var checkCnt = 0;
				var svc_cd = "";
				var svc_nm = "";
				
				for(var i = 0 ; i < idArray.length ; i++){		
					if( $("input:checkbox[id='jqg_jqgrid_"+idArray[i]+"']").is(":checked") ) {
						var rowdata = $("#jqgrid").getRowData(idArray[i]);
						svc_cd = rowdata.svc_cd;
						svc_nm = rowdata.svc_nm;
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
					svc_cd : svc_cd
				}
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/deleteServiceInfo.do",
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
		            	
	               		showSmallBox(result.msg);
		            	
						if (result.status == "success") {
							searchGo();
		            	}
		            	
					}
				});
			}

		</script>