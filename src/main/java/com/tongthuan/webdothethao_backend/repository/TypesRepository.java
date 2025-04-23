package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.Types;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypesRepository extends JpaRepository<Types,Integer> {

    @Query("SELECT ty FROM Types ty WHERE ty.typename = :typeName AND ty.categories.categoriesName = :categoryName")
    Optional<Types> findByNameAndCategoryName(@Param("typeName") String typeName,@Param("categoryName")String categoryName);

    @Query("SELECT ty FROM Types ty WHERE ty.typename = :typeName")
    Optional<Types> findByName(@Param("typeName") String typeName);

    @Query("SELECT ty FROM Types ty WHERE ty.categories.categoriesName = :categoryName")
    List<Types> findByCategoryName(@Param("categoryName") String categoryName);

}
