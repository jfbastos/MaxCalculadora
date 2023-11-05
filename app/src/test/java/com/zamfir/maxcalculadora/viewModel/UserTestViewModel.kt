import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zamfir.maxcalculadora.domain.usecase.UserUseCase
import com.zamfir.maxcalculadora.viewmodel.UserViewModel
import com.zamfir.maxcalculadora.viewmodel.state.UserState
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

@ExperimentalCoroutinesApi
class UserViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var userUseCase: UserUseCase

    private lateinit var userViewModel: UserViewModel

    private lateinit var observer: Observer<UserState>

    @Before
    fun setup() {
        userUseCase = mockk()
        Dispatchers.setMain(testDispatcher)
        userViewModel = UserViewModel(userUseCase)
        observer = mockk(relaxUnitFun = true)
    }

    @Test
    fun `when saveUserData is called, userState should update`() = runBlocking {
        //Aqui eu configurei para o liveData observar para sempre esse objeto
        userViewModel.userState.observeForever(observer)

        // É necessário configurar o mock
        every { userUseCase.salvarUsuario("1000", "Murillo") } just Runs

        // Ação
        userViewModel.salvarDadosUsuario("1000", "Murillo")

        //estava dando problema de tempo para verificação do objeto liveData. Atrasei para poder verificar e sem lançar
        //exceção
        testDispatcher.scheduler.advanceUntilIdle()

        //Aqui eu faço a verificação se o valor experado é igual do atual. Como quero saber se o viewModel está mudando os valores
        assertEquals(true, userViewModel.userState.value?.isSuccess)
    }

    @Test
    fun `when call saveUserData with error, return state error`() {
        userViewModel.userState.observeForever(observer)

        every { userUseCase.salvarUsuario("1000", "Murillo") } throws RuntimeException("Error")

        userViewModel.salvarDadosUsuario("1000", "Murillo")

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(RuntimeException("Error").message, userViewModel.userState.value?.error?.message)
    }

    @After
    fun finishedTest() {
        userViewModel.userState.removeObserver(observer)
        Dispatchers.resetMain()
    }
}
