package com.lzt.entity;

import org.apache.ibatis.annotations.Param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Enzo Cotter on 2018-6-19.
 */
@Entity(name="tb2_pc")
public class PC {
    @Id
    @GeneratedValue
    private Long id;
    private String PCName;
    private String Floor;
    private String Model;
    private String username;
    @Column(name="ASSET_NUMBER")
    private String AssetNumber;
    private String SN;
    private String MAC;
    @Column(name="user_number")
    private String userNumber;
    private String state;

    public PC(){}
    public PC(String pcName,String model,String name,String asset,
              String mac,String sn,String number,String floor,String state)
    {
        setPCName(pcName);
        setModel(model);
        setUsername(name);
        setAssetNumber(asset);
        setMAC(mac);
        setSN(sn);
        setUserNumber(number);
        setFloor(floor);
        setState(state);

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPCName() {
        return PCName;
    }

    public void setPCName(String PCName) {
        this.PCName = PCName;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getAssetNumber() {
        return AssetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        AssetNumber = assetNumber;
    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
