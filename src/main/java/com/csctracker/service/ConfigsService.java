package com.csctracker.service;

import com.csctracker.model.Configs;
import com.csctracker.model.User;
import com.csctracker.repository.ConfigsRepository;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

import java.util.TimeZone;

@Service
public class ConfigsService {
    private final ConfigsRepository configsRepository;

    private final UserInfoRemoteService userInfoRemoteService;

    public ConfigsService(ConfigsRepository configsRepository, UserInfoRemoteService userInfoRemoteService) {
        this.configsRepository = configsRepository;
        this.userInfoRemoteService = userInfoRemoteService;
    }

    public Configs getConfigByUser() {
        return getConfigByUser(RequestInfo.getUser());
    }

    public Configs getConfigByUser(User user) {
        return configsRepository.findByUser(user);
    }

    public void save(Configs configs) throws UnirestException {
        var user = RequestInfo.getUser();
        save(configs, user);
    }

    public void save(Configs configs, User user) {
        if (!configsRepository.existsByUser(user)) {
            configs.setUser(user);
        } else {
            Configs byUser = configsRepository.findByUser(user);
            configs.setId(byUser.getId());
            configs.setUser(user);
            if (configs.getTimeZone() == null) {
                configs.setTimeZone(byUser.getTimeZone());
            }
            if (configs.getFavoriteContact() == null) {
                configs.setFavoriteContact(byUser.getFavoriteContact());
            }
            if (configs.getApplicationNotify() == null) {
                configs.setApplicationNotify(byUser.getApplicationNotify());
            }
            if (configs.getTimeZone() == null) {
                configs.setTimeZone("America/Sao_Paulo");
            }
        }
        configsRepository.save(configs);
    }

    public TimeZone getTimeZone() throws UnirestException {
        return getTimeZone(RequestInfo.getUser());
    }

    public TimeZone getTimeZone(User user) {
        String timeZone = null;
        try {
            timeZone = getConfigByUser(user).getTimeZone();
        } catch (Exception e) {
            //
        }

        TimeZone timeZone1 = null;
        try {
            timeZone1 = TimeZone.getTimeZone(timeZone == null ? "America/Sao_Paulo" : timeZone);
        } catch (Exception e) {
            timeZone1 = TimeZone.getTimeZone("America/Sao_Paulo");
        }
        return timeZone1;
    }
}
