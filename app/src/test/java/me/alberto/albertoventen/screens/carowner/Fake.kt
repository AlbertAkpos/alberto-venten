package me.alberto.albertoventen.screens.carowner

import me.alberto.albertoventen.model.CarOwner

object Fake {
    private val fakeCarOwners = ArrayList<CarOwner>()
    fun getFakeCarOwners(): List<CarOwner> {
        fakeCarOwners.addAll(
            listOf(
                CarOwner(
                    1,
                    "Scott",
                    "Halnning",
                    "scot@so-net.com",
                    "Thailand",
                    "Lincoln",
                    "1996",
                    "Maroon",
                    "Male",
                    "Staff Accountant III",
                    "Cras mi pede, malesuada in, imperdiet et, commodo vulputate"
                ),
                CarOwner(
                    2,
                    "Sissy",
                    "Willbourne",
                    "swillbourne2@xinhuanet.com",
                    "Bolivia",
                    "Lexus",
                    "2004",
                    "Puce",
                    "Female",
                    "Staff Accountant I",
                    "Maecenas rhoncus aliquam lacus. Morbi quis tortor id nulla ultrices aliquet"
                ),
                CarOwner(
                    3,
                    "Birch",
                    "Sworder",
                    "bsworder5@amazon.de",
                    "China",
                    "Ford",
                    "2011",
                    "Yellow",
                    "Male",
                    "Operator",
                    "Duis at velit eu est congue elementum."
                ),
                CarOwner(
                    4,
                    "Cyndy",
                    "Orgill",
                    "corgill9@altervista.org",
                    "Indonesia",
                    "Maybach",
                    "2007",
                    "Mauv",
                    "Female",
                    "Budget/Accounting Analyst IV",
                    "Etiam pretium iaculis justo. In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus."
                )
            )

        )

        return fakeCarOwners
    }
}