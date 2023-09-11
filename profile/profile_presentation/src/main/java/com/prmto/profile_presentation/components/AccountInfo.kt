package com.prmto.profile_presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prmto.core_domain.model.Statistics
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_presentation.components.CircleProfileImage
import com.prmto.profile_presentation.R
import com.prmto.core_presentation.R as CoreR

@Composable
fun AccountInfo(
    statistics: Statistics,
    userDetail: UserDetail,
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
            CircleProfileImage(
                imageUrl = userDetail.profilePictureUrl,
            )
            Statistics(
                statistics = statistics
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        AccountNameAndBio(
            name = userDetail.name,
            bio = userDetail.bio
        )
    }
}

@Composable
fun Statistics(
    statistics: Statistics,
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        StatisticColumn(
            count = "${statistics.postsCount}",
            label = stringResource(R.string.posts)
        )

        StatisticColumn(
            count = "${statistics.followersCount}",
            label = stringResource(R.string.followers)
        )

        StatisticColumn(
            count = "${statistics.followingCount}",
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