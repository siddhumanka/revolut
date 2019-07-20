package com.revolut.customer.storages

import com.revolut.customer.domains.CustomerAccountDetails
import spock.lang.Specification

class AccountStorageSpec extends Specification {

    def "addCustomerAccountDetails() should add the details into the set and return true"() {
        given:
        def storage = new AccountStorage()
        def accountDetails = new CustomerAccountDetails(null)

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
        def accountDetails = new CustomerAccountDetails(null)
        storage.addCustomerAccountDetails(accountDetails)

        when:
        def hasAdded = storage.addCustomerAccountDetails(accountDetails)

        then:
        !hasAdded
    }

}
