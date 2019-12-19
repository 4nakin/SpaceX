package team.lf.spacex.ui.company_info.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CompanyInfo(
    @PrimaryKey
    val ceo: String,
    val coo: String,
    val cto: String,
    val cto_propulsion: String,
    val employees: Int,
    val founded: Int,
    val founder: String,
    @Embedded val headquarters: Headquarters,
    val launch_sites: Int,
    @Embedded val links: Links,
    val name: String,
    val summary: String,
    val test_sites: Int,
    val valuation: Long,
    val vehicles: Int
)

data class Headquarters(
    val address: String,
    val city: String,
    val state: String
)

data class Links(
    val elon_twitter: String,
    val flickr: String,
    val twitter: String,
    val website: String
)