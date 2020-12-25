package com.mynotes.utils

class Constants {
    companion object {
        const val SECRET_CODE_CHANGED = "Your secret code is been successfully changed"
        const val SECRET_CODE = "secretCode"

        // Dialog types
        const val FIRST_TIME = 0
        const val FIRST_TIME_SECRET_CODE_SETTING_INFO = 1
        const val DELETE_NOTE = 2

        // Dialog messages
        const val WELCOME_MSG = "Hey there! welcome to the app.\n\nThis app allows you to " +
                "create simple text notes.\n\nIt has an inbuilt security feature that allows you to create a secret code " +
                "and every time you open the app it will ask for the same before proceeding further\n\n"+
                "The default security code is\n\n0001\n\n" +
                "Requesting you to change the security code based on your convenience. " +
                "You will find an option to change the security code in the dashboard."

        // Preferences
        const val FIRST_TIME_OPEN = "FIRST_TIME_OPEN"
        const val FIRST_TIME_ON_SECRET_CODE_SETTING_PAGE = "FIRST_TIME_ON_SECRET_CODE_SETTING_PAGE"
    }
}