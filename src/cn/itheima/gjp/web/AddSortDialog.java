package cn.itheima.gjp.web;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import cn.itheima.gjp.domain.Sort;
import cn.itheima.gjp.service.SortService;
import cn.itheima.gjp.view.AbstractOperationSortDialog;

/*
 * 添加分类弹框
 */
public class AddSortDialog extends AbstractOperationSortDialog {
   /*
    * 构造  传递的参数 就是说基于谁弹出的
    */
	public AddSortDialog(JDialog dialog) {
		super(dialog);
		setTitle("添加分类对话框"); //对话框的标题
		titleLabel.setText("添加分类");
		
	}
    
	/*
	 *就是确认按钮的操作
	 */
	@Override
	public void confirm() {
		System.out.println("点击确认按钮");
		/*
		 * 1:获取表单数据
		 * 2:对表单数据进行校验
		 * 3:把表单数据中的数据 封装到Sort对象 
		 * 4:调用 service中 addSort 完成添加
		 * 5: 关闭对话框 
		 */
		//1:获取表单数据 
		//获取父分类
		String parent = parentBox.getSelectedItem().toString();
	    //获取分类名称
		String sname = snameTxt.getText();
		//获取 分类描述
	    String sdesc = sdescArea.getText();
	    
	    //2:表单校验 
	   // String  中 有个 trim()方法   去除两端空格 
	       // 验证指的是 父分类必须标明   以及 子分类必须写数据
	    if(parent.equals("=请选择=")||sname.trim().isEmpty()){
	    	//提示  告诉他  请正确对待 要输出数据的   
	    	
	    	// 了解一下  gui操作
	    	//提示框
	    	JOptionPane.showMessageDialog(this, "请填写数据");
	    	return;
	    }
	    
	    // 3: 把表单数据中的数据 封装到Sort对象 
	    // 创建Sort对象
	    Sort sort = new Sort();
	    sort.setParent(parent);
	    sort.setSname(sname);
	    sort.setSdesc(sdesc);
	    
	    //4:调用 service中 addSort 完成添加
	    SortService service = new SortService();
	    
	    try {
			service.addSort(sort);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    JOptionPane.showMessageDialog(this,"添加完毕");
	    
	   // 5 关闭对话框
	   dispose();
	
	
	
	
	
	
	
	
	
	
	
	}

}
