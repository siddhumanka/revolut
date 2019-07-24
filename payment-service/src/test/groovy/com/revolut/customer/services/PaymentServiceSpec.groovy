package com.revolut.customer.services

import com.revolut.customer.clients.CustomerServiceClient
import com.revolut.customer.domains.requests.PaymentRequest
import spock.lang.Specification

import javax.ws.rs.ForbiddenException
import javax.ws.rs.core.Response

class PaymentServiceSpec extends Specification {

    def "payAmount() should debit money from payer's account balance and credit into payee's account"() {
        given:
        def client = Mock(CustomerServiceClient)
        def service = new PaymentService(client)

        def payee = 123
        def payer = 124
        def amount = 500
        when:
        service.payAmount(amount, new PaymentRequest(payeeAccountNumber: payee, payerAccountNumber: payer))

        then:
        1 * client.debitFromAccount(amount, payer) >> Response.status(200).build()
        1 * client.creditInAccount(amount, payee)
    }

    def "payAmount() should fail with ForbiddenException if debit fails with 403"() {
        given:
        def client = Mock(CustomerServiceClient)
        def service = new PaymentService(client)

        def payee = 123
        def payer = 124
        def amount = 500

        when:
        service.payAmount(amount, new PaymentRequest(payeeAccountNumber: payee, payerAccountNumber: payer))

        then:
        1 * client.debitFromAccount(amount, payer) >> Response.status(403).build()
        0 * client.creditInAccount(amount, payee)
        thrown ForbiddenException
    }
}
