package tes.mosaik.service;

import org.springframework.stereotype.Service;
import tes.mosaik.config.JwtProvider;
import tes.mosaik.modal.User;
import tes.mosaik.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserProfileByEmail(email);
    }

    @Override
    public User findUserProfileByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("User not found");
        }
        else{
            return user;
        }
    }

    @Override
    public User findUserProfileByID(Long userID) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userID);
        if(optionalUser.isEmpty()){
            throw new Exception("User not found");
        }
        else{
            return optionalUser.get();
        }
    }

    @Override
    public User updateUsersProjectSize(User user, int number) throws Exception {
        user.setProjectSize(user.getProjectSize() + number);
        return userRepository.save(user);
    }
}
