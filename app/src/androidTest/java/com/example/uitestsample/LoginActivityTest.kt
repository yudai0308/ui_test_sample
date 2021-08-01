package com.example.uitestsample

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<LoginActivity>()

    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun loginActivityTest() {
        val noUserNameMessage = onView(withId(R.id.no_user_name_message)).apply {
            // メッセージが表示されていないことを確認
            // doesNotExist() は存在しないことを確認するため意味が異なる
            check(matches(not(isDisplayed())))
        }

        val loginButton = onView(withId(R.id.login_button)).apply {
            perform(click())
        }
        // EditText に何も入力せずログインボタンを押したとき、メッセージが表示されることを確認
        noUserNameMessage.check(matches(isDisplayed()))

        // EditText にユーザー名を入れてログインボタンをクリック
        val userName = "福沢諭吉"
        onView(withId(R.id.user_name_edit_text)).perform(replaceText(userName))
        loginButton.perform(click())

        // 「ログイン中」が表示されていることを確認
        onView(withId(R.id.now_connecting_message)).check(matches(isDisplayed()))

        // ログインに時間がかかるため R.id.welcome_text が表示されるまで待機
        val cond = Until.hasObject(
            By.res(
                "com.example.uitestsample",
                "welcome_text"
            )
        )
        val success = device.wait(cond, 5000L)
        assertThat(success, `is`(true))

        // ログイン後のメッセージが正しいことを確認
        onView(withId(R.id.welcome_text)).check(
            matches(withText("ようこそ${userName}さん"))
        )
    }
}