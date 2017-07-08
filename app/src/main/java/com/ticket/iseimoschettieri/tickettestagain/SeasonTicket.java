package com.ticket.iseimoschettieri.tickettestagain;

/**
 * Created by Francesco on 08/07/2017.
 */

public class SeasonTicket implements Products {

    private String description;
    private String type;
    private double monthlycost;
    private int duration;
    private PricingStrategy priceCalculator;

    public SeasonTicket(String description, String type, double monthlycost,int duration,PricingStrategy priceCalculator){
        this.description=description;
        this.type=type;
        this.monthlycost=monthlycost;
        this.duration=duration;
        this.priceCalculator=priceCalculator;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getType() {
        return type;
    }

    //TODO valutare se sia meglio chiamare questo metodo ora opuure nel costruttore e in set duration con aggiunta di attributo
    @Override
    public double getCost() {
        return priceCalculator.calculatePrice(monthlycost, duration);
    }

    @Override
    public int getDuration() { //TODO trasformare in int
        return duration;
    }

}
