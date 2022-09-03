package com.game.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.game.system.domain.SysUserRole;

/**
 * User与角色关联表 Data层
 * 
 * @author Yu Yue
 */
public interface SysUserRoleMapper
{
    /**
     * 通过UserID删除User和角色关联
     * 
     * @param userId UserID
     * @return 结果
     */
    public int deleteUserRoleByUserId(Long userId);

    /**
     * 批量删除User和角色关联
     * 
     * @param ids 需要删除的DataID
     * @return 结果
     */
    public int deleteUserRole(Long[] ids);

    /**
     * 通过角色ID查询角色使用数量
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int countUserRoleByRoleId(Long roleId);

    /**
     * 批量新增User角色信息
     * 
     * @param userRoleList User角色列表
     * @return 结果
     */
    public int batchUserRole(List<SysUserRole> userRoleList);

    /**
     * 删除User和角色关联信息
     * 
     * @param userRole User和角色关联信息
     * @return 结果
     */
    public int deleteUserRoleInfo(SysUserRole userRole);

    /**
     * 批量Cancel授权User角色
     * 
     * @param roleId 角色ID
     * @param userIds 需要删除的UserDataID
     * @return 结果
     */
    public int deleteUserRoleInfos(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);
}
