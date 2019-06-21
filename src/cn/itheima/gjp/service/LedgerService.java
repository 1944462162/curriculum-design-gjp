package cn.itheima.gjp.service;

import java.sql.SQLException;
import java.util.List;

import cn.itheima.gjp.dao.LedgerDao;
import cn.itheima.gjp.dao.SortDao;
import cn.itheima.gjp.domain.Ledger;

/*
 * 账务信息的 服务层  
 *    
 */
public class LedgerService {
   
	// 因为 数据的访问 需要依赖于 dao层 数据访问层 
	//在成员位置 创建一个dao对象
	LedgerDao ld = new LedgerDao();
	
	SortDao sortDao = new SortDao();
	
	/**
	 * 查询所有账务信息的方法  
	 *   参数 无
	 *   返回值 List<Ledger>  所有的封装好的  账务信息
	 * @throws SQLException 
	 */
	public List<Ledger> queryAllLedger() throws SQLException{
		
		// 需要dao支持 
		// 通过调用dao层中的查询功能
		List<Ledger> queryAllLedger = ld.queryAllLedger();
		
		return queryAllLedger;
		
		
	}
	
	/**
	 * 根据条件查询 符合条件的Ledger对象方法  
	 *    参数  四个条件参数 
	 *    返回值 List<Ledger>
	 * @throws SQLException 
	 */
	public List<Ledger> queryLedger(String beginTime,String endTime,String parent,String sname) throws SQLException{
		
		/*
		 * 需要dao完成操作
		 */
		List<Ledger> ledgers = ld.queryLedger(beginTime, endTime, parent, sname);
		
		return ledgers;
	}
	
	/**
	 * 添加账务信息功能  
	 *    方法参数   Ledger对象 
	 *       注意 这里要 去查询 Ledger对象sname属性对应的sid并存储
	 * @throws SQLException 
	 */
	public void addLedger(Ledger ledger) throws SQLException{
		/*
		 * 获取到 该对象的sname
		 */
		String sname = ledger.getSname();
		
		// 根据 sname获取对应的sid
		// sortDao中有方法
		int sid = sortDao.getIdBySname(sname);
		//sid存储到ledger中
		ledger.setSid(sid);
		
		// 通过到 存储对象
		ld.addLedger(ledger);
	}
	
	/**
	 * 编辑账务信息功能
	 *   方法参数 就是Ledger对象 
	 * @throws SQLException 
	 */
	public void editLedger(Ledger ledger) throws SQLException {
		/*
		 * 获取到 该对象的sname
		 */
		String sname = ledger.getSname();
		
		// 根据 sname获取对应的sid
		// sortDao中有方法
		int sid = sortDao.getIdBySname(sname);
		//sid存储到ledger中
		ledger.setSid(sid);
		
		// 通过dao修改对象 
		ld.editLedger(ledger);
		
		
	}

	public void deletLedgerById(int lid) throws SQLException {
		//依赖于 dao
		ld.deleteById(lid);
	}
	
	
}
