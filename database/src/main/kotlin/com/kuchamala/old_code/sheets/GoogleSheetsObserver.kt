package com.kuchamala.old_code.sheets

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.kuchamala.sheets.GoogleAuthorization
import java.util.*

class GoogleSheetsObserver {
    fun run() {
        val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val credential = GoogleAuthorization().getCredentials(httpTransport)

        val tableCheckPeriodForChanges: Long = 1000
        val startCheckDelay: Long = 0

        Timer().scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    checkTableForChanges(httpTransport, credential)
                }
            },
            startCheckDelay,
            tableCheckPeriodForChanges
        )
    }

    //TODO: check table
    private fun checkTableForChanges(httpTransport: NetHttpTransport, credential: Credential) {
        GoogleSheetsReader(httpTransport, credential).startRead()
    }
}