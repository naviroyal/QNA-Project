package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserPartDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UsersEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.RestrictedSignUpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class SignUpPartBusinessService {

    @Autowired
    private UserPartDao userPartDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
    public UsersEntity signUp(UsersEntity userEntity) throws RestrictedSignUpException {
        UsersEntity userEntity1 = userPartDao.getUserByUsername(userEntity.getUsername());
        if (userEntity1 != null) {
            throw new RestrictedSignUpException("SGR-001", "Try any other Username, this Username has already been taken");
        }
        UsersEntity userEntity2 = userPartDao.getUserByEmail(userEntity.getEmail());
        if (userEntity2 != null) {
            throw new RestrictedSignUpException("SGR-002", "This user has already been registered, try with any other emailId");
        }
        String[] encryptedText = passwordCryptographyProvider.encrypt(userEntity.getPassword());
        userEntity.setSalt(encryptedText[0]);
        userEntity.setPassword(encryptedText[1]);

        return userPartDao.createUser(userEntity);
    }

}
