package com.smartnotify.resident.service.factory;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.resident.service.ResidentService;

public interface ResidentServiceFactory {

    ResidentService findStrategy(CondominiumType condominiumType);

}
