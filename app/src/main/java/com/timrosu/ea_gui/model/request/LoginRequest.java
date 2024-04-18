package com.timrosu.ea_gui.model.request;

import com.google.gson.annotations.SerializedName;

// ni v uporabi zaradi neuporabe json formata pri posredovanju prijavnih podatkov
public class LoginRequest {
    @SerializedName("uporabnik")
    String uporabnik;
    @SerializedName("geslo")
    String geslo;
}
