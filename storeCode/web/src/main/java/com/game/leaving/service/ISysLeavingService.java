package com.game.leaving.service;

import com.game.leaving.domain.SysLeaving;

import java.util.List;

/**
 * 留言Service接口
 * 
 * @author Yu Yue
 * @date 2022-05-24
 */
public interface ISysLeavingService 
{
    /**
     * 查询留言
     * 
     * @param id 留言主键
     * @return 留言
     */
    public SysLeaving selectSysLeavingById(Long id);

    /**
     * 查询留言列表
     * 
     * @param sysLeaving 留言
     * @return 留言集合
     */
    public List<SysLeaving> selectSysLeavingList(SysLeaving sysLeaving);

    /**
     * 新增留言
     * 
     * @param sysLeaving 留言
     * @return 结果
     */
    public int insertSysLeaving(SysLeaving sysLeaving);

    /**
     * 修改留言
     * 
     * @param sysLeaving 留言
     * @return 结果
     */
    public int updateSysLeaving(SysLeaving sysLeaving);

    /**
     * 批量删除留言
     * 
     * @param ids 需要删除的留言主键集合
     * @return 结果
     */
    public int deleteSysLeavingByIds(Long[] ids);

    /**
     * 删除留言信息
     * 
     * @param id 留言主键
     * @return 结果
     */
    public int deleteSysLeavingById(Long id);
}
