package com.rxmuhammadyoussef.anabeesh.ui.interests;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.di.UIHostComponentProvider;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityComponent;
import com.rxmuhammadyoussef.anabeesh.di.fragment.FragmentModule;
import com.rxmuhammadyoussef.anabeesh.store.model.DrawerItem;
import com.rxmuhammadyoussef.anabeesh.ui.category.CategoryFragment;
import com.rxmuhammadyoussef.anabeesh.ui.host.HostActivity;
import com.rxmuhammadyoussef.core.di.scope.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@FragmentScope
public class InterestsFragment extends Fragment {

    @BindView(R.id.pager)
    ViewPager viewPager;

    @Inject
    PagerAdapter pagerAdapter;

    public static InterestsFragment newInstance() {
        return new InterestsFragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inerests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupViewPager();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof HostActivity) {
            ((HostActivity) getActivity()).setToolbarTitle(getString(R.string.interests));
            ((HostActivity) getActivity()).setSelectedItem(DrawerItem.INTERESTS);
        }
    }

    private void setupViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(CategoryFragment.newInstance());
        pagerAdapter.setFragments(fragments);
        viewPager.setAdapter(pagerAdapter);
    }
}
