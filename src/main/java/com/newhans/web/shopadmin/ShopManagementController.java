package com.newhans.web.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
//import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newhans.dto.ImageHolder;
import com.newhans.dto.ShopExecution;
import com.newhans.entity.Area;
import com.newhans.entity.PersonInfo;
import com.newhans.entity.Shop;
import com.newhans.entity.ShopCategory;
import com.newhans.enums.ShopStateEnum;
import com.newhans.exceptions.ShopOperationException;
import com.newhans.service.AreaService;
import com.newhans.service.ShopCategoryService;
import com.newhans.service.ShopService;
import com.newhans.util.CodeUtil;
import com.newhans.util.HttpServletRequestUtil;
import com.newhans.util.ImageUtil;
import com.newhans.util.PathUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
	@ResponseBody 
	private Map<String, Object> getShopManagementInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId <= 0) {
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if (currentShopObj == null) {
				modelMap.put("redirect", true);
				modelMap.put("url", "/o2o/shopadmin/shoplist");
			}else {
				Shop currentShop = (Shop) currentShopObj;
				modelMap.put("redirect", false);
				modelMap.put("shopId", currentShop.getShopId());
			}
		}else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;
	}
	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		PersonInfo user = new PersonInfo();
		user.setUserId(1L);
		user.setName("test");
		request.getSession().setAttribute("user", user);
		user = (PersonInfo) request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("user", user);
			modelMap.put("success", true);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	
	@RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId > -1) {
			try {
				Shop shop = shopService.getByShopId(shopId);
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}
	
	
	
	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	//responsebody 返回json
	private Map<String, Object> getShopInitInfo(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			//获取parent不为空全部对象
			/*在mapper里，
			 * <if
			test="shopCategoryCondition != null">
			and parent_id is not null
			</if>
			is not null 就是获取所有非空对象
			而这里刚好传入的是一个新对象，符合上述if，返回所有parent不为空全部对象
			*/  
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		}catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//1.接受并转化相应的参数，包括店铺信息以及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		//jackson-databind
		//把信息转换为shop的实体类
		ObjectMapper mapper = new ObjectMapper();
	    Shop shop = null;
	    try {
	    	shop = mapper.readValue(shopStr, Shop.class);
	    }catch (Exception e){
	    	modelMap.put("success", false);
	    	modelMap.put("errMsg", e.getMessage());
	    	return modelMap;
	    }
	    CommonsMultipartFile shopImg = null;
	    CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
	    if (commonsMultipartResolver.isMultipart(request)) {
	    	MultipartHttpServletRequest  multipartHttpServletRequest = (MultipartHttpServletRequest) request;
	    	shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
	    } else {
	    	modelMap.put("success", false);
	    	modelMap.put("errMsg", "上传图片不能为空");
	    	return modelMap;
	    }
		//2.注册店铺
		if (shop != null && shopImg != null) {
			//owner可以从session获取，而不依赖表单的信息
			//Session TODO
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
			//owner.setUserId(1L);
			shop.setOwner(owner);

			ShopExecution se;
			try {
				ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
				se = shopService.addShop(shop, imageHolder);
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					//从session中取出用户可以操作的用户列表
					@SuppressWarnings("unchecked")
					List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
					if (shopList == null || shopList.size() == 0) {
						shopList = new ArrayList<Shop>();
					}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList", shopList);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}			
			} catch (ShopOperationException e) {
		    	modelMap.put("success", false);
		    	modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
		    	modelMap.put("success", false);
		    	modelMap.put("errMsg", e.getMessage());
			}
			//3.返回结果
			return modelMap;
		}else {
	    	modelMap.put("success", false);
	    	modelMap.put("errMsg", "请输入店铺信息/图片信息");
	    	return modelMap;			
		}
		
	}
	
	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//1.接受并转化相应的参数，包括店铺信息以及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		//jackson-databind
		//把信息转换为shop的实体类
		ObjectMapper mapper = new ObjectMapper();
	    Shop shop = null;
	    try {
	    	shop = mapper.readValue(shopStr, Shop.class);
	    }catch (Exception e){
	    	modelMap.put("success", false);
	    	modelMap.put("errMsg", e.getMessage());
	    	return modelMap;
	    }
	    CommonsMultipartFile shopImg = null;
	    CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
	    if (commonsMultipartResolver.isMultipart(request)) {
	    	MultipartHttpServletRequest  multipartHttpServletRequest = (MultipartHttpServletRequest) request;
	    	shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
	    }
		//2.修改店铺信息
		if (shop != null && shop.getShopId() != null) {
			//owner可以从session获取，而不依赖表单的信息
			

			ShopExecution se;
			try {
				if (shopImg == null) {
					se = shopService.modifyShop(shop, null);
				}else {
					ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
					se = shopService.modifyShop(shop, imageHolder);
				}
				if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}			
			} catch (ShopOperationException e) {
		    	modelMap.put("success", false);
		    	modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
		    	modelMap.put("success", false);
		    	modelMap.put("errMsg", e.getMessage());
			}
			//3.返回结果
			return modelMap;
		}else {
	    	modelMap.put("success", false);
	    	modelMap.put("errMsg", "请输入店铺id");
	    	return modelMap;			
		}
		
	}
	
}
