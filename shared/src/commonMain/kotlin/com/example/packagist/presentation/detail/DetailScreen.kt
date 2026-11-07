package com.example.packagist.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import com.example.packagist.resources.back_to_catalog
import com.example.packagist.resources.dependencies
import com.example.packagist.resources.no_dependencies
import com.example.packagist.resources.open_package
import com.example.packagist.resources.package_information
import com.example.packagist.resources.package_not_found
import com.example.packagist.resources.remove_favorite
import com.example.packagist.resources.stars
import com.example.packagist.resources.version
import com.example.packagist.resources.downloads
import org.jetbrains.compose.resources.stringResource

@Composable
fun DetailScreen(
    phpPackage: PhpPackage,
    availableDependencies: Set<String>,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onDependencyClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .safeContentPadding()
            .padding(16.dp)
    ) {
        TextButton(
            onClick = onBackClick,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(stringResource(Res.string.back_to_catalog))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = phpPackage.name,
            style = MaterialTheme.typography.headlineMedium
        )

        TextButton(onClick = onFavoriteClick) {
            Text(
                text = stringResource(
                    if (isFavorite) {
                        Res.string.remove_favorite
                    } else {
                        Res.string.add_favorite
                    }
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = phpPackage.description,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(Res.string.package_information),
                    style = MaterialTheme.typography.titleMedium
                )

                Text(stringResource(Res.string.version, phpPackage.version))
                Text(stringResource(Res.string.downloads, phpPackage.downloads))
                Text(stringResource(Res.string.stars, phpPackage.stars))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(Res.string.dependencies),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (phpPackage.require.isEmpty()) {
            Text(stringResource(Res.string.no_dependencies))
        } else {
            phpPackage.require.forEach { (dependencyName, version) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = dependencyName,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = stringResource(
                                Res.string.version,
                                version
                            ),
                            style = MaterialTheme.typography.bodySmall
                        )

                        if (dependencyName in availableDependencies) {
                            Button(
                                onClick = {
                                    onDependencyClick(dependencyName)
                                }
                            ) {
                                Text(stringResource(Res.string.open_package))
                            }
                        } else {
                            Text(
                                text = stringResource(Res.string.package_not_found),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }
        }
    }
}