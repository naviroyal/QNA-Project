package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.SignInResponse;
import com.upgrad.quora.api.model.SignOutResponse;
import com.upgrad.quora.service.business.AuthenticationPartService;
import com.upgrad.quora.service.business.SignOutPartBusinessService;
import com.upgrad.quora.service.business.SignUpPartBusinessService;
import com.upgrad.quora.api.model.SignUpUserRequest;
import com.upgrad.quora.api.model.SignUpUserResponse;
//import com.upgrad.quora.service.business.SignUpPartBusinessService;
//import com.upgrad.quora.service.entity.UsersEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UsersEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.RestrictedSignOutException;
import com.upgrad.quora.service.exception.RestrictedSignUpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class UserPartController {
    @Autowired
    private SignUpPartBusinessService signUpPartBusinessService;

    @Autowired
    private AuthenticationPartService authenticationPartService;

    @Autowired
    private SignOutPartBusinessService signOutPartBusinessService;

    @RequestMapping(method = RequestMethod.POST, path = "/user/signIn", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignInResponse> signIn(@RequestHeader("authorization") final String authorization) throws AuthenticationFailedException {
        byte[] inp = Base64.getDecoder().decode(authorization.split("Basic: ")[1]);
        String updatedinp = new String(decode);
        String[] inptoArray = updatedinp.split(":");
        UserAuthTokenEntity userAuthToken = authenticationPartService.authenticate(inptoArray[0], inptoArray[1]);
        UsersEntity user = userAuthToken.getUser();
        SignInResponse signInResponse = new SignInResponse().id(user.getUUid()).message("SUCCESSFULLY SIGNEDIN");
        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", userAuthToken.getAccessToken());

        return new ResponseEntity<SignInResponse>(signInResponse, headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/user/signUp", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignUpUserResponse> signUp(final SignUpUserRequest signUpUserRequest) throws RestrictedSignUpException {
        final UsersEntity userEntity = new UsersEntity();
        userEntity.setUUid(UUID.randomUUID().toString());
        userEntity.setFirstName(signUpUserRequest.getFirstName());
        userEntity.setLastName(signUpUserRequest.getLastName());
        userEntity.setUsername(signUpUserRequest.getUserName());
        userEntity.setEmail(signUpUserRequest.getEmailAddress());
        userEntity.setPassword(signUpUserRequest.getPassword());
        userEntity.setSalt("123abc");
        userEntity.setCountry(signUpUserRequest.getCountry());
        userEntity.setAboutMe(signUpUserRequest.getAboutMe());
        userEntity.setDob(signUpUserRequest.getDob());
        userEntity.setRole("notadmin");
        userEntity.setContactNumber(signUpUserRequest.getContactNumber());

        final UsersEntity createdUsersEntity = signUpPartBusinessService.signUp(userEntity);
        SignUpUserResponse userResponse = new SignUpUserResponse().id(createdUsersEntity.getUUid()).status("SUCCESSFULLY REGISTERED USER");
        return new ResponseEntity<SignUpUserResponse>(userResponse, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/user/signOut", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignOutResponse> signOut(@RequestHeader("authorization") final String authorization) throws RestrictedSignOutException {

        UserAuthTokenEntity userAuthToken = signOutPartBusinessService.signOut(authorization);
        UsersEntity userEntity = userAuthToken.getUser();

        SignOutResponse signOutResponse = new SignOutResponse().id(userEntity.getUUid()).message("SUCCESSFULLY SIGNED OUT");

        return new ResponseEntity<SignOutResponse>(signOutResponse, HttpStatus.OK);
    }

}
