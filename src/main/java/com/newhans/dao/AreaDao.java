package com.newhans.dao;

import java.util.List;

import com.newhans.entity.Area;

public interface AreaDao {
	/**
	 * 列出区域列表
	 * @return areaList
	 */
	List<Area> queryArea();
}
