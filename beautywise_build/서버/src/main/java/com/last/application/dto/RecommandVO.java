package com.last.application.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
@Data
public class RecommandVO {
    private List<String> essence;
    private List<String> skin;
    private List<String> crime;
    private List<String> location;
}
