package com.citrus.sample;

import com.citrus.sdk.Environment;

/**
 * Created by salil on 13/6/15.
 */
public interface Constants {

    String BILL_URL = "https://salty-plateau-1529.herokuapp.com/billGenerator.oops.php";
    String RETURN_URL_LOAD_MONEY = "https://salty-plateau-1529.herokuapp.com/redirectUrlLoadCash.php";

    String SIGNUP_ID = "vlrf7z6ojt-signup";
    String SIGNUP_SECRET = "4dfbd300d43402a55cacae7a2be57c8b";
    String SIGNIN_ID = "vlrf7z6ojt-signin";
    String SIGNIN_SECRET = "d1f99c7c57d6836ae8d37065f35a95a4";
    String VANITY = "nativesdk";
    Environment environment = Environment.OOPS;
    boolean enableLogging = true;

    String colorPrimaryDark = "#E7961D";
    String colorPrimary = "#F9A323";
    String textColor = "#ffffff";
}
