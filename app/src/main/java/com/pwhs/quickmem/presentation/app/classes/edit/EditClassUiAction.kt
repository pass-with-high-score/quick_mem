package com.pwhs.quickmem.presentation.app.classes.edit

sealed class EditClassUiAction {
    data object SaveClicked : EditClassUiAction()
    data class TitleChanged(val title: String):EditClassUiAction()
    data class DescriptionChanged(val description: String):EditClassUiAction()
    data class OnAllowSetChanged(val allowSetChanged:Boolean):EditClassUiAction()
    data class OnAllowMemberChanged(val allowMemberChanged:Boolean):EditClassUiAction()
}