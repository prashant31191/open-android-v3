/*
 *
 *    Copyright 2014 Citrus Payment Solutions Pvt. Ltd.
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * /
 */

package com.citrus.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.Environment;
import com.citrus.sdk.TransactionResponse;


public class UIActivity extends ActionBarActivity implements UserManagementFragment.UserManagementInteractionListener, WalletFragmentListener {

    private FragmentManager fragmentManager = null;
    private Context mContext = this;
    private CitrusClient citrusClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        fragmentManager = getSupportFragmentManager();

        citrusClient = CitrusClient.getInstance(mContext);
        citrusClient.init("test-signup", "c78ec84e389814a05d3ae46546d16d2e", "test-signin", "52f7e15efd4208cf5345dd554443fd99", "prepaid", Environment.SANDBOX);

        showUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ui, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_toggle_production:

                if (item.isChecked()) {
                    // Environment is production
                    citrusClient.init("test-signup", "c78ec84e389814a05d3ae46546d16d2e", "test-signin", "52f7e15efd4208cf5345dd554443fd99", "prepaid", Environment.PRODUCTION);
                } else {
                    // Environment is sandbox
                    citrusClient.init("test-signup", "c78ec84e389814a05d3ae46546d16d2e", "test-signin", "52f7e15efd4208cf5345dd554443fd99", "prepaid", Environment.SANDBOX);
                }
                break;
        }

        super.onOptionsItemSelected(item);

        return false;
    }

    private void showUI() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.container, UIActivityFragment.newInstance());

        fragmentTransaction.commit();
    }

    public void onUserManagementClicked(View view) {
        UserManagementFragment fragment = UserManagementFragment.newInstance(this);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.container, fragment);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void onGuestPaymentClicked(View view) {
    }

    @Override
    public void onShowWalletScreen() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.container, WalletPaymentFragment.newInstance());

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onPaymentComplete(TransactionResponse transactionResponse) {
        if (transactionResponse != null) {
            Utils.showToast(mContext, transactionResponse.getMessage());
        }
    }

    @Override
    public void onPaymentTypeSelected(Utils.PaymentType paymentType) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.container, CardPaymentFragment.newInstance(paymentType));

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void onWalletPaymentClicked(View view) {
        onShowWalletScreen();
    }
}
