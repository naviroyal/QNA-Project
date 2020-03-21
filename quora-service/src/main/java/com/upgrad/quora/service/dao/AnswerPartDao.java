package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.AnswersEntity;
import com.upgrad.quora.service.entity.QuestionsEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerPartDao {

    @PersistenceContext
    private EntityManager entityManager;

    public AnswersEntity createAnswers(AnswersEntity answersEntity) {
        entityManager.persist(answersEntity);
        return answersEntity;
    }

    public AnswersEntity editAnswers(AnswersEntity answersEntity) {
        entityManager.merge(answersEntity);
        return answersEntity;
    }

    public AnswersEntity deleteAnswers(AnswersEntity answersEntity) {
        entityManager.remove(answersEntity);
        return answersEntity;
    }

    public UserAuthTokenEntity getUsersAuthToken(String accessToken) {
        try {
            return entityManager.createNamedQuery("userAuthTokenByAccessToken", UserAuthTokenEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public AnswersEntity getAnswer(String answersId) {
        try {
            return entityManager.createNamedQuery("answerByUUid", AnswersEntity.class).setParameter("answersId", answersId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<AnswersEntity> getAllAnswers(final QuestionsEntity questionsEntity) {
        try {
            return entityManager.createNamedQuery("answerByQuestionId", AnswersEntity.class).setParameter("question", questionsEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
