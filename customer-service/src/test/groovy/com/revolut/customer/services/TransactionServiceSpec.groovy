package com.revolut.customer.services

import com.revolut.customer.domains.CustomerBankAccountDetails
import com.revolut.customer.repositories.AccountStorageRepository
import spock.lang.Specification

import javax.ws.rs.ForbiddenException
import javax.ws.rs.NotFoundException

import static com.revolut.customer.helpers.builders.CustomerDetailsRequestBuilder.buildRequest


class TransactionServiceSpec extends Specification {

    AccountStorageRepository storageRepository
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

        def set = new HashSet<CustomerBankAccountDetails>()
        storageRepository = new AccountStorageRepository(set)
        addCustomerToStorage(storageRepository, firstName, lastName, username, accountNumber)
        service = new TransactionService(storageRepository)
    }

    def "creditAmountInAccount() should add money into the customer's account"() {
        given:
        def amount = 1000

        when:
        service.creditAmountInAccount(amount, accountNumber)

        then:
        def totalBalance = storageRepository.getCustomerDetailsByAccountNumber(accountNumber).get().totalBalance
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
        def totalBalance = storageRepository.getCustomerDetailsByAccountNumber(accountNumber).get().totalBalance
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
        def totalBalance = storageRepository.getCustomerDetailsByAccountNumber(accountNumber).get().totalBalance
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


    private boolean addCustomerToStorage(AccountStorageRepository storage, String firstName, String lastName, String username, int accountNumber) {
        storage.addCustomerAccountDetails(new CustomerBankAccountDetails(
                buildRequest(firstName: firstName, lastName: lastName, username: username),
                accountNumber))
    }

}
