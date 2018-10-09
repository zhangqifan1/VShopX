package com.zqf.vshop;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zqf.vshop.databinding.ActivityMainBinding;
import com.zqf.vshop.mvp.ImMvp.ImFm;
import com.zqf.vshop.mvp.MallFm.MallFm;
import com.zqf.vshop.mvp.MapMvp.MapFm;
import com.zqf.vshop.mvp.MyselfMvp.MyselfFm;
import com.zqf.vshop.mvp.MyselfMvp.SocialUtil;
import com.zqf.vshop.mvp.NewsMvp.NewsFm;
import com.zqf.zhangqflibrary.base.activity.BaseActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.MultiActionsNotificationBuilder;
import me.yokeyword.fragmentation.SupportFragment;
import util.ToastUtil;


public class MainActivity extends BaseActivity<ActivityMainBinding> {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int Forth = 3;
    public static final int Fifth = 4;

    private SupportFragment[] mFragments = new SupportFragment[5];

    private String[] titles = {"资讯", "电商", "通讯", "地图", "我的"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        boolean isFirst = extras.getBoolean("isFirst");
        if (isFirst)
            ToastUtil.showAtCenter(getResources().getString(R.string.first_enter_Message), 2000);
    }

    @Override
    public void initView() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new NewsFm());
        fragments.add(new MallFm());
        fragments.add(new ImFm());
        fragments.add(new MapFm());
        fragments.add(new MyselfFm());
        mViewBinding.SlidingTabLayout.setViewPager(mViewBinding.ViewPager, titles, this, fragments);
    }
    @Override
    protected void initData() {
        intiNotifyCation();
    }



    @Override
    protected void initListener() {
        String s = sHA1(this);
        System.out.println("sha1:"+s);
    }

    @Override
    protected void beforeInitView(Bundle savedInstanceState) {
        super.beforeInitView(savedInstanceState);
        if (savedInstanceState == null) {
            mFragments[FIRST] = NewsFm.getInstance();
            mFragments[SECOND] = MallFm.getInstance();
            mFragments[THIRD] = ImFm.getInstance();
            mFragments[Forth] = MapFm.getInstance();
            mFragments[Fifth] = MyselfFm.getInstance();

            loadMultipleRootFragment(R.id.ViewPager, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[Forth],
                    mFragments[Fifth]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = findFragment(NewsFm.class);
            mFragments[SECOND] = findFragment(MallFm.class);
            mFragments[THIRD] = findFragment(ImFm.class);
            mFragments[Forth] = findFragment(MapFm.class);
            mFragments[Fifth] = findFragment(MyselfFm.class);
        }
    }


    @Override
    public void onBackPressedSupport() {
//        onStateNotSaved();
        if (!isFinishing()) {
            super.onBackPressed();
        }
        super.onBackPressedSupport();
    }

    //用处：qq登录和分享回调，以及微博登录回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && SocialUtil.getInstance().socialHelper() != null) {//qq分享如果选择留在qq，通过home键退出，再进入app则不会有回调
            SocialUtil.getInstance().socialHelper().onActivityResult(requestCode, resultCode, data);
        }
    }
    //用处：微博分享回调
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (SocialUtil.getInstance().socialHelper() != null) {
            SocialUtil.getInstance().socialHelper().onNewIntent(intent);
        }
    }

    private void intiNotifyCation() {
        //设置自定义通知栏样式1
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(MainActivity.this);
        //指定状态栏的小图标
        builder.statusBarDrawable = R.drawable.ic_launcher_background;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builder);

        //定制带按钮的Notification样式
        MultiActionsNotificationBuilder builder2 = new MultiActionsNotificationBuilder(MainActivity.this);
        //添加按钮，参数(按钮图片、按钮文字、扩展数据)
        builder2.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "first", "my_extra1");
        builder2.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "second", "my_extra2");
        builder2.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "third", "my_extra3");
        JPushInterface.setPushNotificationBuilder(2, builder2);

        //自定义通知栏样式3
        // 指定定制的 Notification Layout
        CustomPushNotificationBuilder builder3 = new
                CustomPushNotificationBuilder(MainActivity.this,
                R.layout.view_notification,
                R.id.icon,
                R.id.title,
                R.id.text);
        // 指定最顶层状态栏小图标
        builder3.statusBarDrawable = R.drawable.ic_launcher_background;
        // 指定下拉状态栏时显示的通知图标
        builder3.layoutIconDrawable = R.drawable.ic_launcher_background;
        JPushInterface.setPushNotificationBuilder(3, builder3);
    }

    /**
     * 转完的字符串每2个跟一个冒号 切记切记
     */
    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
