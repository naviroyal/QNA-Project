package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UsersEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserPartDao {

    @PersistenceContext
    private EntityManager entityManager;

    public UsersEntity createUser(UsersEntity userEntity){

        entityManager.persist(userEntity);
        return userEntity;

    }

    public UsersEntity getUser(final String userUUid){
        try {
            return entityManager.createNamedQuery("userByUUid", UsersEntity.class).setParameter("uuid", userUUid).getSingleResult();
        }
        catch(NoResultException nre){
            return null;
        }
    }

    public UsersEntity getUserByUsername(final String username){
        try {
            return entityManager.createNamedQuery("userByUsername", UsersEntity.class).setParameter("username", username).getSingleResult();
        }
        catch(NoResultException nre){
            return null;
        }
    }

    public UsersEntity getUserByEmail(final String email){
        try {
            return entityManager.createNamedQuery("userByEmail", UsersEntity.class).setParameter("email", email).getSingleResult();
        }
        catch(NoResultException nre){
            return null;
        }
    }

    public UserAuthTokenEntity createAuthToken(final UserAuthTokenEntity usersAuthTokenEntity){
        entityManager.persist(usersAuthTokenEntity);
        return usersAuthTokenEntity;
    }

    public UserAuthTokenEntity updateAuthToken(final UserAuthTokenEntity usersAuthTokenEntity){
        entityManager.merge(usersAuthTokenEntity);
        return usersAuthTokenEntity;
    }

    public void updateUser(final UsersEntity updatedUsersEntity){
        entityManager.merge(updatedUsersEntity);
    }
    public UserAuthTokenEntity getUsersAuthToken(final String accessToken) {
        try {
            return entityManager.createNamedQuery("userAuthTokenByAccessToken", UserAuthTokenEntity.class)
                    .setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
