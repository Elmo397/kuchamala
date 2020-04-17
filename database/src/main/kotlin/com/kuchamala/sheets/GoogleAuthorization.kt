package com.kuchamala.sheets

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.SheetsScopes
import java.io.File
import java.io.InputStreamReader

class GoogleAuthorization {
    fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {
        val jsonFactory = JacksonFactory.getDefaultInstance()
        val scopes = listOf(SheetsScopes.SPREADSHEETS_READONLY)
        val clientSecrets = loadClientSecrets(jsonFactory)

        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        val flow = GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, jsonFactory, clientSecrets, scopes)
            .setDataStoreFactory(FileDataStoreFactory(File("tokens")))
            .setAccessType("offline")
            .build()

        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

    private fun loadClientSecrets(jsonFactory: JacksonFactory): GoogleClientSecrets {
        val credentialsFilePath = "/credentials.json"
        val credential = GoogleAuthorization::class.java.getResourceAsStream(credentialsFilePath)

        return GoogleClientSecrets.load(jsonFactory, InputStreamReader(credential))
    }
}