package com.lzt.serivice.impl;

import com.lzt.dao.PcDao;
import com.lzt.dao.PcJpaDao;
import com.lzt.entity.PC;
import com.lzt.serivice.PcJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
@Service
public class PcJpaServiceImpl implements PcJpaService {

    @Autowired
    private PcJpaDao pcJpadao;
    @Override
    public void save(String pcName,String model,String name,String asset,
                     String mac,String sn,String number,String floor,String state) {

        pcJpadao.save(new PC(pcName,model,name,asset,mac,sn,number,floor,state));

    }



    @Override
    public void delete(long id) {
        pcJpadao.delete(id);
    }

    @Override
    public void update(long id, String pcName, String model, String name, String asset, String mac, String sn, String number, String floor, String state) {
       PC pc=new PC(pcName,model,name,asset,mac,sn,number,floor,state);
       pc.setId(id);
        pcJpadao.save(pc);
    }
}
