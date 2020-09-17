package com.newhans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newhans.dao.ShopCategoryDao;
import com.newhans.dto.ProductCategoryExecution;
import com.newhans.entity.ShopCategory;
import com.newhans.exceptions.ProductCategoryOperationException;
import com.newhans.service.ShopCategoryService;

@Service
//service标签spring注入
public class ShopCategoryServiceImpl implements ShopCategoryService{
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition){
		return shopCategoryDao.queryShopCategory(shopCategoryCondition);
	}
	
	
}
