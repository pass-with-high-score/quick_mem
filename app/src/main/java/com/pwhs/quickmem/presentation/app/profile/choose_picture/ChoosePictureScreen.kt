package com.pwhs.quickmem.presentation.app.profile.choose_picture

import com.pwhs.quickmem.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.app.profile.choose_picture.component.ChoosePictureTopAppBar
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun ChoosePictureScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: ChoosePictureViewModel = hiltViewModel()
) {
    ChoosePictureUI(
        modifier = modifier,
        onSelectedPicture = {

        },
        onDoneClick = {
            navigator.navigateUp()
        }
    )
}

@Composable
fun ChoosePictureUI(
    modifier: Modifier = Modifier,
    onSelectedPicture: (Int) -> Unit = {},
    title: String = "Choose a picture",
    onDoneClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            ChoosePictureTopAppBar(
                title = title,
                onDoneClick = onDoneClick
            )
        }
    ) { paddingValues->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    painter = painterResource(id =R.drawable.ic_camera),
                    contentDescription = "Camera",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { },
                    tint = Color.Gray
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_gallecy),
                    contentDescription = "Gallery",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { },
                    tint = Color.Gray
                )
            }

            AvatarGrid(onSelectedPicture = onSelectedPicture)
        }
    }
}

@Composable
fun AvatarGrid(onSelectedPicture: (Int) -> Unit) {
    val avatarList = listOf(
        R.drawable.default_avatar, R.drawable.ic_arts, R.drawable.ic_agriculture,
        R.drawable.ic_art, R.drawable.ic_tf, R.drawable.ic_earth_sciences,
        R.drawable.ic_tf, R.drawable.forgot_password_verify_password, R.drawable.ic_economics
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(avatarList) { avatarRes ->
            AvatarItem(avatarRes = avatarRes, onSelected = { onSelectedPicture(avatarRes) })
        }
    }
}

@Composable
fun AvatarItem(avatarRes: Int, onSelected: () -> Unit) {
    Image(
        painter = painterResource(id = avatarRes),
        contentDescription = "Avatar",
        modifier = Modifier
            .padding(8.dp)
            .size(80.dp)
            .clickable { onSelected() }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewChoosePictureScreen() {
    QuickMemTheme {
        ChoosePictureUI(title = "Choose a picture")
    }
}


