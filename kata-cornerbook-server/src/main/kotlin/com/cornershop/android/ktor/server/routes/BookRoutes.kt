package com.cornershop.android.ktor.server.routes

import com.cornershop.android.kata.server.model.Book
import com.cornershop.android.kata.server.model.bookStorage
import com.cornershop.android.ktor.server.model.APIErrorResponse
import com.cornershop.android.ktor.server.utils.Constants
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing

fun Route.bookRouting() {
    route(path = "/api/v1/books") {
        get {
            if (bookStorage.isNotEmpty()) {
                call.respond(bookStorage)
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = APIErrorResponse(
                        error = Constants.BOOK_STORAGE_EMPTY,
                        userErrorMsg = "The storage is empty"
                    )
                )
            }
        }

        get(path = "{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )

            val book = bookStorage.find { it.id == id } ?: return@get call.respond(
                status = HttpStatusCode.NotFound,
                message = APIErrorResponse(
                    error = Constants.BOOK_NOT_VALID,
                    userErrorMsg = "This is a user error message"
                )
            )

            call.respond(book)
        }

        post {
            val book = call.receive<Book>()
            bookStorage.add(book)
            call.respond(HttpStatusCode.OK)
        }

        delete(path = "{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)

            if (bookStorage.removeIf { it.id == id }) {
                call.respondText("Book removed", status = HttpStatusCode.Accepted)
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = APIErrorResponse(
                        error = Constants.BOOK_NOT_VALID,
                        userErrorMsg = "This is a user error message"
                    )
                )
            }
        }
    }
}

fun Application.registerBookRoutes() {
    routing {
        bookRouting()
    }
}