package com.cinek.rozlitch.controllers;

import com.cinek.rozlitch.exception.ForbiddenException;
import com.cinek.rozlitch.helpers.SecurityHelper;
import com.cinek.rozlitch.models.*;
import com.cinek.rozlitch.models.comparators.ItemPriceComparator;
import com.cinek.rozlitch.repositories.ItemRepository;
import com.cinek.rozlitch.repositories.MoneyRequestRepository;
import com.cinek.rozlitch.repositories.UserRepository;
import com.cinek.rozlitch.services.MoneyRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Cinek on 17.11.2017.
 */
@RestController
public class MoneyRequestController {
    @Autowired
    UserRepository userRepo;
    @Autowired
    MoneyRequestRepository requestRepo;
    @Autowired
    MoneyRequestService requestService;
    @Autowired
    ItemRepository itemRepo;

    @GetMapping("/moneyrequests")
    public List<MoneyRequest> findAllMoneyRequests() {
        return requestRepo.findAll();
    }

    @GetMapping(value="/moneyrequests/requested",params = "items_sort_by")
    public List<MoneyRequest> moneyRequestsFromMeSorted(@RequestParam("items_sort_by") String itemsSortBy) {
        String username = SecurityHelper.getLoggedInUsername();
        List<MoneyRequest>  lista = requestRepo.findByCreatorUsername(username);
        sortList(itemsSortBy,lista);
        return  lista;
    }
    @GetMapping (value = "/moneyrequests/debts", params ="items_sort_by")
    public List<MoneyRequest> moneyRequestsToMeItemsSorted(@RequestParam("items_sort_by") String itemsSortBy) {
        String username = SecurityHelper.getLoggedInUsername();
        List<MoneyRequest>  lista = requestRepo.findByRequestedUserUsername(username);
        sortList(itemsSortBy,lista);
        return lista;
    }
    @GetMapping(value="/moneyrequests/requested")
    public List<MoneyRequest> moneyRequestsFromMe() {
        String username = SecurityHelper.getLoggedInUsername();
        List<MoneyRequest>  lista = requestRepo.findByCreatorUsername(username);
        return  lista;
    }

    private void sortList(String sortParam,List<MoneyRequest> moneyRequestList ) {
        switch (sortParam) {
            case "price": {
                for (MoneyRequest mr : moneyRequestList) {
                    mr.sortItems(new ItemPriceComparator());
                }
            }
        }
    }
    @GetMapping("/moneyrequests/debts")
    public List<MoneyRequest> moneyRequestsToMe() {
        String username = SecurityHelper.getLoggedInUsername();
        List<MoneyRequest>  lista = requestRepo.findByRequestedUserUsername(username);
        return lista;
    }

    @PostMapping("/moneyrequests/")
    public void createNewMoneyRequest(@RequestBody MoneyRequest moneyRequest) {
        String username = SecurityHelper.getLoggedInUsername();
        User creator = userRepo.findByUsername(username);
        moneyRequest.setCreator(creator);
        requestRepo.save(moneyRequest);
    }
    @GetMapping("/moneyrequests/get-by-user/{requestedUserId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public List<MoneyRequest> showMoneyRequest(@PathVariable Long requestedUserId) {
       return requestRepo.findByRequestedUserId(requestedUserId);
    }
    @RequestMapping(value="/moneyrequests/{moneyReqId}",method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void addNewItemToMoneyRequest(@RequestBody Item item,@PathVariable Long moneyReqId) {
        String username = SecurityHelper.getLoggedInUsername();
        User creator = userRepo.findByUsername(username);
        List<MoneyRequest> moneyRequests = requestRepo.findByCreatorUsername(username);
        MoneyRequest moneyRequest = requestRepo.findOne(moneyReqId);
        if (moneyRequest == null || !moneyRequests.contains(moneyRequest)) {
            // acces denied
            throw new ForbiddenException();

        } else {
            itemRepo.save(item);
            moneyRequest.addItem(item);
            requestRepo.save(moneyRequest);
              // http status ok
        }



    }


    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value="/moneyrequests/status/{moneyReqId}",method=RequestMethod.PUT)
    public void updateStatus(@RequestBody Status status,@PathVariable Long moneyReqId) {
        String username = SecurityHelper.getLoggedInUsername();
        User loggedInUser = userRepo.findByUsername(username);
        MoneyRequest moneyRequest = requestRepo.findOne(moneyReqId);
        boolean userIsCreatorAndRequestStatusIsAcceptedAndNewStatusIsFinished =
                moneyRequest.getCreator().equals(loggedInUser) &&
                        moneyRequest.getStatus()==Status.ACCEPTED &&
                        status==Status.FINISHED;
        boolean userIsRequestedUserAndRequestStatusIsRequestedAndNewStatusIsAccepted =
                moneyRequest.getRequestedUser().equals(loggedInUser) &&
                        moneyRequest.getStatus()==Status.REQUESTED &&
                        status == Status.ACCEPTED;

        if (moneyRequest != null) {
            if (userIsCreatorAndRequestStatusIsAcceptedAndNewStatusIsFinished ||
                    userIsRequestedUserAndRequestStatusIsRequestedAndNewStatusIsAccepted) {
                moneyRequest.setStatus(status);
                requestRepo.save(moneyRequest);


            } else {
                throw new ForbiddenException();
            }
    } else throw new ForbiddenException();


    }
    @RequestMapping(value="/moneyrequests/history/{userId}",method = RequestMethod.GET)
    public List<MoneyRequest> showMoneyRequestHistory(@PathVariable Long userId) {
        return requestRepo.findByCreatorIdOrRequestedUserIdAndStatus(userId,Status.FINISHED);
    }
}
