package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.dto.adminRequest.AddTypesRequest;
import com.tongthuan.webdothethao_backend.dto.adminRequest.UpdateTypeRequest;
import com.tongthuan.webdothethao_backend.entity.Categories;
import com.tongthuan.webdothethao_backend.entity.Types;
import com.tongthuan.webdothethao_backend.repository.CategoriesRepository;
import com.tongthuan.webdothethao_backend.repository.TypesRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypesRepository typesRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public Page<Types> getAllTypes(Pageable pageable) {
        return typesRepository.findAll(pageable);
    }

    @Override
    public boolean checkExists(String typeName, String categoryName) {
        return typesRepository.findByNameAndCategoryName(typeName, categoryName).orElse(null) != null;
    }

    @Override
    public boolean addType(AddTypesRequest addTypesRequest) {

        Categories category = categoriesRepository.findByName(addTypesRequest.getCategoryName()).orElse(null);
        if (category == null)
            return false;

        if (typesRepository.findByNameAndCategoryName(addTypesRequest.getTypeName(), addTypesRequest.getCategoryName()).orElse(null) != null)
            return false;

        Types type = new Types();
        type.setEnable(true);
        type.setCategories(category);
        type.setTypename(addTypesRequest.getTypeName());
        typesRepository.saveAndFlush(type);
        return true;

    }

    @Override
    public boolean disableType(int typeId) {

        Types type = typesRepository.findById(typeId).orElse(null);
        if (type == null)
            return false;
        type.setEnable(false);
        typesRepository.saveAndFlush(type);
        return true;
    }

    @Override
    public boolean enableType(int typeId) {
        Types type = typesRepository.findById(typeId).orElse(null);
        if (type == null)
            return false;
        type.setEnable(true);
        typesRepository.saveAndFlush(type);
        return true;
    }

    @Override
    public boolean updateType(UpdateTypeRequest updateTypeRequest) {

        Categories category = categoriesRepository.findByName(updateTypeRequest.getCategoryName()).orElse(null);
        if (category == null)
            return false;

//        Types type  = typesRepository.findByNameAndCategoryName(updateTypeRequest.getTypeName(), updateTypeRequest.getCategoryName()).orElse(null);
//        if (type != null)
//            return false;

        Types type = typesRepository.findById(updateTypeRequest.getTypeId()).orElse(null);
        if (type == null)
            return false;

        type.setTypename(updateTypeRequest.getTypeName());
        type.setCategories(category);
        typesRepository.saveAndFlush(type);

        return true;
    }

    @Override
    public List<Types> getTypesByCategoryName(String categoryName) {
        return typesRepository.findByCategoryName(categoryName);
    }


}
