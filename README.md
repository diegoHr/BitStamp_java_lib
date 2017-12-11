# API client of BitStamp in Java

<p align="center">
  <img src="https://github.com/diegoHr/BitStamp_java_lib/raw/master/logo.png?raw=true" alt="BitStamp API client logo"/>
</p>


Query the BitStamp API in Java 17. No external libraries used. And It is compatible with android.


## How to use
It is a maven project, to create the JAR package only execute `mvn clean package -DskipTests` and it will be in the `target` folder.

## Example

### Login
```Java
import com.diego_hernando.bitStamp_java_lib.BitStampAPI;
...
BitStampApi api=new BitStampAPI();
api.setId("id");
api.setKey("key");
api.setSecret("secret");

```

### Operations
```Java
import com.diego_hernando.bitStamp_java_lib.BitStampCurrencyPairs;
...

//Get the balance of ETH and EUR from your account.
String ethEurBalance=api.getBalance(BitStampCurrencyPairs.ETHEUR);

//Get all transactions that you have done in BitStamp.
String transactions=api.getMyTransactions();

//Get your open orders in BitStamp.
String openOrders=api.getOpenOrders();
```
All operations of the API are available except all relating to depositing and withdrawing operations.

** All operations return a String with the JSON response of the server, It is responsability of the user parse and check the JSON.**

### Test execution
If you want to run the test, you will need a BitStamp account and create an access to the API, and complete with your access data the fields of ApiLoginDataTest.java before to execute BitStampAPITest.java.

If you want to execute test with maven input this in the console:
`mvn test`


## API definition

https://www.bitstamp.net/api/
## Development status
Now this project is in beta, the buying and selling operations need to be tested in depth.

And in a future implement public open data operations with the websocket API.
## Contributors
Any contributions are highly appreciated. The best way to contribute code is to open a
[pull request on GitHub](https://help.github.com/articles/about-pull-requests/).

## License
MIT License.
