package com.renderscripttesterror;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class) //
public class FooTest {
  @Rule public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

  @Test public void foo() throws Exception {
    assertThat(rule.getActivity().imageView.getDrawable()).isNotNull();
  }
}