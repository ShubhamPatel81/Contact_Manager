package com.example.Contact_manager_web.Forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ContactForm {

    @NotBlank(message = "Name is required")
    private  String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Address")
    private  String email;

    @NotBlank(message ="Phone number is required " )
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone number ")
    private  String phoneNumber;

    @NotBlank(message = "Address is required")
    private  String address;
    private  String description;

    private String websiteLink;
    private String linkedInLink;
    private  boolean favourate=false;

    //validate file size and resolution
    private MultipartFile contactImage;

    private String picture;
}
