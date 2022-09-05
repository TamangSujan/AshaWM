package com.ayata.urldatabase.model.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsernamePassword {
    private String phone;
    private String password;
}
