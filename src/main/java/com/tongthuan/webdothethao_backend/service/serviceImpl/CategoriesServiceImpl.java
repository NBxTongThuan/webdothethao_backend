package com.tongthuan.webdothethao_backend.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tongthuan.webdothethao_backend.dto.adminRequest.AddCategoryRequest;
import com.tongthuan.webdothethao_backend.dto.adminRequest.UpdateCategoryRequest;
import com.tongthuan.webdothethao_backend.entity.Categories;
import com.tongthuan.webdothethao_backend.repository.CategoriesRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.CategoriesService;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public Page<Categories> findAllPage(Pageable pageable) {
        return categoriesRepository.findAll(pageable);
    }

    @Override
    public List<Categories> findALl() {
        return categoriesRepository.findAll();
    }

    @Override
    public boolean checkExistsByCategoryName(String categoryName) {
        return categoriesRepository.findByName(categoryName).orElse(null) != null;
    }

    @Override
    public boolean addCategory(AddCategoryRequest addCategoryRequest) {

        if (categoriesRepository
                .findByName(addCategoryRequest.getCategoryName())
                .isPresent()) {
            return false;
        }
        Categories category = new Categories();
        category.setCategoriesName(addCategoryRequest.getCategoryName());
        category.setImageData(addCategoryRequest.getCategoryImage());
        category.setEnable(true);
        categoriesRepository.saveAndFlush(category);
        return true;
    }

    @Override
    public boolean deleteCategory(int categoryId) {

        Categories category = categoriesRepository.findById(categoryId).orElse(null);
        if (category == null) return false;
        category.setEnable(false);
        categoriesRepository.saveAndFlush(category);
        return true;
    }

    @Override
    public boolean updateCategory(UpdateCategoryRequest updateCategoryRequest) {
        Categories category = categoriesRepository
                .findById(updateCategoryRequest.getCategoryId())
                .orElse(null);
        if (category == null) return false;
        category.setCategoriesName(updateCategoryRequest.getCategoryName());
        category.setImageData(updateCategoryRequest.getCategoryImage());
        categoriesRepository.saveAndFlush(category);
        return true;
    }

    @Override
    public boolean enableCategory(int categoryId) {
        Categories category = categoriesRepository.findById(categoryId).orElse(null);
        if (category == null) return false;
        category.setEnable(true);
        categoriesRepository.saveAndFlush(category);
        return true;
    }

    @Override
    public Optional<Categories> findById(int categoryId) {
        return categoriesRepository.findById(categoryId);
    }

    @Override
    public Categories findByName(String categoryName) {
        return categoriesRepository.findByName(categoryName).orElse(null);
    }

    @Override
    public Page<Categories> findTopCategoriesByProductCount(Pageable pageable) {
        return categoriesRepository.findTopCategoriesByProductCount(pageable);
    }
}
