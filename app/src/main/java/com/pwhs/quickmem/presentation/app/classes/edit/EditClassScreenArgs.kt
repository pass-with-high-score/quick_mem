package com.pwhs.quickmem.presentation.app.classes.edit

data class EditClassScreenArgs(
    val classId: String,
    val classTitle:String,
    val classDescription:String,
    val classAllowSet:Boolean,
    val classAllowMember:Boolean
)