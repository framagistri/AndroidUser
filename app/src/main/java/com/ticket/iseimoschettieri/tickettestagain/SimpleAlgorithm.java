package com.ticket.iseimoschettieri.tickettestagain;

/**
 * Created by Francesco on 08/07/2017.
 */

public class SimpleAlgorithm implements PricingStrategy {

    @Override
    public double calculatePrice(double cost, int duration) {
        return cost*duration;
    }

}
