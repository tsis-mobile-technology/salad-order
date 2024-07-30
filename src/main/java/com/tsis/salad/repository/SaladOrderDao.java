package com.spring.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

 
@Repository("SaladOrderDao")
public class SaladOrderDao {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    @Resource(name="sqlSession")
    private SqlSession sqlSession;
    
    @Autowired
    @Resource(name="sqlSession2")
    private SqlSession sqlSession2;
    
    @Autowired
    @Resource(name="sqlSession3")
    private SqlSession sqlSession3;

    @Autowired
	private DataSourceTransactionManager transactionManager ;
 
    public void setSqlSession(SqlSession sqlSession)
    {
    	this.sqlSession = sqlSession;
    }
    
    public void setSqlSession2(SqlSession sqlSession2)
    {
    	this.sqlSession2 = sqlSession2;
    }
    
    public void setSqlSession3(SqlSession sqlSession3)
    {
    	this.sqlSession3 = sqlSession3;
    }

    public int salad_order_cnt_mm(HashMap<String , Object> reqHashMap) 
    {
    	return sqlSession3.selectOne("salad_order.salad_order_cnt_mm", reqHashMap);
    }
    
    public int saladOrderCheck(HashMap<String , Object> reqHashMap) 
    {
    	return sqlSession3.selectOne("salad_order.saladOrderCheck", reqHashMap);
    }
    
    public int getMySaladOrderTotalCount(HashMap<String , Object> reqHashMap) 
    {
    	return sqlSession3.selectOne("salad_order.getMySaladOrderTotalCount", reqHashMap);
    }
    
    public List<HashMap<String , Object>> getMySaladOrderList(HashMap<String , Object> reqHashMap) 
    {
        return sqlSession3.selectList("salad_order.getMySaladOrderList", reqHashMap);
    }
    
    public boolean saladOrderSave(HashMap<String , Object> reqHashMap) 
    {
    	
    	boolean r = false;
    	
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    	TransactionStatus status = transactionManager.getTransaction(def);
    	
    	try 
    	{
    		sqlSession3.insert("salad_order.saladOrderSave",reqHashMap);
    		
    		transactionManager.commit(status);
    		
    		r = true;
    	}
    	catch (Exception e) 
    	{
    		log.error("------------------------");
    		log.error(e.toString());
    		log.error("------------------------");
    		
    		transactionManager.rollback(status);
    		r = false;
//    		throw e;
    	}
    	
    	
    	return r;
    
    }

    public HashMap<String , Object> getSaladOrderTotalCount(HashMap<String , Object> reqHashMap) 
    {
    	return sqlSession3.selectOne("salad_order.getSaladOrderTotalCount", reqHashMap);
    }
    
    public List<HashMap<String , Object>> getSaladOrderList(HashMap<String , Object> reqHashMap) 
    {
        return sqlSession3.selectList("salad_order.getSaladOrderList", reqHashMap);
    }
    
    public List<HashMap<String , Object>> getSaladOrderListExcel(HashMap<String , Object> reqHashMap) 
    {
        return sqlSession3.selectList("salad_order.getSaladOrderListExcel", reqHashMap);
    }
    
    public HashMap<String , Object> getSaladOrderCntMM(HashMap<String , Object> reqHashMap) 
    {
    	return sqlSession3.selectOne("salad_order.getSaladOrderCntMM", reqHashMap);
    }
    
    public HashMap<String , Object> getSaladOrderInfo(HashMap<String , Object> reqHashMap) 
    {
    	return sqlSession3.selectOne("salad_order.getSaladOrderInfo", reqHashMap);
    }
    

    public HashMap<String , Object> userLogin(HashMap<String , Object> reqHashMap) 
    {
    	HashMap<String , Object> login_result = null;
    	
    	try 
    	{
    		//login_result = sqlSession2.selectOne("covi_smart.userIdCheckView",reqHashMap);
    		login_result = sqlSession2.selectOne("covi_smart.userIdCheck",reqHashMap);
    		
    	}
    	catch (Exception e) 
    	{
    		log.error("------------------------");
    		log.error(e.toString());
    		log.error("------------------------");
    		
//    		throw e;
    	}
    	
    	return login_result;
    }
    
    public int saladOrderTimeCheck() 
    {
    	return sqlSession3.selectOne("salad_order.saladOrderTimeCheck");
    }
}
