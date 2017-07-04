package com.atrasvida.viewbind;

import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * Created by AvidA on 6/18/2017
 */


public class Res {
    @Documented
    @Target(FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface View {
        java.lang.String value() default "";
    }
    @Documented
    @Target(FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface View_ {
        java.lang.String value() default "";
    }
    @Documented
    @Target(FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface id {
        @IdRes int value() default 0;
        java.lang.String tag() default "";
    }
    @Documented
    @Target(FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ids {
        @IdRes int[] value() default 0;

        java.lang.String tag() default "";
    }


    @Documented
    @Target(METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface OnClick {
        @IdRes int value() default 0;

        java.lang.String tag() default "";
    }
    @Documented
    @Target(METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface OnClicks {
        @IdRes int[] value() default 0;

        java.lang.String tag() default "";
    }
    @Documented
    @Target(FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface String {
    }
    @Documented
    @Target(FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface String_ {
    }

    @Documented
    @Target(FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface StringId {
         @StringRes int value()  ;
    }
    @Documented
    @Target(FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Strings {
         @StringRes int[] value()  ;
    }

    @Documented
    @Target(FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Color {
    }

    @Documented
    @Target(FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Color_ {
    }
    @Documented
    @Target(FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ColorId {
        @ColorRes int value()  ;
    }

    @Documented
    @Target(FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Colors {
        @ColorRes int[] value()  ;
    }
}
