package tech.toshitworks.blogapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarDuration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
sealed class SnackBarEvent {
    data class ShowSnackBar(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Short
    ) : SnackBarEvent()

}

@RequiresApi(Build.VERSION_CODES.O)
fun dateFormatter(inputDateTime: String?): String {
    val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val outputFormatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.LONG)
        .withLocale(Locale.getDefault())
    val dateString = try {
        val dateTime = OffsetDateTime.parse(inputDateTime, inputFormatter)
        dateTime.format(outputFormatter)
    } catch (e: Exception) {
        ""
    }
    return dateString
}