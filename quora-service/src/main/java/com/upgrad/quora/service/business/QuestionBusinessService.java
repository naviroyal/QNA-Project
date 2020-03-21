package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionPartDao;
import com.upgrad.quora.service.dao.UserPartDao;
import com.upgrad.quora.service.entity.QuestionsEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UsersEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.QuestionNotValidException;
import com.upgrad.quora.service.exception.RestrictedSignOutException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionPartBusinessService {

    @Autowired
    private QuestionPartDao questionPartDao;

    @Autowired
    private UserPartDao userPartDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionsEntity createQuestion(QuestionsEntity questionsEntity, String authorization) throws AuthorizationFailedException {

        UserAuthTokenEntity usersAuthTokenEntity = userPartDao.getUsersAuthToken(authorization);

        if (usersAuthTokenEntity == null) {
            throw new AuthorizationFailedException("Authr-1", "User is signed out,Sign in first to post a question");
        }

        if (usersAuthTokenEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException("Authr-2", "User is not signed in");
        }
        questionsEntity.setUsers(usersAuthTokenEntity.getUser());
        return questionPartDao.createQuestion(questionsEntity);
    }

   public List<QuestionsEntity> getAllQuestion(String authorization) throws AuthorizationFailedException{

       UserAuthTokenEntity usersAuthTokenEntity = userPartDao.getUsersAuthToken(authorization);
       if (usersAuthTokenEntity == null) {
           throw new AuthorizationFailedException("Authr-1", "User is signed out,Sign in first to post a question");
       }
       if (usersAuthTokenEntity.getLogoutAt() != null) {
           throw new AuthorizationFailedException("Authr-2", "User is signed out.Sign in first to get all questions");
       }
       return questionPartDao.getAllQuestion();
   }

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionsEntity deleteQuestion(String questionId, String authorization) throws AuthorizationFailedException, QuestionNotValidException {

        QuestionsEntity questionsEntity = questionPartDao.getQuestionByUUid(questionId);
        UserAuthTokenEntity usersAuthTokenEntity = userPartDao.getUsersAuthToken(authorization);
        if (usersAuthTokenEntity == null) {
            throw new AuthorizationFailedException("Authr-1", "User is signed out,Sign in first to post a question");
        }
        if (usersAuthTokenEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException("Authr-2", "User is signed out.Sign in first to delete a question");
        }
        if (questionsEntity == null) {
            throw new QuestionNotValidException("Q-1", "Entered question uuid does not exist");
        }
        if (!(usersAuthTokenEntity.getUser().getRole().equals("admin")) && (usersAuthTokenEntity.getUser().getId() != questionsEntity.getUsers().getId())) {
            throw new AuthorizationFailedException("Authr-3", "Only the question owner or admin can delete the question");
        }
        return questionPartDao.deleteQuestion(questionsEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<QuestionsEntity> getAllQuestionsByUserId(String userid, String authorization) throws AuthorizationFailedException, UserNotFoundException {

        UserAuthTokenEntity usersAuthTokenEntity = userPartDao.getUsersAuthToken(authorization);
        UsersEntity userEntity = userPartDao.getUser(userid);

        if (usersAuthTokenEntity == null) {
            throw new AuthorizationFailedException("Authr-1", "User is signed out,Sign in first to post a question");
        }
        if (usersAuthTokenEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException("Authr-2", "User is signed out.Sign in first to get all questions posted by a specific user");
        }
        if(userEntity == null){
            throw new UserNotFoundException("User-1", "User with entered uuid whose question details are to be seen does not exist");
        }
        return questionPartDao.getAllQuestionsByUserId(userEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionsEntity editQuestion(String questionId, String authorization, QuestionsEntity questionsEntity) throws AuthorizationFailedException, QuestionNotValidException {

        QuestionsEntity questionsEntity1 = questionPartDao.getQuestionByUUid(questionId);
        UserAuthTokenEntity usersAuthTokenEntity = userPartDao.getUsersAuthToken(authorization);
        if(usersAuthTokenEntity == null){
            throw new AuthorizationFailedException("Authr-1", "User is signed out,Sign in first to post a question");
        }
        if (usersAuthTokenEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException("Authr-2", "User is signed out.Sign in first to edit the question");
        }
        if (questionsEntity1 == null) {
            throw new QuestionNotValidException("Q-1", "Entered question uuid does not exist");
        }
        if (usersAuthTokenEntity.getUser().getId() != questionsEntity1.getUsers().getId()){
            throw new AuthorizationFailedException("Authr-3", "Only the question owner can edit the question");
        }
        questionsEntity.setId(questionsEntity1.getId());
        questionsEntity.setUUid(questionsEntity1.getUUid());
        questionsEntity.setUsers(questionsEntity1.getUsers());
        return  questionPartDao.editQuestion(questionsEntity);
    }


}

