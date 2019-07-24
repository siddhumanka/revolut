package e2e

import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget

import static e2e.helpers.HttpClientHelper.*
import static javax.ws.rs.core.Response.Status.NO_CONTENT

class CustomerBankingJourney extends Specification {

    @Shared
    WebTarget target1

    @Shared
    WebTarget target2

    def setup() {
        def client = ClientBuilder.newClient()
        target1 = client.target("http://localhost:9000")
        target2 = client.target("http://localhost:9002")
    }

    def "Payment should reflect in payee's and payer's bank account both"() {
        given: "For two customers"
        def firstName1 = "super"
        def lastName1 = "man"
        def username1 = "superman"

        def firstName2 = "bat"
        def lastName2 = "man"
        def username2 = "batman"
        def accountNumber1 = createAccount(firstName1, lastName1, username1, target1).readEntity(Integer)
        def accountNumber2 = createAccount(firstName2, lastName2, username2, target1).readEntity(Integer)

        def amount1 = "1000"
        def amount2 = "1000"
        def amountToPay = "500"

        creditMoneyInAccount(accountNumber1, amount1, target1)
        creditMoneyInAccount(accountNumber2, amount2, target1)

        when: "when paying one customer 500 from another"
        def response = payUser(amountToPay, accountNumber1, accountNumber2, target2)

        then: "should reflect the amount in both's bank accounts"
        response.status == NO_CONTENT.statusCode

        def accountDetails1 = getAccountDetails(accountNumber1, target1).readEntity(Map)
        def accountDetails2 = getAccountDetails(accountNumber2, target1).readEntity(Map)

        accountDetails1.totalBalance == 500
        accountDetails2.totalBalance == 1500


    }

}
