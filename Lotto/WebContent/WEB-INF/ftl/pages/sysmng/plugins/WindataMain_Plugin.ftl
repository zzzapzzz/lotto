		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		
		<script src="${JS_ROOT}/plugin/jqgrid/jquery.jqGrid.min.js"></script>
		<script src="${JS_ROOT}/plugin/jqgrid/grid.locale-en.min.js"></script>

		<script type="text/javascript">
			var isAction = 'N';
			
			$(document).ready(function() {
				pageSetUp();

				jQuery("#jqgrid").jqGrid({
					url: '${APP_ROOT}/sysmng/windataList.do',
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
						'당첨회차','번호1','번호2','번호3','번호4','번호5','번호6','보너스번호','총합','끝수합','저고비율','홀짝비율','AC'
					],
					colModel : [
						  {name : 'win_count', 	index : 'win_count'}
						, {name : 'num1',		index : 'num1'}
						, {name : 'num2',		index : 'num2'}
						, {name : 'num3',		index : 'num3'}
						, {name : 'num4',		index : 'num4'}
						, {name : 'num5',		index : 'num5'}
						, {name : 'num6',		index : 'num6'}
						, {name : 'bonus_num',	index : 'bonus_num'}
						, {name : 'total',		index : 'total'}
						, {name : 'sum_end_num',index : 'sum_end_num'}
						, {name : 'low_high',	index : 'low_high'}
						, {name : 'odd_even',	index : 'odd_even'}
						, {name : 'ac',			index : 'ac'}
					],
					rowNum : 10,
					rowList : [10, 20, 30],
					pager : '#pjqgrid',
					//sortname : 'id',
					toolbarfilter : true,
					viewrecords : true,
					sortorder : "asc",
					gridComplete : function() {
					},
					caption : "당첨번호 목록",
					multiselect : true,
					autowidth : true,

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
				$("#add").click(popup_add);
				$("#addforfile").click(proc_addforfile);
				$("#modify").click(popup_modify);

			})

			$(window).on('resize.jqGrid', function() {
				$("#jqgrid").jqGrid('setGridWidth', $("#content").width());
			})

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
			}
			
			function popup_modify() {
				var idArray = $("#jqgrid").jqGrid('getDataIDs');		
				var checkCnt = 0;
				var win_count = "";
				
				for(var i = 0 ; i < idArray.length ; i++){		
					if( $("input:checkbox[id='jqg_jqgrid_"+idArray[i]+"']").is(":checked") ) {
					checkCnt = checkCnt+1;
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
					wincount : $("#wincount").val()	
				};	
				
				var page = 1;
				// isAction = Y 인 경우 현재 페이지 유지, N 인 경우 1페이지로 표시
				if (isAction == 'Y') {
					page = jQuery("#jqgrid").getGridParam("page");
					isAction = 'N';
				}
		
				$("#jqgrid").setGridParam({	url: '${APP_ROOT}/sysmng/windataList.do', datatype:'json', postData: param, page: page, sortname: '', sortorder: ''   }).trigger("reloadGrid");
			}
			
		</script>