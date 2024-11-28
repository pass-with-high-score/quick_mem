package com.pwhs.quickmem.presentation.app.explore.create_study_set_ai

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.enums.DifficultyLevel
import com.pwhs.quickmem.core.data.enums.LanguageCode
import com.pwhs.quickmem.core.data.enums.QuestionType
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.ads.AdsUtil
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.interfaces.ReceiveCustomerInfoCallback
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateStudySetAITab(
    modifier: Modifier = Modifier,
    title: String = "",
    description: String = "",
    numberOfFlashcards: Int = 0,
    language: String = "",
    questionType: QuestionType = QuestionType.MULTIPLE_CHOICE,
    difficultyLevel: DifficultyLevel = DifficultyLevel.EASY,
    errorMessage: String = "",
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onNumberOfFlashcardsChange: (Int) -> Unit = {},
    onLanguageChange: (String) -> Unit = {},
    onQuestionTypeChange: (QuestionType) -> Unit = {},
    onDifficultyLevelChange: (DifficultyLevel) -> Unit = {},
    onCreateStudySet: () -> Unit = {},
) {
    val sheetLanguageState = rememberModalBottomSheetState()
    var showBottomSheetLanguage by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    var customer: CustomerInfo? by remember { mutableStateOf(null) }
    LaunchedEffect(key1 = true) {
        Purchases.sharedInstance.getCustomerInfo(object : ReceiveCustomerInfoCallback {
            override fun onError(error: PurchasesError) {
                Timber.e("Error getting customer info: $error")
            }

            override fun onReceived(customerInfo: CustomerInfo) {
                Timber.d("Customer info: $customerInfo")
                customer = customerInfo
            }

        })
    }
    Scaffold(
        floatingActionButton = {
            if (title.isNotEmpty()) {
                FloatingActionButton(
                    onClick = {
                        val isSubscribed = customer?.activeSubscriptions?.isNotEmpty() == true
                        if (isSubscribed) {
                            onCreateStudySet()
                        } else {
                            AdsUtil.rewardedAd(context) {
                                onCreateStudySet()
                            }
                        }
                    },
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_sparkling),
                            contentDescription = stringResource(R.string.txt_create),
                        )
                        Text(
                            text = stringResource(R.string.txt_create),
                            style = typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        },
        bottomBar = {
            Spacer(modifier = Modifier.height(100.dp))
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding),
        ) {
            item {
                Column(
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.txt_warning_ai_not_gen),
                        color = Color.Red,
                        style = typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    value = title,
                    maxLines = 2,
                    isError = errorMessage.isNotEmpty(),
                    supportingText = {
                        Text(
                            text = errorMessage,
                            style = typography.bodyMedium.copy(
                                color = colorScheme.error,
                            )
                        )
                    },
                    colors = colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = colorScheme.primary,
                        focusedSupportingTextColor = colorScheme.error,
                        unfocusedSupportingTextColor = colorScheme.error,
                        errorContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    onValueChange = onTitleChange,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.txt_title_required),
                            style = typography.bodyMedium,
                        )
                    }
                )
            }
            item {
                OutlinedTextField(
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(vertical = 10.dp),
                    value = description,
                    maxLines = 5,
                    onValueChange = onDescriptionChange,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.txt_description_optional),
                            style = typography.bodyMedium,
                        )
                    },
                    colors = colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = colorScheme.primary,
                        focusedSupportingTextColor = colorScheme.error,
                        unfocusedSupportingTextColor = colorScheme.error,
                        errorContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }
            item {
                Text(
                    text = stringResource(R.string.txt_number_of_flashcards_optional),
                    style = typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(stringResource(R.string.txt_number_of_flashcards_5))
                            RadioButton(
                                selected = numberOfFlashcards == 5,
                                onClick = { onNumberOfFlashcardsChange(5) }
                            )
                        }
                        VerticalDivider(
                            modifier = Modifier
                                .height(30.dp)
                                .padding(horizontal = 8.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(stringResource(R.string.txt_number_of_flashcards_10))
                            RadioButton(
                                selected = numberOfFlashcards == 10,
                                onClick = { onNumberOfFlashcardsChange(10) }
                            )
                        }
                        VerticalDivider(
                            modifier = Modifier
                                .height(30.dp)
                                .padding(horizontal = 8.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(stringResource(R.string.txt_number_of_flashcards_15))
                            RadioButton(
                                selected = numberOfFlashcards == 15,
                                onClick = { onNumberOfFlashcardsChange(15) }
                            )
                        }
                        VerticalDivider(
                            modifier = Modifier
                                .height(30.dp)
                                .padding(horizontal = 8.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(stringResource(R.string.txt_number_of_flashcards_20))
                            RadioButton(
                                selected = numberOfFlashcards == 20,
                                onClick = { onNumberOfFlashcardsChange(20) }
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Language",
                    style = typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    onClick = {
                        showBottomSheetLanguage = true
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.txt_language),
                            style = typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = when (language) {
                                LanguageCode.VI.code -> stringResource(R.string.txt_vietnamese)
                                else -> stringResource(R.string.txt_english_us)
                            },
                            style = typography.bodyMedium.copy(
                                color = colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.clickable {
                                showBottomSheetLanguage = true
                            }
                        )
                    }
                }
            }
            item {
                Text(
                    text = stringResource(R.string.txt_question_type),
                    style = typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    )
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(text = stringResource(R.string.txt_multiple_choice))
                            Spacer(modifier = Modifier.weight(1f))
                            RadioButton(
                                selected = questionType == QuestionType.MULTIPLE_CHOICE,
                                onClick = { onQuestionTypeChange(QuestionType.MULTIPLE_CHOICE) }
                            )
                        }
                        HorizontalDivider()
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(text = stringResource(R.string.txt_true_false))
                            Spacer(modifier = Modifier.weight(1f))
                            RadioButton(
                                selected = questionType == QuestionType.TRUE_FALSE,
                                onClick = { onQuestionTypeChange(QuestionType.TRUE_FALSE) }
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = stringResource(R.string.txt_difficulty_level),
                    style = typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    )
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(text = stringResource(R.string.txt_easy))
                            Spacer(modifier = Modifier.weight(1f))
                            RadioButton(
                                selected = difficultyLevel == DifficultyLevel.EASY,
                                onClick = { onDifficultyLevelChange(DifficultyLevel.EASY) }
                            )
                        }
                        HorizontalDivider()
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(text = stringResource(R.string.txt_medium))
                            Spacer(modifier = Modifier.weight(1f))
                            RadioButton(
                                selected = difficultyLevel == DifficultyLevel.MEDIUM,
                                onClick = { onDifficultyLevelChange(DifficultyLevel.MEDIUM) }
                            )
                        }
                        HorizontalDivider()
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(text = stringResource(R.string.txt_hard))
                            Spacer(modifier = Modifier.weight(1f))
                            RadioButton(
                                selected = difficultyLevel == DifficultyLevel.HARD,
                                onClick = { onDifficultyLevelChange(DifficultyLevel.HARD) }
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        if (showBottomSheetLanguage) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheetLanguage = false
                },
                sheetState = sheetLanguageState,
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .clickable {
                                onLanguageChange(
                                    if (language == LanguageCode.VI.code) {
                                        LanguageCode.EN.code
                                    } else {
                                        LanguageCode.VI.code
                                    }
                                )
                                showBottomSheetLanguage = false
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_vn_flag),
                                contentDescription = stringResource(R.string.txt_vn_flag),
                                modifier = Modifier
                                    .size(24.dp)
                            )

                            Text(
                                text = stringResource(R.string.txt_vietnamese),
                                style = typography.bodyMedium.copy(
                                    color = colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                                    .padding(start = 10.dp)
                            )
                        }
                        if (language == LanguageCode.VI.code) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.txt_check),
                                tint = colorScheme.primary
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .clickable {
                                onLanguageChange(LanguageCode.EN.code)
                                showBottomSheetLanguage = false
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_us_flag),
                                contentDescription = stringResource(R.string.txt_us_flag),
                                modifier = Modifier
                                    .size(24.dp)
                            )

                            Text(
                                text = stringResource(R.string.txt_english_us),
                                style = typography.bodyMedium.copy(
                                    color = colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                                    .padding(start = 10.dp)
                            )
                        }

                        if (language == LanguageCode.EN.code) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.txt_check),
                                tint = colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CreateStudySetAITabPreview() {
    QuickMemTheme {
        CreateStudySetAITab(
            modifier = Modifier.padding(16.dp),
            numberOfFlashcards = 5,
            language = "English",
        )
    }
}