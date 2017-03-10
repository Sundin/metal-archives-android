package se.kicksort.metalarchives;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Gustav Sundin on 06/01/17.
 */

public class NavigationManager {
    private FragmentManager fragmentManager;
    private Activity baseActivity;
    private static NavigationManager instance;

    public NavigationManager(FragmentManager fragmentManager, Activity baseActivity) {
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
}
