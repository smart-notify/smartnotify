package com.smartnotify.label.factory;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.label.matcher.ResidencePatternMatcher;

public interface ResidencePatternMatcherFactory {

    ResidencePatternMatcher findStrategy(CondominiumType condominiumType);

}
