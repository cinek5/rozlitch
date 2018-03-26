package com.cinek.rozlitch.services;

import com.cinek.rozlitch.models.MoneyRequest;
import com.cinek.rozlitch.models.Status;
import com.cinek.rozlitch.models.User;
import com.cinek.rozlitch.models.comparators.ItemPriceComparator;
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
    public List<MoneyRequest> findAll() {
        return requestRepo.findAll();
    }
    public MoneyRequest findById(Long requestId) {
       return requestRepo.findOne(requestId);
    }
    public void changeStatus(Long requestId, Status status) {
        MoneyRequest moneyRequest = requestRepo.findOne(requestId);
        moneyRequest.setStatus(status);

    }
    public void changeStatus(MoneyRequest moneyRequest, Status newStatus) {
        moneyRequest.setStatus(newStatus);
        requestRepo.save(moneyRequest);
    }
    public void save(MoneyRequest mr) {
        requestRepo.save(mr);
    }
    public List<MoneyRequest> findByCreatorUsername(String username) {
      return requestRepo.findByCreatorUsername(username);
    }
    public List<MoneyRequest> findByRequestedUserUsername(String username) {
        return requestRepo.findByRequestedUserUsername(username);
    }
    public List<MoneyRequest> findByRequestedUserId(Long requestedUserId) {
        return requestRepo.findByRequestedUserId(requestedUserId);
    }
    public List<MoneyRequest> findByCreatorIdOrRequestedUserIdAndStatus(Long id,Status status) {
        return requestRepo.findByCreatorIdOrRequestedUserIdAndStatus(id, status);
    }
    public List<MoneyRequest> findByCreatorAndStatus(User creator, Status status) {
       return requestRepo.findByCreatorAndStatus(creator, status);
    }
    public void sortList(String sortParam,List<MoneyRequest> moneyRequestList ) {
        switch (sortParam) {
            case "price": {
                for (MoneyRequest mr : moneyRequestList) {
                    mr.sortItems(new ItemPriceComparator());
                }
            }
        }
    }


}
