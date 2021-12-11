package com.muc.wide.Utils

import com.muc.store.util.FileUtils
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class ConfigUtils(var mPath: String) {
    val list = ArrayList<ConfigArray>()
    lateinit var jsonObject: JSONObject

    /**
     * 初始化
     */
    private fun initConfig() {
        jsonObject = JSONObject(FileUtils.readFile(mPath))
    }

    /**
     * 获取conf文件中的value
     *
     * @param attr
     * @return value
     */
    fun getString(key: String): String {
        return jsonObject.getString(key)
    }

    fun getInt(key: String): Int {
        return jsonObject.getInt(key)
    }

    fun getBoolean(key: String): Boolean {
        return jsonObject.getBoolean(key)
    }

    fun getArrayList(key: String):JSONArray{
        return jsonObject.getJSONArray(key)
    }


    fun addConf(key: String, value: String) {
        jsonObject.put(key, value)
    }

    /**
     * 修改json文件中key对应的value
     * @param key
     * @param value
     */
    fun updateConf(key: String, value: Any) {
        var json = jsonObject
        FileUtils.writeFile(mPath, json.apply {
            put(key, value)
        }.toString())
        initConfig()
    }

    fun removeConf(key: String) {
        FileUtils.writeFile(mPath, jsonObject.apply {
            remove(key)
        }.toString())
        initConfig()
    }

    companion object {
        /**
         * 创建配置文件
         * @param mList
         */
        fun createConfig(path: String, map: HashMap<*, *>) {
            FileUtils.writeFile(path, JSONObject(map).toString())
        }
    }

    class ConfigArray(var key: String, var value: String)

    /**
     * 初始化配置文件工具类
     *
     * @param path 要编辑的conf文件路径
     */
    init {

        initConfig()
    }
}