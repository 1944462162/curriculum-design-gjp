package cn.itheima.gjp.web;

import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import cn.itheima.gjp.domain.Ledger;
import cn.itheima.gjp.service.LedgerService;
import cn.itheima.gjp.service.SortService;
import cn.itheima.gjp.view.AbstractOperationLedgerDialog;

public class AddLedger extends AbstractOperationLedgerDialog{
  
	 SortService  sortService = new SortService();
	 LedgerService ledgerService = new LedgerService();
	public AddLedger(JDialog dialog) {
		super(dialog);
		setTitle("添加账务");
		titleLabel.setText("添加账务");
		
	}
   /*
    * 级联功能
    */
	@Override
	public void changeParent() {
		// 获取  收/支 的选择
		String parent = parentBox.getSelectedItem().toString();
		
		// 情况1
		if(parent.equals("-请选择-")){//如果 parent 是请选择 那么 子分类也是请选择
			// sortBox 设置为请选择
			// 了解一下   
			sortBox.setModel(new DefaultComboBoxModel(new String[]{"-请选择-"}));
		}
		
		//情况二  选择的是  收入 或者 支出
		// 根据数据库查询  查询分类的具体内容
		if(parent.equals("收入")||parent.equals("支出")){
			//根据 父分类条件查询
			// 调用service层中 的querySnameByParent方法 
			try {
				List<Object> snameList = sortService.querySnameByParent(parent);
				snameList.add(0, "-请选择-");
				sortBox.setModel(new DefaultComboBoxModel(snameList.toArray()));
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
   /*
    * 确认按钮
    *    验证用户数据 (暂时下午说)
    *    
    *    获取用户填写信息 并封装到ledger对象中
    *    
    *    传递个service层 操作  
    */
	@Override
	public void confirm() {
		//获取父分类 
		String parent = parentBox.getSelectedItem().toString();
		//获取分类名称
		String sname = sortBox.getSelectedItem().toString();
		//获取账户 
		String account = accountTxt.getText();
		//获取创建时间 
		String createtime = createtimeTxt.getText();
		//获取金额
		String smoney = moneyTxt.getText();
		// 获取描述
		String ldesc = ldescTxt.getText();
		
		
		//验证父分类
		if(parent.equals("-请选择-")){
			JOptionPane.showMessageDialog(this, "请选择收入或支出 ");
			return;
		}
		//验证 分类名
		if(sname.equals("-请选择-")){
			JOptionPane.showMessageDialog(this, "请选择分类名称 ");
			return;
		}
		// 验证 账户不能为空  不能没有数据 
		if(account==null || account.isEmpty()){
			JOptionPane.showMessageDialog(this, "请填写账户 ");
			return;
		}
		double money = 0;
		// 金额 怎么是>0  怎么 是数字呢?
		// 使用异常抓取策略
		try{
			//money转换成 double
			money = Double.parseDouble(smoney);
		}catch(NumberFormatException e){
		    //出现异常 说明  我们的转换没有成功  
			JOptionPane.showMessageDialog(this, "必须填写数字 ");
			return;
		}
		
		if(money<=0){
			JOptionPane.showMessageDialog(this, "金额必须大于0 ");
			return;
		}
		
	
		
		//暂时没有做验证 验证下午做 
		Ledger ledger = new Ledger(0, parent, money, 0, sname, account, createtime, ldesc);
		
		try {
			ledgerService.addLedger(ledger);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//操作完关闭 界面
		this.dispose();
		
		JOptionPane.showMessageDialog(this, "添加成功");
		
	}

}
