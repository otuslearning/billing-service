package com.example.billingservice.repository;

import com.example.billingservice.domain.Bill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillRepository extends CrudRepository<Bill, Long> {

    Optional<Bill> findByUserGuid(String userGuid);

    Optional<Bill> findByUserGuidAndAccountGuid(String userGuid, String accountGuid);
}
