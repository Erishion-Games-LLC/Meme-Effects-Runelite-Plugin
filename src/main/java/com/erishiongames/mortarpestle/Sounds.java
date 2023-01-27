package com.erishiongames.mortarpestle;

public enum Sounds {

    MORTAR_PESTLE_BONK("MortarPestleBonk_r1.wav"),
    ;



    private final String resourceName;

    Sounds(String resourceName){
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

}
