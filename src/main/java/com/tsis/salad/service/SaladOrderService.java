package com.spring.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.spring.dao.OrderDao;
import com.spring.dao.SaladOrderDao;


@Service("SaladOrderService")
public class SaladOrderService {
    @Resource(name="SaladOrderDao")
    private SaladOrderDao saladOrderDao;
    
    public int salad_order_cnt_mm(HashMap<String , Object> reqHashMap) 
    {
    	return saladOrderDao.salad_order_cnt_mm(reqHashMap);
    }
    
    public int saladOrderCheck(HashMap<String , Object> reqHashMap) 
    {
    	return saladOrderDao.saladOrderCheck(reqHashMap);
    }
    
    public boolean saladOrderTimeCheck() 
    {
    	boolean r = false;
		if (saladOrderDao.saladOrderTimeCheck()>0){
			r = true;
		}else{
			r = false;
		}
    	return r;
    }
    
    public boolean saladOrderSave(HashMap<String , Object> reqHashMap) 
    {
    	return saladOrderDao.saladOrderSave(reqHashMap);
    }
    
    public HashMap<String, Object> getSaladOrderCntMM(HashMap<String , Object> reqHashMap) 
    {
    	return saladOrderDao.getSaladOrderCntMM(reqHashMap);
    }
    
    public int getMySaladOrderTotalCount(HashMap<String , Object> reqHashMap) 
    {
        return saladOrderDao.getMySaladOrderTotalCount(reqHashMap);
    }
    
    public List<HashMap<String, Object>> getMySaladOrderList(HashMap<String , Object> reqHashMap) 
    {
        return saladOrderDao.getMySaladOrderList(reqHashMap);
    }
    
    public HashMap<String, Object> getSaladOrderTotalCount(HashMap<String , Object> reqHashMap) 
    {
        return saladOrderDao.getSaladOrderTotalCount(reqHashMap);
    }
    
    public List<HashMap<String, Object>> getSaladOrderList(HashMap<String , Object> reqHashMap) 
    {
        return saladOrderDao.getSaladOrderList(reqHashMap);
    }
    
    public List<HashMap<String, Object>> getSaladOrderListExcel(HashMap<String , Object> reqHashMap) 
    {
        return saladOrderDao.getSaladOrderListExcel(reqHashMap);
    }
    
    public HashMap<String, Object> getSaladOrderInfo(HashMap<String , Object> reqHashMap) 
    {
    	return saladOrderDao.getSaladOrderInfo(reqHashMap);
    }
    
    //로그인
    public HashMap<String , Object> userLogIn(HashMap<String , Object> reqHashMap) 
    {
    	return saladOrderDao.userLogin(reqHashMap);
    }
}
