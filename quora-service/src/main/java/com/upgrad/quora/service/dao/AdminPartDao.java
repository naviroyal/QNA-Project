package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UsersEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class AdminPartDao {

    @PersistenceContext
    private EntityManager entityManager;

    public UsersEntity userDeletePart(UsersEntity userEntity){

        entityManager.remove(userEntity);
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

    public UserAuthTokenEntity getUsersAuthToken(final String accesstoken) {
        try {
            return entityManager.createNamedQuery("userAuthTokenByAccessToken", UserAuthTokenEntity.class).setParameter("accessToken", accesstoken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
