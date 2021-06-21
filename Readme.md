# Assumptions
* Currency is £.
* Order when modified changes the timestamp to the modification time.
* Generation of the trade is a print statement to console followed by removal of matching orders from orderbook.
* Decimal rounding isnt necessary on price field.
* Match is when price and quantity of buy and sell orders match. 

#Running using maven - running Unit tests
``` 
mvn clean install
``` 

#Developer Notes
* OrderHandler is the suggested order handling interface in the exercise.
* OrderBookHandler is the implementation of OrderHandler. Its composed with OrderBookMap that returns an OrderBook.
* OrderBookMap is the singleton class to facilitate support for multiple Order books.
* OrderBook handles all the order operations.
* OrderEntry is a model class that tracks modification count , timestamp. Its composed with Order.
* Order is the model class for an order.
* AggregatedOrderEntry is the model class used for aggregated order to calculate price.
* DescendingPrice is the comparator used by OrderBook's ask and bid order maps to sort orders by price and on timeStamp if price match.
* OrderTest has the unit tests.

 
