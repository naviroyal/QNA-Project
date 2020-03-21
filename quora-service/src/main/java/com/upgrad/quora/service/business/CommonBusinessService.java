package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserPartDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UsersEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.RestrictedSignOutException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class CommonPartBusinessService {

    @Autowired
    private AuthenticationPartService authenticationPartService;

    @Autowired
    private UserPartDao userPartDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public UsersEntity usersProfile(String userid, String authorization) throws AuthorizationFailedException, UserNotFoundException {
        UserAuthTokenEntity usersAuthTokenEntity = userPartDao.getUsersAuthToken(authorization);
        UsersEntity userEntity = userPartDao.getUser(userid);
        if (usersAuthTokenEntity == null) {
            throw new AuthorizationFailedException("Authr-1", "User is signed out,Sign in first to post a question");
        }
        if (usersAuthTokenEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException("Authr-2", "User is signed out.Sign in first to get user details");
        }
        if (userEntity == null) {
            throw new UserNotFoundException("User-1", "User with entered uuid does not exist");
        }
        return userEntity;
    }

}
