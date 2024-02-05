import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.lifecycle.ApplicationLifecycle
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import presentation.screens.main.root.App
import presentation.screens.main.root.RootComponent

@OptIn(ExperimentalDecomposeApi::class)
fun MainViewController() = ComposeUIViewController {
    val root = remember {
        RootComponent(DefaultComponentContext(ApplicationLifecycle()))
    }
    App(root)
}
