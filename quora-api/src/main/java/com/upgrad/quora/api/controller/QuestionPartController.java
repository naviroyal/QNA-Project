package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.AuthenticationPartService;
import com.upgrad.quora.service.business.QuestionPartBusinessService;
import com.upgrad.quora.service.entity.AnswersEntity;
import com.upgrad.quora.service.entity.QuestionsEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.QuestionNotValidException;
import com.upgrad.quora.service.exception.RestrictedSignOutException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class QuestionPartController {


    @Autowired
    private QuestionPartBusinessService questionPartBusinessService;

    @Autowired
    private AuthenticationPartService userAuthPartBusinessService;


    @RequestMapping(method = RequestMethod.GET , path = "/question/all/{userid}" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailResponse>> getAllQuestionsByUserId(@PathVariable("userid") final String userid, @RequestHeader("authorization") final String authorization )
            throws AuthorizationFailedException , UserNotFoundException, RestrictedSignOutException {
        final List<QuestionsEntity> questionsEntityList = questionPartBusinessService.getAllQuestionsByUserId(userid, authorization);
        final List<QuestionDetailResponse> allQuestionDetailResponse = new ArrayList<QuestionDetailResponse>();
        for(QuestionsEntity questionsEntity : questionsEntityList) {
            QuestionDetailResponse questionDetailResponse = new QuestionDetailResponse().id(questionsEntity.getUUid()).content(questionsEntity.getContent());
            allQuestionDetailResponse.add(questionDetailResponse);
        }
        return new ResponseEntity<List<QuestionDetailResponse>>(allQuestionDetailResponse, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/question/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailResponse>> GetAllQuestions(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, RestrictedSignOutException{
        final List<QuestionsEntity> allQuestion = questionPartBusinessService.getAllQuestion(authorization);
        List<QuestionDetailResponse> questionResponse = questionslist(allQuestion);
        return new ResponseEntity<List<QuestionDetailResponse>>(questionResponse, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, path = "/question/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> createQuestion(@RequestHeader("authorization") final String authorization, final QuestionRequest questionRequest) throws AuthorizationFailedException, RestrictedSignOutException {
        final ZonedDateTime date = ZonedDateTime.now();
        QuestionsEntity questionsEntity = new QuestionsEntity();
        questionsEntity.setUUid(UUID.randomUUID().toString());
        questionsEntity.setContent(questionRequest.getContent());
        questionsEntity.setDate(date);
        final QuestionsEntity createdQuestion = questionPartBusinessService.createQuestion(questionsEntity , authorization);
        QuestionResponse questionResponse = new QuestionResponse().id(createdQuestion.getUUid()).status("QUESTION CREATED");

        return new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.CREATED);
    }

    
    @RequestMapping(method=RequestMethod.DELETE,path="/question/delete/{questionId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionDeleteResponse>DeleteQuestion(@RequestHeader("authorization") final String authorization, @PathVariable("questionId") final String questionid) throws AuthorizationFailedException, QuestionNotValidException, RestrictedSignOutException {
        QuestionsEntity deletedQuestion = questionPartBusinessService.deleteQuestion(questionid, authorization);
        QuestionDeleteResponse questionDeleteResponse = new QuestionDeleteResponse().id(deletedQuestion.getUUid()).status("QUESTION DELETED");
        return new ResponseEntity<QuestionDeleteResponse>(questionDeleteResponse, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.PUT , path = "/question/edit/{questionId}" ,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public ResponseEntity<QuestionEditResponse> editQuestion(@PathVariable("questionId") final String questionId , @RequestHeader("authorization") final String authorization, QuestionEditRequest questionEditRequest)
            throws AuthorizationFailedException, QuestionNotValidException, RestrictedSignOutException {
        QuestionsEntity questionsEntity = new QuestionsEntity();
        questionsEntity.setContent(questionEditRequest.getContent());
        questionsEntity.setDate(ZonedDateTime.now());
        QuestionsEntity editedQuestion = questionPartBusinessService.editQuestion(questionId, authorization, questionsEntity);
        QuestionEditResponse questionEditResponse = new QuestionEditResponse().id(editedQuestion.getUUid()).status("QUESTION EDITED");
        return new ResponseEntity<QuestionEditResponse>(questionEditResponse,HttpStatus.OK);
    }


    public List<QuestionDetailResponse> questionslist(List<QuestionsEntity> allQuestion){
        List<QuestionDetailResponse> listofquestions = new ArrayList<>();
        for ( QuestionsEntity questionsEntity : allQuestion){
            QuestionDetailResponse Response = new QuestionDetailResponse();
            Response.id(questionsEntity.getUUid());
            Response.content(questionsEntity.getContent());
            listofquestions.add(Response);
        }
        return listofquestions;
    }
}