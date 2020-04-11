package com.elabda3.tripstask.NoConnectionPackage;

import java.io.IOException;

public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "No connectivity";
    }


}
