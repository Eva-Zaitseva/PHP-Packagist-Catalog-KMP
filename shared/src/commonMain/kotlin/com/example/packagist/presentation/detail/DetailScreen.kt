
package com.example.packagist.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.foundation.layout.safeContentPadding
import com.example.packagist.model.PhpPackage

@Composable
fun DetailScreen(
    phpPackage: PhpPackage,
    availableDependencies: Set<String>,
    onBackClick: () -> Unit,
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
            Text("← Назад к каталогу")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = phpPackage.name,
            style = MaterialTheme.typography.headlineMedium
        )

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
                    text = "Информация о пакете",
                    style = MaterialTheme.typography.titleMedium
                )

                Text("Версия: ${phpPackage.version}")
                Text("Загрузки: ${phpPackage.downloads}")
                Text("Звёзды: ${phpPackage.stars}")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Зависимости (require)",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (phpPackage.require.isEmpty()) {
            Text("У этого пакета нет указанных зависимостей.")
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
                            text = "Версия: $version",
                            style = MaterialTheme.typography.bodySmall
                        )

                        if (dependencyName in availableDependencies) {
                            Button(
                                onClick = {
                                    onDependencyClick(dependencyName)
                                }
                            ) {
                                Text("Открыть пакет")
                            }
                        } else {
                            Text(
                                text = "Нет в тестовом каталоге",
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
