package com.ticket.iseimoschettieri.tickettestagain;

/**
 * Created by Francesco on 08/07/2017.
 */

public class SimpleTicket implements Products {
    private final String description;
    private final String type;
    private double cost;
    private int duration;

    public SimpleTicket(String description, String type,double cost, int duration) {
        this.description = description;
        this.type=type;
        this.cost = cost;
        this.duration = duration;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public int getDuration() { //TODO trasformare in int
        return duration;
    }
}
