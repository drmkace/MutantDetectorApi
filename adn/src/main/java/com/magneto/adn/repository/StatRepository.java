package com.magneto.adn.repository;

import com.magneto.adn.entity.Stat;
import com.magneto.adn.util.Constants;
import org.springframework.stereotype.Repository;

@Repository
public class StatRepository {

    public void saveOrUdate(Stat entity) {
        if(this.exists(entity)) {
            this.update(entity);
        }
        else {
            this.save(entity);
        }
    }

    public Stat get() {
        return new Stat(10, 5);
    }

    private void save(Stat entity) {

    }

    private void update(Stat entity) {

    }

    private boolean exists(Stat entity) {
        return false;
    }
}
