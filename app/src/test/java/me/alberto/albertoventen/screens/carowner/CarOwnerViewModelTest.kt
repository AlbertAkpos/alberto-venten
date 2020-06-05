package me.alberto.albertoventen.screens.carowner

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import me.alberto.albertoventen.model.FilterItem
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CarOwnerViewModelTest {
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun should_return_correct_list_size_for_given_country_and_year() {
        val predicate = FilterItem(
            1,
            1996,
            2011,
            "",
            listOf(
                "Indonesia",
                "China",
                "Bolivia",
                "Peru"
            ),
            listOf()
        )

        runBlocking {
            launch(Dispatchers.Main) {
                val list = Fake.getFakeCarOwners()
                val filterList = FilterObject.filterCarOwners(list, predicate)
                assertEquals(3, filterList.size)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun should_return_correct_list_size_when_for_given_gender() {
        val predicate = FilterItem(
            1,
            2007,
            2009,
            "male",
            listOf(
                "Brazil",
                "Indonesia",
                "Egypt",
                "Peru"
            ),
            listOf(
                "Green",
                "Violet",
                "Yellow",
                "Blue"
            )
        )

        runBlocking {
            launch(Dispatchers.Main) {
                val list = Fake.getFakeCarOwners()
                val filterList = FilterObject.filterCarOwners(list, predicate)
                assertEquals(0, filterList.size)
            }
        }
    }
}