package com.yixiongsun.particlelivebackground;

// Particle settings
public class ParticleSettings {

    int numberOfParticles = 100;
    boolean densityEnabled = true;
    int density = 10000;

    String[] colour = {"#ffffff"};

    ParticleShape shape = ParticleShape.CIRCLE;
    float strokeWith = 0;
    String strokeColour = "#ff0000";
    int numberOfPolygonSides = 5;

    float opacityValue = 1;

    boolean randomOpacity = false;
    boolean opacityAnimate = false;
    float opacityAnimateSpeed = 2;
    float opacityMin = 0;
    boolean opacityAnimateSync = false;

    float size = 15;
    boolean randomSize = true;
    boolean sizeAnimate = false;
    float sizeAnimateSpeed = 20;
    float sizeMin = 0;
    boolean sizeAnimateSync = false;
    boolean lineLinked = true;
    float lineDistance = 400;
    String lineColour = "#ffffff";
    float lineOpacity = 1;
    float lineWidth = 2;

    boolean move = true;
    float moveSpeed = 10;
    ParticleDirection moveDirection = ParticleDirection.NONE;
    boolean randomMove = false;
    boolean moveStraight = false;
    String moveOutMode = "out";
    boolean bounce = false;

    boolean attract = false;
    float attractRotateX = 3000;
    float attractRotateY = 3000;

    // Events
    float repulseDuration = 0.4f;
    float repulseDistance = 400;
    boolean onClickEnable = true;
    ParticleEvent onClickEvent = ParticleEvent.ATTRACT;


    public ParticleSettings() {

    }

}
