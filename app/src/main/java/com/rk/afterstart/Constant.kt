package com.rk.afterstart

import com.rk.afterstart.DataModel.RvModel

object Constant
{
    private lateinit var datalist:ArrayList<RvModel>
    fun GetData():ArrayList<RvModel>
    {
        datalist=ArrayList<RvModel>()
        datalist.add(RvModel(R.drawable.ic_launcher_background,"vipul","jadav"))
        datalist.add(RvModel(R.drawable.ic_launcher_background,"vipul","jadav"))
        datalist.add(RvModel(R.drawable.ic_launcher_background,"vipul","jadav"))
        datalist.add(RvModel(R.drawable.ic_launcher_background,"vipul","jadav"))
        datalist.add(RvModel(R.drawable.ic_launcher_background,"vipul","jadav"))
        datalist.add(RvModel(R.drawable.ic_launcher_background,"vipul","jadav"))
        datalist.add(RvModel(R.drawable.ic_launcher_background,"vipul","jadav"))
        datalist.add(RvModel(R.drawable.ic_launcher_background,"vipul","jadav"))
        datalist.add(RvModel(R.drawable.ic_launcher_background,"vipul","jadav"))
        datalist.add(RvModel(R.drawable.ic_launcher_background,"vipul","jadav"))
        datalist.add(RvModel(R.drawable.ic_launcher_background,"vipul","jadav"))
        datalist.add(RvModel(R.drawable.ic_launcher_foreground,"vipul","jadav"))

        return datalist
    }
}