package com.pwhs.quickmem.presentation.app.home.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesomeMotion
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ClassesCard(
    title: String, classname: String, sets: String, member: String
) {
    Surface(
        modifier = Modifier
            .width(250.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(8.dp)),
        color = Color(0xFFF0F0F0),
        shadowElevation = 4.dp
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {

                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.PeopleOutline,
                        contentDescription = "Peoples"
                    )
                }
            }
            Text(text = classname)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {

                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesomeMotion,
                        contentDescription = "Peoples"
                    )
                }
                Text(
                    text = sets,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    ),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {

                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Peoples"
                    )
                }
                Text(
                    text = member,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewClass() {
    ClassesCard(
        title = "ETS 2024",
        classname = "Anh Ngữ MS.Hoa",
        sets = "2 sets",
        member = "30 members"
    )
}