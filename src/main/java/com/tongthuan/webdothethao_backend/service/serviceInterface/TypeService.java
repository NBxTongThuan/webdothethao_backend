package com.tongthuan.webdothethao_backend.service.serviceInterface;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tongthuan.webdothethao_backend.dto.adminRequest.AddTypesRequest;
import com.tongthuan.webdothethao_backend.dto.adminRequest.UpdateTypeRequest;
import com.tongthuan.webdothethao_backend.entity.Types;

public interface TypeService {

    public Page<Types> getAllTypes(Pageable pageable);

    public boolean checkExists(String typeName, String categoryName);

    public boolean addType(AddTypesRequest addTypesRequest);

    public boolean disableType(int typeId);

    public boolean enableType(int typeId);

    public boolean updateType(UpdateTypeRequest updateTypeRequest);

    public List<Types> getTypesByCategoryName(String categoryName);
}
