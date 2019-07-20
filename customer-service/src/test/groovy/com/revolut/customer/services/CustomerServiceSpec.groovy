package com.revolut.customer.services

import com.revolut.customer.domains.CustomerDetails
import com.revolut.customer.storages.AccountStorage
import spock.lang.Specification

class CustomerServiceSpec extends Specification {

    AccountStorage storage
    CustomerService service

    void setup() {
        storage = new AccountStorage()
        service = new CustomerService(storage)
    }

    def "createAccount() should return a account number for a customer"() {
        given:
        def details = new CustomerDetails(username: "batman", firstName: "bat", lastName: "man")

        when:
        def accountNumber = service.createAccount(details)

        then:
        accountNumber != null
    }

    def "createAccount() should return different account number for different customer"() {
        given:
        def details1 = new CustomerDetails(username: "batman", firstName: "bat", lastName: "man")
        def details2 = new CustomerDetails(username: "superman", firstName: "super", lastName: "man")

        when:
        def accountNumber1 = service.createAccount(details1)
        def accountNumber2 = service.createAccount(details2)

        then:
        accountNumber1 != null
        accountNumber2 != null
        accountNumber1 != accountNumber2
    }

    def "getAccountDetails() should except if username with same username is added"() {
        given:
        def username = "batman"
        def firstName = "bat"
        def details1 = new CustomerDetails(username: username, firstName: firstName, lastName: "man")
        def details2 = new CustomerDetails(username: username, firstName: firstName, lastName: "man")

        when:
        def accountNumber1 = service.createAccount(details1)
        def accountNumber2 = service.createAccount(details2)

        then:
        thrown RuntimeException
    }


}
