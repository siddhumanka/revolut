package com.revolut.customer.services

import com.revolut.customer.domains.CustomerBankAccountDetails
import com.revolut.customer.domains.requests.CustomerDetailsRequest
import com.revolut.customer.storages.AccountStorage
import spock.lang.Specification

class TransactionServiceSpec extends Specification {

    def "creditAmountInAccount() should add money into the customer's account"() {
        given:
        def amount = 1000
        def storage = new AccountStorage()
        def username = "username"
        def accountNumber = username.hashCode()
        def lastName = "lastName"
        def firstName = "firstName"

        storage.addCustomerAccountDetails(new CustomerBankAccountDetails(new CustomerDetailsRequest(firstName: firstName, lastName: lastName, username: username), accountNumber))

        def service = new TransactionService(storage)

        when:
        service.creditAmountInAccount(amount, accountNumber)

        then:
        def totalBalance = storage.getCustomerDetailsByAccountNumber(accountNumber).get().totalBalance
        totalBalance == amount
    }

    def "creditAmountInAccount() should add money into the customer's account balance"() {
        given:
        def amount1 = 1000
        def storage = new AccountStorage()
        def username = "username"
        def accountNumber = username.hashCode()
        def lastName = "lastName"
        def firstName = "firstName"
        def amount2 = 1000

        storage.addCustomerAccountDetails(new CustomerBankAccountDetails(
                new CustomerDetailsRequest(firstName: firstName, lastName: lastName, username: username),
                accountNumber))

        def service = new TransactionService(storage)
        service.creditAmountInAccount(amount1, accountNumber)

        when:
        service.creditAmountInAccount(amount2, accountNumber)

        then:
        def totalBalance = storage.getCustomerDetailsByAccountNumber(accountNumber).get().totalBalance
        totalBalance == amount1 + amount2
    }
}
