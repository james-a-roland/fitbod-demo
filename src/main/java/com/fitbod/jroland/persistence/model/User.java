package com.fitbod.jroland.persistence.model;

import com.fitbod.jroland.api.UserApi;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.password.PasswordEncoder;

@Document
public class User implements PersistentObj {

  @Getter
  @Setter
  @Id
  private String email;

  @Getter
  @Setter
  private String encryptedPassword;

  public static User fromApi(UserApi userApi, PasswordEncoder passwordEncoder) {
    User model = new User();
    model.setEmail(userApi.getEmail());
    model.setEncryptedPassword(passwordEncoder.encode(userApi.getPassword()));
    return model;
  }

  public UserApi toApi() {
    UserApi user = new UserApi();
    user.setEmail(email);
    //Do NOT set the password here, even if it is encrypted.
    return user;
  }

}
