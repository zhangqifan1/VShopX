package com.zqf.vshop.mvp.MyselfMvp;

import net.arvin.socialhelper.SocialHelper;

public class SocialUtil {
    private static SocialUtil sInstance = new SocialUtil();

    private static SocialHelper socialHelper;

    private SocialUtil() {
        socialHelper = new SocialHelper.Builder()
                .setQqAppId("1106556586")
                .setWxAppId("wx8226584078a496da")
                .setWxAppSecret("2f386dbf4a8c7bc90c6acc7256f84cea")
                .setWbAppId("568898243")
                .setWbRedirectUrl("http://www.mob.com")
                .build();
    }

    public static SocialUtil getInstance() {
        return sInstance;
    }

    public SocialHelper socialHelper() {
        return socialHelper;
    }
}