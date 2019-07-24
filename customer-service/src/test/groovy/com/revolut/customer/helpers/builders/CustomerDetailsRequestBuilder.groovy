package com.revolut.customer.helpers.builders

import com.revolut.customer.domains.requests.CustomerDetailsRequest

class CustomerDetailsRequestBuilder {

    public static CustomerDetailsRequest buildRequest(username = "batman", firstName = "bat", lastName = "man") {
        new CustomerDetailsRequest(username: username, firstName: firstName, lastName: lastName)
    }

}
