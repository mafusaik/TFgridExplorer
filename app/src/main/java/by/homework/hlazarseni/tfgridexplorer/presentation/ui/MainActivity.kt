package by.homework.hlazarseni.tfgridexplorer.presentation.ui

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.homework.hlazarseni.tfgridexplorer.R
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreen
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd

class MainActivity : AppCompatActivity(R.layout.activity_main)
{
    var contentHasLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        if (savedInstanceState==null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ContainerFragment())
                .addToBackStack(null)
                .commit()
            contentHasLoaded = true
        }
        setupSplashScreen(splashScreen)
    }

   private fun setupSplashScreen(splashScreen: SplashScreen){
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (contentHasLoaded) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            }
        )
       splashScreen.setOnExitAnimationListener { splashScreenView ->
           val slideBack = ObjectAnimator.ofFloat(
               splashScreenView.view,
               View.TRANSLATION_X,
               0f,
               -splashScreenView.view.width.toFloat()
           ).apply {
               interpolator = DecelerateInterpolator()
               duration = 500L
               doOnEnd { splashScreenView.remove() }
           }
           slideBack.start()
       }
    }
}
