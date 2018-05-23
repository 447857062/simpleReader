package com.github.obsessive.simplereader.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.obsessive.simplereader.bean.NavigationEntity;
import com.github.obsessive.simplereader.presenter.Presenter;
import com.github.obsessive.simplereader.presenter.impl.HomePresenterImpl;
import com.github.obsessive.simplereader.ui.activity.base.BaseActivity;
import com.github.obsessive.simplereader.ui.adapter.VPFragmentAdapter;
import com.github.obsessive.simplereader.view.HomeView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.obsessive.library.adapter.ListViewDataAdapter;
import com.github.obsessive.library.adapter.ViewHolderBase;
import com.github.obsessive.library.adapter.ViewHolderCreator;
import com.github.obsessive.library.base.BaseLazyFragment;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.widgets.XViewPager;
import plaidapp.io.simplereader.R;

public class HomeActivity extends BaseActivity implements HomeView{

    @InjectView(R.id.home_container)
    XViewPager homeContainer;
    @InjectView(R.id.home_navigation_list)
    ListView homeNavigationList;
    @InjectView(R.id.home_drawer)
    DrawerLayout homeDrawer;
    private ActionBarDrawerToggle mActionBarDrawerToggle = null;
    private ListViewDataAdapter<NavigationEntity> mNavListAdapter = null;
    private int mCurrentMenuCheckedPos = 0;
    private int mCheckedListItemColorResIds[] = {
            R.color.navigation_checked_picture_text_color,
            R.color.navigation_checked_video_text_color,
            R.color.navigation_checked_music_text_color,
    };
    private Presenter mHomePresenter = null;
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
        return TransitionMode.FADE;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected View getLoadingTargetView() {

        return ButterKnife.findById(this, R.id.home_container);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return true;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected void initViewsAndEvents() {
        mHomePresenter = new HomePresenterImpl(this, this);
        mHomePresenter.initialized();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode()==KeyEvent.KEYCODE_MENU){

        }else if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void initializeViews(List<BaseLazyFragment> fragments, List<NavigationEntity> navigationList) {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, homeDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setTitle(getString(R.string.app_name));
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (null != mNavListAdapter) {
                    setTitle(mNavListAdapter.getItem(mCurrentMenuCheckedPos).getName());
                }
            }
        };

        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        homeDrawer.setDrawerListener(mActionBarDrawerToggle);
        if (null != fragments && !fragments.isEmpty()) {
            homeContainer.setEnableScroll(false);
            homeContainer.setOffscreenPageLimit(fragments.size());
            homeContainer.setAdapter(new VPFragmentAdapter(getSupportFragmentManager(), fragments));
        }
        mNavListAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<NavigationEntity>() {

            @Override
            public ViewHolderBase<NavigationEntity> createViewHolder(int position) {

                return new ViewHolderBase<NavigationEntity>() {
                    ImageView itemIcon;
                    TextView itemName;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.list_item_navigation, null);
                        itemIcon = ButterKnife.findById(convertView, R.id.list_item_navigation_icon);
                        itemName = ButterKnife.findById(convertView, R.id.list_item_navigation_name);

                        return convertView;
                    }

                    @Override
                    public void showData(int i, NavigationEntity navigationEntity) {
                        itemIcon.setImageResource(navigationEntity.getIconResId());
                        itemName.setText(navigationEntity.getName());

                        if (mCurrentMenuCheckedPos == i) {
                            // checked
                            itemName.setTextColor(getResources().getColor(mCheckedListItemColorResIds[i]));
                        } else {
                            // unchecked
                            itemName.setTextColor(getResources().getColor(android.R.color.black));
                        }
                    }
                };
            }
        });
        homeNavigationList.setAdapter(mNavListAdapter);
        mNavListAdapter.getDataList().addAll(navigationList);
        mNavListAdapter.notifyDataSetChanged();
        setTitle(mNavListAdapter.getItem(mCurrentMenuCheckedPos).getName());

        homeNavigationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentMenuCheckedPos = position;
                mNavListAdapter.notifyDataSetChanged();
                homeDrawer.closeDrawer(Gravity.LEFT);
                homeContainer.setCurrentItem(mCurrentMenuCheckedPos, false);
            }
        });
    }
}
