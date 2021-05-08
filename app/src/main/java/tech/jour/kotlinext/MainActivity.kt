package tech.jour.kotlinext

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tencent.mmkv.MMKV
import tech.jour.kotlinext.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private val mmkv: MMKV = MMKV.defaultMMKV()!!

    private var boolean by mmkv.boolean(key = "boolean", defaultValue = false)
    private var int by mmkv.int(key = "int", defaultValue = 0)
    private var long by mmkv.long("long", 0L)
    private var float by mmkv.float(key = "float", defaultValue = 0.0F)
    private var double by mmkv.double(key = "double", defaultValue = 0.0)
    private var byteArray by mmkv.byteArray(key = "byteArray")
    private var string by mmkv.string(key = "string")
    private var stringSet by mmkv.stringSet(key = "stringSet")

    private companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        boolean = true
        int = 100
        long = 100L
        float = 100F
        double = 100.0
        byteArray = ByteArray(100).apply {
            for (i in 0 until 100) {
                set(i, i.toByte())
            }
        }
        string = "MainActivity"
        stringSet = HashSet<String>().apply {
            for (i in 0 until 100) {
                add("第($i)个")
            }
        }

        Log.i(TAG, "boolean:$boolean")
        Log.i(TAG, "int:$int")
        Log.i(TAG, "long:$long")
        Log.i(TAG, "float:$float")
        Log.i(TAG, "double:$double")
        Log.i(TAG, "byteArray:$byteArray")
        Log.i(TAG, "string:$string")
        Log.i(TAG, "stringSet:$stringSet")

    }
}