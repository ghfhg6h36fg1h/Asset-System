package com.lzt.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Enzo Cotter on 2018-7-19.
 */
@Entity(name = "tb2_IBM")
public class IBM {
    @Id
    @GeneratedValue
    private long id;
    private String model1;
    private String name;
    private String model2;
    private String sn;
    private String time;
    private String state;

    public IBM(){}
    public IBM(String name,String model1 ,String model2,String sn,String time)
    {
        setName(name);
        setModel1(model1);
        setModel2(model2);
        setSn(sn);
        setTime(time);
        setState("0");
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModel1() {
        return model1;
    }

    public void setModel1(String model1) {
        this.model1 = model1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel2() {
        return model2;
    }

    public void setModel2(String model2) {
        this.model2 = model2;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
