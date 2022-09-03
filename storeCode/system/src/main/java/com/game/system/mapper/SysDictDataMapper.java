package com.game.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.game.common.core.domain.entity.SysDictData;

/**
 * Dictionary表 Data层
 * 
 * @author Yu Yue
 */
public interface SysDictDataMapper
{
    /**
     * 根据条件分页查询DictionaryData
     * 
     * @param dictData DictionaryData信息
     * @return DictionaryData集合信息
     */
    public List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * 根据Dictionary type查询DictionaryData
     * 
     * @param dictType Dictionary type
     * @return DictionaryData集合信息
     */
    public List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 根据Dictionary type和Dictionary键值查询DictionaryData信息
     * 
     * @param dictType Dictionary type
     * @param dictValue Dictionary键值
     * @return Dictionary标签
     */
    public String selectDictLabel(@Param("dictType") String dictType, @Param("dictValue") String dictValue);

    /**
     * 根据DictionaryDataID查询信息
     * 
     * @param dictCode DictionaryDataID
     * @return DictionaryData
     */
    public SysDictData selectDictDataById(Long dictCode);

    /**
     * 查询DictionaryData
     * 
     * @param dictType Dictionary type
     * @return DictionaryData
     */
    public int countDictDataByType(String dictType);

    /**
     * 通过DictionaryID删除DictionaryData信息
     * 
     * @param dictCode DictionaryDataID
     * @return 结果
     */
    public int deleteDictDataById(Long dictCode);

    /**
     * 批量删除DictionaryData信息
     * 
     * @param dictCodes 需要删除的DictionaryDataID
     * @return 结果
     */
    public int deleteDictDataByIds(Long[] dictCodes);

    /**
     * 新增DictionaryData信息
     * 
     * @param dictData DictionaryData信息
     * @return 结果
     */
    public int insertDictData(SysDictData dictData);

    /**
     * 修改DictionaryData信息
     * 
     * @param dictData DictionaryData信息
     * @return 结果
     */
    public int updateDictData(SysDictData dictData);

    /**
     * 同步修改Dictionary type
     * 
     * @param oldDictType 旧Dictionary type
     * @param newDictType 新旧Dictionary type
     * @return 结果
     */
    public int updateDictDataType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);
}
