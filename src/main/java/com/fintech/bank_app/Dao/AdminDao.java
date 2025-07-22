package com.fintech.bank_app.Dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fintech.bank_app.models.Admin;

public interface AdminDao extends JpaRepository<Admin, Long> {
    
    Optional<Admin> findByEmail(String email);
    
}
