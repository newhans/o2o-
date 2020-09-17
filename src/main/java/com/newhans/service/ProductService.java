package com.newhans.service;

import java.io.InputStream;
import java.util.List;

import com.newhans.dto.ImageHolder;
import com.newhans.dto.ProductCategoryExecution;
import com.newhans.dto.ProductExecution;
import com.newhans.entity.Product;
import com.newhans.exceptions.ProductCategoryOperationException;
import com.newhans.exceptions.ProductOperationException;

public interface ProductService {

	//1.处理缩略图
	//2.处理商品详情图片（列表）
	//3.添加商品信息
	/**
	 * 添加商品信息及图片处理
	 * @param product
	 * @param thumbnail
	 * @param productImgList
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product, ImageHolder thumbnail,  List<ImageHolder> productImgList) throws ProductOperationException;
	
	/**
	 * 通过商品ID查询唯一的商品信息
	 * @param productId
	 * @return
	 */
	Product getProductById(long productId);
	
	/**
	 * 修改商品及图片处理
	 * @param product
	 * @param thumbnail
	 * @param productImgHolderList
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException;
	
	/**
	 * 查询商品列表并分页，可输入的条件：商品名（模糊），商品状态，店铺id，商品类别
	 * @param productCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
	
	
}
