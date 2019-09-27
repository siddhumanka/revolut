package com.revolut.customer.repositories

import com.revolut.customer.domains.CustomerBankAccountDetails
import com.revolut.customer.helpers.builders.SubscriptionRequestBuilder
import spock.lang.Specification

import static com.revolut.customer.helpers.builders.CustomerDetailsRequestBuilder.buildRequest

class AccountStorageRepositorySpec extends Specification {

    AccountStorageRepository storageRepository

    void setup() {
        def set = new HashSet<CustomerBankAccountDetails>()
        storageRepository = new AccountStorageRepository(set)
    }

    def "addCustomerAccountDetails() should add the details into the set and return true"() {
        given:
        def accountDetails = new CustomerBankAccountDetails(null, 1)

        when:
        def hasAdded = storageRepository.addCustomerAccountDetails(accountDetails)

        then:
        def actualSet = storageRepository.getAccounts()
        actualSet.size() > 0
        hasAdded
    }

    def "addCustomerAccountDetails() should return false if set already contains details"() {
        given:
        def accountDetails = new CustomerBankAccountDetails(null, 1)
        storageRepository.addCustomerAccountDetails(accountDetails)

        when:
        def hasAdded = storageRepository.addCustomerAccountDetails(accountDetails)

        then:
        !hasAdded
    }

    def "getCustomerDetailsByAccountNumber() should return customer details for an account number"() {
        given:

        def accountDetails1 = new CustomerBankAccountDetails(buildRequest(lastName: "lastname", firstName: "firstname", username: "username1"), 123,)
        def accountDetails2 = new CustomerBankAccountDetails(buildRequest(lastName: "lastname", firstName: "firstname", username: "username2"), 124,)
        accountDetails2.setSubscription(SubscriptionRequestBuilder.build())
        storageRepository.addCustomerAccountDetails(accountDetails1)
        storageRepository.addCustomerAccountDetails(accountDetails2)

        when:
        def actualAccountDetails = storageRepository.getCustomerDetailsByAccountNumber(124)

        then:
        actualAccountDetails.get().getCustomerPersonalDetails() == accountDetails2.customerPersonalDetails
        actualAccountDetails.get().getAccountNumber() == 124
        actualAccountDetails.get().getSubscription() != null
        actualAccountDetails.get().getSubscription() != null
        actualAccountDetails.get().getSubscription().accountNumber == 987654321
        actualAccountDetails.get().getSubscription().money == 200
        actualAccountDetails.get().getSubscription().time == "10:21"
    }

}
