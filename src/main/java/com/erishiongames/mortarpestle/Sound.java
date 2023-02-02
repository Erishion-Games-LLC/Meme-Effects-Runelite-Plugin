package com.erishiongames.mortarpestle;

public enum Sound {

    MORTAR_PESTLE_BONK("Bonk.wav"),
    ;



    private final String resourceName;

    Sound(String resourceName){
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

}
