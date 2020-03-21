package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionsEntity;
import com.upgrad.quora.service.entity.UsersEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QuestionPartDao {

    @PersistenceContext
    private EntityManager entityManager;

    public QuestionsEntity createQuestion(QuestionsEntity questionsEntity){
        entityManager.persist(questionsEntity);
        return questionsEntity;
    }

    public List<QuestionsEntity> getAllQuestion() {
        try {
            return entityManager.createNamedQuery("allQuestions", QuestionsEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public QuestionsEntity deleteQuestion(QuestionsEntity questionsEntity) {
        entityManager.remove(questionsEntity);
        return questionsEntity;
    }

    public QuestionsEntity getQuestionByUUid(String questionId){
        try {
            return entityManager.createNamedQuery("questionByUUid", QuestionsEntity.class).setParameter("questionId", questionId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<QuestionsEntity> getAllQuestionsByUserId(UsersEntity user){
        try {
            return entityManager.createNamedQuery("questionsByUser", QuestionsEntity.class).setParameter("users", user).getResultList();
        } catch (NoResultException nre){
            return null;
        }
    }

    public QuestionsEntity editQuestion (QuestionsEntity questionsEntity){
        entityManager.merge(questionsEntity);
        return questionsEntity;
    }



}
