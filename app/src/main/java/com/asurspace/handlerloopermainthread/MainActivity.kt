package com.asurspace.handlerloopermainthread

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.asurspace.handlerloopermainthread.contract.ICustomTitleProvider
import com.asurspace.handlerloopermainthread.contract.ICustomToolbarActionProvider
import com.asurspace.handlerloopermainthread.contract.Navigator
import com.asurspace.handlerloopermainthread.contract.model.CustomAction
import com.asurspace.handlerloopermainthread.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateToolbarUI()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_container, MenuFragment())
                .commit()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        updateToolbarUI()
        return super.onCreateOptionsMenu(menu)
    }

    override fun showFirstHandlerVariantFragment() {
        openFragment(FirstFragment())
    }

    override fun showSecondHandlerVariantFragment() {
        openFragment(SecondFragment())
    }

    private fun openFragment(fragment: Fragment, clearBackstack: Boolean = false) {
        if (clearBackstack) {
            clearBackStack()
        }
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .addToBackStack(fragment::class.java.simpleName)
            .replace(R.id.main_container, fragment, fragment::class.java.simpleName)
            .commit()
    }

    private fun clearBackStack() =
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun updateToolbarUI() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.main_container)
        // set custom title listener
        if (currentFragment is ICustomTitleProvider) {
            binding.toolbar.title = getString(currentFragment.provideTitle())
        } else {
            binding.toolbar.setTitle(R.string.app_name)
        }
        // set action button listener
        if (currentFragment is ICustomToolbarActionProvider) {
            createCustomAction(currentFragment.provideAction())
        } else {
            binding.toolbar.menu.clear()
        }
        // set home button listener
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            supportActionBar?.setDisplayShowHomeEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun createCustomAction(action: CustomAction) {
        binding.toolbar.menu.clear()

        val iconRes = DrawableCompat.wrap(ContextCompat.getDrawable(this, action.icon)!!)
        iconRes.setTint(Color.WHITE)

        val menuItem = binding.toolbar.menu.add(action.description)
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        menuItem.icon = iconRes
        menuItem.setOnMenuItemClickListener {
            action.runnable.run()
            return@setOnMenuItemClickListener true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    companion object {
        @JvmStatic
        private val TAG = MainActivity::class.java.simpleName
    }

}