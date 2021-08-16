package com.cornershop.android.kata.cornerbook.data.error

import com.cornershop.android.kata.cornerbook.data.responses.APIErrorResponse

class SuggestedProductErrorContainer(val status: Int = 0, val reason: String, val code: String?, val body: APIErrorResponse) : UserErrorContainer {

    override fun getUserError(): UserError {
        val errorMessage: String? = if (body.humanMessageError != null) {
            body.humanMessageError
        } else {
            "$status " + reason + " " + body.error
        }
        return UserError(UserError.Type.HTTP, errorMessage)
    }

    companion object {
        /*
            App should send the key SUGGESTIONS_ENABLED in Header during precheckout
            An 400 error should be responded with any of following tokens:
            REVISION_STARTED: Customer was notified and Shopper should wait 5 minutes before take any action
            REVISION_ON_ONGOING: Customer was notified and he/she is in process to approve or reject the suggestions
            REVISION_COMPLETED: Customer has finished the review. A payload left to be defined in this stage.
         */
        const val REVISION_STARTED = "REVISION_STARTED"
        const val REVISION_ON_ONGOING = "REVISION_ON_ONGOING"
        const val REVISION_COMPLETED = "REVISION_COMPLETED"
    }
}