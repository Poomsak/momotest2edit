package com.example.lenovo.momotest2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lenovo.momotest2.Fragment.PageDetail;
import com.example.lenovo.momotest2.Fragment.PageMain;
import com.example.lenovo.momotest2.bus.BusProvider;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {

    private String TAG_PAGEMAIN = "TAGPAGEMAIN",TAG_PAGEDETAIL = "TAGPAGEDETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        BusProvider.getInstance().register(this);

        addFragmentFirst();
    }
    private void addFragmentFirst() {

        PageDetail tableBallFragment = PageDetail.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayoutPageMain, tableBallFragment)
                .detach(tableBallFragment)
                .commit();

        PageMain moreFragment = PageMain.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayoutPageMain, moreFragment)
                .detach(moreFragment)
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayoutPageMain, PageMain.newInstance())
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onClickPage(String tag){

        if (tag.length()>0) {
            onManagerFragment(tag);
        }

    }
    private void onManagerFragment(String tag){
        Fragment fragmentAttach = null;//นำเข้า

        if (tag.toString().trim().equals(TAG_PAGEMAIN)){
            fragmentAttach = (PageMain)
                    getSupportFragmentManager().findFragmentByTag(TAG_PAGEMAIN);

        }else if (tag.toString().trim().equals(TAG_PAGEDETAIL)){
            fragmentAttach = (PageDetail) getSupportFragmentManager().findFragmentByTag(TAG_PAGEDETAIL);

        }

        if (!onFragmentIsShow().equals(fragmentAttach))
        {
            getSupportFragmentManager().beginTransaction()
                    .attach(fragmentAttach)
                    .detach(onFragmentIsShow())
                    .commit();
        }

    }
    private Fragment onFragmentIsShow() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.frameLayoutPageMain);
        if (f != null) {
            return f;
        }
        return null;
    }

}
