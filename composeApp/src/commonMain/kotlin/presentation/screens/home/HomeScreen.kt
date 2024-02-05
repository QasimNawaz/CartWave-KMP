package presentation.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cartwave_kmp.composeapp.generated.resources.Res
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.pages.Pages
import com.arkivanov.decompose.extensions.compose.jetbrains.pages.PagesScrollAnimation
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.composables.ImageSlider
import presentation.screens.home.pager.PageContent
import presentation.screens.main.root.RootComponent
import utils.homeDefaultHorizontalPager

@OptIn(ExperimentalFoundationApi::class, ExperimentalDecomposeApi::class)
@Composable
fun HomeScreen(component: HomeScreenComponent) {

    val pages = listOf(
        CategoryPage.All, CategoryPage.Clothing, CategoryPage.Footwear, CategoryPage.Bags
    )
    var selectedPage by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ImageSlider()
        CategoriesRow(
            pages, selectedPage
        ) {
            component.onNavigateToPageIndex(it)
        }

        Pages(
            pages = component.pages,
            onPageSelected = {
                selectedPage = it
            },
            modifier = Modifier.weight(1f),
            pager = homeDefaultHorizontalPager(),
            scrollAnimation = PagesScrollAnimation.Default,
        ) { _, page ->
            PageContent(page, onNavigateToDetail = {
                component.onRootNavigateTo(RootComponent.Configuration.ProductDetailScreenConfig(it))
            })
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CategoriesRow(
    pages: List<CategoryPage>,
    selectedPage: Int,
    onNavigate: (index: Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        pages.forEachIndexed { index, categoryPage ->
            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                border = BorderStroke(
                    width = 2.dp,
                    color = if (selectedPage == index) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        onNavigate.invoke(index)
                    },
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    painter = painterResource(categoryPage.icon),
                    contentDescription = "icon",
                    tint = MaterialTheme.colorScheme.primaryContainer,
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
sealed class CategoryPage(
    val icon: DrawableResource, val title: String
) {

    data object All : CategoryPage(
        icon = Res.drawable.ic_list,
        title = "All",
    )

    data object Clothing : CategoryPage(
        icon = Res.drawable.ic_clothing,
        title = "Clothing and Accessories",
    )

    data object Footwear : CategoryPage(
        icon = Res.drawable.ic_footwear,
        title = "Footwear",
    )

    data object Bags : CategoryPage(
        icon = Res.drawable.ic_bag_wallet_belt,
        title = "Bags, Wallets & Belts",
    )
}