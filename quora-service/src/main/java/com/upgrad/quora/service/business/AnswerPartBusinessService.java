package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AnswerPartDao;
import com.upgrad.quora.service.dao.QuestionPartDao;
import com.upgrad.quora.service.dao.UserPartDao;
import com.upgrad.quora.service.entity.QuestionsEntity;
import com.upgrad.quora.service.entity.AnswersEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.QuestionNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerPartBusinessService {

    @Autowired
    private AnswerPartDao answerPartDao;

    @Autowired
    private QuestionPartDao questionPartDao;

    @Autowired
    private UserPartDao userPartDao;

     @Transactional(propagation = Propagation.REQUIRED)
    public List<AnswersEntity> getAnswersOfQuestion(final String questionId, final String authToken) throws AuthorizationFailedException, QuestionNotValidException {
        QuestionsEntity questionsEntity = questionPartDao.getQuestionByUUid(questionId);
        if (questionsEntity == null) {
            throw new QuestionNotValidException("Q-1", "The question with entered uuid does not exist");
        }
        UserAuthTokenEntity usersAuthTokenEntity = answerPartDao.getUsersAuthToken(authToken);
        if (usersAuthTokenEntity == null) {
            throw new AuthorizationFailedException("Authr-1", "User is signed out,Sign in first to post a question");
        }

        if(usersAuthTokenEntity.getLogoutAt() != null){
            throw new AuthorizationFailedException("Authr-2", "User is signed out,Sign in first to delete an answer");
        }
        List<AnswersEntity> answersEntityList = answerPartDao.getAllAnswers(questionsEntity);

        return answersEntityList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AnswersEntity createAnswers(AnswersEntity answersEntity, final String questionId, final String authToken) throws AuthorizationFailedException, QuestionNotValidException {
        QuestionsEntity questionsEntity = questionPartDao.getQuestionByUUid(questionId);
        if (questionsEntity == null) {
            throw new QuestionNotValidException("Q-1", "The question entered is not valid");
        }
        UserAuthTokenEntity usersAuthTokenEntity = answerPartDao.getUsersAuthToken(authToken);
        if (usersAuthTokenEntity == null) {
            throw new AuthorizationFailedException("Authr-1", "User is signed out,Sign in first to post a question");
        }

        if(usersAuthTokenEntity.getLogoutAt() != null){
            throw new AuthorizationFailedException("Authr-2", "User is not signed in");
        }
        answersEntity.setUsers(usersAuthTokenEntity.getUser());

        answersEntity.setQuestions(questionsEntity);2
        return answerPartDao.createAnswers(answersEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AnswersEntity deleteAnswers(final String answersId, final String authToken) throws AuthorizationFailedException, AnswerNotFoundException {
        UserAuthTokenEntity usersAuthTokenEntity = userPartDao.getUsersAuthToken(authToken);
        if (usersAuthTokenEntity == null) {
            throw new AuthorizationFailedException("Authr-1", "User is signed out,Sign in first to post a question");
        }
        if(usersAuthTokenEntity.getLogoutAt() != null){
            throw new AuthorizationFailedException("Authr-2", "User is signed out.Sign in first to delete an answer");
        }

        AnswersEntity answersEntity = answerPartDao.getAnswer(answersId);
        if (answersEntity == null) {
            throw new AnswerNotFoundException("Ans-1", "Entered answer uuid does not exist");
        }
        String userRole = usersAuthTokenEntity.getUser().getRole();
        Integer signInUserId = usersAuthTokenEntity.getUser().getId();
        Integer answerUserId = answersEntity.getUsers().getId();
        if(!(userRole.equals("admin")) && (signInUserId != answerUserId)){
            throw new AuthorizationFailedException("Authr-3", "Only the answer owner or admin can delete the answer");
        }

        return answerPartDao.deleteAnswers(answersEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AnswersEntity editAnswers(AnswersEntity answersEntity, final String answersId, final String authToken) throws AuthorizationFailedException, AnswerNotFoundException {
        UserAuthTokenEntity usersAuthTokenEntity = answerPartDao.getUsersAuthToken(authToken);
        if (usersAuthTokenEntity == null) {
            throw new AuthorizationFailedException("Authr-1", "User is signed out,Sign in first to post a question");
        }

        if(usersAuthTokenEntity.getLogoutAt() != null){
            throw new AuthorizationFailedException("Authr-2", "User is signed out.Sign in first to edit an answer");
        }
        AnswersEntity answersEntity1 = answerPartDao.getAnswer(answersId);
        if (answersEntity1 == null) {
            throw new AnswerNotFoundException("Ans-1", "Entered answer uuid does not exist");
        }
        if(usersAuthTokenEntity.getUser().getId() != answersEntity1.getUsers().getId()){
            throw new AuthorizationFailedException("Authr-3", "Only answer owner can edit the answer");
        }
        answersEntity.setId(answersEntity1.getId());
        answersEntity.setUUid(answersEntity1.getUUid());
        answersEntity.setUsers(answersEntity1.getUsers());
        answersEntity.setQuestions(answersEntity1.getQuestion());

        return answerPartDao.editAnswers(answersEntity);
    }

}
