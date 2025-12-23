package com.gigchad.music.feature.shared.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.gigchad.music.feature.shared.theme.ApplicationColors
import com.gigchad.music.feature.shared.theme.ApplicationTypography

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    textColor: Color = ApplicationColors.Black,
    containerColor: Color = ApplicationColors.White,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    placeholder: String
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(28.dp))
            .background(containerColor)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = textColor
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(query)
                    keyboardController?.hide()
                }
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    style = ApplicationTypography.bodyMedium,
                    color = textColor
                )
            },
            textStyle = ApplicationTypography.bodyMedium.copy(color = textColor),
            colors = TextFieldDefaults.colors().copy(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,

                focusedContainerColor = ApplicationColors.Transparent,
                unfocusedContainerColor = ApplicationColors.Transparent,

                cursorColor = textColor,
                unfocusedIndicatorColor = ApplicationColors.Transparent,
                focusedIndicatorColor = ApplicationColors.Transparent
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}