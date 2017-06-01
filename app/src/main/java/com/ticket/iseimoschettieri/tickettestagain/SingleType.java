package com.ticket.iseimoschettieri.tickettestagain;

/**
 * Created by Francesco on 01/06/2017.
 */

public class SingleType implements TicketType {
    private final double cost;
    private final int duration;

    public SingleType() {
        cost = 1.5;
        duration = 1;
    }

    @Override
    public double getCost() {
        return cost;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String getType() {
        return "Single";
    }
}
