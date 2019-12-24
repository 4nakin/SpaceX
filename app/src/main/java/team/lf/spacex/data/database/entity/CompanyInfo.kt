package team.lf.spacex.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * These models are used as network ,db models and for representing in companyInfo ui
 * */
@Entity
data class CompanyInfo(
    @PrimaryKey
    val ceo: String,
    val coo: String,
    val cto: String,
    val cto_propulsion: String,
    val employees: Int,
    val founded: String,
    val founder: String,
    @Embedded val headquarters: Headquarters,
    val launch_sites: Int,
    @Embedded val links: CompanyInfoLinks,
    val name: String,
    val summary: String,
    val test_sites: Int,
    val valuation: Long,
    val vehicles: Int,
    //photo from wikipedia
    val mask_photo: String = "https://upload.wikimedia.org/wikipedia/commons/e/ed/Elon_Musk_Royal_Society.jpg"
)

data class Headquarters(
    val address: String,
    val city: String,
    val state: String
)

data class CompanyInfoLinks(
    val elon_twitter: String,
    val flickr: String,
    val twitter: String,
    val website: String
)