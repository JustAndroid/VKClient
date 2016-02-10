package com.test.vkclient;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.test.vkclient.view.fragment.mainscreen.MainFragment;

import org.junit.Test;

import java.util.List;

/**
 * Created by Павел on 10.02.2016.
 */
public class MainFragmentTest extends ActivityInstrumentationTestCase2<MainFragmentActivity> {

    MainFragmentActivity mainFragmentActivity;
    MainFragment mainFragment;
    TextView nameTextView;
    TextView cityTextView;
    ImageView avatarImageView;
    ListView wallListView;


    public MainFragmentTest() {
        super(MainFragmentActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainFragmentActivity = getActivity();
        mainFragmentActivity = (MainFragmentActivity) getActivity();
        mainFragment = mainFragmentActivity.myFragment;
        nameTextView = (TextView)mainFragment.getActivity().findViewById(R.id.nameTextView);
        cityTextView = (TextView)mainFragment.getActivity().findViewById(R.id.cityTextView);
        avatarImageView = (ImageView)mainFragment.getActivity().findViewById(R.id.avatarImageView);
        wallListView = (ListView)mainFragment.getActivity().findViewById(R.id.wallListView);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testViewCreated() throws Exception {
        assertNotNull(mainFragment);
        assertNotNull(nameTextView);
        assertNotNull(cityTextView);
        assertNotNull(avatarImageView);
        assertNotNull(wallListView);
    }

    @Test
    public void testLoadVkInfo() throws Exception {
        assertNotSame("nameTextView is empty", "", nameTextView.getText());
        assertNotSame("cityTextView is empty", "", cityTextView.getText());
        assertNotSame("ListView is Empty", 0, wallListView.getCount());
    }
}

