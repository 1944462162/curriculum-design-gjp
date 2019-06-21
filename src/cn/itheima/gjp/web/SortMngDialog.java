package cn.itheima.gjp.web;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cn.itheima.gjp.domain.Sort;
import cn.itheima.gjp.service.SortService;
import cn.itheima.gjp.view.AbstractSortMngDialog;

public class SortMngDialog extends AbstractSortMngDialog{
    /*
     * 管理界面 构造方法  
     *    带参构造  创建当前对话框用的  传递的是指定的父窗口
     */
	public SortMngDialog(JFrame frame) {
		super(frame);
		/**
		 * 1:构造 弹出窗口的时候 就应该查询数据库 
		 *   所以 调用查询的方法写到 当前的这个构造中
		 * 2:异常的处理 就不要再抛了 
		 *    自行处理 try..catch 
		 */
		try {
			queryAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 写一个 完成查询所有信息 并展示在 页面的方法 
	 *    queryAllSort()
	 * @throws SQLException 
	 */
	
	public void queryAll() throws SQLException{
		//通过 SortService 去完成
		// 创建SortService 对象
		SortService service = new SortService();
		
		// 调用查询所有的功能
		List<Sort> sortList = service.queryAll();
		
		// 怎么把这个集合中的数据  传递到 界面上呢  
		// 有没有这样的功能呢   
		// 了解一下  在 SortMngDialog 父类  AbstractSortMngDialog
		           // 定义了一个功能 setTableModel(List<Sort> list)
	   // 学过继承 父亲有的方法 子类 自动拥有  那么也就是说 
		// 当前的类中 具备这个功能 
		
		//调用 显示在窗口的方法  记住 就可以了 
		setTableModel(sortList);
	}
    
	/*
	 * 添加按钮对应的功能
	 */
	@Override
	public void addSort() {
		System.out.println("添加分类");
		/*
		 * 添加这里弹出添加对话框
		 * 
		 */
		new AddSortDialog(this).setVisible(true);
		
		//再次 刷新  从新查询一遍 再展示  
		try {
			queryAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
   /*
    * 编辑按钮对应的功能
    */
	@Override
	public void editSort() {
		System.out.println("编辑分类");
		// 要验证用户是否选中了滚动栏中的一条记录
		
		// 滚动栏 是 sortDataTable
		
		//1:获取选择的行号 sortDataTable.getSelectRow()
		int row = sortDataTable.getSelectedRow();
		
		System.out.println(row);
		// 如果没有选择 就是小于0  
		if(row<0){
			JOptionPane.showMessageDialog(this, "请选择要编辑分类信息");
		    return;
		}
		
		// 通过行号 获取sort对象  
		//  getSortByTableRow(int row)在本类的父类上已经完成了这个功能编写
		Sort sort = getSortByTableRow(row);
		//如果 sort对象为null  那么说明 选中是空行
		if(sort==null){
			JOptionPane.showMessageDialog(this, "您选中的是空行");
		    return;
		}
		
		// 现在 有个问题 我想要把sort这个对象 传递给  EditSortDialog 怎么做
		/*
		 * 在这里 通过修改界面的构造方法 传递sort对象 到 EditSortDialog窗口中
		 */
		/*
		 * 在编辑这里 弹出编辑对话框
		 */
		new EditSortDialog(this,sort).setVisible(true);
		
		//再次 刷新  从新查询一遍 再展示  
				try {
					queryAll();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	}
   /*
    * 删除按钮对应的功能
    */
	@Override
	public void deleteSort() {
		System.out.println("删除分类");
		//1: 先去获取 选中的行数 
		int row = sortDataTable.getSelectedRow();
		//判断
		if(row<0){
			JOptionPane.showMessageDialog(this, "没选你想删啥,似不似撒");
			return;
		}		
		
		// 2:通过行数 获取 sort
		Sort sort = getSortByTableRow(row);
		// 如果是空 说明选择是空行
		if(sort==null){
			JOptionPane.showMessageDialog(this, "别拿空行糊弄我~~");
			return;
		}
		
		//弹出确认框
		int key = JOptionPane.showConfirmDialog(this, "确认要删除吗");
		
		System.out.println(key);
		
		if(key!=0){ // 没有选择yes
			return; // 没有删除 该干嘛去 
		}
		
		
		// 选择yes
		// 3 将id传递给 service
		SortService service = new SortService();
		
		try {
			service.deleteBySid(sort.getSid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//再次 刷新  从新查询一遍 再展示  
		try {
			queryAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	

}
