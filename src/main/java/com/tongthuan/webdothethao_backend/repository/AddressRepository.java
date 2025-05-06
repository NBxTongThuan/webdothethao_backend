package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,String> {

    @Query("SELECT a FROM Address a WHERE a.user.userName = :userName")
    List<Address> findByUserName(String userName);

}
