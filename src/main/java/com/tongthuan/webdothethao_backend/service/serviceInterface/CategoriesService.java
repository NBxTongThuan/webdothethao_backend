package com.tongthuan.webdothethao_backend.service.serviceInterface;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tongthuan.webdothethao_backend.dto.adminRequest.AddCategoryRequest;
import com.tongthuan.webdothethao_backend.dto.adminRequest.UpdateCategoryRequest;
import com.tongthuan.webdothethao_backend.entity.Categories;

public interface CategoriesService {

    Page<Categories> findAllPage(Pageable pageable);

    List<Categories> findALl();

    boolean checkExistsByCategoryName(String categoryName);

    boolean addCategory(AddCategoryRequest addCategoryRequest);

    boolean deleteCategory(int categoryId);

    boolean updateCategory(UpdateCategoryRequest updateCategoryRequest);

    boolean enableCategory(int categoryId);

    Optional<Categories> findById(int categoryId);

    Categories findByName(String categoryName);

    Page<Categories> findTopCategoriesByProductCount(Pageable pageable);
}
