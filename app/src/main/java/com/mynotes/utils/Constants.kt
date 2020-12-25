package com.mynotes.utils

class Constants {
    companion object {
        const val SECRET_CODE_CHANGED = "Your secret code is been successfully changed"

        // Dialog types
        const val TYPE_FIRST_TIME = 0
        const val TYPE_FIRST_TIME_SECRET_CODE_SETTING = 1
        const val TYPE_DELETE_NOTE = 2

        // Dialog messages
        const val MSG_WELCOME = "Hey there! welcome to the app.\n\nThis app allows you to " +
                "create simple text notes.\n\nIt has an inbuilt security feature that allows you to create a secret code " +
                "and every time you open the app it will ask for the same before proceeding further\n\n"+
                "The default secret code is\n\n0001\n\n" +
                "Requesting you to change the security code based on your convenience. " +
                "You will find an option to change the security code in the dashboard."
        const val MSG_SET_SECRET_CODE = "Set your secret code here.\n\nType in the existing default secret code to continue.\n\n"+
                "While setting the secret code make sure it is of minimum 4 characters and maximum of 15 characters."

        // Preferences
        const val PREF_SECRET_CODE = "secretCode"
        const val PREF_FIRST_TIME_OPEN = "FIRST_TIME_OPEN"
        const val PREF_FIRST_TIME_ON_SECRET_CODE_SETTING = "FIRST_TIME_ON_SECRET_CODE_SETTING_PAGE"
        const val PREF_DARK_MODE = "darkMode"

    }
}