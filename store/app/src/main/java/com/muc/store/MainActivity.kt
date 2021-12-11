package com.muc.store

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.KeyEvent
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.muc.store.activity.InitActivity
import com.muc.store.activity.SearchActivity
import com.muc.store.activity.StartActivity
import com.muc.store.annotations.LayoutBinder
import com.muc.store.annotations.ViewBinder
import com.muc.store.config.AppConfig
import com.muc.store.fragment.AppFragment
import com.muc.store.fragment.MainFragment
import com.muc.store.fragment.ManagementFragment
import com.muc.store.util.ToastUtil
import com.muc.wide.Utils.ConfigUtils
import com.muc.wide.base.BaseActivity
import com.muc.wide.base.BaseFragment
import java.io.File
import android.widget.Toast
import org.json.JSONArray


@LayoutBinder(R.layout.activity_main)
class MainActivity : BaseActivity() {
    @ViewBinder(R.id.main_toolbar)
    lateinit var mToolbar: TextView

    @ViewBinder(R.id.main_btn_search)
    private lateinit var mSearch: ImageView
    private var mMainFragment: MainFragment = MainFragment()
    private var mAppFragment: AppFragment = AppFragment()
    private var mManagementFragment: ManagementFragment = ManagementFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, StartActivity::class.java))
        if (InitActivity.isAndroid11() || InitActivity.isAndroid8(this)) {
            startActivity(Intent(this, InitActivity::class.java))
            return
        }
        /**
         * 检查配置文件
         */
        if (!File(AppConfig.getAppConfigPath()).exists()) {
            ConfigUtils.createConfig(AppConfig.getAppConfigPath(), HashMap<Any, Any>().apply {
                put("ALLOW_MOBILE_DOWNLOAD", true)
            })
        }
        if (!File(AppConfig.getDownloadRecordConfig()).exists()) {
            ConfigUtils.createConfig(AppConfig.getDownloadRecordConfig(), HashMap<Any, Any>().apply {
                put("record", JSONArray())
            })
        }
        initFragment()
        mSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    private fun initFragment() {
        supportFragmentManager.beginTransaction().add(R.id.main_fragment_layout, mMainFragment)
            .add(R.id.main_fragment_layout, mAppFragment)
            .add(R.id.main_fragment_layout, mManagementFragment).commitNow()
        hideAllFragment(mMainFragment)
        findViewById<BottomNavigationView>(R.id.main_bab).apply {
            setOnNavigationItemSelectedListener { item ->
                val title = item.title.toString()
                when (item.itemId) {
                    R.id.menu_main_main -> {
                        hideAllFragment(mMainFragment)
                        mToolbar.text = "推荐"
                    }
                    R.id.menu_main_app -> {
                        hideAllFragment(mAppFragment)
                        mToolbar.text = "应用中心"
                    }
                    R.id.menu_main_setting -> {
                        hideAllFragment(mManagementFragment)
                        mToolbar.text = "设置"
                    }
                }
                true
            }
        }
    }

    /**
     * 隐藏所有Fragment
     */
    private fun hideAllFragment(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction().hide(mMainFragment).hide(mAppFragment)
            .hide(mManagementFragment)
            .show(fragment).commit()
    }

    private val MESSAGE_BACK = 1
    private var isFlag = true

    @SuppressLint("HandlerLeak")
    private val handler: Handler = object : Handler() {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSAGE_BACK -> isFlag = true // 在2s时,恢复isFlag的变量值
            }
        }
    }


    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && isFlag) {
            isFlag = false
            ToastUtil.show("再点一次退出应用")
            handler.sendEmptyMessageDelayed(MESSAGE_BACK, 2000)
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 保证在activity退出前,移除所有未被执行的消息和回调方法,避免出现内存泄漏!
        handler.removeCallbacksAndMessages(null)
    }
}