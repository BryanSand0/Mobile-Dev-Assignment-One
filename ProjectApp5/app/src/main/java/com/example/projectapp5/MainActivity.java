/*
 * Assignment 5
 * File Name: MainActivity.java
 * Full Name: Bryan Sandoval & Lucnel Nordelus
 */

package com.example.projectapp5;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity
        implements
        WelcomeFragment.WelcomeListener,
        CreateUserFragment.CreateUserListener,
        SelectRoleFragment.SelectRoleListener,
        SelectIncomeFragment.SelectIncomeListener,
        SelectLivingStatusFragment.SelectLivingStatusListener,
        ProfileFragment.ProfileListener {

    private static final String CREATE_USER_TAG = "CREATE_USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new WelcomeFragment())
                    .commit();
        }
    }

    /*
     * PART 1
     * Welcome -> Create User
     */
    @Override
    public void onStartClicked() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new CreateUserFragment(), CREATE_USER_TAG)
                .commit();
    }

    /*
     * PART 2
     * Open Role Selection
     */
    @Override
    public void onSelectRole() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new SelectRoleFragment())
                .addToBackStack(null)
                .commit();
    }

    /*
     * PART 2 Placeholders (Parts 4-6 not implemented per instructions)
     */
    @Override
    public void onSelectIncome() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new SelectIncomeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSelectStatus() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new SelectLivingStatusFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onUserCreated(User user) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new ProfileFragment())
                .addToBackStack(null)
                .commit();
    }

    /*
     * PART 3
     * Return selected role
     */
    @Override
    public void onRoleSelected(String role) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(CREATE_USER_TAG);
        if (fragment instanceof CreateUserFragment) {
            ((CreateUserFragment) fragment).setRole(role);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onRoleSelectionCancelled() {
        getSupportFragmentManager().popBackStack();
    }

    /*
     * Interface placeholders for Parts 4-6
     */
    @Override
    public void onIncomeSelected(String income) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(CREATE_USER_TAG);
        if (fragment instanceof CreateUserFragment) {
            ((CreateUserFragment) fragment).setIncome(income);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onIncomeSelectionCancelled() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onLivingStatusSelected(String status) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(CREATE_USER_TAG);
        if (fragment instanceof CreateUserFragment) {
            ((CreateUserFragment) fragment).setStatus(status);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onLivingStatusSelectionCancelled() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCloseProfile() {
        //Just close the system at this point
        System.exit(0);
    }
}