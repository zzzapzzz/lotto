		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		<#assign IMG_ROOT = SystemInfo.img_root>
		
		<script src="${JS_ROOT}/plugin/jqgrid/jquery.jqGrid.min.js"></script>
		<script src="${JS_ROOT}/plugin/jqgrid/grid.locale-en.min.js"></script>

		<script type="text/javascript">
			// plugin 화면 초기화
			function initPlugin() {
				
				dataLayer.push({
				  'pageCategory': 'ServiceInfoInsert',
				  'visitorType': $("#authTask").val()
				});
				
				// Validation
				$("#writestep1-form").validate({
					// Rules for form validation
					rules : {
						svc_cd : {
							required : true,
							maxlength : 10
						},
						svc_nm : {
							required : true,
							maxlength : 50
						},
						svc_type : {
							required : true
						},
						svc_base_type : {
							required : true
						},
						svc_base_val : {
							required : true,
							digits : true
						},
						amount : {
							required : true,
							digits : true
						},
						use_yn : {
							required : true
						}
					},

					// Messages for form validation
					messages : {
						svc_cd : {
							required: "서비스코드를 입력하세요.",
							maxlength: "10글자까지 입력하세요."
						},
						svc_nm : {
							required: "서비스명을 입력하세요.",
							maxlength: "50글자까지 입력하세요."
						},
						svc_type : {
							required: "서비스구분을 선택하세요."
						},
						svc_base_type : {
							required: "서비스기분을 선택하세요."
						},
						svc_base_val : {
							required: "서비스기준값을 입력하세요.",
							digits: "숫자만 입력하세요."
						},
						amount : {
							required: "금액을 입력하세요.",
							digits: "숫자만 입력하세요."
						},
						use_yn : {
							required: "사용여부를 선택하세요."
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
							url: "${APP_ROOT}/sysmng/modifyServiceInfo.do",
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
								
			            		
			            		if (result.status == "success") {
			            			showSmallBox(result.msg);
			            			
									var cid = $("#currCid").val(); 
									openContent(cid);
		            			}
							}
						});
			        }
				});	

			    $("#svc_cd").keydown(function (e) {
			        if(e.keyCode == 13){
			    		$("#svc_nm").focus();        
			        }
			    });
			    $("#svc_nm").keydown(function (e) {
			        if(e.keyCode == 13){
			    		$("#svc_base_type").focus();        
			        }
			    });
			    $("#svc_base_type").select(function (e) {
		    		$("#svc_base_val").focus();        
			    });
			    $("#svc_base_val").keydown(function (e) {
			        if(e.keyCode == 13){
			    		$("#amount").focus();        
			        }
			    });
			    $("#amount").keydown(function (e) {
			        if(e.keyCode == 13){
			    		$("#prmt_cd").focus();        
			        }
			    });
			    $("#prmt_cd").select(function (e) {
		    		$("#use_yn").focus();
			    });
			    
			    $("#svc_nm").focus();
			}
			
			function cancel() {
				var cid = $("#currCid").val(); 
				openContent(cid);
			}
		</script>