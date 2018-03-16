package com.rxmuhammadyoussef.anabeesh.ui.host;

import android.support.v4.app.Fragment;

import com.rxmuhammadyoussef.anabeesh.store.model.DrawerItem;
import com.rxmuhammadyoussef.core.component.activity.BaseScreen;

public interface HostScreen extends BaseScreen {

    void setLayoutDirection();

    void setupToolbar();

    void setToolbarTitle(String title);

    void setupNavigationDrawer();

    void setSelectedItem(@DrawerItem int selectedItem);

    void setFragment(Fragment fragment, String tag);
}
