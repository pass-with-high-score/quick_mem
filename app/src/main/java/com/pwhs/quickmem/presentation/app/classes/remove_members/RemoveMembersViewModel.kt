package com.pwhs.quickmem.presentation.app.classes.remove_members

import androidx.lifecycle.ViewModel
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RemoveMembersViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val appManager: AppManager
):ViewModel(){

}