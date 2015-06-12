package com.citrus.sdk;

/**
 * Created by MANGESH KADAM on 6/3/2015.
 */
public enum Environment {
    SANDBOX {
        public String getBaseUrl() {
            return "https://sandboxadmin.citruspay.com";
        }

        @Override
        public String toString() {
            return "SANDBOX";
        }
    }, PRODUCTION {
        public String getBaseUrl() {
            return "https://admin.citruspay.com";
        }

        @Override
        public String toString() {
            return "PRODUCTION";
        }
    }, NONE {
        public String getBaseUrl() {
            return null;
        }

        @Override
        public String toString() {
            return null;
        }
    };

    public abstract String getBaseUrl();
}
