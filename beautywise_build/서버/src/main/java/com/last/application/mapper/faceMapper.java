package com.last.application.mapper;

import com.last.application.dto.faceDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface faceMapper {

    List<faceDTO> selectFace(int facedto);
    int createMember(faceDTO facedto);

    int updateMember(faceDTO facedto);

}
