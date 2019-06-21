package cn.itheima.gjp.web;

import cn.itheima.gjp.view.AbstractMainFrame;

public class MainJFrame extends AbstractMainFrame{
   
	/**
	 * 点击 账务操作 出来的效果
	 */
	@Override
	public void ledgerMng() {
		// TODO Auto-generated method stub
		System.out.println("账务操作");
		
		//弹出 账务管理对话框
		new LedgerMngDialog(this).setVisible(true);
	}
    /**
     * 点击分类 出来的效果  今天我们做的是分类 
     */
	@Override
	public void sortMng() {
		// TODO Auto-generated method stub
		System.out.println("分类操作");
		
		new SortMngDialog(this).setVisible(true);
		// 从当前的界面弹出 分类管理对话框
		
	}

}
