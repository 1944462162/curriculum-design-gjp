package cn.itheima.gjp.service;

import java.sql.SQLException;
import java.util.List;

import cn.itheima.gjp.dao.SortDao;
import cn.itheima.gjp.domain.Sort;

/*
 * 分类事务的  业务操作
 */
public class SortService {
	
	
	SortDao dao = new SortDao();
	
	/**
	 * 获取分类id并传递给dao层 
	 * @throws SQLException 
	 */
	public void deleteBySid(int sid) throws SQLException{
		
		dao.deleteSortById(sid);
	}
	
	/**
	 * 修改分类信息的功能 
	 * 
	 *    需要传递 一个Sort对象 更新后的数据在这里存储着 
	 *     Sort对象中 有 数据的id  还有 分类名称 父分类 分类描述
	 * @throws SQLException 
	 */
	 public void editSort(Sort sort) throws SQLException{
		 
		 dao.updateSort(sort);
	 }
	
	
	/**
	 * 添加分类信息功能 
	 *   参数 Sort类型(包含 父分类  分类名称 分类描述)
	 *   返回值 void  
	 * @throws SQLException 
	 *   
	 */
	 public void  addSort(Sort sort) throws SQLException{
		 // 将 前台获取的sort对象 通过dao传递到其 addSort(Sort sort)完成数据的添加
//		  SortDao dao = new SortDao();
		  
		  dao.addSort(sort);
		  
	 }
	/**
	 * 查询所有分类信息的功能  
	 * 
	 * 参数  无
	 * 
	 * 返回值 List<Sort> 多个Sort对象 返回
	 * @throws SQLException 
	 */
	public List<Sort> queryAll() throws SQLException {
		// 需要dao层支持 
		//创建 dao对象
//		SortDao dao = new SortDao();
		
		List<Sort> list = dao.queryAll();
		
		return list;
	}
	
	
	/**
	 * 查询所有分类名的功能  
	 *   把dao查询出来的结果 返回给 调用者
	 * @throws SQLException 
	 */
	public List<Object> queryAllSname() throws SQLException{
		//调用dao中 完成的 查询所有的分类名字的功能 
		List<Object> allSname = dao.queryAllSname();
		
		return allSname;
		
	}
	
	
	/**
	 * 根据父分类信息 查询 分类名称  把结果返回给调用者 
	 * 
	 *   结果是List<Object>
	 *   
	 *   参数  String parentName  指定的父分类 
	 * @throws SQLException 
	 */
	public List<Object> querySnameByParent(String parentName) throws SQLException{
		
		// 调用dao中完成 根据父分类查询的方法 
		List<Object> list = dao.querySnameByParent(parentName);
		
		return list;
		
	}
	
	
	
	
	
	
	
	
	
	
}
