package com.last.domain.entitiy;


import lombok.*;

import javax.persistence.*;
@Entity
@Table(name = "face")
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
public class FaceEntitiy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "face_id")
    private Long face_id;

    @Column(name = "face_shape")
    private String face_shape;

    @Column(name = "personal_color")
    private String personal_color;


}