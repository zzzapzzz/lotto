		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		<#assign IMG_ROOT = SystemInfo.img_root>
		
		<script src="${JS_ROOT}/plugin/jqgrid/jquery.jqGrid.min.js"></script>
		<script src="${JS_ROOT}/plugin/jqgrid/grid.locale-en.min.js"></script>

		<script type="text/javascript">
			var isAction = 'N';
			var isDim = "";
			var thisExCount = 0;
			
			$(document).ready(function() {
				setWinCountList();
				
				dataLayer.push({
				  'pageCategory': 'MyLottoMain',
				  'visitorType': $("#authTask").val()
				});
			});
			
			function setWinCountList() {
				var param = {
					page: '1'
				};
				$.ajax({
					type: "POST",
					url: "/sysmng/getWinDataList.do",
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
			        	
			        	if (result) {
			        		
			        		// 필터 등록 시 회차 설정
			        		thisExCount = Number(result.rows.length + 1);
			        	
			        		$("#ex_count").append('<option value="'+ (result.rows.length + 1) +'">'+ (result.rows.length + 1) +'</option>');
				        	for (var i = 0 ; i < result.rows.length ; i++) {
				        		var win_count = result.rows[i].win_count;
				        		
				        		$("#ex_count").append('<option value="'+ win_count +'">'+ win_count +'</option>');
				        	}
			        	}
			        	
			        	initPlugin();
					}
				});
			}
			
			function initPlugin() {
				pageSetUp();

				var param = {
					ex_count : $("#ex_count").val()
				};
				
				jQuery("#jqgrid").jqGrid({
					url: '${APP_ROOT}/mylotto/saveNumList.do',
					postData: param,
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
						'회차','회원번호','순번','번호1','번호2','번호3','번호4','번호5','번호6','당첨결과'
					],
					colModel : [
						  {name : 'ex_count', index : 'ex_count'}
						, {name : 'user_no',	index : 'user_no', hidden:true}
						, {name : 'seq',	index : 'seq'}
						, {name : 'num1',	index : 'num1'}
						, {name : 'num2',	index : 'num2'}
						, {name : 'num3',	index : 'num3'}
						, {name : 'num4',	index : 'num4'}
						, {name : 'num5',	index : 'num5'}
						, {name : 'num6',	index : 'num6'}
						, {name : 'win_rslt',		index : 'win_rslt'}
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

						// 이미지로 표시영역 초기화
						$("#ex_numbers").html("");
		            	
		            	// 검색결과 없을 시 보여지는 메시지
						var col = $("#jqgrid").jqGrid ('getGridParam','colNames');
						var colLength = col.length;
						if (data.rows.length == 0) {
						  	$("#jqgrid>tbody").append ("<tr><td align='center' colspan='"+colLength+"' style='font-weight:bold;'>검색결과가 없습니다.</td></tr>");
						  	//alert('검색결과가 없습니다.');
						} else {
							
							// 이미지로 표시
				        	$("#ex_numbers").append('<br>');
							for (var i = 0 ; i < data.rows.length ; i++) {
								$("#ex_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + data.rows[i].num1 +'.png" alt="' + data.rows[i].num1 + '"/>');
					        	$("#ex_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + data.rows[i].num2 +'.png" alt="' + data.rows[i].num2 + '"/>');
					        	$("#ex_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + data.rows[i].num3 +'.png" alt="' + data.rows[i].num3 + '"/>');
					        	$("#ex_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + data.rows[i].num4 +'.png" alt="' + data.rows[i].num4 + '"/>');
					        	$("#ex_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + data.rows[i].num5 +'.png" alt="' + data.rows[i].num5 + '"/>');
					        	$("#ex_numbers").append('<img src="${IMG_ROOT}/ballnumber/ball_' + data.rows[i].num6 +'.png" alt="' + data.rows[i].num6 + '"/>');
					        	$("#ex_numbers").append('<br>');
					        	
					        	if ((i+1) % 5 == 0) {
						        	$("#ex_numbers").append('<br>');
					        	}
							}
						
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

				$("#ex_count").change(searchGo);
				//$("#search").click(searchGo);
				//$("#add").click(addGo);
				//$("#filterAdd").click(filterAddGo);
				//$('#del').click(delGo);
				
			}
			
			function searchGo() {
				var param = {		
					ex_count : $("#ex_count").val()
				};	
				
				var page = 1;
				// isAction = Y 인 경우 현재 페이지 유지, N 인 경우 1페이지로 표시
				if (isAction == 'Y') {
					page = jQuery("#jqgrid").getGridParam("page");
					isAction = 'N';
				}
		
				$("#jqgrid").setGridParam({	url: '${APP_ROOT}/mylotto/saveNumList.do', datatype:'json', postData: param, page: page, sortname: '', sortorder: ''   }).trigger("reloadGrid");
			}
			
			function addGo() {
				var url = "${APP_ROOT}/mylotto/writeMyDataajax.do" + "?ex_count=" + $("#ex_count").val();
				changeContent(url);
			}
			
			function autoAddCheckGo() {
				var param = {
					ex_count : Number($("#ex_count").val())
				}
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/mylotto/autoAddCheck.do",
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
		            	
						if (result.status == "exist") {
							if (confirm(result.msg)) {
								autoAddGo();
							}
		            	} else {
		            		autoAddGo();
		            	}
		            	
					}
				});
			}
			
			function autoAddGo() {
				var param = {
					ex_count : Number($("#ex_count").val())
				}
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/mylotto/autoAdd.do",
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
			
			function autoAddibm1077() {
				var param = {
					ex_count : Number($("#ex_count").val())
				}
				
				$.ajax({
					type: "POST",
					url: "${APP_ROOT}/mylotto/autoAddibm1077.do",
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
			
			function filterAddGo() {
				var url = "${APP_ROOT}/mylotto/filterAddajax.do?ex_count=" + thisExCount;
				changeContent(url);
			}
			
			function delGo() {
				var idArray = $("#jqgrid").jqGrid('getDataIDs');		
				var checkCnt = 0;
				var ex_count = "";
				var user_no = "";
				var seq = "";
				
				for(var i = 0 ; i < idArray.length ; i++){		
					if( $("input:checkbox[id='jqg_jqgrid_"+idArray[i]+"']").is(":checked") ) {
						var rowdata = $("#jqgrid").getRowData(idArray[i]);
						ex_count = rowdata.ex_count;
						user_no = rowdata.user_no;
						seq = rowdata.seq;
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
		
				if (confirm("[" + ex_count + "] 회차에 등록된 정보를 삭제하시겠습니까?")) {
					var param = {
						ex_count : Number(ex_count),
						user_no : Number(user_no),
						seq : Number(seq)
					}
					
					$.ajax({
						type: "POST",
						url: "${APP_ROOT}/mylotto/deleteMyData.do",
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
			}

		</script>