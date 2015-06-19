/*
   Copyright 2014 Citrus Payment Solutions Pvt. Ltd.
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.citrus.sdk.payment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.citrus.sdk.classes.Month;
import com.citrus.sdk.classes.Year;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by salil on 13/2/15.
 */
public abstract class CardOption extends PaymentOption {

    protected String cardHolderName = null;
    protected String cardNumber = null;
    protected String cardCVV = null;
    protected String cardExpiry = null;
    protected String cardExpiryMonth = null;
    protected String cardExpiryYear = null;
    protected CardScheme cardScheme = null;
    protected String nickName = null;

    CardOption(String token, String cardCVV) {
        this.token = token;
        this.cardCVV = cardCVV;
    }

    CardOption() {
    }

    /**
     * @param cardHolderName - Name of the card holder.
     * @param cardNumber     - Card number.
     * @param cardCVV        - CVV of the card. We do not store CVV at our end.
     * @param cardExpiry     - Expiry date in MM/YY format.
     */
    CardOption(String cardHolderName, String cardNumber, String cardCVV, String cardExpiry) {

        if (!android.text.TextUtils.isEmpty(cardHolderName)) {
            this.cardHolderName = cardHolderName;
        } else {
            this.cardHolderName = "Card Holder Name";
        }

        this.cardNumber = normalizeCardNumber(cardNumber);
        this.cardCVV = cardCVV;
        this.cardExpiry = cardExpiry;
        this.cardScheme = CardScheme.getCardSchemeUsingNumber(cardNumber);

        if (!TextUtils.isEmpty(cardExpiry)) {
            String[] strArr = cardExpiry.split("/");
            if (strArr != null && strArr.length == 2) {
                cardExpiryMonth = strArr[0];
                cardExpiryYear = strArr[1];
            }
        }
    }

    /**
     * @param cardHolderName  - Name of the card holder.
     * @param cardNumber      - Card number.
     * @param cardCVV         - CVV of the card. We do not store CVV at our end.
     * @param cardExpiryMonth - Card Expiry Month 01 to 12 e.g. 01 for January.
     * @param cardExpiryYear  - Card Expiry Year in the form of YYYY e.g. 2015.
     */
    CardOption(String cardHolderName, String cardNumber, String cardCVV, Month cardExpiryMonth, Year cardExpiryYear) {
        if (!android.text.TextUtils.isEmpty(cardHolderName)) {
            this.cardHolderName = cardHolderName;
        } else {
            this.cardHolderName = "Card Holder Name";
        }

        this.cardNumber = normalizeCardNumber(cardNumber);
        this.cardCVV = cardCVV;
        this.cardScheme = CardScheme.getCardSchemeUsingNumber(cardNumber);

        if (cardExpiryMonth != null) {
            this.cardExpiryMonth = cardExpiryMonth.toString();
        }
        if (cardExpiryYear != null) {
            this.cardExpiryYear = cardExpiryYear.toString();
        }

        if (!TextUtils.isEmpty(this.cardExpiryMonth) && !TextUtils.isEmpty(this.cardExpiryYear)) {
            this.cardExpiry = cardExpiryMonth + "/" + cardExpiryYear;
        }
    }

    /**
     * This constructor will be used internally, mostly to display the saved card details.
     *
     * @param name           - User friendly name of the card. e.g. Debit Card (4242) or Credit Card (1234)
     * @param token          - Stored token for Card payment.
     * @param cardHolderName - Name of the card holder.
     * @param cardNumber     - Card number
     * @param cardScheme     - Card scheme e.g. VISA, MASTER etc.
     * @param cardExpiry     - Card expiry date. In MMYYYY format.
     */
    CardOption(String name, String token, String cardHolderName, String cardNumber, CardScheme cardScheme, String cardExpiry) {
        super(name, token);
        if (!android.text.TextUtils.isEmpty(cardHolderName)) {
            this.cardHolderName = cardHolderName;
        } else {
            this.cardHolderName = "Card Holder Name";
        }

        this.cardNumber = normalizeCardNumber(cardNumber);
        this.cardExpiry = cardExpiry;
        this.cardScheme = cardScheme;

        // The received expiry date is in MMYYYY format so take out expiry month and year which will be required for display purpose.
        if (!TextUtils.isEmpty(cardExpiry)) {
            this.cardExpiryMonth = TextUtils.substring(cardExpiry, 0, 2);
            this.cardExpiryYear = TextUtils.substring(cardExpiry, 2, cardExpiry.length());
        }
    }

