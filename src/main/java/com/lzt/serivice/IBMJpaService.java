package com.lzt.serivice;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
public interface IBMJpaService {
    void save(String name, String model1, String model2, String sn,
              String time);

    void delete(long id);
    void update(long id, String name, String model1, String model2, String sn,
                String time);
}
