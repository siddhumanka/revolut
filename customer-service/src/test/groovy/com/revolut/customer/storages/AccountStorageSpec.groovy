package com.revolut.customer.storages

import com.revolut.customer.domains.CustomerBankAccountDetails
import com.revolut.customer.domains.requests.CustomerDetailsRequest
import com.revolut.customer.helpers.builders.CustomerDetailsRequestBuilder
import spock.lang.Specification

import static com.revolut.customer.helpers.builders.CustomerDetailsRequestBuilder.buildRequest

class AccountStorageSpec extends Specification {

    def "addCustomerAccountDetails() should add the details into the set and return true"() {
        given:
        def storage = new AccountStorage()
        def accountDetails = new CustomerBankAccountDetails(null, 1)

        when:
        def hasAdded = storage.addCustomerAccountDetails(accountDetails)

        then:
        def set = storage.getStorage()
        set.size() > 0
        hasAdded
    }

    def "addCustomerAccountDetails() should return false if set already contains details"() {
        given:
        def storage = new AccountStorage()
        def accountDetails = new CustomerBankAccountDetails(null, 1)
        storage.addCustomerAccountDetails(accountDetails)

        when:
        def hasAdded = storage.addCustomerAccountDetails(accountDetails)

        then:
        !hasAdded
    }

    def "getCustomerDetailsByAccountNumber() should return customer details for an account number"() {
        given:
        def storage = new AccountStorage()
        def accountDetails1 = new CustomerBankAccountDetails(buildRequest(lastName: "lastname", firstName: "firstname", username: "username1"), 123)
        def accountDetails2 = new CustomerBankAccountDetails(buildRequest(lastName: "lastname", firstName: "firstname", username: "username2"), 124)
        storage.addCustomerAccountDetails(accountDetails1)
        storage.addCustomerAccountDetails(accountDetails2)

        when:
        def actualAccountDetails = storage.getCustomerDetailsByAccountNumber(124)

        then:
        actualAccountDetails.get().getCustomerPersonalDetails() == accountDetails2.customerPersonalDetails
        actualAccountDetails.get().getAccountNumber() == 124
    }

}
