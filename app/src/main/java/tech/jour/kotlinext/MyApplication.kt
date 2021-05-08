package tech.jour.kotlinext

import android.app.Application
import com.tencent.mmkv.MMKV

/**
 * Created by journey on 2021/5/8.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
    }
}