package com.example.Contact_manager_web.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Contact {
    @Id

    private String id;
    private  String name;
    private String email;
    private  String phoneNumber;
    private String picture;
    @Column(length = 1000)
    private  String description;
    private  boolean favourate = false;
    private String websiteLink;
    private String linkedInLink;
//    private List<SocialLink> socialLinks = new ArrayList<>()

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch =FetchType.EAGER )
    private List<Sociallinks> links = new ArrayList<>();


}
