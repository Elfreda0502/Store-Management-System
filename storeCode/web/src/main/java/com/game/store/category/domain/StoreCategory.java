package com.game.store.category.domain;

import lombok.Data;
import com.game.common.annotation.Excel;
import com.game.common.core.domain.TreeEntity;

/**
 * 商品分类对象 store_category
 * 
 * @author Yu Yue
 * @date 2022-05-13
 */
@Data
public class StoreCategory extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    
    private Long id;

    
    @Excel(name = "父id")
    private Long pid;

    
    @Excel(name = "分类名称")
    private String cateName;

    
    @Excel(name = "排序")
    private Long sort;




}
