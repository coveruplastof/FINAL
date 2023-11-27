package com.last.application.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Data
public class RecommandDTO {
    private int recommand_id;
    private List<RecommandVO> Recommandaa;
    private List<StringVO> Lister;

}