package com.newhans.service;

import java.util.List;

import com.newhans.dto.ProductCategoryExecution;
import com.newhans.entity.ProductCategory;
import com.newhans.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {
	/**
	 * 查询指定某个店铺下的所有商品类别信息
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> getProductCategoryList(long shopId);
	
	/**
	 * 
	 * @param productCategory
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException;
	
	/**
	 * 将此类别下的商品里的类别id置为空，再删除该商品类别
	 * 
	 */
	ProductCategoryExecution deleteProductCategory (long productCategoryId, long shopId) 
		throws ProductCategoryOperationException;
	
}
