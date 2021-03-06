package org.wechat.menu.test;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wechat.common.entity.results.JsonResult;
import org.wechat.menu.entity.BasicMenu;
import org.wechat.menu.entity.ClickMenu;
import org.wechat.menu.entity.GroupButton;
import org.wechat.menu.entity.Menu;
import org.wechat.menu.entity.ReturnMenu;
import org.wechat.menu.entity.ViewMenu;
import org.wechat.menu.entity.WaitMsg;
import org.wechat.menu.utils.MenuConnection;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 创建菜单
 * @author Andy
 *
 */
public class TestMenuConnection {
	private MenuConnection menuConn;

	@Before
	public void init(){
		menuConn = new MenuConnection();
	}
	/**
	 * 测试创建自定义菜单
	 * (测试成功)
	 */
	@Test
	public void testCustomMenu(){
	
		ClickMenu click01 = new ClickMenu();
		click01.setName("我要洗衣");
		click01.setKey("key_01");
		
		ViewMenu viewMenu = new ViewMenu();
		viewMenu.setName("价格查询");
		viewMenu.setUrl("http://www.baidu.com");
		
		ViewMenu viewMenu1 = new ViewMenu();
		viewMenu1.setName("免费取送范围");
		viewMenu1.setUrl("http://www.baidu.com");
		
		
		ViewMenu viewMenu2 = new ViewMenu();
		viewMenu2.setName("代收点分布");
		viewMenu2.setUrl("http://www.baidu.com");

		
		GroupButton group = new GroupButton();
		group.setName("我要洗衣");
		group.setSub_button(new BasicMenu[]{click01,viewMenu,viewMenu1,viewMenu2});
		
		ClickMenu click03 = new ClickMenu();
		click03.setName("推荐有礼");
		click03.setKey("key_02");

		ClickMenu click04 = new ClickMenu();
		click04.setName("关注有礼");
		click04.setKey("key_03");
		
		GroupButton group1 = new GroupButton();
		group1.setName("优惠活动");
		group1.setSub_button(new BasicMenu[]{click03,click04});
		
		
		ClickMenu click05 = new ClickMenu();
		click05.setName("关于三洋");
		click05.setKey("key_04");

		ClickMenu click06 = new ClickMenu();
		click06.setName("洗衣须知");
		click06.setKey("key_05");
		
		GroupButton group2 = new GroupButton();
		group2.setName("服务中心");
		group2.setSub_button(new BasicMenu[]{click05,click06});
		
		org.wechat.menu.entity.Menu menu = new org.wechat.menu.entity.Menu();
		menu.setButton(new BasicMenu[]{group,group1,group2});	//自定义菜单
		
		JsonResult result=menuConn.createMenu(menu, "vWY0B40yT7mA-o_VrQ0CGTjQei1_bAKxHTYO_VjwsgP2KUmlSwM5ZMuvTui2pgssbe6cHafGWCJ9N3aseywgBLheYG9SqTtiGwa9TONsCso");
		Assert.assertNotNull(result);
		System.out.println("errorCode-->"+result.getErrcode());
		System.out.println("errorMsg-->"+result.getErrmsg());
	}
	
	@Test
	public void createMenu() {
		MenuConnection conn = new MenuConnection();
		String token = "Tu3y7pp0oHHhlNYdtHh4VXwtQhq9e1IjILpthI4B-jJUds7nIT1mHRNLeIrr51hA8ASgmJPfuZRuzlpv6vW4iP8GxTYypQTbegeb6l5TxFI";
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("access_token", token);
		String jsonData = "{'button':[{'name':'超值预定','sub_button':[{'key':'rselfmenu_1_2','name':'查询订单','type':'pic_sysphoto','sub_button':[]},{'name':'','type':'url','url':'http://www.baidu.com'}]},{'name':'点我有礼','sub_button':[{'key':'优惠劵','name':'我的优惠劵','type':'click'},{'key':'游记推荐1','name':'游记推荐','type':'click'}]}]}";
		String result = conn.HttpsDefaultExecute("POST", "https://api.weixin.qq.com/cgi-bin/menu/create", map, jsonData);
		System.out.println(result);
	}
	
	
	/**
	 * 测试获取自定义菜单
	 */
	@Test
	public void testGetMenu(){
		String jsonReuslt = menuConn.getMenu("dtuE6axQ3vnplJQM1Wv0idwA9aIyDQBCaeJndH-CanelTMBph1zlE16451j-94q5I2XSwLBHhwDX9ymcbH0Wwngi7UvF_DsNbTUZwpwNY3Y");
		Menu menu = new Menu();
	    List<ReturnMenu> list = new ArrayList<ReturnMenu>();
		if(jsonReuslt!=null&&!"".equals(jsonReuslt)){
			JSONObject object = JSONObject.parseObject(jsonReuslt);
			JSONArray array  = object.getJSONObject("menu").getJSONArray("button");
			for(int i=0;i<array.size();i++){
				ReturnMenu group = new ReturnMenu();
				group = array.getObject(i, ReturnMenu.class);
				list.add(group);
			}
		}
	}
	
	/**
	 * 测试创建菜单
	 */
	@Test
	public void testMenu(){
		WaitMsg msg = new WaitMsg();
		msg.setName("");
		msg.setKey("");
		String result = JSONObject.toJSON(msg).toString();
		System.out.println(result);
	}
	
	/**
	 * 获取自定义菜单
	 */
	@Test
	public void getCustomMenu(){
		String result = menuConn.getMenu("taKBkSX4WIZ6L4roALW-3rss6XARafa32_grA2qdUuNHA1nRp6GyWpsNwbHdqWOUM8mcCf30VPrEWlz8IH10bBOvOTgexU4VVZ7sb6orMH8");
		assertNotNull(result);
		System.out.println(result);
	}
	
	/**
	 * 测试连接查询数据库
	 */
	@Test
	public void testConnjdbc(){
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://127.0.0.1:3306/mall";
			String user = "root";
			String password = "1234567";
			String sql = "";
			try {
				Class.forName(driver);
				Connection conn = DriverManager.getConnection(url, user, password);
				if(!conn.isClosed()){
					PreparedStatement statement = conn.prepareStatement(sql);
					ResultSet set = statement.executeQuery();
					while(set.next()){
						System.out.println(set.getString(""));
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
