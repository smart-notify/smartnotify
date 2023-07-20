package com.smartnotify.resident.service;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.resident.model.Resident;

import java.util.List;

public interface ResidentService {

    List<Resident> findResidentsByResidenceDetails(String residenceDetails, String receptionId);

    CondominiumType getCondominiumType();

}
