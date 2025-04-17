package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {


    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Users findByEmail(@Param("email") String email);

    @Query("SELECT u FROM Users u WHERE u.userName = :userName")
    Optional<Users> findByUserName(@Param("userName") String userName);

    @Query("SELECT COUNT(u) > 0 FROM Users u WHERE u.userName = :userName")
    boolean existsByUserName(@Param("userName") String userName);

    @Query("SELECT COUNT(u) > 0 FROM Users u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT u FROM Users u WHERE u.userId = :userId")
    Users findByUserId(@Param("userId") String userId);

}
