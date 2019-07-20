package com.revolut.customer.domains

import spock.lang.Specification

class CustomerAccountDetailsSpec extends Specification {

    def "equals() should return true for same account number"() {
        given:
        def accountDetails1 = new CustomerAccountDetails(null)
        def accountDetails2 = new CustomerAccountDetails(null)

        when:
        def isEqual = accountDetails1.equals(accountDetails2)

        then:
        isEqual
    }

    def "equals() should return false for different account number"() {
        given:
        def accountDetails1 = new CustomerAccountDetails(null)
        def accountDetails2 = new CustomerAccountDetails(null)

        when:
        def isEqual = accountDetails1.equals(accountDetails2)

        then:
        !isEqual
    }

    def "hashcode() should return account number"() {
        given:
        def accountDetails1 = new CustomerAccountDetails(null)

        when:
        def hashCode = accountDetails1.hashCode()

        then:
        hashCode == 1
    }
}
