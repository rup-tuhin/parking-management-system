# Vehicle parking Management System.
# Getting Started
A system, to keep track of incoming vehicles, capture in_time, capture parking time, capture out_time. 
This is a sample application for a vechicle parking management system.
There is a parking lot, where vechicle can enter from 2 entry gates, and can exit from 2 exit gates.

When a vechicle enters the premise, system will capture its registration Number, and will issue a ticket.
Upon existing, the ticket is to be proceduced at the exit gate.
System will calculate the parked time, and will show payable amount.


While vehicle is entering - 
	POST - http://localhost:8080/service/{vehicleRegNumber}/in

While vehicle is exiting - 
	POST - http://localhost:8080/service/{ticketId}/out

In addition,
* for any vehicle number, we should be able to get the current charge for the parking at any given point of time. 
		http://localhost:8080/service/KA53EF4622/status
* get the list of vehicles parked with time since parked 
		http://localhost:8080/service/status
* check if slots are available for parking
		http://localhost:8080/service/freeSlots

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Rest Repositories](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/htmlsingle/#howto-use-exposing-spring-data-repositories-rest-endpoint)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing Neo4j Data with REST](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
* [Accessing MongoDB Data with REST](https://spring.io/guides/gs/accessing-mongodb-data-rest/)

