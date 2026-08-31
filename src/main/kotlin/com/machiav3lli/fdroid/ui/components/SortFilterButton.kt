package com.machiav3lli.fdroid.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.machiav3lli.fdroid.R
import com.machiav3lli.fdroid.ui.compose.icons.Phosphor
import com.machiav3lli.fdroid.ui.compose.icons.phosphor.Asterisk
import com.machiav3lli.fdroid.ui.compose.icons.phosphor.FunnelSimple

@Composable
fun SortFilterButton(
    isModified: Boolean,
    onClick: () -> Unit,
) {
    BadgedBox(
        badge = {
            if (isModified) {
                Badge(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Icon(
                        imageVector = Phosphor.Asterisk,
                        contentDescription = stringResource(id = R.string.state_modified),
                        modifier = Modifier.size(12.dp),
                    )
                }
            }
        }
    ) {
        FilledIconButton(
            shapes = IconButtonDefaults.shapes(),
            onClick = onClick,
        ) {
            Icon(
                imageVector = Phosphor.FunnelSimple,
                contentDescription = stringResource(id = R.string.sort_filter),
            )
        }

    }
}
