package se.kicksort.metalarchives;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.roughike.bottombar.BottomBar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Gustav Sundin on 01/03/17.
 */

public class BaseActivity extends AppCompatActivity {
    public NavigationManager navigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        BottomBar bottomMenu = (BottomBar) findViewById(R.id.bottom_menu);
        navigationManager = new NavigationManager(getSupportFragmentManager(), this);


        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
        }

        bottomMenu.setOnTabSelectListener(tabId -> {
            //
        });

        BandController bandController = new BandController();
        bandController.getBand("3540317214")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(band -> {
                    Log.d("METAL", band.toString());
                });
    }

    @Override
    public void onBackPressed() {
        // Note: we are intentionally not calling the super implementation since we have built or own navigation manager
        navigationManager.navigateBack();
    }
}
