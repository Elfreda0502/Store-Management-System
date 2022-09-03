package com.game.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.game.common.core.domain.entity.SysDictType;

/**
 * Dictionary表 Data层
 * 
 * @author Yu Yue
 */
@Mapper
public interface SysDictTypeMapper
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
     * 通过DictionaryID删除Dictionary信息
     * 
     * @param dictId DictionaryID
     * @return 结果
     */
    public int deleteDictTypeById(Long dictId);

    /**
     * 批量删除Dictionary type信息
     * 
     * @param dictIds 需要删除的DictionaryID
     * @return 结果
     */
    public int deleteDictTypeByIds(Long[] dictIds);

    /**
     * 新增Dictionary type信息
     * 
     * @param dictType Dictionary type信息
     * @return 结果
     */
    public int insertDictType(SysDictType dictType);

    /**
     * 修改Dictionary type信息
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
    public SysDictType checkDictTypeUnique(String dictType);
}
