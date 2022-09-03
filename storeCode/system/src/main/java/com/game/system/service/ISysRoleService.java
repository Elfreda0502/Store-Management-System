package com.game.system.service;

import java.util.List;
import java.util.Set;
import com.game.common.core.domain.entity.SysRole;
import com.game.system.domain.SysUserRole;

/**
 * Role business layer
 *
 * @author Yu Yue
 */
public interface ISysRoleService
{
    /**
     * Paging query role Data according to conditions
     *
     * @param role role information
     * @return role Data collection information
     */
    public List<SysRole> selectRoleList(SysRole role);

    /**
     * Query the role list based on UserID
     *
     * @param userId UserID
     * @return list of roles
     */
    public List<SysRole> selectRolesByUserId(Long userId);

    /**
     * Query role permissions based on UserID
     *
     * @param userId UserID
     * @return permission list
     */
    public Set<String> selectRolePermissionByUserId(Long userId);

    /**
     * Query all roles
     *
     * @return list of roles
     */
    public List<SysRole> selectRoleAll();

    /**
     * Get a list of role selection boxes based on UserID
     *
     * @param userId UserID
     * @return selected character ID list
     */
    public List<Long> selectRoleListByUserId(Long userId);

    /**
     * Query role by role ID
     *
     * @param roleId role ID
     * @return role object information
     */
    public SysRole selectRoleById(Long roleId);

    /**
     * Check if the role name is unique
     *
     * @param role role information
     * @return result
     */
    public String checkRoleNameUnique(SysRole role);

    /**
     * Verify that role permissions are unique
     *
     * @param role role information
     * @return result
     */
    public String checkRoleKeyUnique(SysRole role);

    /**
     * Check if the role is allowed to operate
     *
     * @param role role information
     */
    public void checkRoleAllowed(SysRole role);

    /**
     * Check whether the role has Data permission
     *
     * @param roleId role id
     */
    public void checkRoleDataScope(Long roleId);

    /**
     * Query the number of roles used by role ID
     *
     * @param roleId role ID
     * @return result
     */
    public int countUserRoleByRoleId(Long roleId);

    /**
     * Added save character information
     *
     * @param role role information
     * @return result
     */
    public int insertRole(SysRole role);

    /**
     * Modify and save character information
     *
     * @param role role information
     * @return result
     */
    public int updateRole(SysRole role);

    /**
     * Modify the role State
     *
     * @param role role information
     * @return result
     */
    public int updateRoleStatus(SysRole role);

    /**
     * Modify Data permission information
     *
     * @param role role information
     * @return result
     */
    public int authDataScope(SysRole role);

    /**
     * delete role by role id
     *
     * @param roleId role ID
     * @return result
     */
    public int deleteRoleById(Long roleId);

    /**
     * Bulk delete role information
     *
     * @param roleIds Role IDs to be deleted
     * @return result
     */
    public int deleteRoleByIds(Long[] roleIds);

    /**
     * Cancel authorized User role
     *
     * @param userRole User and role association information
     * @return result
     */
    public int deleteAuthUser(SysUserRole userRole);

    /**
     * Batch Cancel authorization User role
     *
     * @param roleId role ID
     * @param userIds UserDataID that requires Cancel authorization
     * @return result
     */
    public int deleteAuthUsers(Long roleId, Long[] userIds);

    /**
     * Batch selection of authorized User roles
     *
     * @param roleId role ID
     * @param userIds UserDataID to delete
     * @return result
     */
    public int insertAuthUsers(Long roleId, Long[] userIds);
}