package com.bada.Backend;

import com.bada.Backend.domain.User.entity.User;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class initDB {
    private final EntityManager em;
    //@PostConstruct
    @Transactional
    public void init() {
        User doncham = new User("doncham");
        em.persist(doncham);
    }
}
