/*
 * Assignment 5
 * File Name: MainActivity.java
 * Full Name: Lucnel Nordelus
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
        // Part 4 placeholder
    }

    @Override
    public void onSelectStatus() {
        // Part 5 placeholder
    }

    @Override
    public void onUserCreated(User user) {
        // Part 6 placeholder
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
        // Part 4 placeholder
    }

    @Override
    public void onIncomeSelectionCancelled() {
        // Part 4 placeholder
    }

    @Override
    public void onLivingStatusSelected(String status) {
        // Part 5 placeholder
    }

    @Override
    public void onLivingStatusSelectionCancelled() {
        // Part 5 placeholder
    }

    @Override
    public void onCloseProfile() {
        // Part 6 placeholder
    }
}