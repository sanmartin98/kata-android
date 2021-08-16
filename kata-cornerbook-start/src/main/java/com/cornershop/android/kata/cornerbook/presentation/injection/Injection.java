package com.cornershop.android.kata.cornerbook.presentation.injection;

import android.content.Context;
import android.os.Build;
import android.os.LocaleList;

import java.util.Locale;

public abstract class Injection {

    private static Locale locale;
    private static DataModuleInjector dataModuleInjector;
    private static DomainModuleInjector domainModuleInjector;

    public static void init(Context context) {
        locale = getDefaultLocaleFromContext(context);
        dataModuleInjector = new DataModuleInjector(context);
        domainModuleInjector = new DomainModuleInjector(dataModuleInjector);
    }

    private static Locale getDefaultLocaleFromContext(Context context) {

        Locale locale;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList locales = context.getResources().getConfiguration().getLocales();
            locale = locales.get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }

        return locale;
    }

    public static DomainModuleInjector getDomainModuleInjector() {
        return domainModuleInjector;
    }
}
