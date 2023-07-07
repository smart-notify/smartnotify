package com.smartnotify.label.factory;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.label.normalizer.ResidenceDataNormalizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ResidenceDataNormalizerFactoryImpl implements ResidenceDataNormalizerFactory {

    private Map<CondominiumType, ResidenceDataNormalizer> strategies;

    @Autowired
    public ResidenceDataNormalizerFactoryImpl(final Set<ResidenceDataNormalizer> strategySet) {
        createStrategy(strategySet);
    }

    public ResidenceDataNormalizer findStrategy(final CondominiumType condominiumType) {
        return strategies.get(condominiumType);
    }

    private void createStrategy(final Set<ResidenceDataNormalizer> strategySet) {
        strategies = new HashMap<CondominiumType, ResidenceDataNormalizer>();
        strategySet.forEach(strategy -> strategies.put(strategy.getCondominiumType(), strategy));
    }

}
