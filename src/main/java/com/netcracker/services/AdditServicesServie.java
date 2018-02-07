package com.netcracker.services;

import com.netcracker.DAO.entity.AdditionalServices;

import java.util.List;

/**
 * Created by 12345 on 30.01.2018.
 */
public interface AdditServicesServie {
    AdditionalServices saveAdditServices(AdditionalServices corps);

    List<AdditionalServices> findAllAdditServices();

    AdditionalServices findAdditServicesById(int id);

    boolean deleteAdditServicesById(int id_reserv, int id_service);
}
