package com.sangam.iot.consumer.model;

public class EventAggregator {

    //Initialize total Value
    private Double totalValue = 0.0;

    //Return current value
    public Double getTotalValue() {

        return totalValue;
    }

    //Add to total value
    public EventAggregator add(Double value) {

        totalValue += value;
        return this;
    }


}
