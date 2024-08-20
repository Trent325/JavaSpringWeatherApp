package com.adp.admin_service.repository;

import com.adp.admin_service.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
}

