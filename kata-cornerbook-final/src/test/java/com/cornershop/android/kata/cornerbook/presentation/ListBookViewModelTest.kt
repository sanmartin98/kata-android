package com.cornershop.android.kata.cornerbook.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cornershop.android.kata.cornerbook.CoroutineTestRule
import com.cornershop.android.kata.cornerbook.commons.Either
import com.cornershop.android.kata.cornerbook.commons.SingleValue
import com.example.logic.commons.HandledError
import com.example.logic.commons.HandledError.UnhandledError
import com.cornershop.android.kata.cornerbook.domain.model.Book
import com.cornershop.android.kata.cornerbook.domain.usecases.GetBooksUseCase
import com.cornershop.android.kata.cornerbook.presentation.models.ListBookState
import com.cornershop.android.kata.cornerbook.presentation.models.ListBooksEvents
import com.cornershop.android.kata.cornerbook.presentation.models.ListBooksViewStates
import com.cornershop.android.kata.cornerbook.presentation.models.ListBooksViewStates.DataLoadedState
import com.cornershop.android.kata.cornerbook.presentation.models.ListBooksViewStates.EmptyListState
import com.cornershop.android.kata.cornerbook.presentation.models.ListBooksViewStates.LoadingState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ListBookViewModelTest {

    @get:Rule
    val coroutineTestRule: CoroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var getBookUseCase: GetBooksUseCase

    @MockK
    lateinit var listBookMapper: ListBooksMapper

    @RelaxedMockK
    lateinit var screenStateObserver: Observer<ListBooksViewStates>

    @RelaxedMockK
    lateinit var errorDialogObserver: Observer<SingleValue<HandledError>>

    lateinit var viewModel: ListBookViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = ListBookViewModel(
                getBooksUseCase = getBookUseCase,
                booksMapper = listBookMapper
        )
        viewModel.screenState.observeForever(screenStateObserver)
        viewModel.errorDialog.observeForever(errorDialogObserver)
    }

    @After
    fun tearDown() {
        viewModel.screenState.removeObserver(screenStateObserver)
        viewModel.errorDialog.removeObserver(errorDialogObserver)
    }

    @Test
    fun `Given an Init event When the ListBookViewModel received it and the repository has no books Then return a success `() {
        coroutineTestRule.runBlockingTest {
            // Prep
            val bookList = listOf<Book>()
            coEvery { getBookUseCase.execute() } returns Either.Right(bookList)

            // Given
            val event = ListBooksEvents.InitScreenEvent
            // When
            viewModel.postEvent(event)
            // Then (Verify)
            verifyOrder {
                screenStateObserver.onChanged(LoadingState)
                screenStateObserver.onChanged(EmptyListState)
            }

            coVerify {
                getBookUseCase.execute()
            }

            confirmVerified(screenStateObserver, getBookUseCase)
        }
    }

    @Test
    fun `Given an Init event When the ListBookViewModel received it Then return a success `() {
        coroutineTestRule.runBlockingTest {
            // Prep
            val bookList = listOf<Book>(Book(
                    id = "",
                    title = "A title",
                    description = "a description",
                    author = "author"
            ))

            val bookStateList = listOf(ListBookState(
                    title = "A title",
                    author = "author"
            ))

            coEvery { getBookUseCase.execute() } returns Either.Right(bookList)
            every { listBookMapper.fromDomainToViewState(bookList) } returns bookStateList

            // Given
            val event = ListBooksEvents.InitScreenEvent
            // When
            viewModel.postEvent(event)
            // Then (Verify)
            verifyOrder {
                screenStateObserver.onChanged(LoadingState)
                listBookMapper.fromDomainToViewState(bookList)
                screenStateObserver.onChanged(DataLoadedState(bookStateList))
            }

            coVerify {
                getBookUseCase.execute()
            }

            confirmVerified(
                    screenStateObserver,
                    getBookUseCase,
                    listBookMapper
            )
        }
    }

    @Test
    fun `Given an Init event When the Usecase return an error Then return a success `() {
        coroutineTestRule.runBlockingTest {

            val handledError: HandledError = UnhandledError

            coEvery { getBookUseCase.execute() } returns Either.Left(handledError)

            // Given
            val event = ListBooksEvents.InitScreenEvent
            // When
            viewModel.postEvent(event)
            // Then (Verify)
            verifyOrder {
                screenStateObserver.onChanged(LoadingState)
                errorDialogObserver.onChanged(SingleValue(handledError))
            }

            coVerify {
                getBookUseCase.execute()
            }

            confirmVerified(
                    screenStateObserver,
                    getBookUseCase,
                    errorDialogObserver
            )
        }
    }
}