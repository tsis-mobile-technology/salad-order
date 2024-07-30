    var login = {
    	user_id:"",
    	user_pw:"",
        login : function(){
        	
    		if($("#user_id").val() == "") {
    			alert("아이디를 입력해 주세요.");
    			$("#user_id").focus();
    			return;
    		}
    		if($("#user_pw").val() == "") {
    			alert("비밀번호를 입력해 주세요.");
    			$("#user_pw").focus();
    			return;
    		}
    		const btnElement = document.getElementById("loginbtn");
    		btnElement.innerText = "로그인 처리 중 ...";
    			
    		login.user_id = $('#user_id').val();
    		login.user_pw = $('#user_pw').val();
    		
    		var obj = new Object();
    		obj.user_id = login.user_id;
    	    obj.user_pw =login.user_pw;
    	    
            var json_data = JSON.stringify(obj);
            console.log("json_data : " + json_data);
            
            var request = $.ajax({
            	url: "orderloginOk.do",
                type: 'POST',
                data: json_data,
                dataType: "json",
                processData: false,
                contentType: false
            });
            
            request.done(function (data) // 전송 후, 결과 값 받아오는 부분
            {
            	if (data != null) {
            		var jsonObj = JSON.stringify(data);
            		var jsonData = JSON.parse(jsonObj);

            		if (jsonData.rt_code == '0000') {
            			//alert(jsonData.rt_msg);
            			//alert('success!!');
            			console.info("success!!");
            			
            			var id_save = $("#chk").is(":checked");
                        if(id_save) { // 폰으로 데이터 전달
                            //자동로그인시 앱으로 정보 전달
                        	var set = JSON.stringify({"user_id":login.user_id,"user_pwd":login.user_pw});
                        	appInterface.setinfo(set);
                        	
                        	//정상처리후 쿠키 데이터 저장
                        	$.cookie('id_save', 'Y' , { expires: 120, path: '/' });
                        	$.cookie('id_value', login.user_id , { expires: 120, path: '/' });
                        } else {
                        	appInterface.delinfo();
                        	//자동로그인 쿠키 삭제 
                        	$.removeCookie('id_save', { path: '/' });
                        	$.removeCookie('id_value', { path: '/' });
                        }
                        
                        btnElement.innerText = "로그인";
                        
                        //로그인 후 메인 페이지로 이동
                        //location.replace(data.returnUrl);
                        location.replace("orderIndex.do");
            		} else {
            			btnElement.innerText = "로그인";
            			//alert(jsonData.rt_msg + '[' + jsonData.rt_code + ']');
            			alert(jsonData.rt_msg);
            			
            			//location.replace("orderIndex.do");
            			return false;
            		}
            	}
            });

            request.fail(function (jqXHR, textStatus) // 에러 발생
            {
            	btnElement.innerText = "로그인";
            	alert("서버 통신 오류 발생!");
            });
                        
        } 
    	,
        logout : function(){
        	if(confirm("로그아웃 하시겠습니까?")){
        		
        		//자동로그인 쿠키 삭제 
            	$.removeCookie('id_save', { path: '/' });
            	$.removeCookie('id_value', { path: '/' });
            	
        		appInterface.delinfo();
        		location.href = "orderLogout.do";
        	}
        }
        ,
        autoLogin : function(user_id, user_pw)
        {
        		
        	if (user_id !="" && user_pw !=""){
        		
        		$('#user_id').val(user_id);
            	$('#user_pw').val(user_pw);
        		
        		const btnElement = document.getElementById("loginbtn");
        		btnElement.innerText = "로그인 처리 중 ...";
        			
        		login.user_id = user_id;
        		login.user_pw = user_pw;
        		
        		var obj = new Object();
        		obj.user_id = login.user_id;
        	    obj.user_pw =login.user_pw;
        	    
                var json_data = JSON.stringify(obj);
                console.log("json_data : " + json_data);
                
                var request = $.ajax({
                	url: "orderloginOk.do",
                    type: 'POST',
                    data: json_data,
                    dataType: "json",
                    processData: false,
                    contentType: false
                });
                
                request.done(function (data) // 전송 후, 결과 값 받아오는 부분
                {
                	if (data != null) {
                		var jsonObj = JSON.stringify(data);
                		var jsonData = JSON.parse(jsonObj);

                		if (jsonData.rt_code == '0000') {
                			//alert(jsonData.rt_msg);
                			//alert('success!!');
                			console.info("success!!");
                            
                			btnElement.innerText = "로그인";
                			
                            //로그인 후 메인 페이지로 이동
                            //location.replace(data.returnUrl);
                            location.replace("orderIndex.do");
                		} else {
                			btnElement.innerText = "로그인";
                			//alert(jsonData.rt_msg + '[' + jsonData.rt_code + ']');
                			alert(jsonData.rt_msg);
                			return false;
                		}
                	}
                });

                request.fail(function (jqXHR, textStatus) // 에러 발생
                {
                	alert("서버 통신 오류 발생!");
                });
                
        	}

        	
        }
    };
    
    
    function getMyOrderListMM(userid){
		$.ajax({ 
			type:"POST",
			url: "getMyOrderList.do",
			dataType: "json", 
			data :JSON.stringify({
				USERID : userid,
				YYMM : $("#yymm option:selected").val()
			}),
			contentType:"application/json; charset=utf-8", 
			cache : false, 
			success : function(resData){
				
				var totalCntMM = JSON.parse(resData.totalCntMM);
				$("#YYMM_CNT").empty().html(totalCntMM);
				
				var item = JSON.parse(resData.orderList);
				var selectHtml=[]; 
				var len = item.length;
				
				if(len > 0){
					for (var i = 0; i < item.length; i++){
						var seq = item[i].seq;
						var orderNo = item[i].orderNo;
						
						var orderCnt = item[i].orderCnt;
						var regdate = item[i].regdate;
						
						/* 
						console.log(" >> seq :"+ seq);
						console.log(" >> orderno :"+ orderno);
						console.log(" >> regdate :"+ regdate); 
						*/
						
						
						//selectHtml.push('<div style="cursor: pointer" class="book" id="'+item[i].seq+'">'+timezone+' ~ </div>');
						
						selectHtml.push('<li>');
						selectHtml.push('<div class="when">');
						selectHtml.push('<span class="num">'+regdate+'</span>');
						//selectHtml.push('<span class="num">13:20:22</span>');
						selectHtml.push('</div>');
						selectHtml.push('<div class="id">');
						selectHtml.push('<span class="tit">주문번호</span>');
						selectHtml.push('<span class="num">'+orderNo+'</span>');
						selectHtml.push('</div>');
						selectHtml.push('<div class="place">');
						selectHtml.push('<span class="tit">한옥카페</span>');
						selectHtml.push('<span class="num">'+orderCnt+'</span><span>장</span>');
						selectHtml.push('</div>');
						selectHtml.push('</li>');
    				}	
				}else{
					selectHtml.push('<li>');
					selectHtml.push('<div>');
					selectHtml.push('<div class="con-none">사용내역이 없습니다.</div>');
					selectHtml.push('</li>');
				}
				//console.log("selectHtml:"+selectHtml);
				$("#boardList").empty().html(selectHtml.join(''));
			}, 
				
			/* ajax error 확인방법 */ 
				error : function(request,status,error){ 
					var jsonObj1 = JSON.stringify(request);
					var jsonData1 = JSON.parse(jsonObj1);
					//alert(jsonData1.responseText);
					alert('시스템 오류 발생 하였습니다. \n'+jsonData1.responseText);
					
					/* 
					console.log("1: "+ JSON.stringify(request));
					console.log("2: "+ request); 
					console.log("3: "+ status); 
					console.log("4: "+ error);
					*/
				} 
		});
	}
