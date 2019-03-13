package com.example.xiaojun.huayu.HuaJi.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiaojun.huayu.HuaYu.Tools.Tools;
import com.example.xiaojun.huayu.R;


public class GoodsRecommendFragment extends Fragment {
    private Button BuyNowButton;
    private Button ConnectUsButton;
    private static final String TITLE="title";
    private static final String PRICE="price";
    private static final String SELLER="seller";
    private static final String IMAGEURL="imageurl";
    private static final String USE="use";
    private TextView TitleTextView;
    private TextView PriceTextView;
    private TextView SellerTextView;
    private TextView UseTextView;
    private ImageView GoodsImage;
    private String ImageUrl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_recommend, container, false);
        BuyNowButton=view.findViewById(R.id.buy_now);
        GoodsImage=view.findViewById(R.id.goods_image);
        TitleTextView=view.findViewById(R.id.goods_title);
        PriceTextView=view.findViewById(R.id.goods_price);
        SellerTextView=view.findViewById(R.id.goods_seller);
        UseTextView=view.findViewById(R.id.goods_use);
        String Title=getActivity().getIntent().getStringExtra(TITLE);
        double Price=getActivity().getIntent().getDoubleExtra(PRICE,1);
        String Seller=getActivity().getIntent().getStringExtra(SELLER);
        String ImageUrl=getActivity().getIntent().getStringExtra(IMAGEURL);
        String Use=getActivity().getIntent().getStringExtra(USE);
        TitleTextView.setText(Title);
        PriceTextView.setText("商品价格:"+Price+" RMB");
        SellerTextView.setText("店铺:"+Seller);
        UseTextView.setText(Use);
        Tools.LoadImage(GoodsImage,ImageUrl);
        BuyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.goods_recommend_fragment_container,new BuyNowFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return view;
    }
}
