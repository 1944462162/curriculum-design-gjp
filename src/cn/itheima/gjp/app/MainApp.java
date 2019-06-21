package cn.itheima.gjp.app;

import cn.itheima.gjp.web.MainJFrame;

/*
 * 程序的入口  
 *   这里就是负责整个项目开始的  
 *     里面包含了一个 main方法
*/
public class MainApp {
	//程序入口
   public static void main(String[] args) {
	   //创建 欢迎界面 
	   MainJFrame mj = new MainJFrame();
	   
	   // 设置可见
	   mj.setVisible(true);
   }
}
