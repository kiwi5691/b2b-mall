package com.b2b.mall.common.service.Impl;


import com.b2b.mall.common.service.UserService;
import com.b2b.mall.common.util.BaseHTMLStringCase;
import com.b2b.mall.common.util.DateUtil;
import com.b2b.mall.db.entity.PermissionVO;
import com.b2b.mall.db.entity.RoleVO;
import com.b2b.mall.common.service.AuthService;
import com.b2b.mall.db.mapper.PermissionMapper;
import com.b2b.mall.db.mapper.RoleMapper;
import com.b2b.mall.db.mapper.RolePermissionKeyMapper;
import com.b2b.mall.db.mapper.UserMapper;
import com.b2b.mall.db.model.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author kiwi
 */
@Service
public class AuthServiceImpl implements AuthService {
	private static final Logger logger = LoggerFactory
			.getLogger(AuthServiceImpl.class);

	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private RoleMapper roleMapper;


	@Autowired
	private HttpSession httpSession;

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RolePermissionKeyMapper rolePermissionMapper;

	@Override
	public int addPermission(Permission permission) {
		return this.permissionMapper.insert(permission);
	}

	@Override
	public List<Permission> permList() {
		return this.permissionMapper.findAll();
	}

	@Override
	public void permissionEditGet(Model model, Permission permission) {
		if(permission.getId() != 0){
			Permission permission0 = permissionMapper.selectByPrimaryKey(permission.getId());
			model.addAttribute("permission",permission0);
		}
	}

	@Override
	public void userManageGet(Model model) {
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		User user1 = userMapper.selectByNameAndPwd(user);
		model.addAttribute("user", user1);
	}

	@Override
	public void userManagePost(Model model, User user, HttpSession httpSession) {
		Date date = new Date();
		user.setUpdateDate(date);
		int i = userMapper.update(user);
		httpSession.setAttribute("user",user);
	}

	@Override
	public void managerManangementGet(Model model) {
		List<User> userList = null;
		userList = userMapper.selectAll();
		userList.forEach(user -> {
			user.setAddDateStr(DateUtil.preciseDate(user.getAddDate()));
			user.setUpDateStr(DateUtil.preciseDate(user.getUpdateDate()));
			user.setStateStr(BaseHTMLStringCase.lockCheck(String.valueOf(user.getState())));
		});
		model.addAttribute("userList",userList);
	}


	@Override
	public void updatePerm(Permission permission) {
		 this.permissionMapper.updateByPrimaryKeySelective(permission);
	}

	@Override
	public void managerEdit(Model model, User user) {

		User user1=null;
		user1=userMapper.selectById(user.getUserName());
		user1.setAddDateStr(DateUtil.preciseDate(user1.getAddDate()));
		user1.setUpDateStr(DateUtil.preciseDate(user1.getUpdateDate()));
		user1.setStateStr(BaseHTMLStringCase.lockCheck(String.valueOf(user1.getState())));
		model.addAttribute("user",user1);

	}

	@Override
	public void userSearchGet(Model model) {

		List<Permission> permissionList = null;
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();

		logger.info("id is"+user.getId());
		List<Role> roles = this.getRoleByUser(user.getId());

		if (null != roles && roles.size() > 0) {
			for (Role role : roles) {
				logger.info("role is"+ role.getCode());
				httpSession.setAttribute("role", role.getRoleName());
				permissionList = this.findAllPermsByRoleId(role
						.getId());
			}
		}

		model.addAttribute("permissionList", permissionList);
	}

	@Override
	public Permission getPermission(int id) {
		return this.permissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public String delPermission(int id) {
		//查看该权限是否有子节点，如果有，先删除子节点
		List<Permission> childPerm = this.permissionMapper.findChildPerm(id);
		if(null != childPerm && childPerm.size()>0){
			return "删除失败，请您先删除该权限的子节点";
		}
		if(this.permissionMapper.deleteByPrimaryKey(id)>0){
			return "ok";
		}else{
			return "删除失败，请您稍后再试";
		}
	}

	@Override
	public List<Role> roleList() {
		return this.roleMapper.findList();
	}

	@Override public List<PermissionVO> findPerms() {
		return this.permissionMapper.findPerms();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
	public String addRole(Role role, String permIds) {
		this.roleMapper.insert(role);
		int roleId=role.getId();
		String[] arrays=permIds.split(",");
		logger.debug("权限id =arrays="+arrays.toString());
		setRolePerms(roleId, arrays);
		return "ok";
	}

	@Override public RoleVO findRoleAndPerms(Integer id) {
		return this.roleMapper.findRoleAndPerms(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
	public String updateRole(Role role, String permIds) {
		int roleId=role.getId();
		String[] arrays=permIds.split(",");
		logger.debug("权限id =arrays="+arrays.toString());
		//1，更新角色表数据；
		int num=this.roleMapper.updateByPrimaryKeySelective(role);
		if(num<1){
			//事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "操作失败";
		}
		//2，删除原角色权限；
		batchDelRolePerms(roleId);
		//3，添加新的角色权限数据；
		setRolePerms(roleId, arrays);
		return "ok";
	}



	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
	public String delRole(int id) {
		//1.删除角色对应的权限
		batchDelRolePerms(id);
		//2.删除角色
		int num=this.roleMapper.deleteByPrimaryKey(id);
		if(num<1){
			//事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "操作失败";
		}
		return "ok";
	}

	@Override
	public List<Role> getRoles() {
		//TODO 根据部门和权限等级限制角色显示
		return this.roleMapper.getRoles();
	}

	@Override
	public List<Role> getRoleByUser(Integer userId) {
		return this.roleMapper.getRoleByUserId(userId);
	}

	@Override
	public List<Permission> findPermsByRoleId(Integer id) {
		return this.permissionMapper.findPermsByRole(id);
	}

	@Override
	public List<Permission> findAllPermsByRoleId(Integer id) { return this.permissionMapper.findAllPermsByRole(id); }

	@Override
	public List<PermissionVO> getUserPerms(Integer id) {
		return this.permissionMapper.getUserPerms(id);
	}

	/**
	 * 批量删除角色权限中间表数据
	 * @param roleId
	 */
	private void batchDelRolePerms(int roleId) {
		List<RolePermissionKey> rpks=this.rolePermissionMapper.findByRole(roleId);
		if(null!=rpks && rpks.size()>0){
			for (RolePermissionKey rpk : rpks) {
				this.rolePermissionMapper.deleteByPrimaryKey(rpk);
			}
		}
	}

	/**
	 * 给当前角色设置权限
	 * @param roleId
	 * @param arrays
	 */
	private void setRolePerms(int roleId, String[] arrays) {
		for (String permid : arrays) {
			RolePermissionKey rpk=new RolePermissionKey();
			rpk.setRoleId(roleId);
			rpk.setPermitId(Integer.valueOf(permid));
			this.rolePermissionMapper.insert(rpk);
		}
	}
}
