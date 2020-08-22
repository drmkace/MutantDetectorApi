package com.magneto.adn.dto;

public class PingResponseDto {
    private String restApiStatus;
    private String dataBaseStatus;


    public PingResponseDto(String restApiStatus, String dataBaseStatus) {
        this.restApiStatus = restApiStatus;
        this.dataBaseStatus = dataBaseStatus;
    }

    public String getRestApiStatus() {
        return restApiStatus;
    }

    public void setRestApiStatus(String restApiStatus) {
        this.restApiStatus = restApiStatus;
    }

    public String getDataBaseStatus() {
        return dataBaseStatus;
    }

    public void setDataBaseStatus(String dataBaseStatus) {
        this.dataBaseStatus = dataBaseStatus;
    }
}
