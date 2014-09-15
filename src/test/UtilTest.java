package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.weixin.pojo.Articles;
import org.weixin.pojo.Button;
import org.weixin.pojo.CommonButton;
import org.weixin.pojo.ComplexButton;
import org.weixin.pojo.Menu;
import org.weixin.pojo.ViewButton;

import com.alibaba.fastjson.JSON;
import com.weixin.web.dao.WXmenuDao;
import com.weixin.web.dao.WxArticleDao;
import com.weixin.web.entity.WXmenu;
import com.weixin.web.entity.message.WxArticle;


/**
 * 单元测试util@test
 * @author zsx
 */
//告诉spring怎样执行
@RunWith(SpringJUnit4ClassRunner.class)
// 来标明是web应用测试
@WebAppConfiguration
// bean的配置文件路径，这个是Test类的classpath路径，
// 如果是Spring推荐的目录结构，应该在：项目目录/src/test/resources/里
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/applicationContext.xml" })
// 当然 你可以声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否
// @TransactionConfiguration(defaultRollback = true)
@TransactionConfiguration(defaultRollback = true)
// 记得要在XML文件中声明事务哦~~~我是采用注解的方式
@Transactional
public class UtilTest {

	@Autowired
	private WxArticleDao wxArticleDao;
	@Autowired
	private WXmenuDao wXmenuDao;
	
	@Test
	public void sync() throws IOException {
		String accountId = "0001";
		// 获取主菜单
		List<WXmenu> mainMenu = wXmenuDao
				.find("from WXmenu w where w.accountId = '" + accountId
						+ "' and w.wxMenu = null order by w.orders ");
		if (mainMenu.size() > 0) {
			// 创建按钮组合
			Button button[] = new Button[mainMenu.size()];
			for (int i = 0; i < mainMenu.size(); i++) {
				WXmenu xmenu = mainMenu.get(i);
				// 获取子菜单
				List<WXmenu> subMenu = wXmenuDao
						.find("from WXmenu w where w.wxMenu.id = '"
								+ xmenu.getId() + "' order by w.orders ");
				// 如果没有子菜单
				if (subMenu.size() == 0) {
					if ("click".equals(xmenu.getType())) {
						CommonButton cb = new CommonButton();
						cb.setName(xmenu.getName());
						cb.setType(xmenu.getType());
						cb.setKey(xmenu.getMenuKey());
						button[i] = cb;
					} else if ("view".equals(xmenu.getType())) {
						ViewButton viewButton = new ViewButton();
						viewButton.setName(xmenu.getName());
						viewButton.setType(xmenu.getType());
						viewButton.setUrl(xmenu.getUrl());
						button[i] = viewButton;
					}

				} else {
					// 有子菜单
					ComplexButton complexButton = new ComplexButton();
					complexButton.setName(xmenu.getName());

					Button[] subbutton = new Button[subMenu.size()];

					for (int j = 0; j < subMenu.size(); j++) {
						WXmenu xmenu2 = subMenu.get(j);

						if ("click".equals(xmenu2.getType())) {
							CommonButton cb2 = new CommonButton();
							cb2.setName(xmenu2.getName());
							cb2.setType(xmenu2.getType());
							cb2.setKey(xmenu2.getMenuKey());
							subbutton[j] = cb2;
						} else if ("view".equals(xmenu2.getType())) {
							ViewButton vb = new ViewButton();
							vb.setName(xmenu2.getName());
							vb.setType(xmenu2.getType());
							vb.setUrl(xmenu2.getUrl());
							subbutton[j] = vb;
						}
					}
					// 添加子菜单
					complexButton.setSub_button(subbutton);
					button[i] = complexButton;

				}
			}
			Menu menu = new Menu();
			menu.setButton(button);

			System.out.println(JSON.toJSONString(menu));

		} 
	}

	@Test
	public void uploadnews() {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("massid", "27958f44-6859-471d-a0d9-b9e5858be250");

		List<WxArticle> list = wxArticleDao.find(
				"from WxArticle w where w.wxMass.id = :massid order by w.sort",
				params);

		if (list.size() > 0) {
			List<Articles> articlesList = new ArrayList<Articles>();
			for (int i = 0; i < list.size(); i++) {
				WxArticle wxArticle = list.get(i);
				Articles articles = new Articles();
				//
				articles.setThumb_media_id("");
				articles.setAuthor(wxArticle.getAuthor());
				articles.setTitle(wxArticle.getTitle());
				articles.setContent_source_url(wxArticle.getContentSourceUrl());
				articles.setContent(wxArticle.getContent());
				articles.setDigest(wxArticle.getDigest());
				articles.setShow_cover_pic(wxArticle.getShowCoverPic());

				articlesList.add(articles);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("articles", articlesList);
			
			System.out.println(JSON.toJSONString(map));
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
