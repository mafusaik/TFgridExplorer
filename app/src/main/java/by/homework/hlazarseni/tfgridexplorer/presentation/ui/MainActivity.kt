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
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.animation.doOnEnd
import by.homework.hlazarseni.tfgridexplorer.domain.model.NightMode
import by.homework.hlazarseni.tfgridexplorer.domain.service.NightModeService
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.constants.ValueConstants
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private var contentHasLoaded = false
    private val nightModeService by inject<NightModeService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ContainerFragment())
                .addToBackStack(null)
                .commit()
            initialNightMode()
            contentHasLoaded = true
        }
        //setupSplashScreen(splashScreen)
    }

    private fun setupSplashScreen(splashScreen: SplashScreen) {
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
                duration = ValueConstants.SPLASH_DURATION
                doOnEnd { splashScreenView.remove() }
            }
            slideBack.start()
        }
    }

    private fun initialNightMode() {
        AppCompatDelegate.setDefaultNightMode(
            when (nightModeService.nightMode) {
                NightMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                NightMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                NightMode.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
    }
}
