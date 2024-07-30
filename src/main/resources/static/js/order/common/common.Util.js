/**
 * StringUtil
 * 문자열 처리 util 객체
 */
var StringUtil = {
		
	//EX) str : 대상String
	//	searchStr : 변환 대상 String
	//  replaceStr : 변환 처리 String 
	replaceAll : function(str, searchStr, replaceStr){
		if(str == null || str == searchStr || str == replaceStr){
			return '';
		}else{
			return str.split(searchStr).join(replaceStr);
		}
	},
	trim : function(str){
		if(str == null){
			return '';
		}else{
			return String(str).replace(/^\s+|\s+$/g, '');
		}
	},
	isEmpty : function(str){
		return str == null || this.trim(str) == '';
	},
	isNotEmpty : function(str){
		return !this.isEmpty(str);
	},
	defaultString : function(str, def){
		if(this.isEmpty(def)){
			def = '';
		}
		return this.isEmpty(str) ? def : str;
	},
	getExtension : function(str){
		var ext = '';
		if(this.isNotEmpty(str)){
			var index = str.lastIndexOf('.');
			if(index > 0 && index < str.length -1)
				ext = str.substring(index+1).toLowerCase();
		}
		return ext;
	},
	getByte  : function(str){
		var byte = 0;
		for(var iLoop = 0; iLoop < str.length; iLoop++){
			//한글 2byte
			byte += escape(str.charAt(iLoop)).length > 4 ? 2 : 1;
		}
		
		return byte;
	},
	isBizNo : function(bizNum) {
		bizNum = bizNum.replace(/-/gi, '');
		if(bizNum.length != 10){
			return false;
		}
		var sumMod = 0;
		sumMod = 0;
        sumMod += parseInt(bizNum.substring(0,1));
        sumMod += parseInt(bizNum.substring(1,2)) * 3 % 10;
        sumMod += parseInt(bizNum.substring(2,3)) * 7 % 10;
        sumMod += parseInt(bizNum.substring(3,4)) * 1 % 10;
        sumMod += parseInt(bizNum.substring(4,5)) * 3 % 10;
        sumMod += parseInt(bizNum.substring(5,6)) * 7 % 10;
        sumMod += parseInt(bizNum.substring(6,7)) * 1 % 10;
        sumMod += parseInt(bizNum.substring(7,8)) * 3 % 10;
        sumMod += Math.floor(parseInt(bizNum.substring(8,9)) * 5 / 10);
        sumMod += parseInt(bizNum.substring(8,9)) * 5 % 10;
        sumMod += parseInt(bizNum.substring(9,10));
        
        return !(sumMod % 10 != 0);
			
	},
	removeCSRF : function(str){
		var i = 0;
		var buf = "";
		for( i = 0; i < str.length; i++ )
		{
			if ( str.charAt(i) != '\n' && str.charAt(i) != '\r' )
			{
				buf += str.charAt(i);
			}
		}
		return buf;
	},
	// 3자리수 콤마 
	setComma : function(number){
		if(number == undefined){
			return " ";
		}
		return String(number).replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	},
	setPrice : function(number){
		return StringUtil.defaultString(StringUtil.setComma(number), "0")+" 원";
	},
	// date 표현용 
	setDateBar : function(dateStr){
		if(dateStr == undefined){
			return " ";
		}
		var filter = /^[0-9]*$/;
		if(!filter.test(dateStr)){
			return " ";
		}
		
		var yyyy = dateStr.substring( 0, 4 );
		var mm = dateStr.substring( 4, 6 );
		var dd = dateStr.substring( 6, 8 );
		return yyyy+'.'+mm+'.'+dd;
	    
	},
	formatBizNo : function(str){
		return StringUtil.defaultString(str).replace(/^(\d{3})(\d{2})(\d{5})$/, "$1-$2-$3");
	},
	endWith : function(str, suffix) {
	    return str.indexOf(suffix, str.length - suffix.length) !== -1;
	},
	getTimestamp : function(format){
		var date = new Date();
		format = this.defaultString(format, "yyyy.MM.dd HH:mm:ss");
		format = format.replace("yyyy" , String(date.getFullYear()));
		format = format.replace("MM" , String(date.getMonth()+1));
		format = format.replace("dd" , String(date.getDate()));
		format = format.replace("HH" , String(date.getHours()));
		format = format.replace("mm" , String(date.getMinutes()));
		format = format.replace("ss" , String(date.getSeconds()));
		return format;
	}
};

/**
 * Ajax 처리 관련 객체
 */
function AjaxUtil(){};

AjaxUtil.prototype = {
    type : "POST",

    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
    //contentType: "text/plain; charset=UTF-8",

    dataType: 'json',

    isDefaultError : false,

    isLoading : true,

    inputData : {},

    url : "",

    successFunction : null,

    errorFunction : null,

    setType : function(type){
        this.type = type;
    },

    setContentType : function(contentType){
        this.contentType = contentType;
    },

    setDataType : function(dataType){
        this.dataType = dataType;
    },

    setDefaultError : function(isDefaultError){
        this.isDefaultError = isDefaultError;
    },

    setInputData : function(inputData){
        this.inputData = inputData;
    },

    addInputData : function(name, value){
        this.inputData[name] = value;
    },

    setUrl : function(url){
        this.url = url;
    },

    setSuccess : function(successFunction){
        this.successFunction = successFunction;
    },

    setError : function(errorFunction){
        this.errorFunction = errorFunction;
    },

    setLoading : function(isLoading){
        this.isLoading = isLoading;
    },

    goAjax : function(url, successFunction, errorFunction){
        url = StringUtil.defaultString(url, this.url);
        var type = this.type;
        var contentType = this.contentType;
        var inputData = this.inputData;
        var isDefaultError = this.isDefaultError;
        var isLoading = this.isLoading;
        if(successFunction == null){
            successFunction = this.successFunction;
        }
        if(errorFunction == null){
            errorFunction = this.errorFunction;
        }
        var dataType = this.dataType.toLowerCase();
        /*
        if(dataType == "xml" || dataType == "json"){
            inputData["mType"] = dataType;
        }
        */
        //GET 한글 인코딩 처리 
        if(type =='GET') {
            for(key in inputData) {
            	this.addInputData( key, encodeURI(inputData[key],"UTF-8"));
            }
        }
        
        $.ajax({
            url : url,
            type: type,
            data: inputData,
            contentType: contentType,
            context : this,
            dataType: dataType,
            success: function(data) {
                if(successFunction != null){
                    if(isDefaultError){

                    }else{
                        successFunction(data);
                    }
                }
            },
            error: function(xhr, status, error){
                if(errorFunction != null){
                	errorFunction(xhr, status, error);
                }
            },
            beforeSend:function(){
                if(isLoading){
                    ViewUtil.loading();
                }
            },
            complete:function(){
                if(isLoading){
                    ViewUtil.hideLoading();
                }
            },
        });
    }
};

/**
 * 화면 제어 관련 유틸
 **/
var ViewUtil = {
    loading : function(){
        var $loading = $(".bg_loading");
        
        if($("body").hasClass(".bg_loading") === true){
            $loading.show();
        }else{
            $loading = $("<div class='bg_loading'>");
            $loading.append($("<div class='loading'><img src='/img/ico_hk.svg'></div>") );
            $("body").append($loading);
        }
    },
    hideLoading : function(){
        $(".bg_loading").hide();
    }
};

