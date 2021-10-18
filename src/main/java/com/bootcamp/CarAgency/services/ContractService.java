package com.bootcamp.CarAgency.services;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ContractService {
    public static double totalPrice(Date start_date, Date end_date, double price){
        Long days = end_date.getTime() - start_date.getTime();
        TimeUnit time = TimeUnit.DAYS;
        long diffrence = time.convert(days, TimeUnit.MILLISECONDS);
        return price * diffrence;
    }
}
