package com.upgrad.quora.api.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class AdminPartControllerTest {

    @Autowired
    private MockMvc mvc;


    //This test case passes when you try to delete the user but the JWT token entered does not exist in the database.
    @Test
    public void deleteWithNonExistingAccessToken() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/admin/user/uuid4").header("authorization", "non_existing_access_token"))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Authr-1"));
    }

    //This test case passes when you try to delete the user but the role of the user corresponding to the JWT token entered is notadmin.
    @Test
    public void deleteWithnotadminAsRole() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/admin/user/uuid4").header("authorization", "accesstoken1"))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("Authr-3"));
    }


    //This test case passes when you try to delete the user which does not exist in the database.
    @Test
    public void deleteNonExistingUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/admin/user/non_existing_user_uuid").header("authorization", "accesstoken"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("User-1"));
    }


}