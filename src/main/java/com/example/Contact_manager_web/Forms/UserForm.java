package com.example.Contact_manager_web.Forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserForm {

    @NotBlank(message = "Name is Required")
    @Size(min = 3, message = "Minimum 3 character required")
    private String name;

    @Email(message = "Email is Required")
    private String email;

    @NotBlank(message = "Password is Required")
    @Size(min = 6, message = "Minimum 6 character is required")
    private String password;

    @NotBlank
    private String phoneNumber;

    
    @NotBlank(message = "About is required")
    private String about;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAbout() {
        return about;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}
