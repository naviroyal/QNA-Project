package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AdminPartDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UsersEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminPartPartBusinessService {

    @Autowired
    private AdminPartDao adminPartDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public UsersEntity userDeletePart(final String userid, final String authorization) throws AuthorizationFailedException, UserNotFoundException {
        UserAuthTokenEntity usersAuthTokenEntity = adminPartDao.getUsersAuthToken(authorization);
        if (usersAuthTokenEntity == null) {
            throw new AuthorizationFailedException("Authr-1", "User is signed out,Sign in first to post a question");
        }

        if(usersAuthTokenEntity.getLogoutAt() != null){
            throw new AuthorizationFailedException("Authr-2", "User is signed out");
        }

        if(!(usersAuthTokenEntity.getUser().getRole().equals("admin"))){
            throw new AuthorizationFailedException("Authr-3", "Author is not authorized as admin");
        }

        UsersEntity userEntity = adminPartDao.getUser(userid);
        if (userEntity == null) {
            throw new UserNotFoundException("User-1", "User with entered uuid does not exist");
        }

        return adminPartDao.userDeletePart(userEntity);
    }

}
