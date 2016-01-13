package com.ziroom.business.house.dao.impl;

import org.springframework.stereotype.Repository;

import com.ziroom.business.house.dao.HouseDao;
import com.ziroom.business.house.model.House;
import com.ziroom.common.base.dao.impl.BaseMongoDaoImpl;

@Repository
public class HouseDaoImpl extends BaseMongoDaoImpl<House> implements HouseDao{

}
