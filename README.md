# dataConsumer
Data Consumer Api

Please install the rabbit MQ and make sure it's running before running the application.

1)- It is using event driven architecture (Rabbit MQ). 
As soon as a using first dataProducer store and update endpoints will be triggered, 
this consumer service will try to retrieve the message puuhed to queue(Exchange).

2)- It is exposing a rest get endpoint which will be used by consumer facing api for fetching the data stored in files(Accordance with fileType header).

Get Request -> http://localhost:9001/read






