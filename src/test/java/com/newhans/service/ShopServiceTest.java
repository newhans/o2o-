package com.newhans.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.newhans.BaseTest;
import com.newhans.dto.ImageHolder;
import com.newhans.dto.ShopExecution;
import com.newhans.entity.Area;
import com.newhans.entity.PersonInfo;
import com.newhans.entity.Shop;
import com.newhans.entity.ShopCategory;
import com.newhans.enums.ShopStateEnum;
import com.newhans.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest{
	@Autowired
	//autowired : 将ShopService 的实现类动态地注入到shopService接口里去
	private ShopService shopService;
	
	@Test
	public void testGetShopList() {
		Shop shopCondition = new Shop();
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(2L);
		shopCondition.setShopCategory(sc);
		ShopExecution se = shopService.getShopList(shopCondition, 3, 2);
		System.out.println("店铺列表数为：" + se.getShopList().size());
		System.out.println("店铺总数为：" + se.getCount());		
	}
	
	@Test
	@Ignore
	public void testModifyShop() throws ShopOperationException, FileNotFoundException{
		Shop shop  = new Shop();
		shop.setShopId(6L);
		shop.setShopName("星巴克1");
		File shopImg = new File("C:/Users/DELL/Desktop/pic.jpg");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder("pic.jpg", is);
		ShopExecution shopExecution =  shopService.modifyShop(shop, imageHolder);
 		System.out.println("新的图片地址：" + shopExecution.getShop().getShopImg());
	}
	
	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试的店铺3");
		shop.setShopDesc("test3");
		shop.setShopAddr("test3");
		shop.setPhone("test3");
		//shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		
		File shopImg = new File("C:/Users/DELL/Desktop/QQ图片.jpg");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder(shop.getShopName(), is);
		ShopExecution se = shopService.addShop(shop, imageHolder);
		//System.out.println("分类：" + shop.getShopCategory());
		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
	}
	
}
