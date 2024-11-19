package com.pwhs.quickmem.presentation.app.classes.remove_members

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun RemoveMemberScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator
) {
    RemoveMember()
}

@Composable
fun RemoveMember() {

}