package com.example.reservation_application.util;

import java.util.ResourceBundle;

import static com.example.reservation_application.model.constants.LocalizationConstants.BUNDLE_NAME;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

public enum LocalizationUtil {
    LOCALIZATION_UTIL;

    public String getMessageByKey(String key) {
        var resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, getLocale());
        return resourceBundle.getString(key);
    }
}
