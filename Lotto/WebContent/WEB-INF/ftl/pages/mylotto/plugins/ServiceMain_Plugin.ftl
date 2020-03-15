		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		
		<script src="${JS_ROOT}/plugin/jqgrid/jquery.jqGrid.min.js"></script>
		<script src="${JS_ROOT}/plugin/jqgrid/grid.locale-en.min.js"></script>

		<script type="text/javascript">
			var isAction = 'N';
			
			$(document).ready(function() {
				dataLayer.push({
				  'pageCategory': 'MyLottoService',
				  'visitorType': $("#authTask").val()
				});
				
				setServiceApplyList();
			});
			
			function setServiceApplyList() {
				pageSetUp();
				
				jQuery("#jqgrid").jqGrid({
					url: "/info/getServiceApplyList.do",
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
						'회원번호','서비스코드','서비스명','순번','유형번호','유형','상태코드','상태','시작일자','종료일자'
					],
					colModel : [
						  {name : 'user_no',index : 'user_no', hidden:true}
						, {name : 'svc_cd', index : 'svc_cd', hidden:true}
						, {name : 'svc_nm', index : 'svc_nm'}
						, {name : 'seq',	index : 'seq', hidden: true}
						, {name : 'apply_type',	index : 'apply_type', hidden: true}
						, {name : 'apply_type_nm',	index : 'apply_type_nm'}
						, {name : 'svc_stat',	index : 'svc_stat', hidden: true}
						, {name : 'svc_stat_nm',	index : 'svc_stat_nm'}
						, {name : 'start_date',	index : 'start_date'}
						, {name : 'end_date',	index : 'end_date'}
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
					//caption : "MY로또저장번호 목록",
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
			}

		</script>