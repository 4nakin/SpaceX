package team.lf.spacex.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * These models are used as network ,db models and for representing in companyInfo ui
 * */
@Entity
data class HistoryEvent(
    val details: String?,
    val event_date_unix: String?,
    val event_date_utc: String?,
    val flight_number: Int?,
    @PrimaryKey
    val id: Int,
    @Embedded val links: HistoryEventLinks,
    val title: String?
)

data class HistoryEventLinks(
    val article: String?,
    val reddit: String?,
    val wikipedia: String?
)