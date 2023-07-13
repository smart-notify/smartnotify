package com.smartnotify.resident.service.factory;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.resident.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ResidentServiceFactoryImpl implements ResidentServiceFactory {

    private Map<CondominiumType, ResidentService> strategies;

    @Autowired
    public ResidentServiceFactoryImpl(Set<ResidentService> strategySet) {
        createStrategy(strategySet);
    }

    public ResidentService findStrategy(CondominiumType condominiumType) {
        return strategies.get(condominiumType);
    }

    private void createStrategy(Set<ResidentService> strategySet) {
        strategies = new HashMap<CondominiumType, ResidentService>();
        strategySet.forEach(strategy -> strategies.put(strategy.getCondominiumType(), strategy));
    }
}
