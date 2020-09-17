package com.newhans.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import org.springframework.beans.factory.annotation.Autowired;

import com.newhans.BaseTest;
import com.newhans.entity.ProductImg;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest{
	@Autowired
	private ProductImgDao productImgDao;
	
	@Test
 	public void testCDeleteProductImgByProductId() throws Exception{
		//删除商品详情图片记录
		long productId = 19;
		int effectNum = productImgDao.deleteProductImgByProductId(productId);
		assertEquals(2, effectNum);
	}
	
	@Ignore
	@Test
	public void testABatchInsertProductImg() throws Exception {
		// productId为1的商品里添加两个详情图片记录
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("图片3");
		productImg1.setImgDesc("测试图片1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(1L);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("图片4");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(1L);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);
		//assertEquals(2, productImgList.size());
	}
}
