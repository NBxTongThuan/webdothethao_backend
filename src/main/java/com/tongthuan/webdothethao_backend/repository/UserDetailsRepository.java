package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {

    @Query("SELECT ud FROM UserDetails ud WHERE ud.user.userName = :userName")
    UserDetails findByUserName(@Param("userName") String userName);

    @Query("SELECT ud FROM UserDetails ud WHERE ud.userDetailId = :userDetailId")
    UserDetails findByUserDetailId(@Param("userDetailId") String userDetailId);


}
