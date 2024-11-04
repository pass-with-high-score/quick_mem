package com.pwhs.quickmem.presentation.app.classes.edit

data class EditClassScreenArgs(
    val classId: String,
    val classTitle:String,
    val classDescription:String,
    val isSetAllowed:Boolean,
    val isMemberAllowed:Boolean
)