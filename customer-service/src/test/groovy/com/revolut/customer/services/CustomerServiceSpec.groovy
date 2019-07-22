package com.revolut.customer.services

import com.revolut.customer.domains.requests.CustomerDetailsRequest
import com.revolut.customer.storages.AccountStorage
import spock.lang.Specification

import javax.ws.rs.NotFoundException

class CustomerServiceSpec extends Specification {

    AccountStorage storage
    CustomerService service

    void setup() {
        storage = new AccountStorage()
        service = new CustomerService(storage)
    }

    def "createAccount() should return a account number for a customer"() {
        given:
        def details = new CustomerDetailsRequest(username: "batman", firstName: "bat", lastName: "man")

        when:
        def accountNumber = service.createAccount(details)

        then:
        accountNumber != null
    }

    def "createAccount() should return different account number for different customer"() {
        given:
        def details1 = new CustomerDetailsRequest(username: "batman", firstName: "bat", lastName: "man")
        def details2 = new CustomerDetailsRequest(username: "superman", firstName: "super", lastName: "man")

        when:
        def accountNumber1 = service.createAccount(details1)
        def accountNumber2 = service.createAccount(details2)

        then:
        accountNumber1 != null
        accountNumber2 != null
        accountNumber1 != accountNumber2
    }

    def "createAccount() should except if username with same username is added"() {
        given:
        def username = "batman"
        def firstName = "bat"
        def details1 = new CustomerDetailsRequest(username: username, firstName: firstName, lastName: "man")
        def details2 = new CustomerDetailsRequest(username: username, firstName: firstName, lastName: "man")

        when:
        service.createAccount(details1)
        service.createAccount(details2)

        then:
        thrown RuntimeException
    }

    def "getAccountDetails() should return customer details for an account number"() {
        given:
        def username = "batman"
        def firstName = "bat"
        def details = new CustomerDetailsRequest(username: username, firstName: firstName, lastName: "man")
        def accountNumber = service.createAccount(details)

        when:
        def accountDetails = service.getAccountDetails(accountNumber)

        then:
        accountDetails.username == details.username
        accountDetails.firstName == details.firstName
        accountDetails.lastName == details.lastName
        accountDetails.totalBalance == 0
    }

    def "getAccountDetails() should throw not found exception if customer doesn't exists with provided account number"() {
        when:
        service.getAccountDetails(0000)

        then:
        thrown NotFoundException
    }

}
