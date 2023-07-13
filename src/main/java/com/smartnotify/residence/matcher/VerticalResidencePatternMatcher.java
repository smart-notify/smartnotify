package com.smartnotify.residence.matcher;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.config.exception.ResidenceDetailsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.smartnotify.residence.constants.ResidenceConstants.APARTMENT_PATTERN;
import static com.smartnotify.residence.constants.ResidenceConstants.BLOCK_PATTERN;

@Service
public class VerticalResidencePatternMatcher implements ResidencePatternMatcher {

    @Override
    public String matchResidencePattern(final String labelContent) {
        final Matcher apartmentMatcher = Pattern.compile(APARTMENT_PATTERN).matcher(labelContent);
        final Matcher blockMatcher = Pattern.compile(BLOCK_PATTERN).matcher(labelContent);

        final var apartmentNumber = scanApartmentInfo(apartmentMatcher);
        if (apartmentNumber.isEmpty()) {
            throw new ResidenceDetailsNotFoundException("Residence details not found");
        }

        final var block = scanApartmentInfo(blockMatcher);

        return appendBlockIfPresent(apartmentNumber, block).toString();
    }

    @Override
    public CondominiumType getCondominiumType() {
        return CondominiumType.VERTICAL;
    }

    private StringBuilder scanApartmentInfo(final Matcher matcher) {
        final StringBuilder apartmentDetails = new StringBuilder();
        if (matcher.find()) {
            apartmentDetails.append(matcher.group());
        }
        return apartmentDetails;
    }

    private StringBuilder appendBlockIfPresent(final StringBuilder apartmentNumber, final StringBuilder block) {
        if (block.isEmpty()) {
            return apartmentNumber;
        }
        return apartmentNumber.append(" ").append(block);
    }

}
