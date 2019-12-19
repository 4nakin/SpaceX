package team.lf.spacex.ui.company_info.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEvent(
    val details: String?,
    val event_date_unix: String?,
    val event_date_utc: String?,
    val flight_number: Int?,
    @PrimaryKey
    val id: Int,
    @Embedded val links: SMLinks,
    val title: String?
)

data class SMLinks(
    val article: String?,
    val reddit: String?,
    val wikipedia: String?
)