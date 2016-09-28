package cn.ffcs.system.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.ffcs.system.dao.SysRoleDao;
import cn.ffcs.system.dao.SysRoleMenuDao;
import cn.ffcs.system.dao.SysUserDao;
import cn.ffcs.system.entity.SysRole;
import cn.ffcs.system.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	private SysRoleDao sysRoleDao;
	
	@Resource
	private SysUserDao sysUserDao;
	
	@Resource
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Override
	public List<SysRole> findAll() {
		return this.sysRoleDao.findAll();
	}

	@Override
	public void save(SysRole role) {
		if (role == null) {
			return;
		}
		
		if (role.getRoleName() == null || role.getRoleName().isEmpty()) {
			throw new IllegalArgumentException("role.name.required");
		}
		
		this.sysRoleDao.save(role);
	}

	@Override
	public void update(SysRole role) {
		if (role == null) {
			return;
		}
		
		if (role.getRoleId() == null) {
			throw new IllegalArgumentException("role.id.required");
		}
		
		if (role.getRoleName() == null || role.getRoleName().isEmpty()) {
			throw new IllegalArgumentException("role.name.required");
		}
		
		this.sysRoleDao.update(role);
	}

	@Override
	@Transactional
	public void delete(String roleId) {
		if (roleId == null) {
			return;
		}
		
		this.sysRoleDao.delete(roleId);
		
		// 删除角色需要把用户的角色信息晴空
		this.sysUserDao.updateRoleNull(roleId);
		
		// 删除角色对应的菜单信息
		this.sysRoleMenuDao.deleteByRoleId(roleId);
	}

	
}
