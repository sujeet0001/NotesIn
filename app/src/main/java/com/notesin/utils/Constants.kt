package com.notesin.utils

class Constants {
    companion object {
        
        // Other messages
        const val SECRET_CODE_CHANGED = "Your secret code is been successfully changed"
        const val ENABLE_SECRET_CODE = "Enable secret code"
        const val DISABLE_SECRET_CODE = "Disable secret code"
        const val SET_SECRET_CODE = "Set your secret code"
        const val CHANGE_SECRET_CODE = "Change your secret code"
        const val ENABLE_SECRET_CODE_SUB = "Asks for secret code every time you open the app."
        const val DISABLE_SECRET_CODE_SUB = "Never asks for secret code on opening the app."
        const val SAME_AS_EXISTING_CODE = "The code you entered is same as your existing secret code, please set a different one."
        const val NOTE_TITLE = "Note title"
        const val NOTE_CONTENT = "Note content"
        const val NOTE_TITLE_CONTENT = "Note title and its content"
        const val SHOULD_NOT_BE_EMPTY = " shouldn't be empty"
        const val NO_NOTES_TO_SEARCH = "There are no notes to search"
        const val NO_NOTES = "No notes found!\n\nTap (+) icon to add a note"
        const val NO_SEARCH_RESULTS = "No results found!"
        const val OK = "Ok"
        const val CANCEL = "Cancel"
        const val ERROR = "Error!"
        const val NOT_EMPTY = "Not empty"
        const val DISCARD = "Discard"

        // Intent extras
        const val FROM_ADD_NOTE = "0"
        const val FROM_CHANGE_SECRET_CODE = "1"

        // Dialog types
        const val TYPE_FIRST_TIME = 0
        const val TYPE_SET_SECRET_CODE = 1
        const val TYPE_DELETE_NOTE = 2
        const val TYPE_DISCARD_CHANGES = 3

        // Dialog messages
        const val MSG_WELCOME = "Hey there! Welcome!\n\nThis app allows you to " +
                "create simple text notes.\n\nIt has an inbuilt security feature that allows you to create a secret code " +
                "and every time you open the app it will ask for the same before proceeding further\n\n"+
                "The default secret code is\n\n0001\n\n" +
                "Requesting you to change the security code based on your convenience. " +
                "You will find an option to change the security code in the settings page."
        const val MSG_SET_SECRET_CODE = "Set your secret code here.\n\nType in the existing default secret code to continue.\n\n"+
                "While setting the secret code make sure it is of minimum 4 characters and maximum of 15 characters."
        const val MSG_DELETE_NOTE = "Are you sure you want to delete the note?"
        const val MSG_DISCARD_CHANGES = "Any changes will be discarded because of empty "

        // Preferences
        const val PREF_SECRET_CODE = "0"
        const val PREF_FIRST_TIME_OPEN = "1"
        const val PREF_DARK_MODE = "2"
        const val PREF_SYS_THEME_CHANGES_APP_THEME = "3"
        const val PREF_ENABLE_SECRET_CODE = "4"
        const val PREF_SECRET_CODE_SET = "5"
        const val PREF_UNIQUE_ID = "6"

    }
}