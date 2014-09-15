package com.weixin.web.service.impl;

import myUtil.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weixin.web.dao.ApplyarticlesversionDao;
import com.weixin.web.dao.ArticlesapplyDao;
import com.weixin.web.dao.CurrentstatusDao;
import com.weixin.web.dao.MessageDao;
import com.weixin.web.dao.UserDao;
import com.weixin.web.entity.Articlesapply;
import com.weixin.web.entity.Currentstatus;
import com.weixin.web.entity.Message;
import com.weixin.web.entity.Tuser;
import com.weixin.web.service.InitServiceI;



@Service
@Transactional
public class InitServiceImpl implements InitServiceI {

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageDao messageDao;

	@Autowired
	private ArticlesapplyDao articlesapplyDao;

	@Autowired
	private CurrentstatusDao currentstatusDao;

	@Autowired
	private ApplyarticlesversionDao applyarticlesversionDao;

	@Override
	synchronized public void init() {

		initCurrentstatus();// 初始化审批状态

		initTuser();// 初始化用户

		initMessage();// 初始化消息

		initArticlesapply();// 初始化审批流程

	}

	private void initCurrentstatus() {
		Currentstatus cs = new Currentstatus();
		cs.setId(0);
		cs.setCurrentStatus("待审批");
		currentstatusDao.save(cs);

		Currentstatus cs1 = new Currentstatus();
		cs1.setId(1);
		cs1.setCurrentStatus("审批中");
		currentstatusDao.save(cs1);
		
		Currentstatus cs2 = new Currentstatus();
		cs2.setId(2);
		cs2.setCurrentStatus("已审批");
		currentstatusDao.save(cs2);
		
		Currentstatus cs3 = new Currentstatus();
		cs3.setId(3);
		cs3.setCurrentStatus("驳回");
		currentstatusDao.save(cs3);
	}

	private void initTuser() {
		Tuser tuser = new Tuser();
		tuser.setId("0003");
		tuser.setUserName("xue");
		tuser.setPassWord("123");
		tuser.setUserNumber("0003");
		tuser.setCreateTime(DateUtil.timeFormat());
		tuser.setIsCancel("0");
		tuser.setTuser(null);
		userDao.save(tuser);
		
		Tuser tuser1 = new Tuser();
		tuser1.setId("0002");
		tuser1.setUserName("shu");
		tuser1.setPassWord("123");
		tuser1.setUserNumber("0002");
		tuser1.setCreateTime(DateUtil.timeFormat());
		tuser1.setIsCancel("0");
		tuser1.setTuser(tuser);
		userDao.save(tuser1);
		
		Tuser tuser2 = new Tuser();
		tuser2.setId("0001");
		tuser2.setUserName("zhao");
		tuser2.setPassWord("123");
		tuser2.setUserNumber("0001");
		tuser2.setCreateTime(DateUtil.timeFormat());
		tuser2.setIsCancel("0");
		tuser2.setTuser(tuser1);
		userDao.save(tuser2);
		
	}

	private void initMessage() {
		Message message = new Message();
		message.setId("1001");
		message.setTitle("消息标题");
		message.setContent("消息内容");
		message.setMsgType("消息类型");
		message.setCreateTime(DateUtil.timeFormat());
		
		messageDao.save(message);
		
		Message message1 = new Message();
		message1.setId("1002");
		message1.setTitle("消息标题二");
		message1.setContent("消息内容二");
		message1.setMsgType("消息类型二");
		message1.setCreateTime(DateUtil.timeFormat());
		
		messageDao.save(message1);
	}

	private void initArticlesapply() {
		Articlesapply articlesapply = new Articlesapply();
		articlesapply.setId("9999");
		articlesapply.setMessage(messageDao.get(Message.class, "1001"));
		articlesapply.setApplyUser("0001");
		articlesapply.setApplyReason("理由");
		articlesapply.setApplyTime(DateUtil.timeFormat());
		articlesapply.setCurrentstatus(currentstatusDao.get(Currentstatus.class, 0));
		articlesapply.setOwner("0001");
		
		articlesapplyDao.save(articlesapply);
		
		Articlesapply articlesapply1 = new Articlesapply();
		articlesapply1.setId("8888");
		articlesapply1.setMessage(messageDao.get(Message.class, "1002"));
		articlesapply1.setApplyUser("0001");
		articlesapply1.setApplyReason("理由");
		articlesapply1.setApplyTime(DateUtil.timeFormat());
		articlesapply1.setCurrentstatus(currentstatusDao.get(Currentstatus.class, 0));
		articlesapply1.setOwner("0001");
		
		articlesapplyDao.save(articlesapply1);
	}

	
}
