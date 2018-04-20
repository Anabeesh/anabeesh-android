package com.rxmuhammadyoussef.anabeesh.ui.category;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.di.UIHostComponentProvider;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityComponent;
import com.rxmuhammadyoussef.anabeesh.di.fragment.FragmentModule;
import com.rxmuhammadyoussef.anabeesh.store.model.DrawerItem;
import com.rxmuhammadyoussef.anabeesh.store.model.category.CategoryViewModel;
import com.rxmuhammadyoussef.anabeesh.ui.TitledView;
import com.rxmuhammadyoussef.anabeesh.ui.host.HostActivity;
import com.rxmuhammadyoussef.core.di.qualifier.ForFragment;
import com.rxmuhammadyoussef.core.di.scope.FragmentScope;
import com.rxmuhammadyoussef.core.util.UiUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

@FragmentScope
public class CategoryFragment extends Fragment implements CategoryScreen, TitledView {

    @BindView(R.id.rv_categories)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @Inject
    @ForFragment
    CompositeDisposable disposable;
    @Inject
    UiUtil uiUtil;
    @Inject
    RecyclerAdapter adapter;
    @Inject
    CategoryPresenter presenter;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
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
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.onViewCreated();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
        if (getActivity() instanceof HostActivity) {
            ((HostActivity) getActivity()).setToolbarTitle(getString(R.string.interests));
            ((HostActivity) getActivity()).setSelectedItem(DrawerItem.INTERESTS);
        }
    }

    @Override
    public void onDetach() {
        disposable.clear();
        super.onDetach();
    }

    @Override
    public void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setupRefreshLayout() {
        refreshLayout.setOnRefreshListener(presenter::fetch);
    }

    @Override
    public void showErrorMessage(String message) {
        uiUtil.getErrorToast(message)
                .show();
    }

    @Override
    public void showLoadingAnimation() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoadingAnimation() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void updateUi(DiffUtil.DiffResult diffResult) {
        diffResult.dispatchUpdatesTo(adapter);
    }

    @Override
    public void notifyCategoryChanged(int position) {
        adapter.notifyItemChanged(position);
    }

    @Override
    public String getTitle() {
        return getString(R.string.categories);
    }
}
