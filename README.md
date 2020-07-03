# Vehicle parking Management System.
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
