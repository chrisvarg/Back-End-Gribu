package co.edu.unisabana.Gribu.service;

import co.edu.unisabana.Gribu.dto.UserDTO;
import co.edu.unisabana.Gribu.entity.UserRole;
import co.edu.unisabana.Gribu.exception.ExistingResourceException;
import co.edu.unisabana.Gribu.exception.ResourceNotFoundException;
import co.edu.unisabana.Gribu.repository.UserRepository;
import co.edu.unisabana.Gribu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService{
    @Autowired
    UserRepository userRepository;

    public List<UserDTO> getUsers() {
        if (userRepository.findAll().isEmpty()){
            throw new ResourceNotFoundException("No se Encontraron Usuarios");
        }else {
            return userRepository.findAll()
                    .stream()
                    .map(user -> new UserDTO(
                            user.getEmail(),
                            user.getUsername(),
                            user.getName(),
                            user.getLevel(),
                            user.getDayStreak(),
                            user.getAlliance()
                    ))
                    .collect(Collectors.toList());
        }
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDTO(
                        user.getEmail(),
                        user.getUsername(),
                        user.getName(),
                        user.getLevel(),
                        user.getDayStreak(),
                        user.getAlliance()
                )).orElseThrow(()-> new ResourceNotFoundException(
                        "Usuario con el ID "+id+", no encontrado."));
    }
    public void saveUser(User user) {
        if (this.findByUsername(user.getUsername()) != null) {
            throw new ExistingResourceException("El usuario que intenta utilizar, ya esta en Uso");
        } else if (this.findByEmail(user.getEmail()) != null) {
            throw new ExistingResourceException("El email que intenta utilizar, ya esta en Uso");
        } else {
            user.setUserRole(UserRole.USER);
            user.setCreationDate(ZonedDateTime.now());
            user.setUpdateDate(ZonedDateTime.now());
            user.setLevel(1);
            userRepository.save(user);
        }
    }
    public void updateUser(User user) {
        if (this.findByEmail(user.getEmail())==null) {
            throw new ResourceNotFoundException("El usuario que intenta modificar no existe");
        }else {
            user.setCreationDate(findByEmail(user.getEmail()).getCreationDate());
            user.setUserRole(findByEmail(user.getEmail()).getUserRole());
            user.setId(findByEmail(user.getEmail()).getId());
            user.setUpdateDate(ZonedDateTime.now());
            userRepository.save(user);
        }
    }

    public void deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Usuario con el ID "+id+", no existe.");
        }else {
            userRepository.deleteById(id);
        }
    }
    public Boolean login(String username, String password){
        User user= userRepository.findByUsernameAndPassword(username,password);
        return user != null;
    }
    private User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    private User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
