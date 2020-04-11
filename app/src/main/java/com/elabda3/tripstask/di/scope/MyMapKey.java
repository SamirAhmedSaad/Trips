package com.elabda3.tripstask.di.scope;


import androidx.lifecycle.ViewModel;
import dagger.MapKey;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface MyMapKey {
    Class<? extends ViewModel> value();
}