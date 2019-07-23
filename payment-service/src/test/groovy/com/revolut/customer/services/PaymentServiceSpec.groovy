package com.revolut.customer.services

import com.revolut.customer.clients.CustomerServiceClient
import com.revolut.customer.domains.requests.PaymentRequest
import spock.lang.Specification

class PaymentServiceSpec extends Specification {

    def "payAmount should debit money from payer's account balance and credit into payee's account"() {
        given:
        def client = Mock(CustomerServiceClient)
        def service = new PaymentService(client)

        def payee = 123
        def payer = 124
        def amount = 500
        when:
        service.payAmount(amount, new PaymentRequest(payeeAccountNumber: payee, payerAccountNumber: payer))

        then:
        1 * client.debitFromAccount(amount, payer)
        1 * client.creditInAccount(amount, payee)
    }
}
