package com.csctracker.service;

import com.csctracker.dto.Conversor;
import com.csctracker.dto.UserDTO;
import com.csctracker.model.User;
import com.csctracker.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserInfoRemoteService {
    private final Conversor<User, UserDTO> conversor;
    @Value("${auth.ip:#{\"127.0.0.1\"}}")
    private String ipAuth;
    @Value("${auth.port:${server.port}}")
    private String portAuth;

    private final UserRepository userRepository;

    public UserInfoRemoteService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.conversor = new Conversor<>(User.class, UserDTO.class);
    }


    public User getUser() throws UnirestException {
        var userName = RequestInfo.getHeader("userName");
        if (userName != null && !userName.isBlank()) {
            return userRepository.findByEmail(userName);
        }
        var response = Unirest.get("http://" + ipAuth + ":" + portAuth + "/current-user")
                .header("Authorization", RequestInfo.getHeader("Authorization"))
                .asString();
        RequestInfo.checkResponse(response);
        try {
            var userDto = conversor.toD(response.getBody());
            return conversor.toE(userDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
