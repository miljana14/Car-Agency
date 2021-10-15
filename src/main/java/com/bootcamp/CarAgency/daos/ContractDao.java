package com.bootcamp.CarAgency.daos;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ContractDao {
    List<Date> getAllUnaviableDate(UUID carId);
}
