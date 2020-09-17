package com.newhans.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newhans.dao.HeadLineDao;
import com.newhans.entity.HeadLine;
import com.newhans.service.HeadLineService;

@Service
public class HeadLineServiceImpl implements HeadLineService{
	@Autowired
	private HeadLineDao headLineDao;

	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
		return headLineDao.queryHeadLine(headLineCondition);
	}
	
	
}
