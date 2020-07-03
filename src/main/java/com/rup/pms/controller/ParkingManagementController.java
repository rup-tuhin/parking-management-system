package com.rup.pms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rup.pms.model.VehicleTicket;
import com.rup.pms.service.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rup.pms.error.PMSException.badRequestIfTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/service")
public class ParkingManagementController {

    private static final Logger LOG = LoggerFactory.getLogger(ParkingManagementController.class);

    @Autowired
    private ParkingService service;

    @PostMapping(path = "{vehicleNumber}/in", produces = APPLICATION_JSON_VALUE)
    public String parkIn(@PathVariable(value = "vehicleNumber") String vehicleNumber) {
        badRequestIfTrue(service.getParkingDetails(vehicleNumber) != null, "The vehicle is already parked");
        VehicleTicket ticket = service.parkIn(vehicleNumber);
        LOG.info("Parking the vehicle [{}] in slot [{}]", vehicleNumber, ticket.getSlotNumber());
        return marshall(ticket);
    }

    @PostMapping(path = "{ticketId}/out", produces = APPLICATION_JSON_VALUE)
    public String parkOut(@PathVariable(value = "ticketId") String ticketId) {
        VehicleTicket ticket = service.getTicketDetails(ticketId);
        LOG.info("Clearing out slot [{}]", ticket.getSlotNumber());
        return service.parkOut(ticket);
    }

    @GetMapping(path = "{vehicleNumber}/status", produces = APPLICATION_JSON_VALUE)
    public String getVehicleParkingStatus(@PathVariable(value = "vehicleNumber") String vehicleNumber) {
        LOG.info("Collecting info of [{}]", vehicleNumber);
        VehicleTicket ticket = service.getParkingDetails(vehicleNumber);
        return marshall(ticket);
    }

    @GetMapping(path = "status", produces = APPLICATION_JSON_VALUE)
    public String getParkingStatus() {
        List<VehicleTicket> tickets = service.getCurrentParkingStatus();
        LOG.info("currently occupied slots [{}]", tickets.size());
        return marshall(tickets);
    }

    @GetMapping(path = "freeSlots", produces = APPLICATION_JSON_VALUE)
    public String getFreeSlots() {
        int free = service.getFreeSlotsNumber();
        LOG.info("No of Free slots [{}]", free);
        return marshall(free);
    }

    private String marshall(Object obj) {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException jpe) {
            LOG.error("Error while parsing JSON [{}]", obj, jpe);

        }
        return "";
    }

}
