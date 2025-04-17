package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetail, String> {

    @Query("SELECT ud FROM UserDetail ud WHERE ud.user.userName = :userName")
    UserDetail findByUserName(@Param("userName") String userName);

    @Query("SELECT ud FROM UserDetail ud WHERE ud.userDetailId = :userDetailId")
    UserDetail findByUserDetailId(@Param("userDetailId") String userDetailId);


}
