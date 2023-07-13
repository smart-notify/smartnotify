package com.smartnotify.residence.matcher;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.config.exception.ResidenceDetailsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.smartnotify.residence.constants.ResidenceConstants.HOUSE_PATTERN;

@Service
public class HorizontalResidencePatternMatcher implements ResidencePatternMatcher {

    @Override
    public String matchResidencePattern(final String labelContent) {
        final Pattern pattern = Pattern.compile(HOUSE_PATTERN);
        final Matcher matcher = pattern.matcher(labelContent);

        final var houseNumber = new StringBuilder();

        if (matcher.find()) {
            return houseNumber.append(matcher.group()).toString();
        }
        throw new ResidenceDetailsNotFoundException("Residence details not found");
    }

    @Override
    public CondominiumType getCondominiumType() {
        return CondominiumType.HORIZONTAL;
    }

}
