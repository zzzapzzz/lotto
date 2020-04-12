		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		
		<script src="${JS_ROOT}/plugin/jqgrid/jquery.jqGrid.min.js"></script>
		<script src="${JS_ROOT}/plugin/jqgrid/grid.locale-en.min.js"></script>

		<script type="text/javascript">
			var isAction = 'N';
			var ex_count = Number($("#nextWinCount").val());
			
			$(document).ready(function() {
				var isAjax = "${isAjax?if_exists}";
				if ("N" == isAjax) {
					initPlugin();
				}
				
				dataLayer.push({
				  'pageCategory': 'ExptDataMain',
				  'visitorType': $("#authTask").val()
				});

			});
			
			function initPlugin() {
				/*
				var param = {		
					ex_count : ex_count
				};
				*/
				
				jQuery("#jqgrid").jqGrid({
					// url: '${APP_ROOT}/sysmng/getExDataList.do',
					url: '${APP_ROOT}/sysmng/getExDataNewList.do',
					// postData: param,
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
						'회차','번호1','번호2','번호3','번호4','번호5','번호6'
					],
					colModel : [
						  {name:'ex_count',index:'win_count',width:100, align:'center'}
						, {name:'num1',index:'num1',width:100, align:'center'}
						, {name:'num2',index:'num2',width:100, align:'center'}
						, {name:'num3',index:'num3',width:100, align:'center'}
						, {name:'num4',index:'num4',width:100, align:'center'}
						, {name:'num5',index:'num5',width:100, align:'center'}
						, {name:'num6',index:'num6',width:100, align:'center'}
					],
					rowNum : 30,
					rowList : [10, 20, 30, 100],
					pager : '#pjqgrid',
					sortname : 'SEQ',
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
				
				$("#combinationExtraction").click(combinationGo);
				$("#exDataExtraction").click(extractionGo);
				$("#beforeExDataResult").click(changeExDataResultGo);
				$("#exDataNewExtraction").click(newExtractionGo);
				$("#beforeExDataNewResult").click(changeExDataNewResultGo);
				$("#exDataAnalysis").click(changeAnalysisGo);
			}
			
			$(window).on('resize.jqGrid', function() {
				$("#jqgrid").jqGrid('setGridWidth', $("#content").width());
			});
			
			function changeAnalysisGo() {
				var param = {
					ex_count : ex_count,
					nextAnnounceDate : $("#nextAnnounceDate").val()
				}
				
				var url = "${APP_ROOT}/sysmng/analysisExDataajax.do";
				changeContent(url, param);
			}
			
			function combinationGo() {
				var param = {
					ex_count : $("#nextWinCount").val()
				};
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/insertLottoCombination.do",
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
			    			searchGo();	
		            	} else {
		            		alert(result.msg);
		            	}
					}
				});
			}
			
			function extractionGo() {
				var param = {
					ex_count : $("#nextWinCount").val()
				};
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/extractExpectedNumber.do",
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
			    			searchGo();	
		            	} else {
		            		alert(result.msg);
		            	}
					}
				});
			}
			
			function newExtractionGo() {
				var param = {
					ex_count : $("#nextWinCount").val()
				};
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/insertNewExpectNumbers.do",
					data: param,
					dataType: "json",
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					async: true,
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
		            	} else {
		            		alert(result.msg);
		            	}
					}
				});
			}
			
			function changeExDataResultGo() {
				var url = "${APP_ROOT}/sysmng/resultExDataajax.do";
				changeContent(url);
			}
			
			function changeExDataNewResultGo() {
				var url = "${APP_ROOT}/sysmng/resultExDataNewajax.do";
				changeContent(url);
			}
			
			function searchGo() {
				var param = {		
					ex_count : $("#nextWinCount").val()
				};	
				
				var page = 1;
				// isAction = Y 인 경우 현재 페이지 유지, N 인 경우 1페이지로 표시
				if (isAction == 'Y') {
					page = jQuery("#jqgrid").getGridParam("page");
					isAction = 'N';
				}
		
				$("#jqgrid").setGridParam({	url: '${APP_ROOT}/sysmng/getExDataList.do', datatype:'json', postData: param, page: page, sortname: '', sortorder: ''   }).trigger("reloadGrid");
			}
		</script>