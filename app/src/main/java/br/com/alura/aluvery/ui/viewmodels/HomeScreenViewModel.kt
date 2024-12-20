package br.com.alura.aluvery.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.com.alura.aluvery.dao.ProductDao
import br.com.alura.aluvery.model.Product
import br.com.alura.aluvery.sampledata.sampleCandies
import br.com.alura.aluvery.sampledata.sampleDrinks
import br.com.alura.aluvery.sampledata.sampleProducts
import br.com.alura.aluvery.ui.states.HomeScreenUiState

class HomeScreenViewModel : ViewModel() {

    private val dao = ProductDao()

    var uiState: HomeScreenUiState by mutableStateOf(
        HomeScreenUiState(
            section = mapOf(
                "Todos produtos" to dao.products(),
                "Promoções" to sampleDrinks + sampleCandies,
                "Doces" to sampleCandies,
                "Bebidas" to sampleDrinks
            ),
        onSearchChange = {
            uiState = uiState.copy(searchText = it, searchedProducts = searchedProducts(it))
        }
    ))

    private fun constainInNameOrDescription(text: String) = { product: Product ->
        product.name.contains(
            text,
            ignoreCase = true
        ) ||
                product.description?.contains(
                    text,
                    ignoreCase = true
                ) ?: false
    }

    private fun searchedProducts(text: String) : List<Product> {
        return if (text.isNotBlank()) {
            sampleProducts.filter(constainInNameOrDescription(text)) + dao.products().filter(
                constainInNameOrDescription(text)
            )
        } else emptyList()
    }
}