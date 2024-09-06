package com.BRS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BRS.entity.Rent;
import com.BRS.mapper.RentMapper;

@Service
public class RentService {
    @Autowired
    RentMapper rentMapper;

    public Rent saveRent(Rent rent) {
        return rentMapper.saveRent(rent);
    }

    public Rent updateRent(Rent rent) {
        return rentMapper.updateRent(rent);
    }

    public void removeRent(Long id) {
        rentMapper.removeRent(id);
    }

    public List<Rent> searchRent(Rent rent) {
        return rentMapper.searchRentByKey(rent);
    }

}
