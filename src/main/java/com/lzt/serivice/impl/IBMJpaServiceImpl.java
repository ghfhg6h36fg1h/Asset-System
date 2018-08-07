package com.lzt.serivice.impl;

import com.lzt.dao.IBMJpaDao;
import com.lzt.dao.PcJpaDao;
import com.lzt.entity.IBM;
import com.lzt.entity.PC;
import com.lzt.serivice.IBMJpaService;
import com.lzt.serivice.PcJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
@Service
public class IBMJpaServiceImpl implements IBMJpaService {

    @Autowired
    private IBMJpaDao ibmJpadao;

    @Override
    public void save(String name, String model1, String model2, String sn,
                     String time) {

        ibmJpadao.save(new IBM(name, model1, model2, sn, time));

    }


    @Override
    public void delete(long id) {

        ibmJpadao.delete(id);
    }

    @Override
    public void update(long id, String name, String model1, String model2, String sn,
                       String time) {
        IBM ibm = new IBM(name, model1, model2, sn, time);
        ibm.setId(id);
        ibmJpadao.save(ibm);

    }
}
