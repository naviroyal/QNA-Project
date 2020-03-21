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

public class AnswerPartControllerTest {

    @Autowired
    private MockMvc mvc;


    //This test case passes when you try to create the answer but the JWT token entered does not exist in the database.
    @Test
    public void createAnswersWithNonExistingAccessToken() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/question/database_question_uuid/answer/create?answer=my_answer").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).header("authorization", "non_existing_access_token"))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Authr-1"));
    }

    //This test case passes when you try to create the answer but the user corresponding to the JWT token entered is signed out of the application.
    @Test
    public void createAnswersWithSignedOutUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/question/database_question_uuid/answer/create?answer=my_answer").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).header("authorization", "accesstoken3"))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Authr-2"));
    }

    //This test case passes when you try to create the answer for the question which does not exist in the database.
    @Test
    public void createAnswersForNonExistingQuestion() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/question/non_existing_question_uuid/answer/create?answer=my_answer").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).header("authorization", "accesstoken"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Q-1"));
    }

    //This test case passes when you try to edit the answer but the JWT token entered does not exist in the database.
    @Test
    public void editAnswersWithNonExistingAccessToken() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/answer/edit/answer_uuid?content=edited_answer").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).header("authorization", "non_existing_access_token"))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Authr-1"));
    }

    //This test case passes when you try to edit the answer and the JWT token entered exists in the database but the user corresponding to that JWT token is signed out.
    @Test
    public void editAnswersWithSignedOutUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/answer/edit/answer_uuid?content=edited_answer").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).header("authorization", "accesstoken3"))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Authr-2"));
    }

    //This test case passes when you try to edit the answer which does not exist in the database.
    @Test
    public void editNonExistingAnswer() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/answer/edit/non_existing_answer_uuid?content=edited_answer").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).header("authorization", "accesstoken1"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Ans-1"));
    }

    //This test case passes when you try to edit the answer and the JWT token entered exists in the database and the user corresponding to that JWT token is signed in but the corresponding user is not the owner of the answer.
    @Test
    public void editAnswersWithoutOwnership() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/answer/edit/answer_uuid?content=edited_answer").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).header("authorization", "accesstoken"))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Authr-3"));
    }

    //This test case passes when you try to delete the answer but the JWT token entered does not exist in the database.
    @Test
    public void deleteAnswersWithNonExistingAccessToken() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/answer/delete/answer_uuid").header("authorization", "non_existing_access_token"))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Authr-1"));
    }

    //This test case passes when you try to delete the answer and the JWT token entered exists in the database but the user corresponding to that JWT token is signed out.
    @Test
    public void deleteAnswersWithSignedOutUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/answer/delete/answer_uuid").header("authorization", "accesstoken3"))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Authr-2"));
    }

    //This test case passes when you try to delete the answer which does not exist in the database.
    @Test
    public void deleteNonExistingAnswer() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/answer/delete/non_existing_answer_uuid").header("authorization", "accesstoken1"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Ans-1"));
    }

    //This test case passes when you try to delete the answer and the JWT token entered exists in the database and the user corresponding to that JWT token is signed in but the corresponding user is not the owner of the answer or he is not the admin.
    @Test
    public void deleteAnswersWithoutOwnership() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/answer/delete/answer_uuid").header("authorization", "accesstoken2"))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Authr-3"));
    }

    //This test case passes when you try to get all the answers posted for a specific question but the JWT token entered does not exist in the database.
    @Test
    public void getAnswersOfQuestionWithNonExistingAccessToken() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/answer/all/database_question_uuid").header("authorization", "non_existing_access_token"))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Authr-1"));
    }

    //This test case passes when you try to get all the answers posted for a specific question and the JWT token entered exists in the database but the user corresponding to that JWT token is signed out.
    @Test
    public void getAnswersOfQuestionWithSignedOutUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/answer/all/database_question_uuid").header("authorization", "accesstoken3"))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Authr-2"));
    }

    //This test case passes when you try to get all the answers posted for a specific question which does not exist in the database.
    @Test
    public void getAllAnswersToNonExistingQuestion() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/answer/all/non_existing_question_uuid").header("authorization", "accesstoken"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Q-1"));
    }


}