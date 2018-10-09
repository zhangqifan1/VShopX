package com.zqf.vshop.mvp.ImMvp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zqf.vshop.R;
import com.zqf.vshop.databinding.FragmentImFmBinding;
import com.zqf.zhangqflibrary.base.fragment.BaseFragment;

import util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImFm extends BaseFragment<FragmentImFmBinding> {


    public static ImFm getInstance() {
        Bundle args = new Bundle();
        ImFm fragment = new ImFm();
        fragment.setArguments(args);
        return fragment;
    }

    public ImFm() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_im_fm;
    }

    @Override
    public void initView(View rootView) {
        // 判断sdk是否登录成功过，并没有退出和被踢，否则跳转到登陆界面
//        if (!EMClient.getInstance().isLoggedInBefore()) {
//            Intent intent = new Intent(getActivity(), ECLoginActivity.class);
//            startActivity(intent);
//            return;
//        }

        mViewBinding.ecBtnStartChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取我们发起聊天的者的username
                String chatId = mViewBinding.ecEditChatId.getText().toString().trim();
                if (!TextUtils.isEmpty(chatId)) {
                    // 获取当前登录用户的 username
                    String currUsername = EMClient.getInstance().getCurrentUser();
                    if (chatId.equals(currUsername)) {
                        ToastUtil.showShort("不能和自己聊天");
                        return;
                    }
                    // 跳转到聊天界面，开始聊天
                    Intent intent = new Intent(getActivity(), ECChatActivity.class);
                    intent.putExtra("ec_chat_id", chatId);
                    startActivity(intent);
                } else {
                    ToastUtil.showShort("Username 不能为空");
                }
            }
        });

        mViewBinding.ecBtnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    /**
     * 退出登录
     */
    private void signOut() {
        // 调用sdk的退出登录方法，第一个参数表示是否解绑推送的token，没有使用推送或者被踢都要传false
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(getActivity(),ECLoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

}
