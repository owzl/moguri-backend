package org.moguri.security.util;

import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.moguri.config.RootConfig;
import org.moguri.security.config.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
@Log4j
class JwtProcessorTest {
    @Autowired
    JwtProcessor jwtProcessor;

    @Test
    void generateToken() {
        String username = "user0";
        String token = jwtProcessor.generateToken(username);
        log.info(token);
        assertNotNull(token);
    }

    @Test
    void getUsername() {

        String token = """
                eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMCIsImlhdCI6MTcyNDIwNzg0NiwiZXhwIjoxNzI0MjA4MTQ2fQ.lsXe5uZM5YVpuXsNht9GGHdusWnvn-5AdSiumlXL4wXWKxgHngj7WES9IsWKjIprFUZBRX3JZAKR55QLtLjwxA
                """;
        String username = jwtProcessor.getUsername(token);
        log.info(username);
        assertNotNull(username);
    }


}