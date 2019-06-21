package cn.itheima.gjp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mysql.jdbc.JDBC4LoadBalancedMySQLConnection;

import cn.itheima.gjp.domain.Ledger;
import cn.itheima.gjp.tools.JDBCUtils;

/*
 * 操作 数据库gjp_ledger表的 dao层 数据访问层
 * 
 * 
 */
public class LedgerDao {
   //依赖于  QueryRunner去访问数据库   
	
	//在成员位置 创建一个QueryRunner
		QueryRunner  qr = new QueryRunner(JDBCUtils.getDataSource());
		
		
   /**
    * 查询所有账务信息的方法
    *  List<Ledger> 返回值的意思是 查询所有的账务信息 并且每一个封装成Ledger对象
 * @throws SQLException 
    *  
    */
	public List<Ledger> queryAllLedger() throws SQLException{
		// sql
//		String sql = "select * from gjp_ledger";
		// 这种sql语句 查询出来的结果  没有 sname 没有分类名称 只有分类id
		// 有没有这种查法  
		// 我要的数据 
		/*
		 *    需要显示的数据  与 对应的表  账务表 gjp_ledger
	 显示数据      ID        收/支    分类名     金额    账户     创建时间     说明  
               
       数据库字段    lid       parent   sid      money   account  createtime   ldesc
	                    账务id    父分类   分类id   金额    账户     创建时间      描述
	                    
	            我们 要的 ID        收/支         金额    账户     创建时间     说明    从gjp_ledger表中获取 
	           我们   要的分类名  从 gjp_sort表中获取 
	           
	             需要两张表  单独获取 可以 low
	             使用多表查询 能否完成呢?
	             答案是 可以  
	             怎么做  来到数据库 操作这里 拼接一下 
		 */
		
		String sql = "SELECT l.lid,l.parent,s.sname,l.money,l.account,l.createtime,l.ldesc FROM gjp_sort AS s ,gjp_ledger AS l WHERE s.sid=l.sid";
		Object[] params = {};
		// 执行sql
		/*
		 * 结果集选择什么  BeanListHandler
		 */
		List<Ledger> list = qr.query(sql, new BeanListHandler<Ledger>(Ledger.class), params);
		
		return list;
	}
	
	/**
	 * 根据时间查询 数据库 
	 *    这里面需要注意的是  
	 *      除了时间以外的 其他条件  是 可以有可以没有的   
	 *      
	 *      这里面 要执行sql  是需要拼接完成的    
	 *      
	 *      四个参数  beginTime  endTime parent  sname 
	 *      返回值 List<Ledger>
	 *      
	 *      小概念  集合转换成数组 toArray
	 * @throws SQLException 
	 */
//	 public List<Ledger> queryLedger(String beginTime,String endTime,String parent,String sname) throws SQLException{
//		 
//		 
//		 // 1:初始化 sql语句  
//		    // 因为在当前  时间  条件是必须写出来的 
//		
//		 StringBuilder sb = new StringBuilder("select * from gjp_ledger where createTime between ? and ?");
//		 
//		 //定义一个集合 用来接收参数列表
//		ArrayList<Object> plist = new ArrayList<Object>();
//		
//		// 先把 必选的两个?  的值 加到集合中
//		plist.add(beginTime);
//		plist.add(endTime);
//		
//		// 根据后面两个条件 完成sql的拼接  
//		
//		// 父分类条件拼接 
//		if(parent.equals("收入")||parent.equals("支出")){
//			sb.append("and parent = ?");
//			plist.add(parent);
//		}
//		
//		//分类名称拼接 
//		if(!sname.equals("-请选择-")){
//			// 根据sname获取sid
//			
//			SortDao  sortDao = new SortDao();
//			
//			int sid = sortDao.getIdBySname(sname);
//			// 这里只能拼接sid  因为 ledger表中没有sname
//			sb.append("and sid = ?");
//			
//			plist.add(sid);
//		}
//		System.out.println("sql拼接后的结果"+sb); 
//		
//		String sql = sb.toString();
//		
//		Object[] params = plist.toArray();
//		
//		List<Ledger> list = qr.query(sql, new BeanListHandler<>(Ledger.class), params);
//		
//		return list;
//	 }
//	
	/**
	 * 根据时间查询 数据库  第二种方式 多表方式
	 *    这里面需要注意的是  
	 *      除了时间以外的 其他条件  是 可以有可以没有的   
	 *      
	 *      这里面 要执行sql  是需要拼接完成的    
	 *      
	 *      四个参数  beginTime  endTime parent  sname 
	 *      返回值 List<Ledger>
	 *      
	 *      小概念  集合转换成数组 toArray
	 * @throws SQLException 
	 */
 public List<Ledger> queryLedger(String beginTime,String endTime,String parent,String sname) throws SQLException{
		 
		 
		 // 1:初始化 sql语句  
		    // 因为在当前  时间  条件是必须写出来的 
		
		 StringBuilder sb = new StringBuilder("select l.lid,l.parent,s.sname,l.money,l.account,l.createtime,l.ldesc FROM gjp_sort AS s ,gjp_ledger AS l WHERE s.sid=l.sid and l.createTime between ? and ?");
		 
		 //定义一个集合 用来接收参数列表
		ArrayList<Object> plist = new ArrayList<Object>();
		
		// 先把 必选的两个?  的值 加到集合中
		plist.add(beginTime);
		plist.add(endTime);
		
		// 根据后面两个条件 完成sql的拼接  
		
		// 父分类条件拼接 
		if(parent.equals("收入")||parent.equals("支出")){
			sb.append("and l.parent = ?");
			plist.add(parent);
		}
		
		//分类名称拼接 
		if(!sname.equals("-请选择-")){
	
			sb.append("and s.sname = ?");
			
			plist.add(sname);
		}
		System.out.println("sql拼接后的结果"+sb); 
		
		String sql = sb.toString();
		
		Object[] params = plist.toArray();
		
		List<Ledger> list = qr.query(sql, new BeanListHandler<>(Ledger.class), params);
		
		return list;
	 }
	
	/**
	 *   根据Ledger对象 存储 到数据库表中
	 *     参数Ledger对象 
	 * @throws SQLException 
	 */
     public void  addLedger(Ledger ledger) throws SQLException{
    	  
    	 // sql语句 
    	 String sql = "insert into gjp_ledger(parent,money,sid,account,createtime,ldesc)values(?,?,?,?,?,?)";
    	 
    	 
    	 Object[] params = {ledger.getParent(),ledger.getMoney(),ledger.getSid(),ledger.getAccount(),ledger.getCreatetime(),ledger.getLdesc()};
    	 
    	 
    	 //执行
    	 qr.update(sql, params);
    	 
    	 
    	 
     }
     
     /**
      * 定义方法 编辑账务  
      *  传递Ledger 对象  
     * @throws SQLException 
      */
     public void editLedger(Ledger ledger) throws SQLException{
    	 
    	 String sql = "update gjp_ledger set parent=?,money=?,sid=?,account=?,createTime=?,ldesc=? where lid=?";
    	 Object[] params = {ledger.getParent(),ledger.getMoney(),ledger.getSid(),ledger.getAccount(),ledger.getCreatetime(),ledger.getLdesc(),ledger.getLid()};
     
         qr.update(sql, params);
     }

	public void deleteById(int lid) throws SQLException {
		String sql = "delete from gjp_ledger where lid = ?";
		Object[] params = {lid};
		
		qr.update(sql, params);
	}
	
	
	
}
