package com.rxmuhammadyoussef.anabeesh.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.di.UIHostComponentProvider;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityComponent;
import com.rxmuhammadyoussef.anabeesh.di.fragment.FragmentModule;
import com.rxmuhammadyoussef.anabeesh.store.model.DrawerItem;
import com.rxmuhammadyoussef.anabeesh.ui.host.HostActivity;
import com.rxmuhammadyoussef.anabeesh.ui.login.LoginActivity;
import com.rxmuhammadyoussef.core.di.qualifier.ForFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

public class SettingsFragment extends Fragment implements SettingsScreen {

    @Inject
    SettingsPresenter presenter;
    @Inject
    @ForFragment
    CompositeDisposable disposable;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UIHostComponentProvider) {
            UIHostComponentProvider provider = ((UIHostComponentProvider) context);
            if (provider.getComponent() instanceof ActivityComponent) {
                ((ActivityComponent) provider.getComponent())
                        .plus(new FragmentModule(this))
                        .inject(this);
            } else {
                throw new IllegalStateException("Component must be " + ActivityComponent.class.getName());
            }
        } else {
            throw new AssertionError("host context must implement " + UIHostComponentProvider.class.getName());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof HostActivity) {
            ((HostActivity) getActivity()).setToolbarTitle(getString(R.string.settings));
            ((HostActivity) getActivity()).setSelectedItem(DrawerItem.SETTINGS);
        }
    }

    @Override
    public void onDetach() {
        disposable.clear();
        super.onDetach();
    }

    @Override
    public void onReadyToLogout() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.tv_logout)
    void onLogoutClick() {
        presenter.onLogoutClick();
    }

    @OnClick(R.id.tv_edit_profile)
    void onEditProfileClick() {

    }
}
