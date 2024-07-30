var appInterface = {
	setinfo : function(json) {
		if(getNativeFlag() == NATIVE_ANDROID || getNativeFlag() == TEST_ANDROID){ 
			//QR.setinfo( JSON.stringify({"id":user_id}) );
			QR.setinfo( json );
		} else if(getNativeFlag() == NATIVE_IOS || getNativeFlag() == TEST_IOS ){
			//QR.setinfo( JSON.stringify({"id":user_id}) );
			//webkit.messageHandlers.setinfo.postMessage( JSON.stringify({"id":user_id,"pw":user_pw}) );
			
			window.webkit.messageHandlers.setinfo.postMessage(json);
		} 
	}, nativeGetInfo : function(json) {
		if(getNativeFlag() == NATIVE_ANDROID || getNativeFlag() == TEST_ANDROID ){
			//QR.getinfo( JSON.stringify({"callBack":"appInterface.getInfo"}) );
			QR.getinfo( json );
		} else if(getNativeFlag() == NATIVE_IOS || getNativeFlag() == TEST_IOS ){
			//QR.getinfo( JSON.stringify({"callBack":"appInterface.getInfo"}) );
			//webkit.messageHandlers.getinfo.postMessage( JSON.stringify({"callBack":"appInterface.getInfo"}) );
			window.webkit.messageHandlers.getinfo.postMessage(json);
		} 
	}, getInfo : function(jsonData) {
		if(jsonData != undefined ) {
			$('#user_id').val(jsonData.id);
		} 
		
	}, delinfo : function() {
		if(getNativeFlag() == NATIVE_ANDROID || getNativeFlag() == TEST_ANDROID ){
			QR.delinfo();
		}  else if(getNativeFlag() == NATIVE_IOS || getNativeFlag() == TEST_IOS ){
			//QR.delinfo();
			//webkit.messageHandlers.delinfo.postMessage();
			window.webkit.messageHandlers.delinfo.postMessage("");
		} 
	}, nativeGetVer : function() {
		//alert(navigator.userAgent);
		if(getNativeFlag() == NATIVE_ANDROID || getNativeFlag() == TEST_ANDROID ){
			QR.getver( JSON.stringify({"callBack":"appInterface.getVersion"}) );
		} else if(getNativeFlag() == NATIVE_IOS || getNativeFlag() == TEST_IOS ){
			QR.getver( JSON.stringify({"callBack":"appInterface.getVersion"}) );
		} else {
			appInterface.getVersion( {"ver":'test'} );
		}
	}, getVersion : function(jsonData) {
    	var $layerPop = $('#pop_info');
    	$layerPop.find('.version').html("버전정보<span>"+jsonData.ver+"</span>");
	}, nativeDebug : function(jsonData) {
		if(getNativeFlag() == NATIVE_ANDROID || getNativeFlag() == TEST_ANDROID ){
			QR.getdebug(JSON.stringify(jsonData));
		} else if(getNativeFlag() == NATIVE_IOS || getNativeFlag() == TEST_IOS ){
			QR.getdebug(JSON.stringify(jsonData));
		} else {
			console.log(JSON.stringify(jsonData));
		}
	}, makeqr : function(jsonData){
		if(getNativeFlag() == NATIVE_ANDROID || getNativeFlag() == TEST_ANDROID ){
			QR.makeqr(jsonData);
		}  else if(getNativeFlag() == NATIVE_IOS || getNativeFlag() == TEST_IOS ){
			window.webkit.messageHandlers.makeqr.postMessage(jsonData);
		}
	}
	, autoLoginCall : function(jsonData){
		if(jsonData != undefined ) {
			login.autoLogin(jsonData.id, jsonData.pw);
		}
	}
}




