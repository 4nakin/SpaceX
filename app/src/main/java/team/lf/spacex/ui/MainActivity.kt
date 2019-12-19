package team.lf.spacex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import team.lf.spacex.R

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationDrawer()
        setSupportActionBar(findViewById(R.id.toolbar))


        val navController = this.findNavController(R.id.nav_host_fragment)
        appBarConfiguration =
            AppBarConfiguration.Builder(R.id.launchesFragment, R.id.companyInfoFragment)
                .setDrawerLayout(drawerLayout)
                .build()
        setupActionBarWithNavController( navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.navView)
            .setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun setupNavigationDrawer() {
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawerLayout))
            .apply {
                setStatusBarBackground(R.color.colorPrimaryDark)
            }
    }
}
