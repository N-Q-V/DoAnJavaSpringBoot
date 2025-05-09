package com.example.fashion_spring_boot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterUser {

    @NotBlank(message = "Required information")
    @Size(min = 5, message = "Minimum length is 5")
    private String username;
    @NotBlank(message = "Required information")
    @Size(min = 8, message = "Minimum length is 8")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).*$",
            message = "Password must contain at least 1 number and 1 special character")
    private String password;

    private String firstName;
    private String lastName;
    @NotBlank(message = "Required information")
    @Email(message = "Invalid email")
    private String email;

    public RegisterUser() {
    }

    public RegisterUser(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
