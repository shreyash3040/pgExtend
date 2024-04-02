package com.springserver.auth.repositories;

import com.springserver.auth.model.Role;
import com.springserver.auth.model.RoleID;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, RoleID> {
    List<Role> findByUsername(String username);
}