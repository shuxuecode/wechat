package com.weixin.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import myUtil.DateUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.xml.internal.utils.SuballocatedByteVector;
import com.weixin.core.util.JsonUtil;
import com.weixin.core.util.MD5Util;
import com.weixin.web.dao.TprivilegeDao;
import com.weixin.web.dao.TroleDao;
import com.weixin.web.dao.TrolePrivilegeDao;
import com.weixin.web.dao.TuserRoleDao;
import com.weixin.web.dao.UserDao;
import com.weixin.web.dao.WXmenuDao;
import com.weixin.web.entity.Tprivilege;
import com.weixin.web.entity.Trole;
import com.weixin.web.entity.TrolePrivilege;
import com.weixin.web.entity.Tuser;
import com.weixin.web.entity.TuserRole;
import com.weixin.web.entity.WXmenu;
import com.weixin.web.model.Client;
import com.weixin.web.model.Tree;
import com.weixin.web.model.User;
import com.weixin.web.model.json.JsonModel;
import com.weixin.web.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private TroleDao roleDao;
	@Autowired
	private TuserRoleDao userRoleDao;
	@Autowired
	private TrolePrivilegeDao rolePrivilegeDao;
	@Autowired
	private TprivilegeDao privilegeDao;

	public User login(User user) {
		User reUser = new User();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", user.getUserName());
		params.put("passWord", user.getPassWord());

		Tuser tuser = userDao
				.get(
						"from Tuser t where t.userName = :userName and t.passWord = :passWord",
						params);
		if (tuser != null) {
			BeanUtils.copyProperties(tuser, reUser);
			return reUser;
		} else {
			return null;
		}

	}
	
	public JsonModel checkUsernameIsExist(String name){
		JsonModel json = new JsonModel();
		Tuser user = userDao.get("from Tuser t where t.userName = '" + name.trim() + "'");
		if (user == null) {
			json.setSuccess(true);
			json.setMsg("用户名不存在");
		}else {
			json.setSuccess(false);
			json.setMsg("用户名已经存在");
		}
		return json;
	}

	public List<User> getAllUser() {
		List<User> uList = new ArrayList<User>();

		List<Tuser> tuList = userDao.find("from Tuser t");
		if (tuList.size() > 0) {
			for (Tuser tuser : tuList) {
				User user = new User();
				BeanUtils.copyProperties(tuser, user);
				if (tuser.getTuser() != null) {
					user.set_parentId(tuser.getTuser().getId());
					user.set_parentName(tuser.getTuser().getUserName());
				}
				user.setPassWord(null);//防止管理员通过url看到密码
				Set<TuserRole> set = tuser.getTuserRoles();
				if (set.size() > 0) {
					StringBuffer roleId = new StringBuffer();
					StringBuffer roleName = new StringBuffer();
					for (TuserRole tuserRole : set) {
						Trole role = tuserRole.getTrole();
						roleId.append(role.getId());
						roleId.append(",");
						roleName.append(role.getRoleName());
						roleName.append(",");
					}
					user.setRoleIds(roleId.substring(0, roleId.length() - 1));
					user.setRoleNames(roleName.substring(0,
							roleName.length() - 1));
				}
				uList.add(user);
			}
		}

		return uList;

	}

	@Override
	public List<String> getUserPrivileges(String userId) {
		List<String> urlList = new ArrayList<String>();

		Tuser tuser = userDao.get(Tuser.class, userId);
		if (tuser != null) {
			Set<TuserRole> set1 = tuser.getTuserRoles();
			if (set1 != null && !set1.isEmpty()) {
				for (TuserRole tuserRole : set1) {
					Set<TrolePrivilege> set2 = tuserRole.getTrole()
							.getTrolePrivileges();
					if (set2 != null && !set2.isEmpty()) {
						for (TrolePrivilege trolePrivilege : set2) {
							urlList
									.add(trolePrivilege.getTprivilege()
											.getUrl());
						}
					}
				}
			}

		}

		return urlList;
	}

	@Override
	public List<Trole> getAllRole() {
		return roleDao.find("from Trole r order by r.roleNumber desc");
	}
	
	public List<Tree> getUserRoleByUserId(String userId){
		List<Tree> treeList = new ArrayList<Tree>();
		
		List<Trole> role = roleDao.find("from Trole r order by r.roleNumber desc");
		List<TuserRole> userRole = userRoleDao.find("from TuserRole t where t.tuser.id = '" + userId + "'");
		List<String> userRoleIds = new ArrayList<String>();
		if (userRole.size() > 0) {
			for (TuserRole tuserRole : userRole) {
				userRoleIds.add(tuserRole.getTrole().getId());
			}
		}
		if (role != null && role.size() > 0) {
			for (Trole trole : role) {
				Tree tree = new Tree();
				tree.setId(trole.getId());
				tree.setText(trole.getRoleName());
				//如果有此角色则选中
				if (userRoleIds.contains(trole.getId())) {
					tree.setChecked(true);
				}
				treeList.add(tree);
			}
			return treeList;
		}
		return null;
	}
	
	@Override
	public JsonModel updateUserRole(String userId, String roleIds){
		JsonModel jModel = new JsonModel();
		try {
			Tuser user = userDao.get(Tuser.class, userId);
			List<TuserRole> list = userRoleDao.find("from TuserRole t where t.tuser.id = '" + userId + "'");
			if (list.size() > 0) {
				for (TuserRole tuserRole : list) {
					userRoleDao.delete(tuserRole);
				}
			}
			String[] roleids = roleIds.split(",");
			for (int i = 0; i < roleids.length; i++) {
				Trole trole = roleDao.get(Trole.class, roleids[i]);
				if (trole != null) {
					TuserRole userRole = new TuserRole();
					userRole.setId(UUID.randomUUID().toString());
					userRole.setTrole(trole);
					userRole.setTuser(user);
					
					userRoleDao.save(userRole);
				}
			}
			jModel.setSuccess(true);
			jModel.setMsg("修改成功");
		} catch (Exception e) {
			jModel.setSuccess(false);
			jModel.setMsg("修改失败");
		}
		return jModel;
	}
	
	@Override
	public JsonModel addrole(String name, String num){
		JsonModel jModel = new JsonModel();
		try {
			Trole role = new Trole();
			role.setId(UUID.randomUUID().toString());
			role.setRoleName(name);
			role.setRoleNumber(num);
			
			roleDao.save(role);
			jModel.setSuccess(true);
			jModel.setMsg("新增成功");
		} catch (Exception e) {
			jModel.setSuccess(false);
			jModel.setMsg("新增失败");
		}
		return jModel;
	}
	
	@Override
	public JsonModel updaterole(String id, String name, String num){
		JsonModel jModel = new JsonModel();
		Trole role = roleDao.get(Trole.class, id);
		if (role != null) {
			role.setRoleName(name);
			role.setRoleNumber(num);
			
			roleDao.update(role);
			jModel.setSuccess(true);
			jModel.setMsg("修改成功");
		}else {
			jModel.setSuccess(false);
			jModel.setMsg("修改失败");
		}
		return jModel;
	}
	
	@Override
	public JsonModel deleterole(String id){
		JsonModel jModel = new JsonModel();
		Trole role = roleDao.get(Trole.class, id);
		if (role != null) {
			List<TrolePrivilege> list = rolePrivilegeDao.find("from TrolePrivilege t where t.trole.id = '" + id + "'");
			if (list != null && list.size() > 0) {
				for (TrolePrivilege trolePrivilege : list) {
					rolePrivilegeDao.delete(trolePrivilege);
				}
			}
			roleDao.delete(role);
			jModel.setSuccess(true);
			jModel.setMsg("删除成功");
		}else {
			jModel.setSuccess(false);
			jModel.setMsg("删除失败");
		}
		return jModel;
	}

	@Override
	public List<Tree> getAllPrivilegeTree() {
		List<Tree> listTree = new ArrayList<Tree>();

		List<Tprivilege> privilegeList = privilegeDao.find("from Tprivilege");
		if (privilegeList.size() > 0) {
			for (Tprivilege tprivilege : privilegeList) {
				//如果是父节点
				if (tprivilege.getTprivilege() == null) {
					Tree parentTree = new Tree();
					parentTree.setId(tprivilege.getId());
					parentTree.setText(tprivilege.getName());
					parentTree.setIconCls(tprivilege.getUrl());//暂时用icon字段代替url
					
					//获取该节点下的子节点
					List<Tree> subTree = new ArrayList<Tree>();
					List<Tprivilege> sublist = privilegeDao
							.find("from Tprivilege t where t.tprivilege.id = '"
									+ tprivilege.getId() + "'");
					if (sublist.size() > 0) {
						for (Tprivilege tprivilege2 : sublist) {

							Tree subTree2 = new Tree();
							subTree2.setId(tprivilege2.getId());
							subTree2.setText(tprivilege2.getName());
							subTree2.setIconCls(tprivilege2.getUrl());//暂时用icon字段代替url
							subTree2.setState(null);//防止再次分支

							subTree.add(subTree2);
						}
					}

					parentTree.setChildren(subTree);
					

					listTree.add(parentTree);
				}
			}
		}
		return listTree;
	}

	@Override
	public List<Tree> getPrivilegeTreeByRoleId(String roleId) {
		
		List<TrolePrivilege> rpList = rolePrivilegeDao.find("from TrolePrivilege t where t.trole.id = '" + roleId + "'");
		if (rpList.size() > 0) {
			//获取角色的权限列表
			List<String> rolePrivileges = new ArrayList<String>();
			for (TrolePrivilege privilege : rpList) {
				rolePrivileges.add(privilege.getTprivilege().getId());
			}
			
			List<Tree> listTree = new ArrayList<Tree>();
			List<Tprivilege> privilegeList = privilegeDao.find("from Tprivilege");
			if (privilegeList.size() > 0) {
				for (Tprivilege tprivilege : privilegeList) {
					//如果是父节点
					if (tprivilege.getTprivilege() == null) {
						Tree parentTree = new Tree();
						parentTree.setId(tprivilege.getId());
						parentTree.setText(tprivilege.getName());
						
						//获取该节点下的子节点
						List<Tree> subTree = new ArrayList<Tree>();
						List<Tprivilege> sublist = privilegeDao
								.find("from Tprivilege t where t.tprivilege.id = '"
										+ tprivilege.getId() + "'");
						if (sublist.size() > 0) {
							for (Tprivilege tprivilege2 : sublist) {

								Tree subTree2 = new Tree();
								subTree2.setId(tprivilege2.getId());
								subTree2.setText(tprivilege2.getName());
								subTree2.setState(null);//防止再次分支
								//判断是否有此权限，有则选中
								if (rolePrivileges.contains(tprivilege2.getId())) {
									subTree2.setChecked(true);
								}

								subTree.add(subTree2);
							}
						}
						parentTree.setChildren(subTree);

						listTree.add(parentTree);
					}
				}
			}
			return listTree;
		}else {
			return getAllPrivilegeTree();
		}		
	}

	@Override
	public JsonModel updateRolePrivileges(String roleId, String ids) {
		JsonModel jModel = new JsonModel();
		try {
			List<TrolePrivilege> list = rolePrivilegeDao.find("from TrolePrivilege t where t.trole.id = '" + roleId + "'");
			if (list != null && list.size() > 0) {
				for (TrolePrivilege trolePrivilege : list) {
					rolePrivilegeDao.delete(trolePrivilege);
				}
			}
			String[] privilegeIds = ids.split(",");
			//
			Set<Tprivilege> setParentPrivilege = new HashSet<Tprivilege>();
			Trole role = roleDao.get(Trole.class, roleId);
			for (int i = 0; i < privilegeIds.length; i++) {
				
				Tprivilege tprivilege = privilegeDao.get(Tprivilege.class, privilegeIds[i]);
				//获取父权限，并用set去重
				if (tprivilege.getTprivilege() != null) {
					setParentPrivilege.add(tprivilege.getTprivilege());
				}
				
				TrolePrivilege trolePrivilege = new TrolePrivilege();
				trolePrivilege.setId(UUID.randomUUID().toString());
				trolePrivilege.setTrole(role);
				trolePrivilege.setTprivilege(tprivilege);
				
				rolePrivilegeDao.save(trolePrivilege);
			}
			if (setParentPrivilege.size() > 0) {
				for (Tprivilege tprivilege : setParentPrivilege) {
					TrolePrivilege trp = new TrolePrivilege();
					trp.setId(UUID.randomUUID().toString());
					trp.setTrole(role);
					trp.setTprivilege(tprivilege);
					
					rolePrivilegeDao.save(trp);
				}
			}
			jModel.setSuccess(true);
			jModel.setMsg("保存成功");
		} catch (Exception e) {
			jModel.setSuccess(false);
			jModel.setMsg("保存失败");
		}
		return jModel;
	}

	@Override
	public List<Tree> getPrivilegeParentTree() {
		List<Tree> listTree = new ArrayList<Tree>();
		List<Tprivilege> list = privilegeDao.find("from Tprivilege t where t.tprivilege is null ");
		if (list.size() > 0) {
			for (Tprivilege tprivilege : list) {
				Tree tree = new Tree();
				tree.setId(tprivilege.getId());
				tree.setText(tprivilege.getName());
				
				listTree.add(tree);
			}
			return listTree;
		}else {
			return null;
		}
	}

	@Override
	public JsonModel addprivilege(String name, String url, String pid) {
		JsonModel json = new JsonModel(); 
		try {
			Tprivilege tp = new Tprivilege();
			tp.setId(UUID.randomUUID().toString());
			tp.setName(name);
			tp.setUrl(url);
			Tprivilege tprivilege = privilegeDao.get(Tprivilege.class, pid);
			if (tprivilege != null) {
				tp.setTprivilege(tprivilege);
			}
			tp.setCreateTime(DateUtil.timeFormat());
			
			privilegeDao.save(tp);
			json.setSuccess(true);
			json.setMsg("添加成功");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("添加失败");
		}
		return json;
	}

	@Override
	public JsonModel deleteprivilege(String id) {
		JsonModel json = new JsonModel(); 
		try {
			Tprivilege tp = privilegeDao.get(Tprivilege.class, id);
			if (tp != null) {
				if (tp.getTprivilege() == null) {
					List<Tprivilege> list = privilegeDao.find("from Tprivilege t where t.tprivilege.id = '" + id + "'");
					if (list.size() > 0) {
						for (Tprivilege tprivilege : list) {
							privilegeDao.delete(tprivilege);
						}
					}
				}
				privilegeDao.delete(tp);
				json.setSuccess(true);
				json.setMsg("删除成功");
			}else{
				json.setSuccess(false);
				json.setMsg("删除失败");
			}
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("删除失败");
		}
		return json;
	}

	@Override
	public JsonModel updateprivilege(String id, String name, String url,
			String pid) {
		JsonModel json = new JsonModel(); 
		try {
			Tprivilege tp = privilegeDao.get(Tprivilege.class, id);
			if (tp != null) {
				tp.setName(name);
				tp.setUrl(url);
				Tprivilege tprivilege = privilegeDao.get(Tprivilege.class, pid);
				if (tprivilege != null) {
					tp.setTprivilege(tprivilege);
				}else {
					tp.setTprivilege(null);
				}
				
				privilegeDao.update(tp);
				json.setSuccess(true);
				json.setMsg("修改成功");
			}else {
				json.setSuccess(false);
				json.setMsg("修改失败");
			}
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("修改失败");
		}
		return json;
	}
}
