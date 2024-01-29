package kmp.cartwave.store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.retainedComponent
import di.initKoin
import presentation.screens.main.root.App
import presentation.screens.main.root.RootComponent

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
//        enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.auto(
//                Color.Transparent.toArgb(),
//                Color.Transparent.toArgb()
//            ),
//            navigationBarStyle = SystemBarStyle.auto(
//                Color.Transparent.toArgb(),
//                Color.Transparent.toArgb()
//            )
//        )
        installSplashScreen()
        super.onCreate(savedInstanceState)
//        installSplashScreen()
        initKoin()
        val root = retainedComponent {
            RootComponent(it)
        }
        setContent {
            App(root)
        }
    }
}