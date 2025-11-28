package com.example.krasilnikovapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home_main"
    ) {
        composable("home_main") {
            HomeMainScreen(
                viewModel = viewModel,
                onNavigateToDetails = {
                    navController.navigate("home_details")
                }
            )
        }

        composable("home_details") {
            HomeDetailsScreen(
                navController = navController
            )
        }
    }
}


@Composable
fun HomeMainScreen(
    viewModel: MainViewModel,
    onNavigateToDetails: () -> Unit
) {
    var rememberText by remember { mutableStateOf("Remember: зникне при повороті") }
    var saveableText by rememberSaveable { mutableStateOf("Saveable: збережеться") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Домашня сторінка",
            fontSize = 28.sp,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // viewModel текст
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                text = viewModel.homeText.value,
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Button(
            onClick = { viewModel.updateHomeText() },
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
                .padding(vertical = 8.dp)
        ) {
            Text("Оновити (ViewModel)", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        // зникає при повороті
        Text(text = rememberText, fontSize = 14.sp)
        Button(
            onClick = { rememberText = "Remember: ${System.currentTimeMillis() % 10000}" },
            modifier = Modifier.width(200.dp)
        ) {
            Text("Remember Test")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // зберігається
        Text(text = saveableText, fontSize = 14.sp)
        Button(
            onClick = { saveableText = "Saveable: ${System.currentTimeMillis() % 10000}" },
            modifier = Modifier.width(200.dp)
        ) {
            Text("Saveable Test")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Поверніть екран, щоб побачити різницю",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onNavigateToDetails,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(
                text = "Перейти до деталей →",
                fontSize = 18.sp
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDetailsScreen(navController: NavHostController) {
    var clickCount by rememberSaveable { mutableStateOf(0) }
    var userInput by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Деталі") },
                navigationIcon = {
                    IconButton(onClick = {
                        // Повернення назад
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "🔍 Екран деталей",
                fontSize = 32.sp,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Інформація про навігацію",
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Ви успішно перейшли на другий підекран!",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Натисніть стрілку ← вгорі або кнопку назад на пристрої для повернення.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                    )
                }
            }

            // лічильник кліків
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Лічильник кліків",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "$clickCount",
                        fontSize = 48.sp,
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                    Button(
                        onClick = { clickCount++ },
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ) {
                        Text("Збільшити", fontSize = 16.sp)
                    }
                }
            }

            // введення
            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("Введіть текст") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (userInput.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(
                        text = "Ви ввели: $userInput",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // кнопка назад
            OutlinedButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp)
            ) {
                Text(
                    text = "← Повернутися назад",
                    fontSize = 18.sp
                )
            }

            Text(
                text = "Стан збережений за допомогою rememberSaveable",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}