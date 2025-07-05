package com.tongthuan.webdothethao_backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tongthuan.webdothethao_backend.entity.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

    @Query("SELECT c FROM Categories c WHERE c.categoriesName = :categoryName")
    Optional<Categories> findByName(@Param("categoryName") String categoryName);

    @Query(
            """
				SELECT c
				FROM Categories c
				JOIN c.typesList t
				JOIN t.productsList p
				GROUP BY c
				ORDER BY COUNT(p) DESC
			""")
    Page<Categories> findTopCategoriesByProductCount(Pageable pageable);
}
