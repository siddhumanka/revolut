package com.revolut.customer.domains

import spock.lang.Specification

class CustomerAccountDetailsTest extends Specification {

    def "new CustomerAccountDetails() should add username hashkey as account number"() {
        given:
        def username = "username"

        when:
        def customerAccountDetails = new CustomerAccountDetails(new CustomerDetails(username: username))

        then:
        customerAccountDetails.getAccountNumber() == username.hashCode()
    }
}
