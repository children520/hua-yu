package com.example.xiaojun.huayu.HuaHu.Fragment;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaojun.huayu.HuaYu.Tools.Tools;
import com.example.xiaojun.huayu.R;
import com.example.xiaojun.huayu.UserLogin.Bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class ChangePassordFragment extends Fragment {
    @BindView(R.id.edt_new_password)
    EditText mEdtNewPassword;
    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.tv_info)
    TextView mTvInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_reset_password, container, false);
        ButterKnife.bind(getActivity());
        return view;
    }
    @OnClick({R.id.btn_send, R.id.btn_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                User user = BmobUser.getCurrentUser(User.class);
                String phone = user.getMobilePhoneNumber();
                Boolean verify = user.getMobilePhoneNumberVerified();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getActivity(), "请先绑定手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (verify == null || !verify) {
                    Toast.makeText(getActivity(), "请先绑定手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                /**
                 * TODO template 如果是自定义短信模板，此处替换为你在控制台设置的自定义短信模板名称；如果没有对应的自定义短信模板，则使用默认短信模板。
                 */
                BmobSMS.requestSMSCode(phone, "HuaYu", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId, BmobException e) {
                        if (e == null) {
                            mTvInfo.append("发送验证码成功，短信ID：" + smsId + "\n");
                        } else {
                            mTvInfo.append("发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                        }
                    }
                });
                break;
            case R.id.btn_reset:
                String newPassword = mEdtNewPassword.getText().toString().trim();
                if (TextUtils.isEmpty(newPassword)) {
                    Toast.makeText(getActivity(), "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = mEdtCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(getActivity(), "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                BmobUser.resetPasswordBySMSCode(code, newPassword, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            mTvInfo.append("重置成功");
                        } else {
                            mTvInfo.append("重置失败：" + e.getErrorCode() + "-" + e.getMessage());
                        }
                    }
                });
                break;

            default:
                break;
        }
    }
}