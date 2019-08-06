package com.example.xiaojun.huayu.HuaJi.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaojun.huayu.HuaJi.Bean.Order;
import com.example.xiaojun.huayu.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class BuyNowFragment extends Fragment {
    private static final String PRICE="price";
    private static final String TITLE="title";

    private TextView PriceTextView;
    private TextView TotalTextView;
    private EditText NameEditText;
    private EditText AddressEditText;
    private EditText PhoneEditText;
    private Button WechatPayButton;
    private double Price;
    private String GoodsName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy_now, container, false);
        PriceTextView=view.findViewById(R.id.buy_now_price);
        TotalTextView=view.findViewById(R.id.total_price);
        NameEditText=view.findViewById(R.id.buy_now_name_edit);
        AddressEditText=view.findViewById(R.id.buy_now_address_edit);
        PhoneEditText=view.findViewById(R.id.buy_now_phone_edit);
        Price=getActivity().getIntent().getDoubleExtra(PRICE,1);
        double TotalPrice=Price+5;
        PriceTextView.setText("$"+Price);
        TotalTextView.setText("$"+TotalPrice);
        return view;
    }
    private void requestOrder(){
        Order order=new Order();
        GoodsName=getActivity().getIntent().getStringExtra(TITLE);
        String Name=NameEditText.getText().toString();
        String Address=AddressEditText.getText().toString();
        String Phone=PhoneEditText.getText().toString();
        if(Name.equals("")||Address.equals("")||Phone.equals("")){
            Toast.makeText(getActivity(),"提示:数据未填写完整",Toast.LENGTH_SHORT).show();
        }else{
            order.setAddress(Address);
            order.setName(Name);
            order.setPhone(Phone);
            order.setGoodsName(GoodsName);
            order.setGoodsPrice(Price);
            order.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null){
                        Toast.makeText(getActivity(),"支付成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"支付失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

}