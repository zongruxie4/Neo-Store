package com.machiav3lli.fdroid.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.machiav3lli.fdroid.data.database.entity.Repository
import com.machiav3lli.fdroid.data.entity.ProductItem
import com.machiav3lli.fdroid.manager.network.createIconUri

val PRODUCT_CARD_ICON = 48.dp
val PRODUCT_CARD_HEIGHT = 64.dp
val PRODUCT_CAROUSEL_HEIGHT = 164.dp
val PRODUCT_CARD_WIDTH = 220.dp

@Composable
fun ProductCard(
    product: ProductItem,
    repo: Repository? = null,
    onUserClick: (ProductItem) -> Unit = {},
) {
    val imageDataPair by remember(product, repo) {
        mutableStateOf(
            createIconUri(
                product.icon,
                repo?.address,
                repo?.authentication
            )
        )
    }

    ListItem(
        modifier = Modifier
            .border(2.dp, MaterialTheme.colorScheme.outlineVariant, MaterialTheme.shapes.large)
            .width(IntrinsicSize.Max)
            .widthIn(
                min = PRODUCT_CARD_HEIGHT,
                max = PRODUCT_CARD_WIDTH,
            ),
        onClick = { onUserClick(product) },
        shapes = ListItemDefaults.shapes(
            shape = MaterialTheme.shapes.large,
        ),
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent,
        ),
        leadingContent = {
            NetworkImage(
                modifier = Modifier.size(PRODUCT_CARD_ICON),
                data = imageDataPair.first,
                fallbackData = imageDataPair.second,
            )
        },
        overlineContent = {
            Text(
                text = product.version,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        content = {
            Text(
                text = product.name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
    )
}

@Preview
@Composable
fun ProductCardPreview() {
    ProductCard(ProductItem())
}