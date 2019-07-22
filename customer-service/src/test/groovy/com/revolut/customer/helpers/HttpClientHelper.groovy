package com.revolut.customer.helpers

import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.Response

import static javax.ws.rs.client.Entity.entity
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE

class HttpClientHelper {

    static Response createAccount(String firstName, String lastName, String username, WebTarget target) {
        return target.path("/account")
                .request(APPLICATION_JSON_TYPE)
                .post(entity([firstName: firstName, lastName: lastName, username: username], APPLICATION_JSON_TYPE))
    }


    static Response getAccountDetails(accountNumber, target) {
        return target.path("/account/${accountNumber}")
                .request(APPLICATION_JSON_TYPE)
                .get()
    }

}
