package com.prmto.profile_presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prmto.profile_presentation.R
import com.prmto.core_presentation.R as CoreR

@Composable
fun AccountInfo(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqHZYyumeGLb9wJKCNqgDtB4q4LYYVTwJYp2cQwcc&s")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .border(
                        width = 3.dp,
                        color = Color.LightGray,
                        shape = CircleShape
                    ),
                contentScale = ContentScale.Crop
            )
            Statistics()
        }
        Spacer(modifier = Modifier.height(8.dp))
        AccountNameAndBio(
            name = "Jacob",
            bio = "Android Developer"
        )
        EditProfileButton()
    }
}

@Composable
fun Statistics() {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        StatisticColumn(
            count = "54",
            label = stringResource(R.string.posts)
        )

        StatisticColumn(
            count = "834",
            label = stringResource(R.string.followers)
        )

        StatisticColumn(
            count = "162",
            label = stringResource(R.string.following)
        )
    }
}

@Composable
fun StatisticColumn(
    modifier: Modifier = Modifier,
    count: String,
    label: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = count,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun AccountNameAndBio(
    name: String,
    bio: String
) {
    Text(
        modifier = Modifier.padding(start = 8.dp),
        text = name,
        style = MaterialTheme.typography.titleSmall.copy(
            fontSize = 16.sp,
            color = colorResource(id = CoreR.color.secondTextColor)
        )
    )
    Text(
        modifier = Modifier.padding(start = 8.dp),
        text = bio,
        style = MaterialTheme.typography.bodySmall.copy(
            color = colorResource(id = CoreR.color.secondTextColor)
        )
    )
}

@Composable
fun EditProfileButton(
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(35.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(6.dp)
            )
            .clip(RoundedCornerShape(6.dp)),
        onClick = { /*TODO*/ }
    ) {
        Text(text = stringResource(id = R.string.edit_profile))
    }
}