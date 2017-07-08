package com.ticket.iseimoschettieri.tickettestagain;

/**
 * Created by Francesco on 08/07/2017.
 */

public interface PricingStrategy {

    public double calculatePrice(double cost,int duration);

}
