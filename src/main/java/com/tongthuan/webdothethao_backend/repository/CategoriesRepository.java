package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
}
