package cn.itheima.gjp.web;

import java.security.Provider.Service;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cn.itheima.gjp.domain.Ledger;
import cn.itheima.gjp.service.LedgerService;
import cn.itheima.gjp.service.SortService;
import cn.itheima.gjp.view.AbstractLedgerMngDialog;

/*
 * 账务管理界面 
 */
public class LedgerMngDialog extends AbstractLedgerMngDialog {

	LedgerService ls = new LedgerService();

	/*
	 * 构造
	 */
	public LedgerMngDialog(JFrame frame) {
		super(frame);
		/*
		 * 查询所有账务信息 ,弹出窗口时即展示
		 */
		try {
			// 查询所有账务信息
			queryAllLedger();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * 查询并展示的功能
	 */
	public void queryAllLedger() throws SQLException {
		// 需要查询到所有的账务信息 并展示
		// LedgerService中 存在一个方法 queryAllLedger 完成了 查询所有账务信息的操作
		// 创建LedgerService对象

		// 查询所有账务
		List<Ledger> allLedger = ls.queryAllLedger();

		System.out.println(allLedger);

		// 所有的ledger对象设置到 下拉框中 ledgerDataTable
		// 将数据 设置到 ledgerDataTable 下拉框中
		// 其实 该类的父类 中有个方法 setTableModel(List<Ledger> ledgers)
		setTableModel(allLedger);

		// 还应在展示的时候 完成 总收入 总支出的计算

		// 求累加之和 遍历集合

		// 遍历集合 List<Ledger> allLedger
		// 遍历之前 定义两个变量 分贝存储 收入/支出 的累加结果
		double inMoney = 0; // 总收入 求和变量
		double payMoney = 0;// 总支出 求和变量

		for (int i = 0; i < allLedger.size(); i++) {
			// 获取每一个 Ledger对象
			Ledger ledger = allLedger.get(i);
			// 获取 该条账务信息的 父分类 要么是收入 要么是支出
			String parent = ledger.getParent();
			// 获取 该条账务信息的 金额
			double money = ledger.getMoney();

			// 判断 父分类 也就是判断该条账务信息 是收入还是支出

			if (parent.equals("收入")) {
				// 总收入 累加
				inMoney += money;
				// inMoney = money+inMoney;
			} else if (parent.equals("支出")) {
				// 总支出 累加
				payMoney += money;
			}
		}

		//

		System.out.println(inMoney);
		System.out.println(payMoney);

		// 总支出 总收入 求出来 的
		// 设置到 页面中
		// 总收入 和总支出 是标签
		inMoneyTotalLabel.setText("总收入:" + inMoney);

		payMoneyTotalLabel.setText("总支出:" + payMoney);

	}

	/*
	 * 添加账务信息
	 */
	@Override
	public void addLedger() {
		System.out.println("添加账务 信息");

		/*
		 * 弹出添加界面
		 */
		new AddLedger(this).setVisible(true);
	}

	/*
	 * 编辑 账务信息
	 */
	@Override
	public void editLedger() {
		System.out.println("编辑账务 信息");

		// 当点击编辑之后 要去检查 有没有选中 需要编辑的
		// 选中了才弹 没有选中 给提示 弹框提示
		// 获取被选中的行
		int row = ledgerDataTable.getSelectedRow();

		if (row < 0) {
			JOptionPane.showMessageDialog(this, "请选择要编辑的数据");
			return;
		}
		// 根据行号 可以获取对象
		// 父类中有个方法 getLedagerByTableRow
		Ledger ledger = getLedgerByTableRow(row);

		if (ledger == null) {
			JOptionPane.showMessageDialog(this, "您选择的是空行");
			return;
		}

		// 当验证通过了 就可以 弹出编辑对话框
		// 因为编辑的时候 需要将 数据传递给 编辑界面 所以 我们需要将 ledger对象 传递给
		// 编辑的界面上 也就是 窗体开启的时候 显示选择的数据
		new EditLedger(ledger, this).setVisible(true);

		// 编辑页面做完 刷新
		try {
			// 查询所有账务信息
			queryAllLedger();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * 删除账务 信息
	 */
	@Override
	public void deleteLedger() {
		System.out.println("删除账务 信息");

		// 当点击编辑之后 要去检查 有没有选中 需要编辑的
		// 选中了才弹 没有选中 给提示 弹框提示
		// 获取被选中的行
		int row = ledgerDataTable.getSelectedRow();

		if (row < 0) {
			JOptionPane.showMessageDialog(this, "请选择要编辑的数据");
			return;
		}
		// 根据行号 可以获取对象
		// 父类中有个方法 getLedagerByTableRow
		Ledger ledger = getLedgerByTableRow(row);

		if (ledger == null) {
			JOptionPane.showMessageDialog(this, "您选择的是空行");
			return;
		}

		// 确认删除框
		// 弹出确认框
		int key = JOptionPane.showConfirmDialog(this, "确认要删除吗");

		System.out.println(key);

		if (key != 0) { // 没有选择yes
			
			return; // 没有删除 该干嘛去
		}
		
		//r如果要删除 那么 就 要交给 service完成 
		
		try {
			ls.deletLedgerById(ledger.getLid());
			JOptionPane.showMessageDialog(this, "删除成功");
			//刷新界面
			queryAllLedger();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询账务的方法 根据选择的条件 完成 账务信息查询 时间 : 起始时间 结束时间 含头含尾 父分类: 收/支 分类名: 分类
	 */
	@Override
	public void queryLedger() {
		System.out.println("查询账务 信息");

		// 1:获取页面的参数数据
		// 时间
		// 起始时间
		String beginTime = beginDateTxt.getText();
		// 结束时间
		String endTime = endDateTxt.getText();

		// 父分类
		String parent = parentBox.getSelectedItem().toString();
		// 分类名称
		String sname = sortBox.getSelectedItem().toString();

		// 将这些参数 传递 service

		try {
			List<Ledger> allLedger = ls.queryLedger(beginTime, endTime, parent, sname);

			// 设置到表格
			setTableModel(allLedger);

			// 遍历集合 List<Ledger> allLedger
			// 遍历之前 定义两个变量 分贝存储 收入/支出 的累加结果
			double inMoney = 0; // 总收入 求和变量
			double payMoney = 0;// 总支出 求和变量

			for (int i = 0; i < allLedger.size(); i++) {
				// 获取每一个 Ledger对象
				Ledger ledger = allLedger.get(i);
				// 获取 该条账务信息的 父分类 要么是收入 要么是支出
				String parentname = ledger.getParent();
				// 获取 该条账务信息的 金额
				double money = ledger.getMoney();

				// 判断 父分类 也就是判断该条账务信息 是收入还是支出

				if (parentname.equals("收入")) {
					// 总收入 累加
					inMoney += money;
					// inMoney = money+inMoney;
				} else if (parentname.equals("支出")) {
					// 总支出 累加
					payMoney += money;
				}
			}

			//

			System.out.println(inMoney);
			System.out.println(payMoney);

			// 总支出 总收入 求出来 的
			// 设置到 页面中
			// 总收入 和总支出 是标签
			inMoneyTotalLabel.setText("当前总收入:" + inMoney);

			payMoneyTotalLabel.setText("当前总支出:" + payMoney);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 创建一个 SortService对象
	SortService sortService = new SortService();

	/*
	 * 关于下拉框的 级联操作 根据父分类选择的变化, 去查询对应的sort表 中数据 以作显示
	 */
	@Override
	public void parentChange() {
		System.out.println("级联操作");

		// 1:获取 选择的内容
		// 父分类下拉列表 被选择的内容
		// parentBox 有个方法 getSelectedItem().toString()
		String parentName = parentBox.getSelectedItem().toString();

		// 2:根据 选择的内容 完成 对应的显示
		// 情况1 没选 也就是 -请选择-
		if (parentName.equals("-请选择-")) {
			// 那么 小分类下拉列表中 就是 -请选择-
			// 往 小分类下拉列表中的操作 有个方法 setBoxModel
			// 这个方法要的是 List集合

			// 创建一个空集合
			ArrayList<Object> list = new ArrayList<Object>();
			// 添加 -请选择-
			list.add("-请选择-");
			setBoxModel(list);
		} else if (parentName.equals("收入/支出")) {
			// 情况二 选择全部

			try {
				// 需要service 完成功能 获取所有的分类名
				List<Object> queryAllSname = sortService.queryAllSname();
				// 现在应该在集合的第一个位置添加一个 "-请选择-"字符串
				// List集合中 有个而方法 add(int index,Object obj)
				// 往指定位置进行添加
				queryAllSname.add(0, "-请选择-");

				// 获取到所有名字了 显示到下列框中

				setBoxModel(queryAllSname);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			// 情况三 选择了 收入 或者 支出 二选一

			try {
				// 才需要 service 完成功能 根据父分类的选择 parentName 进行制定父分类对应小分类
				List<Object> querySnameByParent = sortService.querySnameByParent(parentName);

				// 在第一个位置 添加 -请选择-
				querySnameByParent.add(0, "-请选择-");

				// 获取到所有名字了 在下拉框中显示
				setBoxModel(querySnameByParent);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
