package com.game.store.product.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import com.game.common.annotation.Excel;
import com.game.common.core.domain.BaseEntity;

/**
 * 商品列表对象 store_product
 *
 * @author Yu Yue
 * @date 2022-05-14
 */
@Data
public class StoreProduct extends BaseEntity
{
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;




    private Long id;


    @Excel(name = "商品图片")
    private String image;


    @Excel(name = "轮播图")
    private String sliderImage;


    @Excel(name = "商品名称")
    private String storeName;


    @Excel(name = "商品简介")
    private String storeInfo;


    @TableField(exist = false)
    private String cateName;


    private Long cateId;


    @Excel(name = "商品价格")
    private BigDecimal price;


    @Excel(name = "排序")
    private Long sort;


    @Excel(name = "销量")
    private String sales;


    @Excel(name = "库存")
    private String stock;


    @Excel(name = " State", readConverterExp = "0=：未上架，1：上架")
    private Integer isShow;


    @Excel(name = "产品描述")
    private String description;


    @Excel(name = "成本价")
    private BigDecimal cost;


    @Excel(name = "虚拟销量")
    private Long ficti;


    @Excel(name = "浏览量")
    private Long browse;

}
