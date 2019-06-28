package com.designpattern;

public class DP10Facade {

    public static void main(String[] args) {
        TV tv = new TV();
        Audio audio = new Audio();
        Light light = new Light();

        Home home = new Home(audio, light, tv);

        home.enjoyTv();
        home.enjoyMusic();
        home.goOut();

    }
}

/**
 * 내부구성품 1. TV
 */
class TV{
    private boolean turnedOn = false;
    public void turnOn(){
        turnedOn = true;
        System.out.println("TV on");
    }

    public void turnOff(){
        turnedOn = false;
        System.out.println("TV off");
    }

    public boolean isTurnedOn(){
        return turnedOn;
    }
}


/**
 * 내부구성품 2. 오디오
 */
class Audio{
    private boolean playing = false;

    public void play(){
        playing = true;
        System.out.println("Audio Play");
    }

    public void stop(){
        playing = false;
        System.out.println("Audio Stop");
    }

    public boolean isPlaying(){
        return playing;
    }
}

/**
 * 내부구성품 3. 전등
 */
class Light{

    private int lightness = 0;

    public int getLightness() {
        return lightness;
    }

    public void setLightness(int lightness) {
        System.out.println("lightness change " + lightness);
        this.lightness = lightness;
    }
}


class Home{

    private Audio audio;
    private Light light;
    private TV tv;

    public Home(Audio audio, Light light, TV tv){
        this.audio = audio;
        this.light = light;
        this.tv = tv;
    }

    public void enjoyTv(){
        System.out.println("===불을 밝게하고 TV보기");
        light.setLightness(2);
        tv.turnOn();
    }

    public void enjoyMusic(){
        System.out.println("===불을 어둡게하고 음악듣기");
        light.setLightness(1);
        audio.play();
    }

    public void goOut(){
        System.out.println("===TV끄고, 음악도 끄고, 불도 끄고 외출하기.");

        if(tv.isTurnedOn()){
            tv.turnOff();
        }

        if(audio.isPlaying()){
            audio.stop();
        }

        light.setLightness(0);
    }
}