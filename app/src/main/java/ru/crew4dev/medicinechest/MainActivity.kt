package ru.crew4dev.medicinechest

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.terrakok.cicerone.Router
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import ru.crew4dev.medicinechest.ui.praeperetum.PraeperetumFragment

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var toggle: ActionBarDrawerToggle? = null
    //private val router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            //val frag = PraeperetumFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PraeperetumFragment.newInstance())
                .commitNow()
            useUpButton(false)
            //transaction.replace(R.id.fragment_layout_id, fragment)
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        toggle?.let { drawerLayout.addDrawerListener(it) }
        toggle?.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send, R.id.nav_praeperetum
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun useUpButton(value: Boolean) {
        val actionBar = supportActionBar
        if (value) {
            //actionBar.setDisplayHomeAsUpEnabled(false);
            toggle?.setDrawerIndicatorEnabled(false)
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setDisplayShowHomeEnabled(true)
            toggle?.setToolbarNavigationClickListener({ v -> onBackPressed() })
        } else {
            toggle?.setDrawerIndicatorEnabled(true)
            //actionBar.setDisplayHomeAsUpEnabled(false); //Окончательно скрывает бургер
            actionBar?.setDisplayShowHomeEnabled(false) //Кажется ни на что не влияет
            toggle?.setToolbarNavigationClickListener(null)
        }
    }
}
