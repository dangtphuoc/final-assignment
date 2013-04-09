function makeAjaxRequest(url, method, mine, cbSuccess, cbFail, data) {

	$.ajax({
		type : method,
		url : url,
		contentType : "application/json; charset=utf-8",
		data : data,
		dataType : mine,
		async : true,
		success : function(result) {
			if(typeof cbSuccess === 'function') {
				cbSuccess(result);
			} else {
				var fn = window[cbSuccess];
				if(typeof fn === 'function') {
				    fn(result);
				}
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			Contact.addErrorMessage("There is an error with system (ErrorCode = " + jqXHR.status + ", ErrorMessage = " + jqXHR.responseText);
		}
	});
}