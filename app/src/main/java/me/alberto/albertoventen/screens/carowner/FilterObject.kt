package me.alberto.albertoventen.screens.carowner

import de.siegmar.fastcsv.reader.CsvReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.alberto.albertoventen.model.CarOwner
import me.alberto.albertoventen.model.FilterItem
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object FilterObject {

    suspend fun filterCarOwners(
        listOfCarOwners: List<CarOwner>,
        predicate: FilterItem
    ): List<CarOwner> {
        val list = ArrayList<CarOwner>()
        withContext(Dispatchers.IO) {
            for (item in listOfCarOwners) {
                if (item.carColor.toLowerCase() in predicate.colors.map { it.toLowerCase() }
                    || predicate.colors.isEmpty()
                ) {
                    if (item.year.toLong() in predicate.startYear..predicate.endYear) {
                        if (item.country.toLowerCase() in predicate.countries.map { it.toLowerCase() } ||
                            predicate.countries.isEmpty()
                        ) {

                            list.add(
                                CarOwner(
                                    item.id,
                                    item.firstName,
                                    item.lastName,
                                    item.email,
                                    item.country,
                                    item.carModel,
                                    item.year,
                                    item.carColor,
                                    item.gender,
                                    item.jobTitle,
                                    item.bio
                                )
                            )

                        }
                    }
                }
            }
        }

        return list
    }

    suspend fun fetchFile(file: File): List<CarOwner> {
        val list = ArrayList<CarOwner>()
        withContext(Dispatchers.IO) {
            try {
                val reader = CsvReader()
                reader.setFieldSeparator(',')
                reader.setContainsHeader(true)
                reader.setSkipEmptyRows(true)
                reader.parse(BufferedReader(FileReader(file.absolutePath)))
                    .use { parser ->
                        while (true) {
                            val row = parser.nextRow() ?: break
                            list.add(
                                CarOwner(
                                    row.getField(0).toLong(),
                                    row.getField(1),
                                    row.getField(2),
                                    row.getField(3),
                                    row.getField(4),
                                    row.getField(5),
                                    row.getField(6),
                                    row.getField(7),
                                    row.getField(8),
                                    row.getField(9),
                                    row.getField(10)
                                )
                            )
                        }
                    }
            } catch (error: Exception) {
                error.printStackTrace()
            }
        }
        return list
    }
}