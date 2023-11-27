package com.last.domain.entitiy;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int user_id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profile_image_url")
    private String profile_image_url;

    @Column(name = "email")
    private String email;

    @Column(name = "accessToken")
    private String accessToken;

    public User(String nickname, String profile_image_url, String email, String accessToken) {
        this.nickname = nickname;
        this.profile_image_url = profile_image_url;
        this.email = email;
        this.accessToken = (accessToken != null) ? accessToken : "DefaultToken";
    }
}