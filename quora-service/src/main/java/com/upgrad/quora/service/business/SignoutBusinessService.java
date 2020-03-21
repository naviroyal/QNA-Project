package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserPartDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UsersEntity;
import com.upgrad.quora.service.exception.RestrictedSignOutException;
import com.upgrad.quora.service.exception.RestrictedSignUpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class SignOutPartBusinessService {
    @Autowired
    private AuthenticationPartService authenticationPartService;

    @Autowired
    private UserPartDao userPartDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthTokenEntity signOut(String authorization) throws RestrictedSignOutException {
        UserAuthTokenEntity userAuthToken = authenticationPartService.getUsersAuthToken(authorization);
        UsersEntity user = userAuthToken.getUser();
        userAuthToken.setLogoutAt(ZonedDateTime.now());
        userPartDao.updateAuthToken(userAuthToken);
        return userAuthToken;
    }
}
