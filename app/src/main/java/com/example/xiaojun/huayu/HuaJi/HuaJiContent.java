package com.example.xiaojun.huayu.HuaJi;

import cn.bmob.v3.BmobObject;

public class HuaJiContent extends BmobObject {
    private String ImageUrl;
    private String Title;
    private double Price;
    private String Seller;

    public char getGoodsSort() {
        return GoodsSort;
    }

    public void setGoodsSort(char goodsSort) {
        GoodsSort = goodsSort;
    }

    private char GoodsSort;
    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getSeller() {
        return Seller;
    }

    public void setSeller(String seller) {
        this.Seller = seller;
    }


}
