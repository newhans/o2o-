package com.newhans.service.impl;

import java.util.List;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newhans.dao.ProductCategoryDao;
import com.newhans.dao.ProductDao;
import com.newhans.dto.ProductCategoryExecution;
import com.newhans.entity.ProductCategory;
import com.newhans.enums.ProductCategoryStateEnum;
import com.newhans.exceptions.ProductCategoryOperationException;
import com.newhans.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Autowired
	private ProductDao ProductDao;
	
	@Override
	public List<ProductCategory> getProductCategoryList(long shopId){
		return productCategoryDao.queryProductCategoryList(shopId);
	}
	@Override
	@Transactional
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
		throws ProductCategoryOperationException{
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if (effectNum <= 0) {
					throw new ProductCategoryOperationException("店铺创建失败");
				}else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}
			}catch (Exception e) {
				throw new ProductCategoryOperationException("batchAddProductCategory error: " + e.getMessage());
			}
		}else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}
	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		//解除tb_product里的商品与该productCategoryId的关联
		try {
			int effectedNum = ProductDao.updateProductCategoryToNull(productCategoryId);
			if (effectedNum < 0) {
				throw new ProductCategoryOperationException("商品类别更新失败");
			}
		}catch (Exception e) {
				throw new ProductCategoryOperationException("deleteProductCategory error:" + e.getMessage());
		}
		//删除该productCategory
		try {
			int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if (effectedNum <= 0) {
				throw new ProductCategoryOperationException("店铺类别删除失败");
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		}catch (Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory erroe:" + e.getMessage());
		}
	}
	
	

}
