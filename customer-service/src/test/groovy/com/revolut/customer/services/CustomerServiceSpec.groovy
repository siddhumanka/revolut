package com.revolut.customer.services

import com.revolut.customer.domains.CustomerBankAccountDetails
import com.revolut.customer.repositories.AccountStorageRepository
import spock.lang.Specification

import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException

import static com.revolut.customer.helpers.builders.CustomerDetailsRequestBuilder.buildRequest

class CustomerServiceSpec extends Specification {

    AccountStorageRepository storageRepository
    CustomerService service

    void setup() {
        def set = new HashSet<CustomerBankAccountDetails>()

        storageRepository = new AccountStorageRepository(set)
        service = new CustomerService(storageRepository)
    }

    def "createAccount() should return a account number for a customer"() {
        given:
        def details = buildRequest()

        when:
        def accountNumber = service.createAccount(details)

        then:
        accountNumber != null
    }

    def "createAccount() should return different account number for different customer"() {
        given:
        def details1 = buildRequest()
        def details2 = buildRequest("superman", "super", "man")

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
        def details1 = buildRequest()
        def details2 = buildRequest()

        when:
        service.createAccount(details1)
        service.createAccount(details2)

        then:
        thrown BadRequestException
    }

    def "getAccountDetails() should return customer details for an account number"() {
        given:
        def details = buildRequest()
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
