package com.rxmuhammadyoussef.anabeesh.ui.interests;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rxmuhammadyoussef.anabeesh.ui.TitledView;
import com.rxmuhammadyoussef.core.di.scope.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@FragmentScope
public class PagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();

    @Inject
    PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    void setFragments(List<Fragment> fragments) {
        this.fragments.clear();
        this.fragments.addAll(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (fragments.get(position) instanceof TitledView) {
            return ((TitledView) fragments.get(position)).getTitle();
        } else {
            return "Title";
        }
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
