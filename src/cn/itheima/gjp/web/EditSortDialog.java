package cn.itheima.gjp.web;

import java.security.Provider.Service;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import cn.itheima.gjp.domain.Sort;
import cn.itheima.gjp.service.SortService;
import cn.itheima.gjp.view.AbstractOperationSortDialog;

/*
 * 编辑分类弹框
 */
public class EditSortDialog extends AbstractOperationSortDialog {
	Sort sort;
   /*
    * 构造  传递的参数 就是说基于谁弹出的
    */
	public EditSortDialog(JDialog dialog,Sort sort) {
		super(dialog);
		
		
		this.sort = sort;
		// 设置标题
		setTitle("编辑分类界面");
		titleLabel.setText("编辑分类");
		// sort对象传递过来了 
		// 需要把 选中的这行信息的原始内容 显示到 编辑界面中
		// 被修改的分类对象中的 信息获取出来 
		String parent = sort.getParent();//获取了要修改的父分类
		String sname = sort.getSname();//获取了要修改分类名称
		String sdesc = sort.getSdesc();//获取了要修改分类描述 
		
		System.out.println(parent);
		System.out.println(sname);
		System.out.println(sdesc);
		
		// 将刚才获取的内容 显示到 这个编辑界面上 
//		表单上 父分类
//	      parentBox
//             表单上的  分类名称 
//	      snameTxt
//             表单上的  描述信息
//	      sdescArea
		// 设置 描述信息 
		sdescArea.setText(sdesc);
		snameTxt.setText(sname);
		parentBox.setSelectedItem(parent);
		
		
		
	}
    
	/*
	 *就是确认按钮的操作  
	 *    1:获取当前页面中最新的信息 

	      2:必须要知道 更新的这条信息的id是什么   

	      3:封装最新信息  传递给service层 处理 

	      4:关闭编辑界面
	 */
	@Override
	public void confirm() {
		//获取当前页面的信息 
		String sname = snameTxt.getText();
		String sdesc = sdescArea.getText();
		String parent = parentBox.getSelectedItem().toString();
		/*
		 * 
		 * 重点是  我要更新 被选中的那条记录 sort对象  
		 *   现在在构造中  那怎么传递到我的这个confirm方法中呢  
		 *   
		 *   我使用 一种  成员变量值传递问题
		 *    在成员位置 创建一个  Sort sort ;对象  
		 *    
		 *    在构造中 写一句话 
		 *      this.sort = sort; 将选中的那条记录 
		 *       传递到成员位置了  
		 *       
		 *       现在 我的confirm方法中可以使用了 
		 */
		// 将新获取到的信息 进行一个 更新操作 
		sort.setParent(parent);
		sort.setSname(sname);
		sort.setSdesc(sdesc);
		
		
		// 把这个对象传递给 service 
		SortService service = new SortService();
		
		try {
			service.editSort(sort);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(this, "编辑成功");
		// 页面消失 
		dispose();
		
		
		
	}

}
