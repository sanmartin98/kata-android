package com.example.data.commons

import java.math.BigInteger
import java.security.MessageDigest

object Utils {
    @JvmStatic
    fun checkStringNotNullOrEmpty(string: String?) = !string.isNullOrEmpty()

    @JvmStatic
    fun checkStringNotNullOrBlank(string: String?) = !string.isNullOrBlank()


    @JvmStatic
    fun checkListNotEmpty(list: List<*>?): Boolean = list?.isNotEmpty() == true

    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    fun String.sha1(): String {
        val md = MessageDigest.getInstance("SHA-1")
        val digest = md.digest(toByteArray())
        val stringBuilder = StringBuilder()
        for (byte in digest) stringBuilder.append("%02x".format(byte))
        return stringBuilder.toString()
    }

    fun formatDoubleValues(displayValue: String) : String {

        return try {
            displayValue.toDouble().let {
                if (isWhole(it)){
                    String.format("%.0f", it)
                }else{
                    String.format("%.2f", it)
                }
            }
        } catch (e: Exception) {
            displayValue
        }

    }

    private fun isWhole(doubleValue: Double) : Boolean {
        return doubleValue - doubleValue.toInt() == 0.0
    }
}
