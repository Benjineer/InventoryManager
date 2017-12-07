/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cardinalstone.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.cardinalstone.entities.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author prodigy4440
 */
@Singleton
@Startup
public class CacheManager {

    @PersistenceContext
    private EntityManager entityManager;

    private List<UserEntity> cache;

    @PostConstruct
    public void init() {
        cache = new ArrayList<>();

//        List<String> keys = entityManager.createQuery("SELECT u.token FROM UserEntity u", String.class)
//                .getResultList();
//        cache.addAll(keys);
    }

    public void put(UserEntity userEntity) {
        cache.add(userEntity);
    }

    public UserEntity getUser(String key) {
        return cache.stream().filter(u -> u.getToken().matches(key)).findFirst().get();
    }

    public void remove(UserEntity userEntity) {
        cache.remove(userEntity);
    }

    public boolean hasToken(String key) {

        return !cache.stream().noneMatch(u -> u.getToken().equals(key));
    }

    public void clear() {
        cache.clear();
    }
}
