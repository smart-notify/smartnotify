package com.smartnotify.resident.usecases.delete.factory;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.resident.usecases.delete.DeleteResident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class DeleteResidentFactoryImpl implements DeleteResidentFactory {

    private Map<CondominiumType, DeleteResident> strategies;

    @Autowired
    public DeleteResidentFactoryImpl(final Set<DeleteResident> strategySet) {
        createStrategy(strategySet);
    }

    public DeleteResident findStrategy(final CondominiumType condominiumType) {
        return strategies.get(condominiumType);
    }

    private void createStrategy(final Set<DeleteResident> strategySet) {
        strategies = new HashMap<CondominiumType, DeleteResident>();
        strategySet.forEach(strategy -> strategies.put(strategy.getCondominiumType(), strategy));
    }

}
