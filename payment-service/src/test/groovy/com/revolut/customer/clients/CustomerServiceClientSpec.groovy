package com.revolut.customer.clients

import com.revolut.customer.configs.CustomerServiceFactory
import spock.lang.Specification

import javax.ws.rs.ProcessingException
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.Response

class CustomerServiceClientSpec extends Specification {

    def "debitFromAccount() should debit money from payer's account"() {
        given:
        def payerAccountNumber = 123
        def amount = 500
        def baseUrl = "http://baseUrl:2990"
        def creditPath = "/creditPath"
        def debitPath = "/debitPath"

        def httpClient = Mock(Client)
        def serviceFactory = new CustomerServiceFactory(baseUrl: baseUrl, debitPath: debitPath, creditPath: creditPath)
        def client = new CustomerServiceClient(httpClient, serviceFactory)

        def mockWebTarget = Mock(WebTarget)
        def mockRequest = Mock(Invocation.Builder)

        when:
        client.debitFromAccount(amount, payerAccountNumber)

        then:
        1 * httpClient.target(baseUrl + debitPath + "/${payerAccountNumber}/${amount}") >> mockWebTarget
        1 * mockWebTarget.request() >> mockRequest

    }

    def "creditInAccount() should credit money into payee's account"() {
        given:
        def payeeAccountNumber = 124
        def amount = 500
        def baseUrl = "http://baseUrl:2990"
        def creditPath = "/creditPath"
        def debitPath = "/debitPath"

        def httpClient = Mock(Client)
        def serviceFactory = new CustomerServiceFactory(baseUrl: baseUrl, debitPath: debitPath, creditPath: creditPath)
        def client = new CustomerServiceClient(httpClient, serviceFactory)

        def mockWebTarget = Mock(WebTarget)
        def mockRequest = Mock(Invocation.Builder)

        when:
        client.creditInAccount(amount, payeeAccountNumber)

        then:
        1 * httpClient.target(baseUrl + creditPath + "/${payeeAccountNumber}/${amount}") >> mockWebTarget
        1 * mockWebTarget.request() >> mockRequest
    }

    def "debitFromAccount() should return response with error status code if debit fails"() {
        given:
        def payerAccountNumber = 123
        def amount = 500
        def baseUrl = "http://baseUrl:2990"
        def creditPath = "/creditPath"
        def debitPath = "/debitPath"

        def httpClient = Mock(Client)
        def serviceFactory = new CustomerServiceFactory(baseUrl: baseUrl, debitPath: debitPath, creditPath: creditPath)
        def client = new CustomerServiceClient(httpClient, serviceFactory)

        def mockWebTarget = Mock(WebTarget)
        def mockRequest = Mock(Invocation.Builder)

        when:
        def response = client.debitFromAccount(amount, payerAccountNumber)

        then:
        1 * httpClient.target(baseUrl + debitPath + "/${payerAccountNumber}/${amount}") >> mockWebTarget
        1 * mockWebTarget.request() >> mockRequest
        1 * mockRequest.get() >> Response.status(403).build()
        response.status == 403
    }

}
