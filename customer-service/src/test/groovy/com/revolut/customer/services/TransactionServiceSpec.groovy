package com.revolut.customer.services

import com.revolut.customer.domains.CustomerBankAccountDetails
import com.revolut.customer.domains.requests.CustomerDetailsRequest
import com.revolut.customer.storages.AccountStorage
import spock.lang.Specification

import javax.ws.rs.ForbiddenException
import javax.ws.rs.NotFoundException

class TransactionServiceSpec extends Specification {

    AccountStorage storage
    TransactionService service
    String username
    int accountNumber
    String lastName
    String firstName

    void setup() {
        username = "username"
        accountNumber = username.hashCode()
        lastName = "lastName"
        firstName = "firstName"

        storage = new AccountStorage()
        addCustomerToStorage(storage, firstName, lastName, username, accountNumber)
        service = new TransactionService(storage)
    }

    def "creditAmountInAccount() should add money into the customer's account"() {
        given:
        def amount = 1000

        when:
        service.creditAmountInAccount(amount, accountNumber)

        then:
        def totalBalance = storage.getCustomerDetailsByAccountNumber(accountNumber).get().totalBalance
        totalBalance == amount
    }

    def "creditAmountInAccount() should add money into the customer's account balance"() {
        given:
        def amount1 = 1000
        def amount2 = 1000

        service.creditAmountInAccount(amount1, accountNumber)

        when:
        service.creditAmountInAccount(amount2, accountNumber)

        then:
        def totalBalance = storage.getCustomerDetailsByAccountNumber(accountNumber).get().totalBalance
        totalBalance == amount1 + amount2
    }

    def "creditAmountInAccount() should fail with not found exception with invalid account number"() {
        when:
        service.creditAmountInAccount((int) 122, (int) 230094503894)

        then:
        thrown NotFoundException
    }

    def "debitAmountFromAccount() should debit money from customer's account details"() {
        given:
        def amount1 = 1000
        def amount2 = 500

        service.creditAmountInAccount(amount1, accountNumber)

        when:
        service.debitAmountFromAccount(amount2, accountNumber)

        then:
        def totalBalance = storage.getCustomerDetailsByAccountNumber(accountNumber).get().totalBalance
        totalBalance == amount1 - amount2
    }

    def "debitAmountFromAccount() should fail with forbidden exception if debit amount is greater than total balance"() {
        given:
        def amount1 = 1000
        def amount2 = 5000

        service.creditAmountInAccount(amount1, accountNumber)

        when:
        service.debitAmountFromAccount(amount2, accountNumber)

        then:
        thrown ForbiddenException
    }


    def "debitAmountFromAccount() should fail with not found exception with invalid account number"() {
        given:
        when:
        service.creditAmountInAccount((int) 122, (int) 230094503894)

        then:
        thrown NotFoundException
    }


    private boolean addCustomerToStorage(AccountStorage storage, String firstName, String lastName, String username, int accountNumber) {
        storage.addCustomerAccountDetails(new CustomerBankAccountDetails(
                new CustomerDetailsRequest(firstName: firstName, lastName: lastName, username: username),
                accountNumber))
    }

}
