package com.game.system.service;

import java.util.List;
import com.game.common.core.domain.entity.SysDictData;
import com.game.common.core.domain.entity.SysDictType;

/**
 * Dictionary 业务层
 *
 * @author Yu Yue
 */
public interface ISysDictTypeService
{
    /**
     * 根据条件分页查询Dictionary type
     *
     * @param dictType Dictionary type信息
     * @return Dictionary type集合信息
     */
    public List<SysDictType> selectDictTypeList(SysDictType dictType);

    /**
     * 根据所有Dictionary type
     *
     * @return Dictionary type集合信息
     */
    public List<SysDictType> selectDictTypeAll();

    /**
     * 根据Dictionary type查询DictionaryData
     *
     * @param dictType Dictionary type
     * @return DictionaryData集合信息
     */
    public List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 根据Dictionary typeID查询信息
     *
     * @param dictId Dictionary typeID
     * @return Dictionary type
     */
    public SysDictType selectDictTypeById(Long dictId);

    /**
     * 根据Dictionary type查询信息
     *
     * @param dictType Dictionary type
     * @return Dictionary type
     */
    public SysDictType selectDictTypeByType(String dictType);

    /**
     * 批量删除Dictionary信息
     *
     * @param dictIds 需要删除的DictionaryID
     */
    public void deleteDictTypeByIds(Long[] dictIds);

    /**
     * 加载Dictionary缓存Data
     */
    public void loadingDictCache();

    /**
     * 清空Dictionary缓存Data
     */
    public void clearDictCache();

    /**
     * 重置Dictionary缓存Data
     */
    public void resetDictCache();

    /**
     * 新增保存Dictionary type信息
     *
     * @param dictType Dictionary type信息
     * @return 结果
     */
    public int insertDictType(SysDictType dictType);

    /**
     * 修改保存Dictionary type信息
     *
     * @param dictType Dictionary type信息
     * @return 结果
     */
    public int updateDictType(SysDictType dictType);

    /**
     * 校验Dictionary type称是否唯一
     *
     * @param dictType Dictionary type
     * @return 结果
     */
    public String checkDictTypeUnique(SysDictType dictType);
}
