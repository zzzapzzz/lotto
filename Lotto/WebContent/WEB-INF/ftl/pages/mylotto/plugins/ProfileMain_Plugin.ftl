		<#assign APP_ROOT = SystemInfo.app_root>
		<#assign JS_ROOT = SystemInfo.js_root>
		
		<script src="${JS_ROOT}/plugin/jqgrid/jquery.jqGrid.min.js"></script>
		<script src="${JS_ROOT}/plugin/jqgrid/grid.locale-en.min.js"></script>

		<script type="text/javascript">
			var isAction = 'N';
			
			$(document).ready(function() {
				pageSetUp();
				
				dataLayer.push({
				  'pageCategory': 'MyLottoProfile',
				  'visitorType': $("#authTask").val()
				});
				
				// Validation
				$("#userinfo-form").validate({
					// Rules for form validation
					rules : {
						nickname : {
							required : true,
							maxlength : 100
						},
						email : {
							required : true,
							maxlength : 100
						},
						mbtlnum : {
							required : true,
							digits : true
						}
					},

					// Messages for form validation
					messages : {
						nickname : {
							required: "닉네임을 입력하세요.",
							maxlength: "100글자까지 입력하세요."
						},
						email : {
							required: "이메일을 입력하세요.",
							maxlength: "100글자까지 입력하세요."
						},
						mbtlnum : {
							required: "연락처를 입력하세요.",
							digits: "숫자만 입력하세요."
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
							url: "${APP_ROOT}/sysmng/modifyUserInfo.do",
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
								
			            		
			            		if (result.status == "isDuplicate") {
				               		showBigBox('서비스코드 중복',result.msg, 'W');
				               		$("#svc_cd").focus();
				            	} else if (result.status == "success") {
			            			showSmallBox(result.msg);
			            			
									var cid = $("#currCid").val(); 
									openContent(cid);
		            			}
							}
						});
			        }
				});	

				$("#svc_type").select(function (e) {
		    		setNextCode();
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
			});

		</script>