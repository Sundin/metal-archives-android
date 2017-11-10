package se.kicksort.metalarchives;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.album.AlbumFragment;
import se.kicksort.metalarchives.band.BandFragment;

/**
 * Created by Gustav Sundin on 06/01/17.
 */

public class NavigationManager {
    private FragmentManager fragmentManager;
    private BaseActivity baseActivity;
    private static NavigationManager instance;

    public NavigationManager(FragmentManager fragmentManager, BaseActivity baseActivity) {
        this.fragmentManager = fragmentManager;
        this.baseActivity = baseActivity;
        instance = this;
    }

    public static NavigationManager getInstance() {
        return instance;
    }

    public void open(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment.toString())
                .commit();
    }

    private void openAsRoot(Fragment fragment) {
        popEveryFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void popEveryFragment() {
        int backStackCount = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            int backStackId = fragmentManager.getBackStackEntryAt(i).getId();
            fragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void navigateBack() {
        hideKeyboard(baseActivity);
        if (fragmentManager.getBackStackEntryCount() == 0) {
            baseActivity.moveTaskToBack(false);
        } else {
            fragmentManager.popBackStackImmediate();
            baseActivity.showSearchBar();
        }
    }

    public void popToRoot() {
        int backStackCount = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            fragmentManager.popBackStackImmediate();
        }
    }

    private static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void openStartView() {
        StartFragment fragment = new StartFragment();
        openAsRoot(fragment);
    }

    public void openAlbum(String albumId) {
        AlbumFragment fragment = new AlbumFragment();
        fragment.setAlbumId(albumId);
        open(fragment);
        fragment.getScrollEvents().subscribe(scrollSubject::onNext);
        baseActivity.showSearchBar();
    }

    public void openBand(String bandName, String bandId) {
        BandFragment bandFragment = new BandFragment();
        bandFragment.setBand(bandName, bandId);
        NavigationManager.getInstance().open(bandFragment);
        bandFragment.getScrollEvents().subscribe(scrollSubject::onNext);
        baseActivity.showSearchBar();
    }

    private final PublishSubject<Integer> scrollSubject = PublishSubject.create();

    public Observable<Integer> getScrollEvents() {
        return scrollSubject;
    }
}
