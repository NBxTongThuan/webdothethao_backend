package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {
}
