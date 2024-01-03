import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

class BirdViewModel: ViewModel() {
    private val httpClient: HttpClient = HttpClient() {
        install(ContentNegotiation) {
            json()
        }
    }
}