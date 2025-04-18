package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.entity.Categories;
import com.tongthuan.webdothethao_backend.repository.CategoriesRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
