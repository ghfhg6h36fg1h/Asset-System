package com.lzt.serivice;


import com.lzt.entity.PC;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
public interface PcJpaService {
    void save(String pcName,String model,String name,String asset,
              String mac,String sn,String number,String floor,String state,String usb,String mcafee,String net);

    void save(PC pc);
    void delete(long id);
    void update(long id, String pcName, String model, String name, String asset, String mac, String sn, String number, String floor, String state,String usb,String mcafee,String net);
}
