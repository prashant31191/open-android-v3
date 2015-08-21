<h3><i>How to Save your Card and Bank options</i></h3>

<li>This methods are used to save payment options wherein it can be used to show <b>Saved Cards</b> and <b>Banks</b> on your UI</li>

<li><h5>How to save Credit Card?</h5></li>

```java
 citrusClient.savePaymentOption(new CreditCardOption("Bruce Wayne", "4111111111111111", "123", Month.getMonth("12"), Year.getYear("2016")), new Callback<CitrusResponse>() {
            @Override
            public void success(CitrusResponse citrusResponse) { }

            @Override
            public void error(CitrusError error) {}
        });
  ```      
        
<li><h5>How to save Debit Card?</h5></li>
```java
 citrusClient.savePaymentOption(new DebitCardOption("Tom Cruise", "4111111111111111", "111", Month.getMonth("12"), Year.getYear("2016")), new Callback<CitrusResponse>() {
            @Override
            public void success(CitrusResponse citrusResponse) { }

            @Override
            public void error(CitrusError error) { }
        });
```

<li><h5>How to save Net Banking option?</h5></li>

```java
 citrusClient.savePaymentOption(new NetbankingOption("Vijaya Bank", "CID042"), new Callback<CitrusResponse>() {
            @Override
            public void success(CitrusResponse citrusResponse) { }

            @Override
            public void error(CitrusError error) { }
        });
```
