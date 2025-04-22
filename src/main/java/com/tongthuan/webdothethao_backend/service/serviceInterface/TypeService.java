package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.dto.adminRequest.AddTypesRequest;
import com.tongthuan.webdothethao_backend.dto.adminRequest.UpdateTypeRequest;
import com.tongthuan.webdothethao_backend.entity.Types;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.Optional;


public interface TypeService {

    public Page<Types> getAllTypes(Pageable pageable);

    public boolean checkExists(String typeName,String categoryName);

    public boolean addType(AddTypesRequest addTypesRequest);

    public boolean disableType(int typeId);

    public boolean enableType(int typeId);

    public boolean updateType(UpdateTypeRequest updateTypeRequest);

}
