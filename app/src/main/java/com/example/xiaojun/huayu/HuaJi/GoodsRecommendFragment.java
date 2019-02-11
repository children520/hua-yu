package com.example.xiaojun.huayu.HuaJi;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.xiaojun.huayu.R;



public class GoodsRecommendFragment extends Fragment {
    private Button BuyNowButton;
    private Button ConnectUsButton;
    private static final String TITLE="title";
    private static final String PRICE="price";
    private static final String SELLER="seller";
    private static final String IMAGEURL="imageurl";
    private TextView TitleTextView;
    private TextView PriceTextView;
    private TextView SellerTextView;
    private String ImageUrl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_recommend, container, false);
        BuyNowButton=view.findViewById(R.id.buy_now);
        ConnectUsButton=view.findViewById(R.id.connect_us);
        TitleTextView=view.findViewById(R.id.goods_title);
        PriceTextView=view.findViewById(R.id.goods_price);
        SellerTextView=view.findViewById(R.id.goods_seller);
        String Title=getActivity().getIntent().getStringExtra(TITLE);
        double Price=getActivity().getIntent().getDoubleExtra(PRICE,1);
        String Seller=getActivity().getIntent().getStringExtra(SELLER);
        String ImageUrl=getActivity().getIntent().getStringExtra(IMAGEURL);
        TitleTextView.setText(Title);
        PriceTextView.setText("商品价格:"+Price+"");
        SellerTextView.setText("店铺:"+Seller);
        BuyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.goods_recommend_fragment_container,new BuyNowFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        ConnectUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
