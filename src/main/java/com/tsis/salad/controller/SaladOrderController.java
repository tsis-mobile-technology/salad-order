package com.spring.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.common.BaseConstants;
import com.spring.common.CryptoUtil;
import com.spring.service.SaladOrderService;
import com.spring.util.CommonUtil;


@Controller
public class SaladOrderController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    SaladOrderService saladOrderService;

    @RequestMapping(value = "saladOrderNoCheck.do" , produces = "application/text; charset=utf8")
    public @ResponseBody String saladOrderNoCheck(HttpServletRequest request , HttpServletResponse response, @RequestBody String data)
    {
    	String rt_code = ""; 
    	String rt_msg = "";
    	
    	try {
    		log.info("data >>>>>" + data.toString());
    		
    		JSONObject req_jsonObj = new JSONObject(data);  
    		log.info("req >>>>>" + req_jsonObj.toString());
    		
        	HashMap<String, Object> reqHashMap = CommonUtil.jsonToHashMap(req_jsonObj);
        	log.info(">>>>>>>>> saladOrderNoCheck reqHashMap : " +  reqHashMap.toString());
        	
        	if(saladOrderService.saladOrderCheck(reqHashMap)>0){
        		rt_code = "0000";
        		rt_msg ="주문완료 (QR 스캔완료)";
        	}else{
        		rt_code = "9999";
        		rt_msg ="주문전(QR 스캔 미완료)";
        	}
        	
    	}catch (Exception e){
    		rt_code = "system error";
    		rt_msg ="시스템 에러 발생\n관리자에게 문의해주세요.";
    		e.printStackTrace();
    	}
    	
    	JSONObject jsonObj = new JSONObject();
    	jsonObj.put("rt_code", rt_code);
    	jsonObj.put("rt_msg", rt_msg);
    	
	    log.info("jsonObject >>> "+jsonObj.toString());
	    
	    String output = jsonObj.toString();    	
        return output;
    }
    
    @RequestMapping(value = "saladOrderLogin.do")
	public String saladOrderLogin(HttpServletRequest request , HttpServletResponse response) 
	{
		log.info(">>> saladOrderLogin.do");
		return "order/login";
	}
    
    @RequestMapping(value = "saladOrderloginOk.do" , produces = "application/text; charset=utf8")
    public @ResponseBody String saladOrderloginOk(HttpServletRequest request , HttpServletResponse response, @RequestBody String data)
    {
    	String rt_code = ""; 
    	String rt_msg = "";
    	
    	System.out.println(">>>>>> saladOrderloginOk.do");
    	
    	JSONObject req_jsonObj = new JSONObject(data);    
    	HashMap<String, Object> reqHashMap = CommonUtil.jsonToHashMap(req_jsonObj);
    	
		//예외 처리 계정
		String[] passArr = {"28086660"};

		if (CommonUtil.useLoop(passArr, reqHashMap.get("user_id").toString()))
		{
			reqHashMap.put("user_pass","");	
		}else{
			reqHashMap.put("user_pass","N");	
		}
		
		log.info(">>>>>>>>> user_pass : " +  reqHashMap.get("user_pass").toString());
		log.info(">>>>>>>>> Login reqHashMap : " +  reqHashMap.toString());
    	
		//004:정규직,023:계약직,026:협력직(x) 
		HashMap<String, Object> login_result = saladOrderService.userLogIn(reqHashMap);
    	
    	if(login_result != null)
		{
    		log.info(">>>>>>>>>>>>>>>db ur_codr : "+ login_result.get("user_id"));
    		log.info(">>>>>>>>>>>>>>>db dn_codr : "+ login_result.get("dn_code"));
    		log.info(">>>>>>>>>>>>>>>db pwd : "+ login_result.get("pwd"));
    		log.info(">>>>>>>>>>>>>>>db jobgroup : "+ login_result.get("jobgroup"));
    		log.info(">>>>>>>>>>>>>>>CryptoUtil pwd : "+ CryptoUtil.hashSHA256(reqHashMap.get("user_pw").toString()));
    		
			if(login_result.get("pwd").equals(CryptoUtil.hashSHA256(reqHashMap.get("user_pw").toString())))
			{
				rt_code = "0000";
				
				request.getSession().setAttribute("USERID",login_result.get("user_id"));
				request.getSession().setAttribute("USERNAME",login_result.get("user_name"));
				request.getSession().setAttribute("USERDEPT",login_result.get("user_dept"));
				request.getSession().setMaxInactiveInterval(60*60); //60분
			}
			else
			{
				rt_code = "7777";
				rt_msg = "비밀번호가 올바르지 않습니다.";
			}
		}
		else
		{				
			rt_code = "8888";
			rt_msg = "등록(승인)된 사번이 아닙니다.";
		}
    	
    	JSONObject jsonObj = new JSONObject();
    	jsonObj.put("rt_code", rt_code);
    	jsonObj.put("rt_msg", rt_msg);
    	
	    log.info("jsonObject >>> "+jsonObj.toString());
	    
	    String output = jsonObj.toString();    	
        return output;
    }
    
    @RequestMapping(value = "saladOrderLogout.do")
	public String saladOrderLogout(HttpServletRequest request , HttpServletResponse response) 
	{	
    	try
    	{
    		request.getSession().setAttribute("USERID","");
        	request.getSession().setAttribute("USERNAME","" );
        	request.getSession().setAttribute("USERDEPT","");
        	request.getSession().invalidate();	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return "redirect:saladOrderLogin.do";
	}
    
    @RequestMapping(value = "saladOrderSession.do")
	public @ResponseBody String saladOrderSession(HttpServletRequest request , HttpServletResponse response) 
	{
    	String rt_code = "";
    	String rt_msg = "";
    	
    	String USERID = "";
    	String USERNAME = "";
    	String USERDEPT = "";
    	
    	try
    	{
    		USERID = request.getSession().getAttribute("USERID")== null?"":(String)request.getSession().getAttribute("USERID");
	    	USERNAME = request.getSession().getAttribute("USERNAME")==null?"":(String)request.getSession().getAttribute("USERNAME");
	    	USERDEPT = request.getSession().getAttribute("USERDEPT")==null?"":(String)request.getSession().getAttribute("USERDEPT");	
	    	
	    	log.info("USERID : "+ USERID);
	    	log.info("USERNAME : "+ USERNAME);
	    	log.info("USERDEPT : "+ USERDEPT);
	    	
	    	if (USERID.equals("") || USERID.equals("USERNAME") || USERID.equals("USERDEPT"))
	    	{
	    		rt_code="9999";
	    		rt_msg ="로그인 인증 실패";
	    	}else{
	    		rt_code="0000";
	    		rt_msg ="로그인 인증 성공";
	    	}
        	
    		
    	}catch(Exception e){
    		e.printStackTrace();
    		rt_code="system error";
    		rt_msg ="시스템 에러 발생\n관리자에게 문의해주세요.";
    	}
    	
    	
    	JSONObject jsonObj = new JSONObject();
    	jsonObj.put("USERID", USERID);
    	jsonObj.put("USERNAME", USERNAME);
    	jsonObj.put("USERDEPT", USERDEPT);
    	
    	jsonObj.put("rt_code", rt_code);
    	jsonObj.put("rt_msg", rt_msg);
    	
	    log.info("jsonObject >>> "+jsonObj.toString());
	    
	    String output = jsonObj.toString();    	
        return output;
	}
    
    //메인페이지
    @RequestMapping(value = "saladOrderIndex.do")
	public String saladOrderIndex(Model model, HttpServletRequest request , HttpServletResponse response) 
	{
		log.info(">>> saladOrderIndex.do");
		
		String USERID = ""; 
		String USERNAME="" ;
		String USERDEPT="";
		
		try
		{
			USERID = request.getSession().getAttribute("USERID")== null?"":(String)request.getSession().getAttribute("USERID");
	    	USERNAME = request.getSession().getAttribute("USERNAME")==null?"":(String)request.getSession().getAttribute("USERNAME");
	    	USERDEPT = request.getSession().getAttribute("USERDEPT")==null?"":(String)request.getSession().getAttribute("USERDEPT");
	    	
	    	log.info("USERID >>>>>>"+USERID);
	    	log.info("USERNAME >>>>>>"+USERNAME);
	    	log.info("USERDEPT >>>>>>"+USERDEPT);
	    	
	    	if (USERID.equals("") || USERNAME.equals(""))
	    	{
	    		log.info("USERID , USERNAME >>>>>> NULL!!!!");
	    		
	    		return "redirect:orderLogin.do";
	    	}
	    	else
	    	{
	    		HashMap<String, Object> reqHashMap = new HashMap<String, Object>();
	    		reqHashMap.put("user_id", USERID);

	    		model.addAttribute("USERID", USERID);
	    		model.addAttribute("USERNAME", USERNAME);
	    		model.addAttribute("USERDEPT", USERDEPT);
	    		
	    		model.addAttribute("orderCntMM", saladOrderService.getSaladOrderCntMM(reqHashMap));
	    		model.addAttribute("maxCnt", BaseConstants.MAX_CNT);
	    	}
		}catch(Exception e){
			e.printStackTrace();
			
			return "redirect:orderLogin.do";
		}
				
		return "order/index";
	}
    
    @RequestMapping(value = "getMySaladOrderList.do" , produces = "application/text; charset=utf8")
    public @ResponseBody String getMySaladOrderList(Model model, 
    		@RequestBody String data,
    		HttpServletRequest request, 
    		HttpServletResponse response)
    {
    	
		log.info(">>>>>> getMySaladOrderList.do");
		
    	JSONObject req_jsonObj = new JSONObject(data);    	
    	HashMap<String, Object> reqHashMap = CommonUtil.jsonToHashMap(req_jsonObj);
    	
    	log.info("USERID >>>>>>"+reqHashMap.get("USERID"));
    	log.info("YYMM >>>>>>"+reqHashMap.get("YYMM"));
    	
    	ObjectMapper mapper = new ObjectMapper();
    	
    	int totalCntMM = 0;
    	
    	String orderlist = "";
		try {
			orderlist = mapper.writeValueAsString(saladOrderService.getMySaladOrderList(reqHashMap));
			totalCntMM = saladOrderService.getMySaladOrderTotalCount(reqHashMap);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    	JSONObject jsonObj = new JSONObject();
    	jsonObj.put("rtCode", "0000");
    	jsonObj.put("totalCntMM", totalCntMM);
    	jsonObj.put("orderList", orderlist);
    	
	    log.info("jsonObject >>> "+jsonObj.toString());
	    
	    String output = jsonObj.toString();
		
        return output;
    }
 
    @RequestMapping(value = "salad_order_save.do")
	public @ResponseBody Map<String, Object> orderSave(HttpServletRequest req , HttpServletResponse res, @RequestBody String data) 
	{
		log.debug(">>> salad_order_save.do");
		log.debug("data >>> " + data);
		
		Map<String, Object> rt_jsonObject = new HashMap<String, Object>();
		
		String rt_code = "", rt_msg = "";
		
		final int limit_cnt = BaseConstants.MAX_CNT;
		int order_cnt_mm  ; //이번달 주문한 음료 잔수
		int order_last_cnt_mm= 0;    //최종 주문한 카운트 
		
		String order_no = ""; 	//주문 번호
		String order_cnt ="0"; 	//주문 음료 잔수
		String order_date ="" ; // 주문일자
		
		if (data.equals("") || data == null) 
		{
			rt_code = "E5555";
			rt_msg = "잘못된 경로로 접근 하였습니다. ";
		}
		else
		{	
			// 주문 시간 체크 기능 추가
			if(saladOrderService.saladOrderTimeCheck()){
        		        	
        		// 현재 날짜/시간
    			Date now = new Date();
    			
    			// 포맷팅 정의
    			SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");			
    			order_date = formatter.format(now);

    			JSONObject req_jsonObj = new JSONObject(data);    	
    	    	HashMap<String, Object> reqHashMap = CommonUtil.jsonToHashMap(req_jsonObj);
    	    	
    	    	log.debug("reqHashMap >>>>>>"+reqHashMap.toString());
    	    	
    	    	order_cnt = reqHashMap.get("orderCnt").toString();
    	    	order_no = reqHashMap.get("orderNo").toString();
    	    		
    			//이번달 주문한 카운트
    			order_cnt_mm = saladOrderService.salad_order_cnt_mm(reqHashMap);
    			log.debug("order_cnt_mm >> " + order_cnt_mm);

    			//남은카운트 + 주문카운트 체크
    			order_last_cnt_mm = order_cnt_mm+Integer.parseInt(order_cnt);
    			log.debug("order_add >> " + order_last_cnt_mm);
    			 
    			if(limit_cnt > order_cnt_mm && limit_cnt >= order_last_cnt_mm)
    			{
    				//주문번호 중복 체크
    				if(saladOrderService.saladOrderCheck(reqHashMap) > 0)
    				{	
    					rt_code = "E8888";
    					rt_msg = "스캔 완료된 QR코드 입니다.\n주문 완료하기 버튼을 눌러주세요.";
    				}
    				else
    				{
    					if (saladOrderService.saladOrderSave(reqHashMap))
    					{
    						rt_code = "S0000";
    						rt_msg = "정상주문 처리 되었습니다.("+ order_last_cnt_mm + "/" + limit_cnt+")";
    						
    						rt_jsonObject.put("order_no", order_no);
    						rt_jsonObject.put("order_date", order_date);
    						rt_jsonObject.put("order_cnt", order_cnt);
    						rt_jsonObject.put("order_last_cnt_mm", order_last_cnt_mm);
    						rt_jsonObject.put("limit_cnt", limit_cnt);
    					}
    					else
    					{
    						rt_code = "E9999";
    			    		rt_msg ="주문 실패 하였습니다";
    					}
    				}
    			}
    			else
    			{
    				rt_code = "E2020";
    				rt_msg = "매월 "+limit_cnt+"잔까지 주문 가능합니다.("+ order_cnt_mm + "/" + limit_cnt+")";
    			}
			
			}else{
        		rt_code = "9999";
        		rt_msg ="사용(주문) 가능 시간은 아래와 같습니다.\n-09시 이전\n-11시 30분 ~ 13시\n-17시 30분 이후";
        	}
			
			
		}
		
		rt_jsonObject.put("rt_code", rt_code);
		rt_jsonObject.put("rt_msg", rt_msg);
		
	    
	    log.debug("jsonObject >>> "+rt_jsonObject.toString());
	    
	    return rt_jsonObject;
	}
    
    //주문 정보 - iOS
    @RequestMapping(value = "saladOrderInfo.do")
    public String saladOrderInfo(Model model, HttpServletRequest request, HttpServletResponse response, 
    		@RequestBody String data)
    {

     	log.info(">>> saladOrderInfo.do");
     	
     	log.info("saladOrderInfo data >>>"+ data.toString());
     	
		
    	try
		{
	    	
    		String USERID = ""; 
    		String USERNAME="" ;
    		String USERDEPT="";
    		
    		USERID = request.getSession().getAttribute("USERID")== null?"":(String)request.getSession().getAttribute("USERID");
	    	USERNAME = request.getSession().getAttribute("USERNAME")==null?"":(String)request.getSession().getAttribute("USERNAME");
	    	USERDEPT = request.getSession().getAttribute("USERDEPT")==null?"":(String)request.getSession().getAttribute("USERDEPT");
	    	
	    	log.info("USERID >>>>>>"+USERID);
	    	log.info("USERNAME >>>>>>"+USERNAME);
	    	log.info("USERDEPT >>>>>>"+USERDEPT);
	    	
	    	if (USERID.equals("") || USERNAME.equals(""))
	    	{
	    		log.info("saladOrderInfo >>>>>> iOS SESSION NULL !!! ");
	    		return "redirect:saladOrderLogin.do";
	    	}
	    	else
	    	{
				JSONObject req_jsonObj = new JSONObject(data);    	
		    	HashMap<String, Object> reqHashMap = CommonUtil.jsonToHashMap(req_jsonObj);
		    	log.info("reqHashMap >>>"+ reqHashMap.toString());
				model.addAttribute("orderInfo", saladOrderService.getSaladOrderInfo(reqHashMap));
				model.addAttribute("orderCntMM", saladOrderService.getSaladOrderCntMM(reqHashMap));
				model.addAttribute("maxCnt", BaseConstants.MAX_CNT);
	    	}

		}catch(Exception e){
			log.info("saladOrderInfo >>>>>> iOS Error !!! ");
			e.printStackTrace();
		}
    	return "order/saladOrderInfo";
    }
    
    //주문 정보 - andriod
    @RequestMapping(value = "saladOrderInfo_android.do")
    public String saladOrderInfo_Android(Model model, HttpServletRequest request, HttpServletResponse response)
    {
    	try
		{	    	
    		String USERID = ""; 
    		String USERNAME="" ;
    		String USERDEPT="";
    		
    		USERID = request.getSession().getAttribute("USERID")== null?"":(String)request.getSession().getAttribute("USERID");
	    	USERNAME = request.getSession().getAttribute("USERNAME")==null?"":(String)request.getSession().getAttribute("USERNAME");
	    	USERDEPT = request.getSession().getAttribute("USERDEPT")==null?"":(String)request.getSession().getAttribute("USERDEPT");
	    	
	    	log.info("USERID >>>>>>"+USERID);
	    	log.info("USERNAME >>>>>>"+USERNAME);
	    	log.info("USERDEPT >>>>>>"+USERDEPT);
	    	
	    	if (USERID.equals("") || USERNAME.equals(""))
	    	{
	    		log.info("saladOrderInfo_Android >>>>>> Android SESSION NULL !!! ");
	    		return "redirect:saladOrderLogin.do";
	    	}
	    	else
	    	{
				HashMap<String, Object> reqHashMap = CommonUtil.getReqHashMap(request);
				model.addAttribute("orderInfo", saladOrderService.getSaladOrderInfo(reqHashMap));
	    		model.addAttribute("orderCntMM", saladOrderService.getSaladOrderCntMM(reqHashMap));
	    		model.addAttribute("maxCnt", BaseConstants.MAX_CNT);
	    	}
	    	
			
		}catch(Exception e){
			e.printStackTrace();
			log.info("saladOrderInfo_Android.do >>>>>> Error!!!!");
			return "redirect:orderLogin.do";
		}

    	return "order/saladOrderInfo";
    }
    
    
    //CMS Controller
    @RequestMapping(value = "saladOrderList.do")
	public String saladOrderList(HttpServletRequest req , HttpServletResponse res) 
	{
		log.info(">>> saladOrderList.do");
		return "cms/order/saladOrderList";
	}
    
    @RequestMapping(value = "getSaladOrderList.do" , produces = "application/text; charset=utf8")
    public @ResponseBody String getSaladOrderList(Model model, 
    		@RequestBody String data,
    		HttpServletRequest request, 
    		HttpServletResponse response)
    {
    	
		log.info(">>>>>> getSaladOrderList.do");
		
		JSONObject jsonObj = new JSONObject();
		
		try{
			JSONObject req_jsonObj = new JSONObject(data);    	
	    	HashMap<String, Object> reqHashMap = CommonUtil.jsonToHashMap(req_jsonObj);
	    	
	    	log.info("reqHashMap >>>>>>"+reqHashMap.toString());
	    	
	    	int pageNum 	= (int) (reqHashMap.get("pageNo")==null?1:Integer.parseInt(reqHashMap.get("pageNo").toString()));
	    	int pageSize 	= (int) (reqHashMap.get("pageSize")==null?10:Integer.parseInt(reqHashMap.get("pageSize").toString()));
	    	int pageBlock 	= (int) (reqHashMap.get("pageBlock")==null?10:Integer.parseInt(reqHashMap.get("pageBlock").toString()));
	    	
	    	HashMap<String, Object> cntHashMap = new HashMap<String, Object>() ;
	    	cntHashMap = saladOrderService.getSaladOrderTotalCount(reqHashMap);
	    	
	    	String orderSumCnt = cntHashMap.get("orderSumCnt").toString();
	    	String totalCount = cntHashMap.get("orderTotalCnt").toString();
	    	
	        int totalPage = (Integer.parseInt(totalCount) + (pageSize - 1)) / pageSize;
	        
	        if (totalPage==0) totalPage = 1;
	        
	        int startPage = ((pageNum - 1) / pageBlock) * pageBlock + 1;
	        int endPage = startPage + pageBlock - 1;
	        if (endPage > totalPage) 
	        { 
	            endPage = totalPage;
	        }

	        int startNo = pageNum==1?0:(pageNum-1) * pageSize;
	    	int endNo = pageSize;
	    	
	    	log.info("startPage>>>"+ startPage);
	    	log.info("endPage>>>"+ endPage);
	    	log.info("startNo >>>"+ startNo);
	    	log.info("endNo >>>"+ endNo);
	    	
	    	reqHashMap.put("startNo",startNo);
	    	reqHashMap.put("endNo",endNo);
	    	
	    	ObjectMapper mapper = new ObjectMapper();
	    	
	    	String orderlist = "";
			try {
				orderlist = mapper.writeValueAsString(saladOrderService.getSaladOrderList(reqHashMap));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			jsonObj.put("rtCode", "0000");
	    	jsonObj.put("orderList", orderlist);
	    	jsonObj.put("pageNum", pageNum);
	    	jsonObj.put("totalCount", totalCount);
	    	jsonObj.put("orderSumCnt", orderSumCnt);
			
		}catch(Exception e){
			e.printStackTrace();
			jsonObj.put("rtCode", "9999");
		}
    	
	    log.info("jsonObject >>> "+jsonObj.toString());
	    
	    String output = jsonObj.toString();
		
        return output;
    }
    
    
    @RequestMapping(value = "saladOrderListExcel.do")
	public void saladOrderListExcel(HttpServletRequest request , HttpServletResponse response)  
	{
		log.info(">>> saladOrderListExcel.do");
	
		try
		{
			HashMap<String, Object> reqHashMap = CommonUtil.getReqHashMap(request);
			log.info("reqHashMap >>>>>>"+reqHashMap.toString());
			
			HashMap<String, Object> cntHashMap = new HashMap<String, Object>() ;
	    	cntHashMap = saladOrderService.getSaladOrderTotalCount(reqHashMap);
	    	
	    	String orderSumCnt = cntHashMap.get("orderSumCnt").toString();
	    	String totalCount = cntHashMap.get("orderTotalCnt").toString();
	    	
	    	List<HashMap<String, Object>> list  = new ArrayList<HashMap<String, Object>>();
	    	list = saladOrderService.getSaladOrderListExcel(reqHashMap);
	    	
			Workbook wb = new XSSFWorkbook();
	        Sheet sheet = wb.createSheet("간편식 주문현황");
	        Row row = null;
	        Cell cell = null;
	        int rowNum = 0;
	        
	        //첫줄
	        row = sheet.createRow(rowNum++);
	        cell = row.createCell(0);
	        cell.setCellValue("주문건수:"+totalCount);
	        
	        cell = row.createCell(1);
	        cell.setCellValue("주문 잔수:"+orderSumCnt);
	        
	        //두번째줄
	        row = sheet.createRow(rowNum++);
	        cell = row.createCell(0);
	        cell.setCellValue("");
	        
	        cell = row.createCell(1);
	        cell.setCellValue("");
	        
	        // Header
	        row = sheet.createRow(rowNum++);
	        cell = row.createCell(0);
	        cell.setCellValue("번호");
	        
	        cell = row.createCell(1);
	        cell.setCellValue("이름");
	        
	        cell = row.createCell(2);
	        cell.setCellValue("사번");
	        
	        cell = row.createCell(3);
	        cell.setCellValue("부서");
	        
	        cell = row.createCell(4);
	        cell.setCellValue("주문번호");
	        
	        cell = row.createCell(5);
	        cell.setCellValue("주문카운트");
	        
	        cell = row.createCell(6);
	        cell.setCellValue("주문 시간");
	        
	        // Body
	        for (int i=0; i<list.size(); i++) {
	            row = sheet.createRow(rowNum++);
	            cell = row.createCell(0);
	            cell.setCellValue(list.get(i).get("seq").toString());
	            
	            cell = row.createCell(1);
	            cell.setCellValue(list.get(i).get("name").toString());
	            
	            cell = row.createCell(2);
	            cell.setCellValue(list.get(i).get("id").toString());
	            
	            cell = row.createCell(3);
	            cell.setCellValue(list.get(i).get("dept").toString());
	           
	            cell = row.createCell(4);
	            cell.setCellValue(list.get(i).get("orderNo").toString());
	            
	            cell = row.createCell(5);
	            cell.setCellValue(list.get(i).get("orderCnt").toString());
	            
	            cell = row.createCell(6);
	            cell.setCellValue(list.get(i).get("regdate").toString());
	            
	        }

	        // 컨텐츠 타입과 파일명 지정
	        response.setContentType("ms-vnd/excel");
	        response.setHeader("Content-Disposition", "attachment;filename=HANOK_LIST.xlsx");
	        wb.write(response.getOutputStream());
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
    
    @RequestMapping(value = "saladOrderTimeCheck.do" , produces = "application/text; charset=utf8")
    public @ResponseBody String saladOrderTimeCheck(HttpServletRequest request , HttpServletResponse response, @RequestBody String data)
    {
    	String rt_code = ""; 
    	String rt_msg = "";
    	
    	try {
        	if(saladOrderService.saladOrderTimeCheck()){
        		rt_code = "0000";
        		rt_msg ="주문 가능";
        	}else{
        		rt_code = "9999";
        		rt_msg ="사용(주문) 가능 시간은 아래와 같습니다.\n-09시 이전\n-11시 30분 ~ 13시\n-17시 30분 이후";
        	}
        	
    	}catch (Exception e){
    		rt_code = "system error";
    		rt_msg ="시스템 에러 발생\n관리자에게 문의해주세요.";
    		e.printStackTrace();
    	}
    	
    	JSONObject jsonObj = new JSONObject();
    	jsonObj.put("rt_code", rt_code);
    	jsonObj.put("rt_msg", rt_msg);
    	
	    log.info("jsonObject >>> "+jsonObj.toString());
	    
	    String output = jsonObj.toString();    	
        return output;
    }
}
