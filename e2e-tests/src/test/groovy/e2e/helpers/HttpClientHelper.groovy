package e2e.helpers

import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.Response

import static javax.ws.rs.client.Entity.entity
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE

class HttpClientHelper {

    static Response createAccount(String firstName, String lastName, String username, WebTarget target) {
        return target.path("/account")
                .request(APPLICATION_JSON_TYPE)
                .post(entity([firstName: firstName, lastName: lastName, username: username], APPLICATION_JSON_TYPE))
    }

    static Response getAccountDetails(accountNumber, target) {
        return target.path("/account/${accountNumber}")
                .request(APPLICATION_JSON_TYPE)
                .get()
    }

    static Response creditMoneyInAccount(accountNumber, amount, target) {
        target.path("/credit/${accountNumber}/${amount}")
                .request(APPLICATION_JSON_TYPE)
                .get()
    }

    static Response payUser(amount, payerAccountNumber, payeeAccountNumber, target) {
        target.path("/pay/${amount}")
                .request(APPLICATION_JSON_TYPE)
                .post(entity([payerAccountNumber: payerAccountNumber.intValue(), payeeAccountNumber: payeeAccountNumber.intValue()], APPLICATION_JSON_TYPE))


    }
}
