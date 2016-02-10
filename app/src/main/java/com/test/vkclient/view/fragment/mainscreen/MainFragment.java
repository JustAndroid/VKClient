package com.test.vkclient.view.fragment.mainscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.vkclient.R;
import com.test.vkclient.model.User;
import com.test.vkclient.model.WallResponse;
import com.test.vkclient.utils.DataBaseHelper;
import com.test.vkclient.view.adapter.WallAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Павел on 08.02.2016.
 * Главный экран - отображение информации о пользвателе и стены
 */
public class MainFragment extends Fragment implements MainFragmentView{

    @Bind(R.id.nameTextView)TextView nameTextView;
    @Bind(R.id.cityTextView)TextView cityTextView;
    @Bind(R.id.avatarImageView)ImageView avatarImageView;
    @Bind(R.id.wallListView)ListView wallListView;
    @Bind(R.id.swipe_refresh_layout)SwipeRefreshLayout swipeRefreshLayout;
    private int totaloffset;
    private WallResponse wallResponse;
    private WallAdapter wallAdapter;
    private DataBaseHelper dataBaseHelper;
    private MainFragmentPresenterImpl mainFragmentPresenter;
    public static final int COUNTOFPOSTS = 10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_main, container, false);
        ButterKnife.bind(this, view);

        dataBaseHelper = new DataBaseHelper(getActivity());
        mainFragmentPresenter = new MainFragmentPresenterImpl(this);
        mainFragmentPresenter.callGetUserInfo(dataBaseHelper);
        mainFragmentPresenter.callGetWall(COUNTOFPOSTS, 0);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainFragmentPresenter.callGetWall(COUNTOFPOSTS,0);
            }
        });

        //если последний видимые элемент списка = предпоследнем элементу аррейлиста, грузим более старые посты
        wallListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (wallListView.getLastVisiblePosition() == mainFragmentPresenter.getWallResponse().getItems().size() - 1) {
                    mainFragmentPresenter.callGetWall(COUNTOFPOSTS, mainFragmentPresenter.getTotaloffset());
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        return view;
    }

    //Установка инфы о пользователе в вью
    @Override
    public void setUserInfo(User user) {
        nameTextView.setText(user.getFirst_name() + " " + user.getLast_name());
        cityTextView.setText(user.getCity().getTitle());
        Picasso.with(getActivity()).load(user.getPhoto_max()).into(avatarImageView);
    }

    //Остановить pull to refresh
    @Override
    public void stopRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    //установить адаптер для листвью
    @Override
    public void setWallAdapter(WallResponse wallResponse) {
        wallAdapter = new WallAdapter(getActivity(), wallResponse.getItems(), wallResponse.getProfiles());
        wallListView.setAdapter(wallAdapter);
    }

    //добавить более старые посты в адаптер
    @Override
    public void notifyAdapter() {
        wallAdapter.notifyDataSetChanged();
    }
}
