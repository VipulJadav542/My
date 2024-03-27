package com.rk.afterstart.HelperKt

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.PopupMenu
import com.emreesen.sntoast.SnToast
import com.emreesen.sntoast.Type
import com.rk.afterstart.R
import java.lang.Exception

class CustomMenu {
    @SuppressLint("NotConstructor")
    fun custommenu(context: Context, view: View) {
        val pop=PopupMenu(context, view)
        pop.inflate(R.menu.menu)
        pop.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.share -> {
                    SnToast.Builder().context(context).type(Type.SUCCESS).message("Share")
                        .iconSize(34).textSize(18).animation(true).duration(1000)
                        .backgroundColor(R.color.green).build()
                    true
                }
                R.id.edit -> {
                    SnToast.Builder().context(context).type(Type.SUCCESS).message("Edit")
                        .iconSize(34).textSize(18).animation(true).duration(1000)
                        .backgroundColor(R.color.green).build()
                    true
                }

                R.id.delete -> {
                    SnToast.Builder().context(context).type(Type.SUCCESS).message("Delete")
                        .iconSize(34).textSize(18).animation(true).duration(1000)
                        .backgroundColor(R.color.green).build()
                    true
                }

                else -> false
            }
        }
        try {
//            SnToast.Builder().context(context).type(Type.SUCCESS).message("Success")
//                .iconSize(34).textSize(18).animation(true).duration(1000)
//                .backgroundColor(R.color.green).build()
        }
        catch (ex:Exception)
        {
            SnToast.Builder().context(context).type(Type.WARNING).message("Error")
                .iconSize(34).textSize(18).animation(true).duration(1000)
                .backgroundColor(R.color.green).build()
        }
        finally {
            pop.show()
        }
    }
}