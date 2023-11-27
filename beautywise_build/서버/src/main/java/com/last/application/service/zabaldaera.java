package com.last.application.service;

import com.last.application.dto.RecommandDTO;
import com.last.application.dto.RecommandVO;
import com.last.application.dto.StringVO;
import com.last.application.dto.faceDTO;
import com.last.application.mapper.RecommandMapper;
import com.last.application.mapper.faceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class zabaldaera {
    private faceMapper FaceMapper;
    private RecommandMapper RecommandMapper;
    @Autowired
    public zabaldaera(faceMapper faceMapper, com.last.application.mapper.RecommandMapper recommandMapper) {
        FaceMapper = faceMapper;
        RecommandMapper = recommandMapper;
    }


    public List<faceDTO> selectFace(int facedto) {
        return FaceMapper.selectFace(facedto);
    }

    public int createMember(faceDTO facedto) {
        return FaceMapper.createMember(facedto);
    }

    public int updateMember(faceDTO facedto) {
        return FaceMapper.updateMember(facedto);
    }

    public List<StringVO> SelectRecommand(int stringvo) {
        return RecommandMapper.selectRecommand(stringvo);
    }

    public int CreateRecommand(StringVO recommandDTO) {
        return RecommandMapper.createRecommand(recommandDTO);
    }

    public int DeleteRecommand(int RecommandDTO) {
        return RecommandMapper.deleteRecommand(RecommandDTO);
    }


}
