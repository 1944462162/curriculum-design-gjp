package cn.itheima.gjp.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itheima.gjp.domain.Sort;
import cn.itheima.gjp.tools.JDBCUtils;

/*
 * gjp_sort  
 *   表的数据访问层 
 */
public class SortDao {
	//这个就是 操作数据库的对象
	QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
	/**
	 * 删除 分类功能 
	 *    需要传递 被删除数据的sid  
	 * @throws SQLException 
	 *    
	 */
	public void deleteSortById(int sid) throws SQLException{
		System.out.println(sid);
		//2:sql语句
		String sql = "delete from gjp_sort where sid = ?";
		Object[] params = {sid};
		
		//执行
		qr.update(sql,params);
		
	}
	
	
	/**
	 *  编辑分类功能  
	 *     需要传递 一个Sort对象 更新后的数据在这里存储着 
	 *     Sort对象中 有 数据的id  还有 分类名称 父分类 分类描述
	 * @throws SQLException 
	 */
	public void updateSort(Sort sort) throws SQLException{
		
		// sql语句   根据 id进行数据的更新 
		String  sql = "update gjp_sort set sname= ? , parent= ? ,sdesc=? where sid = ?";
	    
		Object[] params = {sort.getSname(),sort.getParent(),sort.getSdesc(),sort.getSid()};
		
		//执行
		qr.update(sql,params);
	
	}   
	
	/**
	 * 添加分类信息功能 
	 *   参数  : Sort对象  需要 service层传递的   
	 *          (包含 父分类  分类名称  分类的描述 ) 
	 *   返回值 没有  void 默认可以添加成功  
	 * @throws SQLException 
	 */
	
	public void addSort(Sort sort) throws SQLException{
		//1:创建QueryRunner对象
//		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		
		//2:执行sql 
		String sql = "insert into gjp_sort(sname,parent,sdesc) values(?,?,?)";
		Object[] params = {sort.getSname(),sort.getParent(),sort.getSdesc()};
		
		
		qr.update(sql, params);
	}
	
	/**
	 * 查数据库  DBUtil技术 
	 * 查询所有分类信息 
	 *    参数: 无 
	 *    返回值:  List<Sort> 返回的是存有多个Sort对象的集合
	 * @throws SQLException 
	 */
	public List<Sort> queryAll() throws SQLException{
		
		//1:创建QueryRunner对象
//		QueryRunner  qr = new QueryRunner(JDBCUtils.getDataSource());
		
		//2:执行sql
		String sql = "select * from gjp_sort";
		Object[] params = {};
		
		
		// 查询多条  BeanListHandler 这种处理方式  
		
		List<Sort> list = qr.query(sql, new BeanListHandler<Sort>(Sort.class), params);
		
		
		return list;
	
	} 
	
	
	/**
	 *  查询所有分类名称功能  
	 *    参数 没有
	 *    返回值  List<Object>
	 * @throws SQLException 
	 */
	public List<Object> queryAllSname() throws SQLException{
		
		// 查询所有分类的sql语句
		String sql = "select sname from gjp_sort";
		Object[] params = {};
		/*
		 * 这次 我们发现 我们查询出来的数据 是单列数据 
		 * 所以要使用单列处理器  ColumnListHandler
		 *    
		 */
		List<Object> list = qr.query(sql, new ColumnListHandler(), params);
		
		return list;
		
	}
	
	/**
	 * 根据父分类信息  查询分类名称功能  
	 *   参数  String  代表  传递的 父分类名 要么是收入 要么是支出
	 *   返回值 List<Object>  返回的那一列的分类名
	 * @throws SQLException 
	 */
	public List<Object> querySnameByParent(String parentName) throws SQLException{
		//查询 指定父分类  的  分类名称
		
		String sql = "select sname from gjp_sort where parent = ?";
		Object[] params = {parentName};
		
		//执行
		/*
		 * 这次 查询出来数据 单列数据
		 * 所以 要使用 单列处理器 ColumnListHandler
		 */
		List<Object> list = qr.query(sql, new ColumnListHandler(), params);
		
		return list;
		
	}
	
	/**
	 * 根据sname 找到对应的sid
	 *    参数  就是  sname
	 *    返回值  就是对应的sid
	 * @throws SQLException 
	 */
	public int getIdBySname(String sname) throws SQLException{
		//sql
		String sql = "select sid from gjp_sort where sname = ?";
		Object[] params = {sname};
		
		/*
		 *  定睛一看  
		 *      发现 这个查询的结果  是 一个单数据  
		 *      使用接收的 结果集处理方式 
		 *       ScalarHandler 
		 */
		//  这里存在一个转型
		int sid = (int)qr.query(sql, new ScalarHandler(), params);
		
		return sid;
		
	}
	
	
	
	
}
