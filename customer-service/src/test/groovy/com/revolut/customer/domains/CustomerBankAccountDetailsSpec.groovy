package com.revolut.customer.domains

import spock.lang.Specification

class CustomerBankAccountDetailsSpec extends Specification {

    def "equals() should return true for same account number"() {
        given:
        def accountDetails1 = createCustomerBankAccountDetailsFrom()
        def accountDetails2 = createCustomerBankAccountDetailsFrom()

        when:
        def isEqual = accountDetails1 == accountDetails2

        then:
        isEqual
    }

    def "equals() should return false for different account number"() {
        given:
        def accountDetails1 = createCustomerBankAccountDetailsFrom()
        def accountDetails2 = createCustomerBankAccountDetailsFrom(null, 2)

        when:
        def isEqual = accountDetails1 == accountDetails2

        then:
        !isEqual
    }

    def "hashcode() should return account number"() {
        given:
        def accountDetails1 = createCustomerBankAccountDetailsFrom()

        when:
        def hashCode = accountDetails1.hashCode()

        then:
        hashCode == 1
    }

    def "constructor() should set default value of totalBalance as 0"() {
        when:
        def accountDetails1 = createCustomerBankAccountDetailsFrom()

        then:
        accountDetails1.totalBalance == 0
    }

    private static CustomerBankAccountDetails createCustomerBankAccountDetailsFrom(details = null, accountNumber = 1) {
        new CustomerBankAccountDetails(details, accountNumber)
    }
}
