package com.crec.shield.entity.project.camera;

import java.util.List;

/**
 * Created by Administrator on 2018/9/23.
 */

public class ProjectCameraResp {
    private String cameraId;

    private String ip;

    private Integer port;

    private String username;

    private String password;

    private List<Integer> channel;

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getChannel() {
        return channel;
    }

    public void setChannel(List<Integer> channel) {
        this.channel = channel;
    }
}
