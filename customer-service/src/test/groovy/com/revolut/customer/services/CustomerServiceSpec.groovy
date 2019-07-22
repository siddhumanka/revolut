package com.revolut.customer.services

import com.revolut.customer.domains.requests.CustomerDetailsRequest
import com.revolut.customer.storages.AccountStorage
import spock.lang.Specification

import javax.ws.rs.BadRequestException
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
        def details = createRequest()

        when:
        def accountNumber = service.createAccount(details)

        then:
        accountNumber != null
    }

    private CustomerDetailsRequest createRequest(username = "batman", firstName = "bat", lastName = "man") {
        new CustomerDetailsRequest(username: username, firstName: firstName, lastName: lastName)
    }

    def "createAccount() should return different account number for different customer"() {
        given:
        def details1 = createRequest()
        def details2 = createRequest("superman", "super", "man")

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
        def details1 = createRequest()
        def details2 = createRequest()

        when:
        service.createAccount(details1)
        service.createAccount(details2)

        then:
        thrown BadRequestException
    }

    def "getAccountDetails() should return customer details for an account number"() {
        given:
        def details = createRequest()
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
