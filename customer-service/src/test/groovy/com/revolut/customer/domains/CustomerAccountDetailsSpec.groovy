package com.revolut.customer.domains

import spock.lang.Specification

class CustomerAccountDetailsSpec extends Specification {

    def "equals() should return true for same account number"() {
        given:
        def accountDetails1 = new CustomerAccountDetails(null, 1)
        def accountDetails2 = new CustomerAccountDetails(null, 1)

        when:
        def isEqual = accountDetails1 == accountDetails2

        then:
        isEqual
    }

    def "equals() should return false for different account number"() {
        given:
        def accountDetails1 = new CustomerAccountDetails(null, 1)
        def accountDetails2 = new CustomerAccountDetails(null, 2)

        when:
        def isEqual = accountDetails1 == accountDetails2

        then:
        !isEqual
    }

    def "hashcode() should return account number"() {
        given:
        def accountDetails1 = new CustomerAccountDetails(null, 1)

        when:
        def hashCode = accountDetails1.hashCode()

        then:
        hashCode == 1
    }
}
