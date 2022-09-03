package com.game.leaving.mapper;

import com.game.leaving.domain.SysLeaving;

import java.util.List;


/**
 * 留言Mapper接口
 * 
 * @author Yu Yue
 * @date 2022-05-24
 */
public interface SysLeavingMapper 
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
     * 删除留言
     * 
     * @param id 留言主键
     * @return 结果
     */
    public int deleteSysLeavingById(Long id);

    /**
     * 批量删除留言
     * 
     * @param ids 需要删除的Data主键集合
     * @return 结果
     */
    public int deleteSysLeavingByIds(Long[] ids);
}
