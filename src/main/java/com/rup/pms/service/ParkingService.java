package com.rup.pms.service;

import com.rup.pms.model.VehicleTicket;

import java.util.List;

public interface ParkingService {
    public VehicleTicket parkIn(String vehicleNumber);
    public String parkOut(VehicleTicket ticket);
    public VehicleTicket getParkingDetails(String vehicleNumber);
    public VehicleTicket getTicketDetails(String id);
    public List<VehicleTicket> getCurrentParkingStatus();
    public int getFreeSlotsNumber();
}
