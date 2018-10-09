package com.zqf.vshop.mvp.MyselfMvp;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zqf.vshop.R;
import com.zqf.vshop.bean.XFBean;
import com.zqf.vshop.databinding.FragmentMyselfFmBinding;
import com.zqf.zhangqflibrary.base.fragment.BaseFragment;

import net.arvin.socialhelper.callback.SocialLoginCallback;
import net.arvin.socialhelper.callback.SocialShareCallback;
import net.arvin.socialhelper.entities.QQShareEntity;
import net.arvin.socialhelper.entities.ShareEntity;
import net.arvin.socialhelper.entities.ThirdInfoEntity;
import net.arvin.socialhelper.entities.WBShareEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyselfFm extends BaseFragment<FragmentMyselfFmBinding> implements View.OnClickListener, UniversalVideoView.VideoViewCallback {

    private static final String TAG = "MyselfFm";
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    private static final String VIDEO_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

    UniversalVideoView mVideoView;
    UniversalMediaController mMediaController;

    View mBottomLayout;
    View mVideoLayout;
    TextView mStart;

    private int mSeekPosition;
    private int cachedHeight;
    private boolean isFullscreen;
    public static MyselfFm getInstance() {
        Bundle args = new Bundle();
        MyselfFm fragment = new MyselfFm();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_myself_fm;
    }

    @Override
    public void initView(View rootView) {
        mVideoLayout = mViewBinding.videoLayout;
        mBottomLayout = mViewBinding.bottomLayout;
        mVideoView = mViewBinding.videoView;
        mMediaController = mViewBinding.mediaController;
        mVideoView.setMediaController(mMediaController);
        setVideoAreaSize();
        mVideoView.setVideoViewCallback(this);
        mStart = mViewBinding.start;
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSeekPosition > 0) {
                    mVideoView.seekTo(mSeekPosition);
                }
                mVideoView.start();
                mMediaController.setTitle("Big Buck Bunny");
            }
        });

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "onCompletion ");
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {

        mViewBinding.QQLogin.setOnClickListener(this);
        mViewBinding.WBLogin.setOnClickListener(this);
        mViewBinding.QQshare.setOnClickListener(this);
        mViewBinding.Wbshare.setOnClickListener(this);
        mViewBinding.voiceHc.setOnClickListener(this);
        mViewBinding.voiceSb.setOnClickListener(this);
        mViewBinding.MobSMS.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.QQLogin:

                SocialUtil.getInstance().socialHelper().loginQQ(getActivity(), new SocialLoginCallback() {
                    @Override
                    public void loginSuccess(ThirdInfoEntity info) {
                        String nickname = info.getQqInfo().getNickname();
                        Toast.makeText(getContext(), nickname+"", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void socialError(String msg) {

                    }
                });
                break;
            case R.id.WBLogin:
                SocialUtil.getInstance().socialHelper().loginWB(getActivity(), new SocialLoginCallback() {
                    @Override
                    public void loginSuccess(ThirdInfoEntity info) {
                        ToastUtil.showShort(info.getWbInfo().getCity());
                    }

                    @Override
                    public void socialError(String msg) {
                        ToastUtil.showShort(msg.toString());
                    }
                });
                break;
            case R.id.QQshare:
                ShareEntity appInfo = QQShareEntity.createAppInfo("标题", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2263453617,2764418059&fm=27&gp=0.jpg", "简要说明", "AppName");
                SocialUtil.getInstance().socialHelper().shareQQ(getActivity(), new SocialShareCallback() {
                    @Override
                    public void shareSuccess() {

                    }

                    @Override
                    public void socialError(String msg) {
                        ToastUtil.showShort(msg.toString());
                    }
                }, appInfo);
                break;
            case R.id.Wbshare:
                ShareEntity shareEntity =
                        WBShareEntity.createTextInfo("你好啊,我是赛利亚");
                SocialUtil.getInstance().socialHelper().shareWB(getActivity(), new SocialShareCallback() {
                    @Override
                    public void shareSuccess() {

                    }

                    @Override
                    public void socialError(String msg) {
                        ToastUtil.showShort(msg.toString());
                    }
                }, shareEntity);
                break;
            case R.id.voiceHc:
                // 申请录音权限
                AndPermission.with(this)
                        .permission(Permission.RECORD_AUDIO)
                        .rationale(new DefaultRationale())
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                ToastUtil.showShort("录音权限申请成功");
                            }
                        }).onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtil.showShort("录音权限申请失败");
                    }
                })
                        .start();
                Listen();

                break;
            case R.id.voiceSb:
                shuo("临兵斗者皆阵列在前");
                break;
            case R.id.MobSMS:
                SMSsdkUI();
                break;
            default:
                break;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause ");
        if (mVideoView != null && mVideoView.isPlaying()) {
            mSeekPosition = mVideoView.getCurrentPosition();
            Log.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
            mVideoView.pause();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState Position=" + mVideoView.getCurrentPosition());
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        mSeekPosition = savedInstanceState.getInt(SEEK_POSITION_KEY);
//        Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
//    }

    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        if (isFullscreen) {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.GONE);

        } else {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = this.cachedHeight;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onPause(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPause UniversalVideoView callback");
    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onBufferingStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
    }

    @Override
    public boolean onBackPressedSupport() {
        if (this.isFullscreen) {
            mVideoView.setFullscreen(false);
        } else {
            super.onBackPressedSupport();
        }
        return super.onBackPressedSupport();
    }


    private StringBuilder mStringBuilder;

    /**
     * 把声音转换为文字
     */
    public void Listen() {
        //1.创建RecognizerDialog对象,第二个参数就是一个初始化的监听器,我们用不上就设置为null
        RecognizerDialog mDialog = new RecognizerDialog(getActivity(), null);
        //2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");//设置为中文模式
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");//设置普通话模式
        //若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
        //mDialog.setParameter("asr_sch", "1");
        //mDialog.setParameter("nlp_version", "2.0");
        //创建一个装每次解析数据的容器
        mStringBuilder = new StringBuilder();
        //3.设置回调接口
        mDialog.setListener(new RecognizerDialogListener() {
            @Override//识别成功执行,参数recognizerResult 识别的结果,Json格式的字符串
            //第二参数 b:等于true时会话结束.方法才不会继续回调
            //一般情况下通过onResult接口多次返回结果,完整识别内容是多次结果累加的
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                //拿到讯飞识别的结果
                String resultString = recognizerResult.getResultString();
/*                System.out.println("讯飞识别的结果 " + resultString);
                System.out.println("b参数是什么 " + b);*/
                //自定义解析bean数据的方法,得到解析数据
                String content = parseData(resultString);
//                System.out.println("解析后的数据 "+ content);
                mStringBuilder.append(content);
                //对参数2b进行判断,如果为true,代表这个方法不会对调,那么我们容器的数据转为字符串,拿来使用即可
                if (b) {
                    String result = mStringBuilder.toString();
//                    Toast.makeText(getActivity(), ""+result, Toast.LENGTH_SHORT).show();
//                    //回答对象,在没有匹配到用户说的话,默认输出语句
//                    String anwser="不好意思,请大声点，老子耳朵不好使";
//                    if(result.contains("你好")){
//                        anwser="你好,我是你的智能语音助手,很高兴为你服务";
//                    }else if(result.contains("安卓学习哪家强")){
//                        anwser="我有橘麻麦皮不知当讲不当讲";
//                    }else if(result.contains("美女")){
//                        String [] anwserList=new String[]{"你是坏人不和你玩","小助手很纯洁,不要说我听不懂的话","小助手我就是美女,主人","500元,小助手帮主人找美女一起打英雄联盟"};
//                        int random = (int)(Math.random() * anwserList.length);
//                        anwser=anwserList[random];
//                    }
                    shuo(result);
                }
            }

            @Override//识别失败执行的方法,speechError错误码
            public void onError(SpeechError speechError) {
                System.out.println("错误码 " + speechError);
            }
        });
        //4.显示dialog，接收语音输入
        mDialog.show();
    }

    /***一个自定义的方法***/
    private String parseData(String resultString) {
        //创建gson对象.记得要关联一下gson.jar包,方可以使用
        Gson gson = new Gson();
        //参数1 String类型的json数据   参数2.存放json数据对应的bean类
        XFBean xfBean = gson.fromJson(resultString, XFBean.class);
        //创建集合,用来存放bean类里的对象
        ArrayList<XFBean.WS> ws = xfBean.ws;
        //创建一个容器,用来存放从每个集合里拿到的数据,使用StringBUndle效率高
        StringBuilder stringBuilder = new StringBuilder();
        for (XFBean.WS w : ws) {
            String text = w.cw.get(0).w;
            stringBuilder.append(text);
        }
        //把容器内的数据转换为字符串返回出去
        return stringBuilder.toString();
    }


    /******************************语音合成代码*****************************/

    /**
     * 把文字转换为声音
     */
    public void shuo(String result) {
        //1.创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(getActivity(), null);
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        // 设置发音人（更多在线发音人，用户可参见 附录13.2
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端,这些功能用到了讯飞服务器,所以要有网络
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
        //仅支持保存为 pcm 和 wav 格式，如果不需要保存合成音频，注释该行代码
        // mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
        // 3.开始合成,第一个参数就是转换成声音的文字,自定义,第二个参数就是合成监听器对象,我们不需要对声音有什么特殊处理,就传null
        mTts.startSpeaking(result, null);
    }

    private void SMSsdkUI() {
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
// 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");
// 提交用户信息（此方法可以不调用）
//                    registerUser(country, phone);
                }
                //提交验证码成功
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE)
                {
                    ToastUtil.showShort("成功");
                }

//                //提交验证码成功，此时已经验证成功了
//                else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE)
//                {
//                    System.out.println("2222222222");
//                }

            }
        });
        registerPage.show(getContext());
    }

    /**
     * 置视频区域大小
     */
    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                mVideoView.setVideoPath(VIDEO_URL);
                mVideoView.requestFocus();
            }
        });
    }
}
