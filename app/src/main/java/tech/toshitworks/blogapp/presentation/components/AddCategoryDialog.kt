package tech.toshitworks.blogapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddCategoryDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    title: String,
    description: String,
    onTitleNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
) {
    var titleError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    titleError = when {
        title.isBlank() -> "Title cannot be blank"
        title.length !in 5..15 -> "Title should be between 5 to 15 characters"
        else -> null
    }
    var descriptionError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    descriptionError = when {
        description.isBlank() -> "Description cannot be blank"
        description.length !in 15..100 -> "Description should be between 15 to 100 characters"
        else -> null
    }
    if (isOpen)
        AlertDialog(
            title = {
                Text(
                    text = "Add Category"
                )
            },
            text = {
                Column {
                    OutlinedTextField(
                        label = {
                            Text(
                                text = "Add a title for category"
                            )
                        },
                        singleLine = true,
                        value = title,
                        onValueChange = {
                            onTitleNameChange(it)
                        },
                        isError = titleError != null && title.isNotBlank(),
                        supportingText = {
                            Text(
                                text = titleError.orEmpty()
                            )
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                    OutlinedTextField(
                        label = {
                            Text(
                                text = "Add a description for category"
                            )
                        },
                        singleLine = true,
                        value = description,
                        onValueChange = {
                            onDescriptionChange(it)
                        },
                        isError = descriptionError != null && description.isNotBlank(),
                        supportingText = {
                            Text(
                                text = descriptionError.orEmpty()
                            )
                        }
                    )
                }
            },
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClick,
                    enabled = titleError == null && descriptionError == null
                ) {
                    Text(
                        text = "Save"
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(
                        text = "Cancel"
                    )
                }
            }
        )
}