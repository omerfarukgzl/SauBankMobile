package com.example.saugetir.utils

import android.util.Base64.*
import android.util.Log
import com.example.saugetir.utils.Constants.SECRET_KEY_BACKEND
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


object EncryptionUtil {

    @Throws(Exception::class)
    fun generateSignature(randomKey: String, body: String): String {
        val concatenatedString = SECRET_KEY_BACKEND + randomKey + body
        Log.d("body", body)
        val digest = MessageDigest.getInstance("SHA256")
        val encodedHash = digest.digest(concatenatedString.toByteArray());
        return encodeToString(encodedHash, DEFAULT).trim().uppercase(Locale.ENGLISH);
    }

    @Throws(Exception::class)
    fun isSignatureValid(randomKey: String, body: String, signature: String): Boolean {
        val generatedSignature = generateSignature(randomKey, body)
        return generatedSignature == signature
    }

    @Throws(Exception::class)
    fun encrypt(plainText: String): String {
        val secretKey = SecretKeySpec(SECRET_KEY_BACKEND.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(plainText.toByteArray(charset("UTF-8")))
        return encodeToString(encryptedBytes, NO_WRAP)
    }

    @Throws(Exception::class)
    fun decrypt(encryptedText: String): String {
        val secretKey = SecretKeySpec(SECRET_KEY_BACKEND.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decryptedBytes = cipher.doFinal(decode(encryptedText, NO_WRAP))
        return String(decryptedBytes)
    }

    fun generateRandomKey(): String {
        return UUID.randomUUID().toString()
    }
}