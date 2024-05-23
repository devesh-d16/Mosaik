package tes.mosaik.service;

import tes.mosaik.modal.User;

public interface UserService {

    User findUserProfileByJwt(String jwt) throws Exception;

    User findUserProfileByEmail(String email) throws Exception;

    User findUserProfileByID(Long userID) throws Exception;

    User updateUsersProjectSize(User user, int number) throws Exception;
}
