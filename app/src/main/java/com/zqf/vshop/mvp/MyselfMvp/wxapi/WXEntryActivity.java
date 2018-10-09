package com.zqf.vshop.mvp.MyselfMvp.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zqf.vshop.mvp.MyselfMvp.SocialUtil;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String wxAppId = SocialUtil.getInstance().socialHelper().getBuilder().getWxAppId();
        api = WXAPIFactory.createWXAPI(this, wxAppId, true);
        api.registerApp(wxAppId);

        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        //登录
        if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
                String code = ((SendAuth.Resp) baseResp).code;
                SocialUtil.getInstance().socialHelper().sendAuthBackBroadcast(this, code);
            } else {
                SocialUtil.getInstance().socialHelper().sendAuthBackBroadcast(this, null);
            }
        } else if (baseResp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
            if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
                SocialUtil.getInstance().socialHelper().sendShareBackBroadcast(this, true);
            } else {
                SocialUtil.getInstance().socialHelper().sendShareBackBroadcast(this, false);
            }
        }
        onBackPressed();
    }
}