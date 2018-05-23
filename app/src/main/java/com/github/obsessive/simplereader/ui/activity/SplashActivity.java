package com.github.obsessive.simplereader.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.obsessive.simplereader.presenter.Presenter;
import com.github.obsessive.simplereader.presenter.impl.SplashPresenterImpl;
import com.github.obsessive.simplereader.ui.activity.base.BaseActivity;
import com.github.obsessive.simplereader.view.SplashView;

import butterknife.InjectView;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.utils.TLog;
import plaidapp.io.simplereader.R;

public class SplashActivity extends BaseActivity implements SplashView {

    @InjectView(R.id.splash_image)
    ImageView splashImage;
    @InjectView(R.id.splash_version_name)
    TextView splashVersionName;
    @InjectView(R.id.splash_copyright)
    TextView splashCopyright;
    private Presenter mSplashPresenter = null;

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }
    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
    @Override
    public void navigateToHomePage() {
        readyGoThenKill(HomeActivity.class);
    }
    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }
    @Override
    protected void initViewsAndEvents() {
        TLog.i(TAG_LOG,"initViewsAndEvents");
        mSplashPresenter = new SplashPresenterImpl(this, this);
        mSplashPresenter.initialized();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }
    @Override
    protected void onNetworkDisConnected() {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void animateBackgroundImage(Animation animation) {
        splashImage.startAnimation(animation);
    }

    @Override
    public void initializeViews(String versionName, String copyright, int backgroundResId) {
        splashVersionName.setText(versionName);
        splashCopyright.setText(copyright);
        splashImage.setImageResource(backgroundResId);
    }

    @Override
    public void initializeUmengConfig() {

    }

}
