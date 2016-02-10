package com.test.vkclient.view.fragment.mainscreen;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.test.vkclient.model.User;
import com.test.vkclient.model.WallResponse;
import com.test.vkclient.utils.DataBaseHelper;
import com.test.vkclient.view.activity.MainActivityMethods;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Павел on 09.02.2016.
 */
public class MainFragmentPresenterImpl implements MainFragmentPresenter {


    private MainFragmentView mainFragmentView;
    private WallResponse wallResponse;
    //Переменная смещения для подгрузки более старых постов
    private int totaloffset;

    public MainFragmentPresenterImpl (MainFragmentView mainFragmentView) {
        this.mainFragmentView = mainFragmentView;
    }


    //Метод для получение информации о пользователе
    @Override
    public void callGetUserInfo(final DataBaseHelper dataBaseHelper) {
        VKRequest userRequest = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_max, city"));
        userRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                //Преобразование в класс User и кеширование
                try {
                    JSONArray array = response.json.getJSONArray("response");
                    String resp = array.get(0).toString();
                    Gson gson = new Gson();
                    User user = gson.fromJson(resp, User.class);
                    SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
                    dataBaseHelper.writeToDatabase(db, user);
                    mainFragmentView.setUserInfo(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            //Если не удается загрузить данные, берем из кеша
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
                SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
                User user = dataBaseHelper.readDatabase(db);
                mainFragmentView.setUserInfo(user);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
                User user = dataBaseHelper.readDatabase(db);
                mainFragmentView.setUserInfo(user);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });
    }

    //Загрузка стены
    @Override
    public void callGetWall(int count, final int offset) {
        VKRequest wallReuest = VKApi.wall().get(VKParameters.from(VKApiConst.COUNT, count, VKApiConst.OFFSET, offset, VKApiConst.EXTENDED, 1));
        wallReuest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {

                    //если смещение = 0, грузим последние посты и формируем адаптер, если !=0, грузим более старые посты
                    //и добавляем их в адаптер
                    String string = response.json.getString("response");
                    Gson gson = new Gson();
                    if (offset == 0) {
                        wallResponse = gson.fromJson(string, WallResponse.class);
                        mainFragmentView.setWallAdapter(wallResponse);
                        mainFragmentView.stopRefresh();
                    } else {
                        WallResponse wallResponsetemp = gson.fromJson(string, WallResponse.class);
                        wallResponse.getItems().addAll(wallResponsetemp.getItems());
                        wallResponse.getProfiles().addAll(wallResponsetemp.getProfiles());
                        mainFragmentView.notifyAdapter();
                    }

                    //увеличиваем смещение
                    totaloffset = totaloffset + MainFragment.COUNTOFPOSTS;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.d("ERROR", "ERROR");
                mainFragmentView.stopRefresh();
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });
    }

    public WallResponse getWallResponse() {
        return wallResponse;
    }

    public int getTotaloffset() {
        return totaloffset;
    }
}
