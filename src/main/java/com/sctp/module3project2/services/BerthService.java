package com.sctp.module3project2.services;

import com.sctp.module3project2.entity.Berth;

import java.util.List;

public interface BerthService {
    List<Berth> getAllBerths();

    Berth getBerthById(Long id);

    Berth createBerth(Berth berth);

    Berth saveBerth(Berth berth);

    Berth updateBerth(Long id, Berth berth);

    // void deleteBerth(Long id);

}
