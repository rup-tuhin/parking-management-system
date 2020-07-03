package com.rup.pms.service;

import com.rup.pms.model.VehicleTicket;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.rup.pms.error.PMSException.badRequestIfTrue;

@Service
public class ParkingServiceImpl implements ParkingService {

    @Value("${maxCapacity}")
    private int maxCapacity;
    @Value("${unitCost}")
    private int unitCost;
    @Value("${freeHours}")
    private int freeHours;
    @Value("${basePrice}")
    private int basePrice;

    private Map<String, VehicleTicket> slots = new ConcurrentHashMap<>(100);

    @Override
    public VehicleTicket parkIn(String vehicleNumber) {
        String nextId = generateUniqueId();

        VehicleTicket ticket = VehicleTicket.newBuilder()
                .ticketId(UUID.randomUUID())
                .vehicleNumber(vehicleNumber)
                .inTime(new Date())
                .slotNumber(nextId)
                .build();
        VehicleTicket parkedTicket = slots.putIfAbsent(nextId, ticket);
        badRequestIfTrue(null != parkedTicket, "The slot is already Occupied");
        return slots.get(nextId);
    }

    @Override
    public String parkOut(VehicleTicket ticket) {
        badRequestIfTrue(ticket.getSlotNumber() == null, "No parking slot found");
        VehicleTicket originalTicket = slots.remove(ticket.getSlotNumber());
        originalTicket.setOutTime(new Date());
        return calculateAmount(originalTicket);
    }

    @Override
    public VehicleTicket getParkingDetails(String vehicleNumber) {
        VehicleTicket parkedTicket = slots.values()
                .stream()
                .filter(t -> null == t.getOutTime())
                .filter(t -> t.getVehicleNumber().equals(vehicleNumber))
                .findFirst().orElse(null);
        if (parkedTicket != null) {
            VehicleTicket copyTicket = parkedTicket.clone();
            copyTicket.setOutTime(new Date());
            calculateAmount(copyTicket);
            return copyTicket;
        }
        return parkedTicket;
    }

    @Override
    public VehicleTicket getTicketDetails(String id) {
        return slots.values()
                .stream()
                .filter(t -> UUID.fromString(id).equals(t.getTicketId()))
                .findFirst().orElse(null);
    }

    @Override
    public List<VehicleTicket> getCurrentParkingStatus() {
        List<VehicleTicket> details = slots.values().stream()
                .filter(t -> null == t.getOutTime())
                .map(VehicleTicket::clone)
                .collect(Collectors.toList());
        details.forEach(t -> {
            t.setOutTime(new Date());
            calculateAmount(t);
        });
        return details;
    }

    @Override
    public int getFreeSlotsNumber() {
        return maxCapacity - slots.size();
    }

    private synchronized String generateUniqueId() {
        Set<String> existingIds = slots.keySet();
        String id;
        do {
            id = Integer.valueOf(RandomUtils.nextInt(0, maxCapacity)).toString();
        } while (existingIds.contains(id));
        return StringUtils.leftPad(id, 3, "0");
    }

    private String calculateAmount(VehicleTicket originalTicket) {
        long duration = (originalTicket.getOutTime().getTime() - originalTicket.getInTime().getTime()) / (60 * 60 * 1000);
        long chargeableDuration = duration > freeHours ? duration - freeHours : 0;
        long chargeAmount = basePrice + chargeableDuration * unitCost;
        originalTicket.setChargeAmount(chargeAmount);
        return String.valueOf(chargeAmount);
    }
}
