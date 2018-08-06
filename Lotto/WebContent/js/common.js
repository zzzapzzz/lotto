function checkExcelFile(id, allowCnt) {
	var validExts = new Array(".xlsx", ".xls");
	
	var file = $("#"+id).val();
	var fileCnt = $("#"+id)[0].files.length;
    var fileExt = file.substring(file.lastIndexOf('.'));
    if (validExts.indexOf(fileExt) < 0) {
      alert("유효하지 않은 파일이 선택되었습니다. 유효한 파일의 유형은 " +
               validExts.toString() + "입니다.");
      return false;
    } else if (fileCnt > allowCnt) {
    	alert("파일은 " + allowCnt + "개만 업로드 할 수 있습니다.");
    	return false;
    } else {
    	return true;
    }
}

function setCodeList(code_type, selectId) {
	var param = {
		'code_type' : code_type	
	};
	
	$.ajax({
		type: "POST",
		url: "/common/getCodeList.do",
		dataType: "json",
		data: param,
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
        	
        	$('#'+selectId).append('<option value="" selected>선택</option>');
        	
        	for (var i = 0 ; i < result.rows.length ; i++) {
        		var code_id = result.rows[i].code_id;
        		var code_nm = result.rows[i].code_nm;
        		
        		$('#'+selectId).append('<option value="'+ code_id +'">'+ code_nm +'</option>');
        	}
        	
		}
	});
}

function showSmallBox (title) {
	$.smallBox({
		title : title,
		content : "<i class='fa fa-clock-o'></i> <i>2 seconds ago...</i>",
		color : "#296191",
		iconSmall : "fa fa-check bounce animated",
		timeout : 4000
	});
}