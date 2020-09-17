package com.newhans.service;

import java.util.List;

import com.newhans.entity.ShopCategory;

public interface ShopCategoryService {
	/**
	 * 根据查询条件获取ShopCategory列表
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
