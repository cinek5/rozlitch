package com.cinek.rozlitch.services;

import com.cinek.rozlitch.models.MoneyRequest;
import com.cinek.rozlitch.models.Status;
import com.cinek.rozlitch.repositories.MoneyRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Cinek on 14.11.2017.
 */
@Service
public class MoneyRequestService {
    @Autowired
    private MoneyRequestRepository requestRepo;


    public void changeStatus(Long requestId, Status status) {
        MoneyRequest moneyRequest = requestRepo.findOne(requestId);
        moneyRequest.setStatus(status);

    }
    public void save(MoneyRequest mr) {
        requestRepo.save(mr);
    }


}