    /**
     * Get the type of the card, i.e. DEBIT or CREDIT.
     *
     * @return
     */
    public abstract String getCardType();

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getCardExpiryYear() {
        return cardExpiryYear;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public String getCardExpiryMonth() {

        return cardExpiryMonth;
    }

    public String getCardCVV() {

        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public CardScheme getCardScheme() {
        return cardScheme;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    private String normalizeCardNumber(String number) {
        if (number == null) {
            return null;
        }
        return number.trim().replaceAll("\\s+|-", "");
    }

    @Override
    public Drawable getOptionIcon(Context context) {
        // Return the icon depending upon the scheme of the card.
        Drawable drawable = null;

        int resourceId = 0;
        if (cardScheme == CardScheme.VISA) {
            resourceId = context.getResources().getIdentifier("visa", "drawable", context.getPackageName());
        } else if (cardScheme == CardScheme.MASTER_CARD) {
            resourceId = context.getResources().getIdentifier("mcrd", "drawable", context.getPackageName());
        } else if (cardScheme == CardScheme.MAESTRO) {
            resourceId = context.getResources().getIdentifier("mtro", "drawable", context.getPackageName());
        } else if (cardScheme == CardScheme.DINERS) {
            resourceId = context.getResources().getIdentifier("dinerclub", "drawable", context.getPackageName());
        } else if (cardScheme == CardScheme.JCB) {
            resourceId = context.getResources().getIdentifier("jcb", "drawable", context.getPackageName());
        } else if (cardScheme == CardScheme.AMEX) {
            resourceId = context.getResources().getIdentifier("amex", "drawable", context.getPackageName());
        } else if (cardScheme == CardScheme.DISCOVER) {
            resourceId = context.getResources().getIdentifier("discover", "drawable", context.getPackageName());
        }

        if (resourceId == 0) {
            if ((resourceId = context.getResources().getIdentifier("default_card", "drawable", context.getPackageName())) != 0) {
                drawable = context.getResources().getDrawable(resourceId);
            }
        } else {
            drawable = context.getResources().getDrawable(resourceId);
        }

        return drawable;
    }

    @Override
    public String toString() {
        return super.toString() + "CardOption{" +
                "cardHolderName='" + cardHolderName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardCVV='" + cardCVV + '\'' +
                ", cardExpiry='" + cardExpiry + '\'' +
                ", cardExpiryMonth='" + cardExpiryMonth + '\'' +
                ", cardExpiryYear='" + cardExpiryYear + '\'' +
                ", cardScheme='" + cardScheme + '\'' +
                '}';
    }

    /**
     * Returns the no of digits of CVV for that particular card.
     * <p/>
     * Only AMEX has 4 digit CVV, else all cards have 3 digit CVV.
     *
     * @return
     */
    public int getCVVLength() {
        int cvvLength = 3;

        if (cardScheme == CardScheme.AMEX) {
            cvvLength = 4;
        }

        return cvvLength;
    }

    @Override
    public String getSavePaymentOptionObject() {
        JSONObject object = null;
        try {
            object = new JSONObject();
            JSONArray paymentOptions = new JSONArray();

            JSONObject option = new JSONObject();
            option.put("owner", cardHolderName);
            option.put("number", cardNumber);
            option.put("scheme", cardScheme.toString());
            option.put("expiryDate", cardExpiry);
            option.put("type", getCardType());
            paymentOptions.put(option);

            object.put("paymentOptions", paymentOptions);
            object.put("type", "payment");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object != null ? object.toString() : null;
    }

    /**
     * Denotes the type of the card. i.e. Credit or Debit.
     */
    public static enum CardType {
        DEBIT {
            public String getCardType() {
                return "debit";
            }
        }, CREDIT {
            public String getCardType() {
                return "credit";
            }
        };

        /**
         * Get the type of the card in string, i.e. debit or credit.
         *
         * @return
         */
        public abstract String getCardType();
    }

    public static enum CardScheme {
        VISA, MASTER_CARD, MAESTRO, DINERS, JCB, AMEX, DISCOVER;

        public static CardScheme getCardScheme(String cardScheme) {
            if ("visa".equalsIgnoreCase(cardScheme)) {
                return VISA;
            } else if ("mcrd".equalsIgnoreCase(cardScheme)) {
                return MASTER_CARD;
            } else if ("mtro".equalsIgnoreCase(cardScheme)) {
                return MAESTRO;
            } else if ("DINERS".equalsIgnoreCase(cardScheme)) {
                return DINERS;
            } else if ("jcb".equalsIgnoreCase(cardScheme)) {
                return JCB;
            } else if ("amex".equalsIgnoreCase(cardScheme)) {
                return AMEX;
            } else if ("DISCOVER".equalsIgnoreCase(cardScheme)) {
                return DISCOVER;
            } else {
                return null;
            }
        }

        public static CardScheme getCardSchemeUsingNumber(String cardNumber) {
            com.citrus.card.CardType cardType = com.citrus.card.CardType.typeOf(cardNumber);
            CardScheme cardScheme = null;
            if (cardType != null) {
                switch (cardType) {
                    case VISA:
                        cardScheme = CardScheme.VISA;
                        break;
                    case MCRD:
                        cardScheme = CardScheme.MASTER_CARD;
                        break;
                    case MTRO:
                        cardScheme = CardScheme.MAESTRO;
                        break;
                    case DINERS:
                        cardScheme = CardScheme.DINERS;
                        break;
                    case DISCOVER:
                        cardScheme = CardScheme.DISCOVER;
                        break;
                    case AMEX:
                        cardScheme = CardScheme.AMEX;
                        break;
                    case JCB:
                        cardScheme = CardScheme.JCB;
                        break;
                }

            }
            return cardScheme;
        }
    }
}
