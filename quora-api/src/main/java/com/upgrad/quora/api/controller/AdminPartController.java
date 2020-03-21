package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDeletePartResponse;
import com.upgrad.quora.service.business.AdminPartPartBusinessService;
import com.upgrad.quora.service.entity.UsersEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AdminPartController {

    @Autowired
    private AdminPartPartBusinessService adminPartPartBusinessService;

    @RequestMapping(method = RequestMethod.DELETE, path = "/admin/user/{userid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDeletePartResponse> userDeletePart (@PathVariable("userid") final String userid, @RequestHeader("authorization") final String authorization) throws  UserNotFoundException,AuthorizationFailedException {
        final UsersEntity deletedUsersEntity = adminPartPartBusinessService.userDeletePart(userid, authorization);
        UserDeletePartResponse userDeletePartResponse = new UserDeletePartResponse().id(deletedUsersEntity.getUUid()).status("USER DELETED SUCCESSFULLY");
        return new ResponseEntity<UserDeletePartResponse>(userDeletePartResponse, HttpStatus.OK);

    }

}
