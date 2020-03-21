package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.business.AuthenticationPartService;
import com.upgrad.quora.service.business.CommonPartBusinessService;
import com.upgrad.quora.service.dao.UserPartDao;
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
public class CommonPartController {
    @Autowired
    private UserPartDao userPartDao;
    @Autowired
    private AuthenticationPartService authenticationPartService;
    @Autowired
    private CommonPartBusinessService commonPartBusinessService;
    @RequestMapping(method = RequestMethod.GET, path = "/userprofile/{userid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> usersProfile(@PathVariable("userid") final String userid,
                                                           @RequestHeader("authorization") final String authorization) throws  UserNotFoundException,AuthorizationFailedException {
        UsersEntity userEntity = commonPartBusinessService.usersProfile(userid, authorization);

        UserDetailsResponse userDetailsResponse = new UserDetailsResponse().firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName()).emailAddress(userEntity.getEmail())
                .contactNumber(userEntity.getContactNumber()).dob(userEntity.getDob()).aboutMe(userEntity.getAboutMe())
                .country(userEntity.getCountry()).userName(userEntity.getUsername());

        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
        
    }
}
