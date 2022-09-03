package com.game.system.service;

import java.util.List;
import com.game.common.core.domain.entity.SysDictData;

/**
 * Dictionary 业务层
 * 
 * @author Yu Yue
 */
public interface ISysDictDataService
{
    /**
     * 根据条件分页查询DictionaryData
     * 
     * @param dictData DictionaryData信息
     * @return DictionaryData集合信息
     */
    public List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * 根据Dictionary type和Dictionary键值查询DictionaryData信息
     * 
     * @param dictType Dictionary type
     * @param dictValue Dictionary键值
     * @return Dictionary标签
     */
    public String selectDictLabel(String dictType, String dictValue);

    /**
     * 根据DictionaryDataID查询信息
     * 
     * @param dictCode DictionaryDataID
     * @return DictionaryData
     */
    public SysDictData selectDictDataById(Long dictCode);

    /**
     * 批量删除DictionaryData信息
     * 
     * @param dictCodes 需要删除的DictionaryDataID
     */
    public void deleteDictDataByIds(Long[] dictCodes);

    /**
     * 新增保存DictionaryData信息
     * 
     * @param dictData DictionaryData信息
     * @return 结果
     */
    public int insertDictData(SysDictData dictData);

    /**
     * 修改保存DictionaryData信息
     * 
     * @param dictData DictionaryData信息
     * @return 结果
     */
    public int updateDictData(SysDictData dictData);
}
