package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.entity.Types;
import com.tongthuan.webdothethao_backend.repository.TypesRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypesRepository typesRepository;

    @Override
    public List<Types> getAllTypes() {
        return typesRepository.findAll();
    }
}
