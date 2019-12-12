package com.amal.hellochurch;

 class Preacher {
    private final String name;
    private final String description;
    private final String ministry;
    private final String onlinechannel;
    private final String imageName;

    public Preacher(String name,String description,String ministry,
                    String onlinechannel,String imageName){
        this.name = name;
        this.description = description;
        this.ministry = ministry;
        this.onlinechannel = onlinechannel;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getMinistry() {
        return ministry;
    }

    public String getOnlinechannel() {
        return onlinechannel;
    }

    public String getImageName() {
        return imageName;
    }
}
