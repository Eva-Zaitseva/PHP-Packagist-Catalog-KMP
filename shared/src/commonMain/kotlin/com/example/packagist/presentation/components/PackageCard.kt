package com.example.packagist.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.packagist.model.PhpPackage
import com.example.packagist.resources.Res
import com.example.packagist.resources.add_favorite
import com.example.packagist.resources.downloads
import com.example.packagist.resources.remove_favorite
import com.example.packagist.resources.stars
import com.example.packagist.resources.version
import org.jetbrains.compose.resources.stringResource

@Composable
fun PackageCard(
    phpPackage: PhpPackage,
    isFavorite: Boolean,
    onPackageClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        onClick = onPackageClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = phpPackage.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                TextButton(onClick = onFavoriteClick) {
                    Text(
                        text = (if (isFavorite) "★ " else "☆ ") +
                                stringResource(
                                    if (isFavorite) {
                                        Res.string.remove_favorite
                                    } else {
                                        Res.string.add_favorite
                                    }
                                )
                    )
                }
            }

            Text(
                text = phpPackage.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(
                    Res.string.version,
                    phpPackage.version
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = stringResource(
                    Res.string.downloads,
                    phpPackage.downloads
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = stringResource(
                    Res.string.stars,
                    phpPackage.stars
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}