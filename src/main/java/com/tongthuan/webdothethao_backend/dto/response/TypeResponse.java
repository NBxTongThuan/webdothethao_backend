package com.tongthuan.webdothethao_backend.dto.response;

import com.tongthuan.webdothethao_backend.entity.Types;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeResponse {
    private int typeId;
    private String typeName;

    public TypeResponse(Types type) {
        this.typeId = type.getTypeId();
        this.typeName = type.getTypename();
    }
}
