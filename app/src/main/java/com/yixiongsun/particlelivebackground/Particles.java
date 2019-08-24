package com.yixiongsun.particlelivebackground;

public class Particles {
    // Class that defines a single particle like colour, position, and others
    int radius;
    int size;
    int velocitySize;
    boolean sizeStatus;
    private int sizeSpeed;
    double positionX;
    double positionY;
    double velocityX;
    double velocityY;
    private String colour;
    double opacityValue;
    boolean opacityAnimEnable;
    int velocityOpacity;
    boolean opacityStatus;

    public Particles(String colour, double opacityValue, double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.colour = colour;
        this.opacityValue = opacityValue;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getVelocitySize() {
        return velocitySize;
    }

    public void setVelocitySize(int velocitySize) {
        this.velocitySize = velocitySize;
    }

    public int getSizeSpeed() {
        return sizeSpeed;
    }

    public void setSizeSpeed(int sizeSpeed) {
        this.sizeSpeed = sizeSpeed;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public double getOpacityValue() {
        return opacityValue;
    }

    public void setOpacityValue(double opacityValue) {
        this.opacityValue = opacityValue;
    }

    public boolean isOpacityAnimEnable() {
        return opacityAnimEnable;
    }

    public void setOpacityAnimEnable(boolean opacityAnimEnable) {
        this.opacityAnimEnable = opacityAnimEnable;
    }

    public int getVelocityOpacity() {
        return velocityOpacity;
    }

    public void setVelocityOpacity(int velocityOpacity) {
        this.velocityOpacity = velocityOpacity;
    }

    public boolean isOpactityStatus() {
        return opacityStatus;
    }

    public void setOpactityStatus(boolean opactityStatus) {
        this.opacityStatus = opactityStatus;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
}
