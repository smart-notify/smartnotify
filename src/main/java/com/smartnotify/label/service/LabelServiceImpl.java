package com.smartnotify.label.service;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.label.factory.ResidenceDataNormalizerFactory;
import com.smartnotify.label.factory.ResidencePatternMatcherFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LabelServiceImpl implements LabelService {

    private final ResidencePatternMatcherFactory patternMatcher;

    private final ResidenceDataNormalizerFactory dataNormalizer;

    public String matchResidencePatternAndNormalize(final String labelContent) {
        //final var condominiumType = Condominium.getAuthenticatedCondominium().getCondominiumType();
        // Mocking until spring security is on. TODO: remove this mock
        final var condominiumType = CondominiumType.VERTICAL;

        final String residenceDetails = matchResidenceDetails(labelContent, condominiumType);
        return normalizeResidenceDetails(residenceDetails, condominiumType);
    }

    private String matchResidenceDetails(final String labelContent, final CondominiumType condominiumType) {
        final var matcher = this.patternMatcher.findStrategy(condominiumType);
        return matcher.matchResidencePattern(labelContent);
    }

    private String normalizeResidenceDetails(final String residenceDetails, final CondominiumType condominiumType) {
        final var normalizer = dataNormalizer.findStrategy(condominiumType);
        return normalizer.normalize(residenceDetails);
    }

}
