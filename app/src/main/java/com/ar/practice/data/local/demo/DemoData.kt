package com.ar.practice.data.local.demo

import com.ar.practice.R
import com.ar.practice.R.drawable.*
import com.ar.practice.data.model.Country
import com.ar.practice.data.model.Transaction

object DemoData {
    val countries = listOf(
        Country(
            1,
            ic_flag_aland_island,
            "Aland Islands",
            false
        ),
        Country(
            2,
            ic_flag_bulgaria,
            "Bulgaria",
            false
        ),
        Country(
            3,
            ic_flag_india,
            "India",
            false
        ),
        Country(
            4,
            ic_flag_france,
            "France",
            false
        ),
        Country(
            5,
            ic_flag_romanian_leu,
            "Romanian Leu",
            false
        ),
        Country(
            6,
            ic_flag_uk,
            "United Kingdom",
            false
        ),
        Country(
            7,
            ic_flag_usa,
            "United States of America",
            false
        ),
        Country(
            8,
            ic_flag_romanian_leu,
            "Romanian Leu",
            false
        ),
        Country(
            9,
            ic_flag_uk,
            "United Kingdom",
            false
        ),
        Country(
            10,
            ic_flag_usa,
            "United States of America",
            false
        )
    )

    val transactionHistory = listOf(
        Transaction(
            id = 0,
            date = "Today",
            title = "Added to your CAD account",
            type = "in",
            icon = ic_cash_in,
            amount = "+ 300.00",
        ),
        Transaction(
            id = 1,
            date = "Today",
            title = "Payment to Mike",
            type = "out",
            icon = ic_cash_out,
            amount = "- 200.00",
        ),
        Transaction(
            id = 2,
            date = "10 May 2024",
            title = "Added to your CAD account",
            type = "in",
            icon = ic_cash_in,
            amount = "+ 400.00",
        ),
        Transaction(
            id = 3,
            date = "10 May 2024",
            title = "Payment to Jon Doe",
            type = "out",
            icon = ic_cash_out,
            amount = "- 220.00",
        ),
        Transaction(
            id = 4,
            date = "25 March 2023",
            title = "Added to your CAD account",
            type = "in",
            icon = ic_cash_in,
            amount = "+ 500.00",
        ),
        Transaction(
            id = 5,
            date = "25 March 2023",
            title = "Payment to Mike",
            type = "out",
            icon = ic_cash_out,
            amount = "- 300.00",
        ),
    )
}