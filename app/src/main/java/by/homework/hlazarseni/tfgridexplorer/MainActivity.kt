package by.homework.hlazarseni.tfgridexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.view.isVisible
import by.homework.hlazarseni.tfgridexplorer.fragments.ContainerFragment

class MainActivity : AppCompatActivity(R.layout.activity_main)
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      //  val containerFragmentView = findViewById<LinearLayout>(R.id.container_fragment_view)

        if (savedInstanceState==null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ContainerFragment())
                .addToBackStack(null)
                .commit()
        }
//        supportFragmentManager
//            .addOnBackStackChangedListener {
//              containerFragmentView.isVisible = supportFragmentManager.backStackEntryCount == 0
//            }
    }
}
