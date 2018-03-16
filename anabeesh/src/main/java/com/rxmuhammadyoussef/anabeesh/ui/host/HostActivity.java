package com.rxmuhammadyoussef.anabeesh.ui.host;

import android.graphics.Typeface;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.rxmuhammadyoussef.anabeesh.AnabeeshApplication;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.di.UIHostComponentProvider;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityComponent;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityModule;
import com.rxmuhammadyoussef.anabeesh.store.model.DrawerItem;
import com.rxmuhammadyoussef.anabeesh.ui.home.HomeFragment;
import com.rxmuhammadyoussef.core.component.activity.BaseActivity;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;

import java.util.Stack;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@ActivityScope
public class HostActivity extends BaseActivity implements HostScreen, Drawer.OnDrawerItemClickListener, UIHostComponentProvider {

    private final Stack<String> backStack = new Stack<>();
    private ActivityComponent activityComponent;
    private Drawer drawer;

    @BindView(R.id.tb_host)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView titleTextView;

    @Inject
    HostPresenter hostPresenter;

    @Override
    protected void onCreateActivity() {
        activityComponent = AnabeeshApplication.getComponent(this)
                .plus(new ActivityModule(this));
        activityComponent.inject(this);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_host;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_host, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            if (!backStack.empty()) {
                backStack.pop();
            }
            super.onBackPressed();
        }
    }

    @Override
    public void setLayoutDirection() {
        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    public void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public void setToolbarTitle(String title) {
        titleTextView.setText(title);
    }

    @Override
    public void setupNavigationDrawer() {
        AccountHeader header = createHeader();
        PrimaryDrawerItem home = createDrawerItem(R.string.home, DrawerItem.HOME, false);
        PrimaryDrawerItem bookmarks = createDrawerItem(R.string.bookmarks, DrawerItem.BOOKMARKS, false);
        PrimaryDrawerItem interests = createDrawerItem(R.string.interests, DrawerItem.INTERESTS, false);
        PrimaryDrawerItem joinUs = createDrawerItem(R.string.join_us, DrawerItem.JOIN_US, true);
        PrimaryDrawerItem settings = createDrawerItem(R.string.settings, DrawerItem.SETTINGS, false);
        DividerDrawerItem dividerDrawerItem = new DividerDrawerItem();
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .withSelectedItem(-1)
                .withSliderBackgroundColorRes(R.color.colorNavDrawerContentBackground)
                .addDrawerItems(home, bookmarks, interests, dividerDrawerItem, joinUs, dividerDrawerItem, settings)
                .withOnDrawerItemClickListener(this)
                .build();
    }

    @Override
    public void setSelectedItem(int selectedItem) {
        drawer.setSelection(selectedItem);
    }

    @Override
    public void setFragment(Fragment fragment, String tag) {
        if (!backStack.empty() && backStack.get(backStack.size() - 1).contentEquals(tag)) {
            return;
        }
        backStack.add(tag);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(tag)
                .commit();
    }

    private AccountHeader createHeader() {
        return new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorNavDrawerHeaderBackground)
                .withTextColorRes(R.color.colorTextPrimaryDark)
                .withTypeface(Typeface.MONOSPACE)
                .withDividerBelowHeader(false)
                .withAlternativeProfileHeaderSwitching(false)
                .withPaddingBelowHeader(true)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(new ProfileDrawerItem()
                        .withName("محمد يوسف محمد")
                        .withIcon(getResources().getDrawable(R.mipmap.ic_launcher)))
                .withOnAccountHeaderListener((view, profile, currentProfile) -> false)
                .build();
    }

    private PrimaryDrawerItem createDrawerItem(@StringRes int nameRes, @DrawerItem int itemIdentifier, boolean shouldHighlight) {
        PrimaryDrawerItem primaryDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(itemIdentifier)
                .withName(nameRes)
                .withSelectedColorRes(R.color.colorNavDrawerContentBackground)
                .withTypeface(Typeface.MONOSPACE);
        if (shouldHighlight) {
            primaryDrawerItem.withTextColorRes(R.color.colorAccent)
                    .withSelectedTextColorRes(R.color.colorAccent);
        } else {
            primaryDrawerItem.withTextColorRes(R.color.colorTextSecondaryLight)
                    .withSelectedTextColorRes(R.color.colorTextPrimaryDark);
        }
        return primaryDrawerItem;
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        if (view == null) {
            return false;
        }
        switch (view.getId()) {
            case DrawerItem.HOME:
                setFragment(new HomeFragment(), HomeFragment.class.getSimpleName());
                return false;
            case DrawerItem.BOOKMARKS:
                //TODO navigate to bookmarks
                return true;
            case DrawerItem.INTERESTS:
                //TODO navigate to interests
                return true;
            case DrawerItem.JOIN_US:
                //TODO navigate to join us
                return true;
            case DrawerItem.SETTINGS:
                //TODO navigate to settings
                return true;
            default:
                return false;
        }
    }

    @Override
    public ActivityComponent getComponent() {
        return activityComponent;
    }
}
