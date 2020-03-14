		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		<#assign IMG_ROOT = SystemInfo.img_root>
		
		<script src="${JS_ROOT}/plugin/jqgrid/jquery.jqGrid.min.js"></script>
		<script src="${JS_ROOT}/plugin/jqgrid/grid.locale-en.min.js"></script>

		<script type="text/javascript">
			// plugin 화면 초기화
			function initPlugin() {
				
				dataLayer.push({
				  'pageCategory': 'WinDataInsert',
				  'visitorType': $("#authTask").val()
				});
				
				//회차값 자동설정
				setNextWinData();
				
				$.validator.addMethod("lessThan", function(value, element, params){
					// true : SUCCESS , false : FAIL 
				    return !isNaN(value) && (Number(value) <= Number(params)); 
				}, "Must be less than 45.");
				
				// Validation
				$("#writestep1-form").validate({
					// Rules for form validation
					rules : {
						num1 : {
							required : true,
							lessThan : 45
						},
						num2 : {
							required : true,
							lessThan : 45
						},
						num3 : {
							required : true,
							lessThan : 45
						},
						num4 : {
							required : true,
							lessThan : 45
						},
						num5 : {
							required : true,
							lessThan : 45
						},
						num6 : {
							required : true,
							lessThan : 45
						},
						bonus_num : {
							required : true,
							lessThan : 45
						}
					},

					// Messages for form validation
					messages : {
						num1 : {
							required: "번호1을 입력하세요.",
							lessThan: "45이하의 숫자를 입력하세요."
						},
						num2 : {
							required: "번호2를 입력하세요.",
							lessThan: "45이하의 숫자를 입력하세요."
						},
						num3 : {
							required: "번호3을 입력하세요.",
							lessThan: "45이하의 숫자를 입력하세요."
						},
						num4 : {
							required: "번호4를 입력하세요.",
							lessThan: "45이하의 숫자를 입력하세요."
						},
						num5 : {
							required: "번호5를 입력하세요.",
							lessThan: "45이하의 숫자를 입력하세요."
						},
						num6 : {
							required: "번호6을 입력하세요.",
							lessThan: "45이하의 숫자를 입력하세요."
						},
						bonus_num : {
							required: "보너스번호를 입력하세요.",
							lessThan: "45이하의 숫자를 입력하세요."
						}
					},
					// Do not change code below
					errorPlacement : function(error, element) {
						error.insertAfter(element.parent());
					},
					invalidHandler: function (form, validator) {
			            var errors = validator.numberOfInvalids();
			            if (errors) {
			            	// 커스텀 수정
			                var msg = '';
			            	$.each(validator.errorList, function(i){
			            		if (i > 0) {
			            			msg += '<br>';
			            		}
			            		msg += validator.errorList[i].message;
			            	});
			            	
			            	showBigBox("필수 입력값 확인!",msg, "W");
			                //alert(validator.errorList[0].message);
			                validator.errorList[0].element.focus();
			            }
			        }, 
			        submitHandler: function (form) {
			        
			            $.ajax({
							type: "POST",
							url: "${APP_ROOT}/sysmng/insertWinData.do",
			                data: $(form).serialize(),
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
									var cid = $("#currCid").val(); 
									openContent(cid);
		            			}
							}
						});
			        }
				});	

				$("#num1").keydown(function (e) {
			        if(e.keyCode == 13){
			    		$("#num2").focus();        
			        }
			    });
			    $("#num2").keydown(function (e) {
			        if(e.keyCode == 13){
			    		$("#num3").focus();        
			        }
			    });
			    $("#num3").keydown(function (e) {
			        if(e.keyCode == 13){
			    		$("#num4").focus();        
			        }
			    });
			    $("#num4").keydown(function (e) {
			        if(e.keyCode == 13){
			    		$("#num5").focus();        
			        }
			    });
			    $("#num5").keydown(function (e) {
			        if(e.keyCode == 13){
			    		$("#num6").focus();        
			        }
			    });
			    $("#num6").keydown(function (e) {
			        if(e.keyCode == 13){
			    		$("#bonus_num").focus();        
			        }
			    });
			}
			
			function setNextWinData() {
	            $.ajax({
					type: "POST",
					url: "${APP_ROOT}/sysmng/getLastWinData.do",
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

						if (result.status == "success") {
							var nextWinCount = Number(result.data.win_count) + 1;
							$("#win_count").val(nextWinCount);
							$("#num1").focus();
		            	} else {
		            		alert(result.msg);
		            	}
					}
				});
				
			}
			
			function cancel() {
				var cid = $("#currCid").val(); 
				openContent(cid);
			}
			
			function save() {
				$('#writestep1-form').submit();
			}
		</script>