package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserPartDao;
import com.upgrad.quora.service.entity.QuestionsEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UsersEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.RestrictedSignOutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class AuthenticationPartService {

    @Autowired
    private UserPartDao userPartDao;

    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthTokenEntity authenticate(final String username, final String password) throws AuthenticationFailedException {

        UsersEntity userEntity = userPartDao.getUserByUsername(username);
        if(userEntity == null){
            throw new AuthenticationFailedException("ATH-001", "This username does not exist");
        }

        final String encryptedPassword = cryptographyProvider.encrypt(password, userEntity.getSalt());
        if(encryptedPassword.equals(userEntity.getPassword())){
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            UserAuthTokenEntity userAuthToken = new UserAuthTokenEntity();
            userAuthToken.setUUid(UUID.randomUUID().toString());
            userAuthToken.setUser(userEntity);
            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);
            userAuthToken.setAccessToken(jwtTokenProvider.generateToken(userEntity.getUUid(), now, expiresAt));

            userAuthToken.setLoginAt(date);
            userAuthToken.setExpiresAt(expiresAt);
            userPartDao.createAuthToken(userAuthToken);
            userPartDao.updateUser(userEntity);

            return userAuthToken;
        }
        else{
            throw new AuthenticationFailedException("ATH-002", "Password failed");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthTokenEntity getUsersAuthToken(final String authToken) throws RestrictedSignOutException {
        UserAuthTokenEntity usersAuthTokenEntity = userPartDao.getUsersAuthToken(authToken);
        if (usersAuthTokenEntity == null) {
            throw new RestrictedSignOutException("SGR-001", "User is not Signed in");
        }
        return usersAuthTokenEntity;
    }

}
