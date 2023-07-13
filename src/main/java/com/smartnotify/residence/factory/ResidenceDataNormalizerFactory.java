package com.smartnotify.label.factory;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.label.normalizer.ResidenceDataNormalizer;

public interface ResidenceDataNormalizerFactory {

    ResidenceDataNormalizer findStrategy(CondominiumType condominiumType);

}
