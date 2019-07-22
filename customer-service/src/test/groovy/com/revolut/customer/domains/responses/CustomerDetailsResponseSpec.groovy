package com.revolut.customer.domains.responses

import spock.lang.Specification

class CustomerDetailsResponseSpec extends Specification {

    def "withFieldName() should create the valid object or default null or 0"() {
        given:
        def firstName = "firstName"
        def lastName = "lastName"
        def totalBalance = 19

        when:
        def response = new CustomerDetailsResponse()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withTotalBalance(totalBalance)

        then:
        response.totalBalance == totalBalance
        response.lastName == lastName
        response.firstName == firstName
        response.username == null
    }
}
