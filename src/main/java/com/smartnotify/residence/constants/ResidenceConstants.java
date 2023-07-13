package com.smartnotify.residence.constants;

public final class ResidenceConstants {

    private ResidenceConstants() {
    }

    public static final String APARTMENT_PATTERN = "(?i)ap([.]?|(to[.]?|artamento))?\\s*\\d+";
    public static final String BLOCK_PATTERN = "(?i)((bloco)|(blco)[.]?|(blc)[.]?|(bl)[.]?)\\s*([0-9]{1,3}| [a-z])";
    public static final String HOUSE_PATTERN = "(?i)(casa)\\s*[a-z]?\\d{1,6}[a-z]?";

}
