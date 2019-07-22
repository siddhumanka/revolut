package com.revolut.customer.domains

import spock.lang.Specification

class CustomerBankAccountDetailsSpec extends Specification {

    def "equals() should return true for same account number"() {
        given:
        def accountDetails1 = new CustomerBankAccountDetails(null, 1)
        def accountDetails2 = new CustomerBankAccountDetails(null, 1)

        when:
        def isEqual = accountDetails1 == accountDetails2

        then:
        isEqual
    }

    def "equals() should return false for different account number"() {
        given:
        def accountDetails1 = new CustomerBankAccountDetails(null, 1)
        def accountDetails2 = new CustomerBankAccountDetails(null, 2)

        when:
        def isEqual = accountDetails1 == accountDetails2

        then:
        !isEqual
    }

    def "hashcode() should return account number"() {
        given:
        def accountDetails1 = new CustomerBankAccountDetails(null, 1)

        when:
        def hashCode = accountDetails1.hashCode()

        then:
        hashCode == 1
    }

    def "constructor() should set default value of totalBalance as 0"() {
        when:
        def accountDetails1 = new CustomerBankAccountDetails(null, 1)

        then:
        accountDetails1.totalBalance == 0
    }
}
