package com.yuraku.workers.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MEmpRepository
        extends JpaRepository<MEmp, Integer> {
        }

