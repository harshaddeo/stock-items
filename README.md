# Problem
In an online shop we would like to provide for all available products the stock data. Additionally
for analytic reasons, we also want to keep track on some basic statistics.
Develop an application in JAVA or Scala with an HTTP API for putting and getting the data in an
easy and convenient way. Please provide within your application the following endpoints:

#### POST /updateStock

#### GET /stock?productId=vegetable-123

#### GET /statistics?time=[today,lastMonth]


## Assumptions- 
1. User do get request to see the statistics of tock for particular time rage. Range can be eiter TODAY or LAST_MONTH. </br>
If we pass TODAY then we will only see statistics of today(starting from midnight till now). 
And if we pass LAST_MONTH then we can see statistics of last month till now.

2. When user do 'updateStock' post request, user is supposed to pass complete stock item. </br>
- Following is brief elaboration how logic works-
- If the requested stock quantity is greater than existing stock quantity the it will be treated as stockUpdate request is performed by 
Vendor (updating stock with new quantity) </br>
- If the requested stock quantity is less than existing stock quantity the it will be treated as stockUpdate is performed by Consumer. 



## Technologies Used
Java8 </br>
Maven </br>
SpringBoot application for Rest API </br>
Rest assured for test </br>
Postman tool for testing endpoints </br>

## Steps to run the project:

- Go to command line and type git clone https://github.com/harshaddeo/stock-items.git
- Go to root directory of checked out project.
- Run mvn clean install
- Open up intellij. Go to Open. Go to git repo folder and open project . On file menu go to project structure. 
- Update language level support to 8
- Go to any test and run the junit test.
- NOTE: Maven and Java 8 must be installed.

- Command : mvn clean install 
- Maven will load all the depdendencies and build the project(run the tests as well), and run the application.

## On postman tool(or your favorite rest client) -
1. Run GET request - /stock?productId=vegetable-123
You will see the below output - 
```json
{
    "product": {
        "productId": "milk-002",
        "stock": {
            "id": "000002",
            "timestamp": "2019-05-20T15:46:05 +0200",
            "quantity": 870
        }
    },
    "requestTimeStamp": "2019-05-20T13:46:31.964"
}
```
2. Run Get Request to get statistics of last month
- http://localhost:8080/statistics?time=LAST_MONTH
you will get output as -
```json
{
    "requestTimestamp": "2019-05-20T13:47:00.955",
    "range": "LAST_MONTH",
    "topAvailableProducts": [
        {
            "id": "000001",
            "timestamp": "2019-05-20T15:46:05.147+02:00",
            "productId": "vegetable-123",
            "quantity": 1000
        },
        {
            "id": "000002",
            "timestamp": "2019-05-20T15:46:05.148+02:00",
            "productId": "milk-002",
            "quantity": 870
        }
    ],
    "topSellingProducts": []
}
```
3. Run Post request to update stocks as
http://localhost:8080/updateStock
with body as raw content (JSON)
```json
{
	"id" : "000002",
	"timestamp" : "2019-04-20T20:44:01 +0000",
	"productId": "milk-002",
	"quantity": "50"

}
```
You will see the status as 201 - Created.

4. After running queries 1 & 2 again you will see the stock is updated with appropriate values.


## Thoughts about problem -

I would like explain how I approached the problem. For that I will talk about it under each end point which is created.

1. POST /updateStock
- The purpose of this URL is to update the stock. 
- An update can occur in 2 scenarios - increasing inventory or consuming inventory.
- To know if the inventory (stock) is updated by vendor or consumer, I added a logic based on quantity which is provided in the request.
Obviously if the user of the endpoint is customer/consumer of stock then he is allowed to send same or less quantity in request.
- If the request is from vendor then itemsSold value for that stock id will remain 0.
- If the request is from Consumer/customer then itemsold value for that stock id will be (existing quantity - requested quantity)
- items_sold table will be updated with requested timestamp and itemsold value and stockid.
- So we can now easily caclulate top selling items by grouping up stock id and adding up all itemsSold values.

2. GET /stock?productId=vegetable-123
- This is simple get request which is responsible to deliver information about the requested product.
- I create 2 entities as 'Product' and 'Stock' where each product having 1 stock. (One to One mapping)
- This also tells us that each stock is belongs to a product (bi directional mapping)
- requesting for particular product will deliver product attributes along with stock attributes.

3. GET /statistics?time=[today,lastMonth]
- time can be either today or lastmonth. According to timespan the api will return the statistics to the client.
- Statics will have information about top available products which is top 3 product having highest quantity available.
Topselling product are top 3 product having highest value of items sold for the given span. 


## Edge Cases considered

Case 1.
When user updates the stock with old timestamp, then it will impact on 'topsellingitems' for that timestamp.
As this is expected that if vendor wanted to update the information of 'topsellingitems' from past month then he can manipulate it.

## Edge Cases need to be consider further-
Case 1.
How you handle concurrent request against one endpoint?
- If it expected that updateStock operation can be performed concurrently over network.
- we can implement a service that controls the updates on stock using optimistic concurrency control. 
- This means that the service always expects to receive 'If-Unmodified-Since' and 'If-Match' HTTP headers. 
- These headers are also called conditional headers, because they condition the service to respond according to their values. 
When sent by the client, they take the values of the Last-Modified, respectively ETag headers.

Case 2
- Is the backend always in a valid state? - What about Race conditions?
- In case of race condition we can make the client to send an appropriate response (something like 409/410 http codes) 
with details indicating that this request is no more valid and the client should try again. 

Case 3
- When updateStock enters future timestamp?
- This can be handled by specifying validation of current_timestamp in the functionality.

Case 4
- If there are many items having same (highest) quantity and (highest) same soldeitem count. Then which 3 need to be displayed?
- Obviously, we should not restrict topavailable items and topselling items to limit 3, we should display multiple by relevance. 

Case 5
- The solution is completely RESTFUL?
- No, it is not following all the guidelines of REST application. 
- E.g. representation of the resource does not provide information or hypermedia about another resources. It should navigate 
or give knowledge to the user about further resources. (Hateos)

