package com.example.abheisenberg.inshortscontest.model

/**
 * Created by abheisenberg on 9/9/17.
 */
data class Article (
        val ID: Int,
        val TITLE: String,
        val URL: String,
        val PUBLISHER: String,
        val CATEGORY: String,
        val HOSTNAME: String,
        val TIMESTAMP: Long,
        var FAV: Int
)