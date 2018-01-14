package se.kicksort.metalarchives

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.inputmethod.InputMethodManager

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import se.kicksort.metalarchives.album.AlbumFragment
import se.kicksort.metalarchives.band.BandFragment

/**
 * Created by Gustav Sundin on 06/01/17.
 */

class NavigationManager(private val fragmentManager: FragmentManager, private val baseActivity: BaseActivity) {

    private val scrollSubject = PublishSubject.create<Int>()

    val scrollEvents: Observable<Int>
        get() = scrollSubject

    init {
        instance = this
    }

    fun open(fragment: Fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment.toString())
                .commit()
    }

    private fun openAsRoot(fragment: Fragment) {
        popEveryFragment()
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }

    private fun popEveryFragment() {
        val backStackCount = fragmentManager.backStackEntryCount
        for (i in 0 until backStackCount) {
            val backStackId = fragmentManager.getBackStackEntryAt(i).id
            fragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    fun navigateBack() {
        hideKeyboard(baseActivity)
        if (fragmentManager.backStackEntryCount == 0) {
            baseActivity.moveTaskToBack(false)
        } else {
            fragmentManager.popBackStackImmediate()
            baseActivity.showSearchBar()
        }
    }

    fun popToRoot() {
        val backStackCount = fragmentManager.backStackEntryCount
        for (i in 0 until backStackCount) {
            fragmentManager.popBackStackImmediate()
        }
    }

    fun openStartView() {
        val fragment = StartFragment()
        openAsRoot(fragment)
    }

    fun openAlbum(albumId: String) {
        val fragment = AlbumFragment()
        fragment.setAlbumId(albumId)
        open(fragment)
        fragment.scrollEvents.subscribe { scrollSubject.onNext(it) }
        baseActivity.showSearchBar()
    }

    fun openBand(bandName: String, bandId: String) {
        val bandFragment = BandFragment()
        bandFragment.setBand(bandName, bandId)
        NavigationManager.instance.open(bandFragment)
        bandFragment.scrollEvents.subscribe { scrollSubject.onNext(it) }
        baseActivity.showSearchBar()
    }

    companion object {
        lateinit var instance: NavigationManager

        private fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity.currentFocus
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
