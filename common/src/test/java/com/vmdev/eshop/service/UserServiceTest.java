package com.vmdev.eshop.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceTest {

    private static final UserService userService = new UserService();

    @Test
    void getTest() {
        userService.setTest("test");
        assertEquals("test", userService.getTest());
    }

    @Test
    void setTest() {
        userService.setTest("test");
        assertEquals("test", userService.getTest());
    }
}