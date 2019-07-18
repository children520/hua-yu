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


    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    private int State;
    public BlueTooth(String Name,String Address,int State){
        this.Name=Name;
        this.State=State;
        this.Address=Address;
    }
}
