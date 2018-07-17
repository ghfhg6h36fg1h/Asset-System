package com.lzt.serivice;

import com.lzt.entity.PC;

import java.util.List;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
public interface PcJpaService {
    void save(String pcName,String model,String name,String asset,
              String mac,String sn,String number,String floor,String state);

    void delete(long id);
    void update(long id, String pcName, String model, String name, String asset, String mac, String sn, String number, String floor, String state);
}
