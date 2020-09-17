package com.newhans.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.newhans.entity.ProductCategory;

public interface ProductCategoryDao {
	List<ProductCategory> queryProductCategoryList(long shopId);
	
	/**
	 * 批量增加商品类别
	 * @param productCategoriyList
	 * @return 影响的行数
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
	
	/**
	 * 删除指定商品类别
	 * return 影响的行数
	 * 
	 * 多参数mybatis无法识别，所以要用Param
	 */
	int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
