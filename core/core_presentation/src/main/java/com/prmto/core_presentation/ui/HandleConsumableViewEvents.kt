package com.prmto.core_presentation.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.prmto.core_domain.constants.asString
import com.prmto.core_presentation.util.UiEvent

@Composable
fun HandleConsumableViewEvents(
    consumableViewEvents: List<UiEvent>,
    onEventNavigate: (String) -> Unit,
    onEventConsumed: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = consumableViewEvents) {
        if (consumableViewEvents.isEmpty()) return@LaunchedEffect
        consumableViewEvents.forEach { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate -> {
                    onEventNavigate(uiEvent.route)
                    onEventConsumed()
                }

                is UiEvent.ShowMessage -> {
                    Toast.makeText(
                        context,
                        uiEvent.uiText.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                    onEventConsumed()
                }
            }
        }
    }
}