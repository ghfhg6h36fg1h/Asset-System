package com.lzt.serivice.impl;

import com.lzt.dao.ModelFloorDao;
import com.lzt.entity.ModelFloor;
import com.lzt.serivice.ModelFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
@Service
public class ModelFloorServiceImpl implements ModelFloorService {

    @Autowired
    ModelFloorDao mfdao;

    @Override
    public List findAll() {

        Iterator Imf=mfdao.findAll().iterator();
        List mfList=new ArrayList<ModelFloor>();
        while (Imf.hasNext())
            mfList.add(Imf.next());
        return mfList;
    }
}
