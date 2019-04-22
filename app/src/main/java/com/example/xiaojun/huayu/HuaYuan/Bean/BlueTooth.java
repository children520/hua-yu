package com.example.xiaojun.huayu.HuaYuan.Bean;

public class BlueTooth {
    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private String Address;
    private String Name;
    public BlueTooth(String name,String Address){
        this.Name=name;
        this.Address=Address;
    }
}
