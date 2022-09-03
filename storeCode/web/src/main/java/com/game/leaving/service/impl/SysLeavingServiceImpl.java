package com.game.leaving.service.impl;

import java.util.List;

import com.game.leaving.domain.SysLeaving;
import com.game.leaving.mapper.SysLeavingMapper;
import com.game.leaving.service.ISysLeavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 留言Service业务层处理
 * 
 * @author Yu Yue
 * @date 2022-05-24
 */
@Service
public class SysLeavingServiceImpl implements ISysLeavingService
{
    @Autowired
    private SysLeavingMapper sysLeavingMapper;

    /**
     * 查询留言
     * 
     * @param id 留言主键
     * @return 留言
     */
    @Override
    public SysLeaving selectSysLeavingById(Long id)
    {
        return sysLeavingMapper.selectSysLeavingById(id);
    }

    /**
     * 查询留言列表
     * 
     * @param sysLeaving 留言
     * @return 留言
     */
    @Override
    public List<SysLeaving> selectSysLeavingList(SysLeaving sysLeaving)
    {
        return sysLeavingMapper.selectSysLeavingList(sysLeaving);
    }

    /**
     * 新增留言
     * 
     * @param sysLeaving 留言
     * @return 结果
     */
    @Override
    public int insertSysLeaving(SysLeaving sysLeaving)
    {
        return sysLeavingMapper.insertSysLeaving(sysLeaving);
    }

    /**
     * 修改留言
     * 
     * @param sysLeaving 留言
     * @return 结果
     */
    @Override
    public int updateSysLeaving(SysLeaving sysLeaving)
    {
        return sysLeavingMapper.updateSysLeaving(sysLeaving);
    }

    /**
     * 批量删除留言
     * 
     * @param ids 需要删除的留言主键
     * @return 结果
     */
    @Override
    public int deleteSysLeavingByIds(Long[] ids)
    {
        return sysLeavingMapper.deleteSysLeavingByIds(ids);
    }

    /**
     * 删除留言信息
     * 
     * @param id 留言主键
     * @return 结果
     */
    @Override
    public int deleteSysLeavingById(Long id)
    {
        return sysLeavingMapper.deleteSysLeavingById(id);
    }
}
