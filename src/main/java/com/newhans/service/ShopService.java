package com.newhans.service;

import java.io.File;
import java.io.InputStream;

import com.newhans.dto.ImageHolder;
import com.newhans.dto.ShopExecution;
import com.newhans.entity.Shop;
import com.newhans.exceptions.ShopOperationException;

public interface ShopService {
	/**
	 *根据shopCondition分页返回相应店铺列表
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
	
	/**
	 * 根据店铺id获取店铺信息
	 * @param shopId
	 * @return
	 */
	Shop getByShopId(long shopId);
	
	
	/**
	 * 更新店铺信息，包括对图片的处理
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
	
	//ImageUtil需要用到fileName（eg.获取格式jpg/png)
	ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
	
	
	
}
