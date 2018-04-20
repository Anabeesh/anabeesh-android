package com.rxmuhammadyoussef.anabeesh.ui.host;

import android.support.v4.app.Fragment;
import android.widget.EditText;

import com.rxmuhammadyoussef.anabeesh.store.model.DrawerItem;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserViewModel;
import com.rxmuhammadyoussef.core.component.activity.BaseActivityScreen;

public interface HostActivityScreen extends BaseActivityScreen {

    void setLayoutDirection();

    void setupToolbar();

    void setToolbarTitle(String title);

    void setupNavigationDrawer(UserViewModel userViewModel);

    void setSelectedItem(@DrawerItem int selectedItem);

    void setFragment(Fragment fragment, String tag);

    EditText getSearchEditText();
}
