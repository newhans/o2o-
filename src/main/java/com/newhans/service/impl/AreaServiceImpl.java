package com.newhans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newhans.dao.AreaDao;
import com.newhans.entity.Area;
import com.newhans.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService{
	@Autowired
	private AreaDao areaDao;
	@Override
	public List<Area> getAreaList(){
		return areaDao.queryArea();
	}
}
