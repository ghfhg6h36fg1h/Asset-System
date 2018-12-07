package com.lzt.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Enzo Cotter on 2018-6-19.
 */
@Entity(name = "tb2_record")
public class Record {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String operation;
    private String time;
    private String remark;

    public Record(String username, String time, String operation, String remark) {
        this.setOperation(operation);
        this.setRemark(remark);
        this.setTime(time);
        this.setUsername(username);
    }
public Record(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
