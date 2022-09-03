package com.game.system.mapper;

import java.util.List;
import com.game.system.domain.SysUserPost;

/**
 * User与岗位关联表 Data层
 * 
 * @author Yu Yue
 */
public interface SysUserPostMapper
{
    /**
     * 通过UserID删除User和岗位关联
     * 
     * @param userId UserID
     * @return 结果
     */
    public int deleteUserPostByUserId(Long userId);

    /**
     * 通过岗位ID查询岗位使用数量
     * 
     * @param postId 岗位ID
     * @return 结果
     */
    public int countUserPostById(Long postId);

    /**
     * 批量删除User和岗位关联
     * 
     * @param ids 需要删除的DataID
     * @return 结果
     */
    public int deleteUserPost(Long[] ids);

    /**
     * 批量新增User岗位信息
     * 
     * @param userPostList User角色列表
     * @return 结果
     */
    public int batchUserPost(List<SysUserPost> userPostList);
}
