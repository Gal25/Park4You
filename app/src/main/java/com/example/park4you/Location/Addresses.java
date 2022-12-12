package com.example.park4you.Location;

import java.util.HashMap;
import java.util.List;

public class Addresses {
    public HashMap<String, List<String>> addresses;

    public Addresses(){
        addresses = new HashMap<>();
    }

    public HashMap<String, List<String>> getAddresses() {
        return addresses;
    }
}
