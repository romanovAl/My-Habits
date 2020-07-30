package ru.romanoval.testKotlin.data.model

import java.io.Serializable

data class DeleteResponse (val code: Int, val message: String) : Serializable