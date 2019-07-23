		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		
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
				
				setWinCountList();
				
			});

			function initPlugin() {
				/*
				var param = {		
					win_count : 99999999	//DTO int형 변수 초기값 설정
				};
				*/
				
				jQuery("#jqgrid").jqGrid({
					url: '${APP_ROOT}/sysmng/getWinDataList.do',
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
						'회차','번호1','번호2','번호3','번호4','번호5','번호6','보너스'
					],
					colModel : [
						  {name:'win_count',index:'win_count',width:100, align:'center'}
						, {name:'num1',index:'num1',width:100, align:'center'}
						, {name:'num2',index:'num2',width:100, align:'center'}
						, {name:'num3',index:'num3',width:100, align:'center'}
						, {name:'num4',index:'num4',width:100, align:'center'}
						, {name:'num5',index:'num5',width:100, align:'center'}
						, {name:'num6',index:'num6',width:100, align:'center'}
						, {name:'bonus_num',index:'bonus_num',width:100,align:'center'}
					],
					rowNum : 100,
					rowList : [10, 20, 30, 100],
					pager : '#pjqgrid',
					sortname : 'WIN_COUNT',
					sortorder : 'DESC',
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
				$("#uploadFile").click(uploadFileGo);
				$("#winDataAnalysis").click(changeAnalysisGo);
				
				$("#search_win_count").change(searchGo);
				
				$("#searchKey").keydown(function (e) {
			        if(e.keyCode == 13){
			            searchGo();
			        }
			    });
				
			}
			
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
			        	
			        	$("#search_win_count").append('<option value="">전체</option>');
			        	for (var i = 0 ; i < result.rows.length ; i++) {
			        		var win_count = result.rows[i].win_count;
			        		
			        		$("#search_win_count").append('<option value="'+ win_count +'">'+ win_count +'</option>');
			        	}
			        	
			        	searchGo();
					}
				});
			}
			
			$(window).on('resize.jqGrid', function() {
				$("#jqgrid").jqGrid('setGridWidth', $("#content").width());
			});
			
			function changeAnalysisGo() {
				var param = {
					win_count : $("#win_count").val()
				}
				
				var url = "${APP_ROOT}/sysmng/analysisWinDataajax.do";
				changeContent(url, param);
			}
			
			function uploadFileGo() {
				$("#excel").val("");
				
				$("#status").html("");
		        var percentVal = '0%';
		        $(".bar").attr("width", percentVal);
		        $(".percent").html(percentVal);
		        
				var url = "javascript:uploadFile();";
				$("#uploadfile-form").attr("action", url);
				
				layer_popup('uploadPopup', 'C');
			}
			
			function uploadFile() {
				
				var excel = $("#excel").val();
				var result = checkExcelFile("excel",1);
				if (result) {
					var url = "${APP_ROOT}/sysmng/uploadFileForWinData.do";
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
			
			function searchGo() {
				var param = {		
					win_count : Number($("#search_win_count").val())
				};	
				
				var page = 1;
				// isAction = Y 인 경우 현재 페이지 유지, N 인 경우 1페이지로 표시
				if (isAction == 'Y') {
					page = jQuery("#jqgrid").getGridParam("page");
					isAction = 'N';
				}
		
				$("#jqgrid").setGridParam({	url: '${APP_ROOT}/sysmng/getWinDataList.do', datatype:'json', postData: param, page: page, sortname: '', sortorder: ''   }).trigger("reloadGrid");
			}
			
			function changeWriteGo() {
				var url = "${APP_ROOT}/sysmng/writeWinDataajax.do";
				changeContent(url);
			}
			
			function changeModifyGo() {
				var idArray = $("#jqgrid").jqGrid('getDataIDs');		
				var checkCnt = 0;
				var win_count = "";
				
				for(var i = 0 ; i < idArray.length ; i++){		
					if( $("input:checkbox[id='jqg_jqgrid_"+idArray[i]+"']").is(":checked") ) {
						var rowdata = $("#jqgrid").getRowData(idArray[i]);
						win_count = rowdata.win_count;
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
					win_count : Number(win_count)
				}
				var url = "${APP_ROOT}/sysmng/modifyWinDataajax.do";
				changeContent(url, param);
			}
			
			function deleteGo() {
				var idArray = $("#jqgrid").jqGrid('getDataIDs');		
				var checkCnt = 0;
				var win_count = "";
				
				for(var i = 0 ; i < idArray.length ; i++){		
					if( $("input:checkbox[id='jqg_jqgrid_"+idArray[i]+"']").is(":checked") ) {
						var rowdata = $("#jqgrid").getRowData(idArray[i]);
						win_count = rowdata.win_count;
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
		
				if (confirm("[" + win_count + "] 회차 로또 번호정보를 삭제하시겠습니까?")) {
					var param = {
						win_count : Number(win_count)
					}
					
					$.ajax({
						type: "POST",
						url: "${APP_ROOT}/sysmng/deleteWinData.do",
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
			
			function layer_popup(el, type){
				
				if ("winDataInfoPopup" == el) {
					$("#uploadPopup").addClass("hide");
					$("#winDataInfoPopup").removeClass("hide");
				} else {
					$("#winDataInfoPopup").addClass("hide");
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
		        	$("#win_count").focus();
		        } else if ("U" == type) {
		        	$("#num1").focus();
		        }
		    }
		</script>