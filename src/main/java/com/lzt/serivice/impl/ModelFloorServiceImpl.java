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

    @Override
    public void save(String name,String type,String remark) {
        mfdao.save(new ModelFloor(name,type,remark));
    }

    @Override
    public void update(long id, String name, String type, String remark) {
        ModelFloor mf=new ModelFloor(name,type,remark);
        mf.setId(id);
        mfdao.save(mf);
    }

    @Override
    public void delete(long id) {
        mfdao.delete(id);
    }
}
