package com.machiav3lli.fdroid.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.machiav3lli.fdroid.R
import com.machiav3lli.fdroid.data.database.entity.ProductIconDetails
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.collections.immutable.toPersistentSet

@Composable
fun PackagesListDialogUI(
    title: String,
    selectedPackageNames: ImmutableSet<String>,
    allPackages: ImmutableSet<ProductIconDetails>,
    openDialogCustom: MutableState<Boolean>,
    withSearchBar: Boolean = false,
    onPackagesListChanged: (newList: Set<String>) -> Unit,
) {
    val allPackages = allPackages.sortedWith { pi1: ProductIconDetails, pi2: ProductIconDetails ->
        val b1 = selectedPackageNames.contains(pi1.packageName)
        val b2 = selectedPackageNames.contains(pi2.packageName)
        if (b1 != b2)
            if (b1) -1 else 1
        else
            pi1.label.compareTo(pi2.label, ignoreCase = true)
    }

    MultiSelectionDialogUI(
        titleText = title,
        entryMap = allPackages.associate { it.packageName to it.label }.toPersistentMap(),
        selectedItems = selectedPackageNames.toPersistentList(),
        withSearchBar = withSearchBar,
        openDialogCustom = openDialogCustom,
    ) {
        onPackagesListChanged(it.toSet())
    }
}

@Composable
fun GlobalBlockListDialogUI(
    allPackages: ImmutableSet<ProductIconDetails>,
    currentBlocklist: ImmutableSet<String>,
    openDialogCustom: MutableState<Boolean>,
    onPackagesListChanged: (newList: Set<String>) -> Unit,
) {
    PackagesListDialogUI(
        title = stringResource(id = R.string.packages_blocklist),
        allPackages = allPackages.toPersistentSet(),
        selectedPackageNames = currentBlocklist.toPersistentSet(),
        openDialogCustom = openDialogCustom,
        withSearchBar = true,
        onPackagesListChanged = onPackagesListChanged,
    )
}
