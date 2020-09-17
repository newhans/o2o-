package com.newhans.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.springframework.beans.factory.annotation.Autowired;

import com.newhans.BaseTest;
import com.newhans.dto.ImageHolder;
import com.newhans.dto.ProductExecution;
import com.newhans.entity.Product;
import com.newhans.entity.ProductCategory;
import com.newhans.entity.Shop;
import com.newhans.enums.ProductStateEnum;
import com.newhans.exceptions.ShopOperationException;


public class ProductServiceTest extends BaseTest{
	@Autowired
	private ProductService productService;
	
	@Ignore
	@Test
	public void testAddProduct() throws ShopOperationException, FileNotFoundException {
		// 创建shopId为1且productCategoryId为1的商品实例并给其成员变量赋值
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(2L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("测试商品1");
		product.setProductDesc("测试商品1");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		// 创建缩略图文件流
		File thumbnailFile = new File("C:\\Users\\DELL\\Desktop\\jpg\\1.jpeg");
		InputStream is = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
		// 创建两个商品详情图文件流并将他们添加到详情图列表中
		File productImg1 = new File("C:\\Users\\DELL\\Desktop\\jpg\\1.jpeg");
		InputStream is1 = new FileInputStream(productImg1);
		File productImg2 = new File("C:\\Users\\DELL\\Desktop\\jpg\\2.jpeg");
		InputStream is2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(productImg1.getName(), is1));
		productImgList.add(new ImageHolder(productImg2.getName(), is2));
		// 添加商品并验证
		ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	}
	
	@Test
	public void testModifyProduct() throws ShopOperationException, FileNotFoundException {
		// 创建shopId为1且productCategoryId为1的商品实例并给其成员变量赋值
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(4L);
		product.setProductId(1L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("正式的商品0904");
		product.setProductDesc("正式的商品0904");
		// 创建缩略图文件流
		File thumbnailFile = new File("C:\\Users\\DELL\\Desktop\\QQ图片.jpg");
		InputStream is = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
		// 创建两个商品详情图文件流并将他们添加到详情图列表中
		File productImg1 = new File("C:\\Users\\DELL\\Desktop\\QQ图片.jpg");
		InputStream is1 = new FileInputStream(productImg1);
		File productImg2 = new File("C:\\Users\\DELL\\Desktop\\QQ图片.jpg");
		InputStream is2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(productImg1.getName(), is1));
		productImgList.add(new ImageHolder(productImg2.getName(), is2));
		// 添加商品并验证
		ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	}
}
