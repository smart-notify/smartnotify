package com.smartnotify.label.factory;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.label.matcher.ResidencePatternMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ResidencePatternMatcherFactoryImpl implements ResidencePatternMatcherFactory {

    private Map<CondominiumType, ResidencePatternMatcher> strategies;

    @Autowired
    public ResidencePatternMatcherFactoryImpl(final Set<ResidencePatternMatcher> strategySet) {
        createStrategy(strategySet);
    }

    public ResidencePatternMatcher findStrategy(final CondominiumType condominiumType) {
        return strategies.get(condominiumType);
    }

    private void createStrategy(final Set<ResidencePatternMatcher> strategySet) {
        strategies = new HashMap<CondominiumType, ResidencePatternMatcher>();
        strategySet.forEach(strategy -> strategies.put(strategy.getCondominiumType(), strategy));
    }

}
