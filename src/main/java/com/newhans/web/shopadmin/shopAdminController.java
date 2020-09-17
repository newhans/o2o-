package com.newhans.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin", method = {RequestMethod.GET})
public class shopAdminController {
	@RequestMapping(value = "/shopoperation")
	public String shopOperation() {
		return "shop/shopoperation";
	}
//resources/spring/spring-web.xml定义了viewResolver
	@RequestMapping(value = "/shoplist")
	public String shopList() {
		return "shop/shoplist";
	}

	@RequestMapping(value = "/shopmanagement")
	public String shopManagement() {
		return "shop/shopmanagement";
	}	
	
	
	@RequestMapping(value = "/productcategorymanagement", method = RequestMethod.GET)
	private String productCategoryManage() {
		return "shop/productcategorymanagement"; 
	}
	
	@RequestMapping(value = "/productoperation")
	public String productOperation() {
		//转发至商品添加/编辑页面
		return "shop/productoperation";
	}
	
	@RequestMapping(value = "productmanagement")
	public String productManagement() {
		//转发至商品管理页面
		return "shop/productmanagement";
	}
}
