This is a simple and basic banking application which provides
1. Adding a customer to the bank.
2. Credit money to bank account.
3. Get personal details and total balance using account number
4. Transactions : 
    4.1 Send money to another using account number
Start application by 
```
Terminal-1 : $ ./gradlew :customer-service:run 
Terminal-2 : $ ./gradlew :payment-service:run 
```

- To create new bank account
```
POST http://localhost:9000/account 
    body : {
    "firstName" : "firstName",
    "lastName"  : "lastName",
    "username"  : "username"
    }
```
returns Account number

- To credit money into the account
```
GET http://localhost:9000/credit/{accountNumber}/{amount}
```

returns 200 OK for successful credit of money

- To debit money into the account 
```
GET http://localhost:9000/debit/{accountNumber}/{amount}
```
returns 200 OK for successful debit of money
returns 403 FORBIDDEN if insufficient money

- To personal details and total balance

```
GET http://localhost:9000/account/{accountNumber}
```
return 200 OK with account balance and details

- To transfer money or pay using account number
```
POST http://localhost:9002/pay/{amount}
    body : {
        "payerAccountNumber" : payerAccountNumber[int],
        "payeeAccountNumber" : payeeAccountNumber[int],
    }
```

- Run end-to-end tests using ()
```
./gradlew :e2e-tests:test
```

- Run unit/functional tests using  (Turn off the applications or change ports in resources/test-config.yml)
```
./gradlew :customer-service:test :payment-service:test
```
