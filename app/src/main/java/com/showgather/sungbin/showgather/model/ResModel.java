package com.showgather.sungbin.showgather.model;

public class ResModel {
    public String name;
    public String address;
    public String diff;
    public String phone;
    public String image_url;
    public String lat;
    public String lon;

    public ResModel(String name,String address,String lat,String lon,String diff,String phone,String image_url){
        this.name=name;
        this.address=address;
        this.lat=lat;
        this.lon=lon;
        this.phone=phone;
        this.diff=diff;
        this.image_url=image_url;
    }
}
