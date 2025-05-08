package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,String> {

    @Query("SELECT a FROM Address a WHERE a.user.userName = :userName")
    List<Address> findByUserName(@Param("userName") String userName);

    @Query("DELETE FROM Address a WHERE a.addressId = :addressId")
    void deleteById(@Param("addressId") String addressId);

}
