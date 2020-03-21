package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.AnswerDetailsResponse;
import com.upgrad.quora.api.model.AnswerEditRequest;
import com.upgrad.quora.api.model.AnswerRequest;
import com.upgrad.quora.api.model.AnswerPartResponse;
import com.upgrad.quora.service.business.AnswerPartBusinessService;
import com.upgrad.quora.service.entity.AnswersEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.QuestionNotValidException;
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
public class AnswerPartController {

    @Autowired
    private AnswerPartBusinessService answerPartBusinessService;
     @RequestMapping(method = RequestMethod.GET, path = "answer/all/{questionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AnswerDetailsResponse>> getAnswersOfQuestion  (@PathVariable("questionId") final String questionId, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, QuestionNotValidException {

        final List<AnswersEntity> allAnswers = answerPartBusinessService.getAnswersOfQuestion(questionId, authorization);
        final List<AnswerDetailsResponse> allAnswerDetailsResponse = new ArrayList<AnswerDetailsResponse>();
        for(AnswersEntity answersEntity : allAnswers) {
            AnswerDetailsResponse answerDetailResponse = new AnswerDetailsResponse().id(answersEntity.getUUid()).questionContent(answersEntity.getQuestion().getContent()).answerContent(answersEntity.getAns());
            allAnswerDetailsResponse.add(answerDetailResponse);
        }
        return new ResponseEntity<List<AnswerDetailsResponse>>(allAnswerDetailsResponse, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.POST, path = "/question/{questionId}/answer/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerPartResponse> createAnswers (@PathVariable("questionId") final String questionId, @RequestHeader("authorization") final String authorization, final AnswerRequest answerRequest) throws AuthorizationFailedException, QuestionNotValidException {

        final ZonedDateTime date = ZonedDateTime.now();
        AnswersEntity answersEntity = new AnswersEntity();
        answersEntity.setUUid(UUID.randomUUID().toString());
        answersEntity.setAns(answerRequest.getAnswer());
        answersEntity.setDate(date);

        final AnswersEntity answerCreated = answerPartBusinessService.createAnswers(answersEntity , questionId, authorization);
        AnswerPartResponse answerPartResponse = new AnswerPartResponse().id(answerCreated.getUUid()).status("ANSWER CREATED");

        return new ResponseEntity<AnswerPartResponse>(answerPartResponse, HttpStatus.CREATED);
    }
    @RequestMapping(method = RequestMethod.DELETE, path = "/answer/delete/{answersId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerPartResponse> deleteAnswers  (@PathVariable("answersId") final String answersId, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, AnswerNotFoundException {
        final AnswersEntity answerDeleted = answerPartBusinessService.deleteAnswers(answersId, authorization);
        AnswerPartResponse answerPartResponse = new AnswerPartResponse().id(answerDeleted.getUUid()).status("ANSWER DELETED");

        return new ResponseEntity<AnswerPartResponse>(answerPartResponse, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.PUT, path = "/answer/edit/{answersId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerPartResponse> editAnswer  (@PathVariable("answersId") final String answersId, @RequestHeader("authorization") final String authorization, final AnswerEditRequest answerEditRequest) throws AuthorizationFailedException, AnswerNotFoundException {

        final ZonedDateTime date = ZonedDateTime.now();
        AnswersEntity answersEntity = new AnswersEntity();
        answersEntity.setAns(answerEditRequest.getContent());
        answersEntity.setDate(date);

        final AnswersEntity answerEdited = answerPartBusinessService.editAnswers(answersEntity , answersId, authorization);
        AnswerPartResponse answerPartResponse = new AnswerPartResponse().id(answerEdited.getUUid()).status("ANSWER EDITED");

        return new ResponseEntity<AnswerPartResponse>(answerPartResponse, HttpStatus.OK);
    }


}
