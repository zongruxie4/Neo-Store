package com.machiav3lli.fdroid.ui.components.appsheet

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.unit.dp
import be.digitalia.compose.htmlconverter.htmlToAnnotatedString
import com.machiav3lli.fdroid.ui.components.ExpandableItemsBlock

@Composable
fun HtmlTextBlock(
    modifier: Modifier = Modifier,
    shortText: String,
    longText: String = "",
    onUriClick: (String) -> Unit = {},
) {
    val text = remember(shortText, longText) {
        htmlToAnnotatedString(
            longText,
            linkInteractionListener = { link ->
                if (link is LinkAnnotation.Url) onUriClick(link.url)
            }
        )
    }

    ExpandableItemsBlock(
        modifier = modifier.fillMaxWidth(),
        heading = shortText
    ) {
        Text(
            text,
            modifier = Modifier
                .animateContentSize()
                .padding(12.dp)
                .fillMaxWidth()
        )
    }
}
