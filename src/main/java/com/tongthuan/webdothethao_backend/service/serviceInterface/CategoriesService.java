package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.entity.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoriesService {

    Page<Categories> findAllPage(Pageable pageable);

    List<Categories> findALl();

    boolean checkExistsByCategoryName(String categoryName);

}
