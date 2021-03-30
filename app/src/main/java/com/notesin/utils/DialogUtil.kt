package com.notesin.utils

import android.app.Dialog

class DialogUtil {

    companion object {

        @Volatile private var dialogUtil: DialogUtil? = null

        fun get(): DialogUtil {
            synchronized(this){
                if (dialogUtil == null) {
                    dialogUtil = DialogUtil()
                }
            }
            return dialogUtil!!
        }
    }

    fun setDialog(dialog: Dialog): DialogViewCreator{
        return DialogViewCreator(dialog)
    }

}