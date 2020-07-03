package com.rup.pms.model;

import java.util.Date;
import java.util.UUID;

public class VehicleTicket {

    private UUID ticketId;
    private String vehicleNumber;
    private Date inTime;
    private Date outTime;
    private String slotNumber;
    private long chargeAmount;

    private VehicleTicket(UUID ticketId, String vehicleNumber, Date inTime, String slotNumber) {
        this.ticketId = ticketId;
        this.vehicleNumber = vehicleNumber;
        this.inTime = inTime;
        this.slotNumber = slotNumber;
    }

    public static TicketBuilder newBuilder() {
        return new TicketBuilder();
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public Date getInTime() {
        return inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        assert this.inTime.before(outTime);
        this.outTime = outTime;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public long getChargeAmount() {
        return this.chargeAmount;
    }

    public void setChargeAmount(long amount) {
        this.chargeAmount = amount;
    }

    @Override
    public VehicleTicket clone() {
        VehicleTicket ticket;
        try {
            ticket = (VehicleTicket) super.clone();
        } catch (CloneNotSupportedException e) {
            ticket = new VehicleTicket(this.ticketId, this.vehicleNumber, this.inTime, this.slotNumber);
        }
        return ticket;
    }

    public static class TicketBuilder {

        private UUID ticketId;
        private String vehicleNumber;
        private Date inTime;
        private String slotNumber;

        public TicketBuilder ticketId(UUID ticketId) {
            this.ticketId = ticketId;
            return this;
        }

        public TicketBuilder vehicleNumber(String number) {
            this.vehicleNumber = number;
            return this;
        }

        public TicketBuilder inTime(Date inTime) {
            this.inTime = inTime;
            return this;
        }

        public TicketBuilder slotNumber(String slotNumber) {
            this.slotNumber = slotNumber;
            return this;
        }

        public VehicleTicket build() {
            return new VehicleTicket(this.ticketId, this.vehicleNumber, this.inTime, this.slotNumber);
        }
    }
}
