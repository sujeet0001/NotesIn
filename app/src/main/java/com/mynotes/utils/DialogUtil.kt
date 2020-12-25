package com.mynotes.utils

import android.app.Dialog

class DialogUtil {

    companion object {

        private var dialogUtil: DialogUtil? = null

        fun get(): DialogUtil {
            if (dialogUtil == null) {
                dialogUtil = DialogUtil()
            }
            return dialogUtil!!
        }
    }

    fun setDialog(dialog: Dialog): DialogViewCreator{
        return DialogViewCreator(dialog)
    }

}