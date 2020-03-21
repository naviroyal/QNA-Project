package com.upgrad.quora.api.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserPartControllerTest {

    @Autowired
    private MockMvc mvc;

    //This test case passes when you signUp with a username that already exists in the database.
    @Test
    public void signUpWithRepeatedUserName() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/signUp?firstName=a&lastName=a&userName=username&emailAddress=a&password=a&country=a&aboutMe=a&dob=a&contactNumber=a").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("SGR-001"));
    }

    //This test case passes when you signUp with an email that already exists in the database.
    @Test
    public void signUpWithRepeatedEmail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/signUp?firstName=a&lastName=a&userName=non_existing_username&emailAddress=email&password=a&country=a&aboutMe=a&dob=a&contactNumber=a").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("SGR-002"));
    }


    //This test case passes when you try to signOut but the JWT token entered does not exist in the database.
    @Test
    public void signOutWithNonExistingAccessToken() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/signOut").header("authorization", "non_existing_access_token"))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("SGR-001"));
    }
}