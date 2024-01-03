import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BirdsUiState(val images: List<BirdImage>)

class BirdViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<BirdsUiState> = MutableStateFlow(BirdsUiState(emptyList()))
    val uiState: StateFlow<BirdsUiState> = _uiState.asStateFlow()

    private val httpClient: HttpClient = HttpClient() {
        install(ContentNegotiation) {
            json()
        }
    }

    fun updateImages() {
        viewModelScope.launch {
            val images: List<BirdImage> = getImages()
            _uiState.update {
                it.copy(images = images)
            }
        }
    }

    override fun onCleared() {
        httpClient.close()
    }

    private suspend fun getImages(): List<BirdImage> {
        return httpClient.get("https://sebi.io/demo-image-api/pictures.json").body<List<BirdImage>>()
    }
}