package com.yixiongsun.particlelivebackground;

public class Particles {
    // Class that defines a single particle like colour, position, and others
    private int radius;
    private int size;
    private int velocitySize;
    private boolean size_status;
    private int sizeSpeed;
    private int positionX;
    private int positionY;
    private String colour;
    private int opacityValue;
    private boolean opacityAnimEnable;
    private int velocityOpacity;
    private boolean opacityStatus;

    public Particles(int positionX, int positionY, String colour, int opacityValue) {
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

    public boolean isSize_status() {
        return size_status;
    }

    public void setSize_status(boolean size_status) {
        this.size_status = size_status;
    }

    public int getSizeSpeed() {
        return sizeSpeed;
    }

    public void setSizeSpeed(int sizeSpeed) {
        this.sizeSpeed = sizeSpeed;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getOpacityValue() {
        return opacityValue;
    }

    public void setOpacityValue(int opacityValue) {
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
}
