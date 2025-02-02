package co.edu.unisabana.Gribu.controller;

import co.edu.unisabana.Gribu.dto.AuthDTO;
import co.edu.unisabana.Gribu.entity.User;
import co.edu.unisabana.Gribu.repository.UserRepository;
import co.edu.unisabana.Gribu.security.JWTAuthorization;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@AllArgsConstructor
public class AuthController {

  private JWTAuthorization jwtAuthorization;
  private UserRepository userRepository;

  @PostMapping(path = "/auth")
  public ResponseEntity<Object> auth(@RequestBody AuthDTO authDTO) {
    User user = userRepository.findByUsernameAndPassword(authDTO.getUsername(),
        authDTO.getPassword());
    if (user != null) {
      return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("token", jwtAuthorization.getJWTToken(authDTO.getUsername()) ));
      //new ResponseEntity<>(jwtAuthorization.getJWTToken(authDTO.getUsername()), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(jwtAuthorization.getJWTToken("mal xd"), HttpStatus.CONFLICT);
    }
  }
}
