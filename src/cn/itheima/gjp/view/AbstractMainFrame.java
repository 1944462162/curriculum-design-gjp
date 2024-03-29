package cn.itheima.gjp.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.itheima.gjp.tools.GUITools;



public abstract class AbstractMainFrame extends JFrame {
	static {
    	try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 组件
	 */
	private JLabel titleLabel = new JLabel(new ImageIcon("gjp.jpg"));//标题
	private JButton ledgerBtn = new JButton("账务管理");//账务管理
	private JButton sortBtn = new JButton("分类管理");//分类管理
	
	public AbstractMainFrame() {
		this.init();// 初始化操作
		this.addComponent();// 添加组件
		this.addListener();// 添加监听器
	}
	
	// 初始化操作
	private void init() {
		this.setTitle("欢迎使用管家婆家庭记账软件");// 标题
		this.setSize(600, 400);// 窗体大小与位置
		GUITools.center(this);//设置窗口在屏幕上的位置
		this.setResizable(false);// 窗体大小固定
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗口默认操作
	}
	
	// 添加组件
	private void addComponent() {
		this.add(this.titleLabel, BorderLayout.NORTH);
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(null);
		this.add(btnPanel);
		
		ledgerBtn.setBounds(40, 20, 120, 50);
		sortBtn.setBounds(440, 20, 120, 50);
		
		Font font = new Font("华文彩云", Font.BOLD, 20);
		
		ledgerBtn.setFont(font);
		sortBtn.setFont(font);
		
		btnPanel.add(ledgerBtn);
		btnPanel.add(sortBtn);
	}

	// 添加监听器
	private void addListener() {
		ledgerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ledgerMng();
			}
		});
		sortBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sortMng();
			}
		});
	}
	
	public abstract void ledgerMng();
	public abstract void sortMng();
}
