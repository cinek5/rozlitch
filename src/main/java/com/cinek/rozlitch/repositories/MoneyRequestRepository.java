package com.cinek.rozlitch.repositories;

import com.cinek.rozlitch.models.MoneyRequest;
import com.cinek.rozlitch.models.Status;
import com.cinek.rozlitch.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Cinek on 14.11.2017.
 */
@Repository
public interface MoneyRequestRepository extends JpaRepository<MoneyRequest,Long> {
    List<MoneyRequest> findByCreatorUsername(String username);
    List<MoneyRequest> findByRequestedUserUsername(String username);
    @Query(value = "select  mr  from MoneyRequest mr where mr.requestedUser.id = :requestedUserId")
    List<MoneyRequest> findByRequestedUserId(@Param("requestedUserId")Long requestedUserId);
    @Query(value ="select  mr from MoneyRequest mr where (mr.requestedUser.id = :id or mr.creator.id= :id ) and mr.status = :status")
    List<MoneyRequest> findByCreatorIdOrRequestedUserIdAndStatus(@Param("id")Long id,@Param("status") Status status);
    List<MoneyRequest> findByCreatorAndStatus(User creator,Status status);

}
