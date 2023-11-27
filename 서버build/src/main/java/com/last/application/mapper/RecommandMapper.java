package com.last.application.mapper;

import com.last.application.dto.RecommandDTO;
import com.last.application.dto.RecommandVO;
import com.last.application.dto.StringVO;
import com.last.application.dto.faceDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RecommandMapper {

    List<StringVO> selectRecommand(int RecommandDTO);
    int createRecommand(StringVO recommandDTO);

    int deleteRecommand(int recommandDTO);

}
