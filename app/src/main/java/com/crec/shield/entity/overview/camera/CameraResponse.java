package com.crec.shield.entity.overview.camera;

import java.util.List;

public class CameraResponse {

    private List<CameraEntity> data;
    private Integer code;

    public List<CameraEntity> getData() {
        return data;
    }

    public void setData(List<CameraEntity> data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static class CameraEntity{
        private String id;
        private String area; // 片区
        private String project; // 项目
        private String line; // 线路
        private String location; // 监控位置
        private Integer isAttention; // 是否关注
        private String ip;//相机ip
        private Integer port;//端口
        private String username;//用户名
        private String password;//密码
        private String channelname;//通道名称
        private Integer channel;//通道

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Integer getIsAttention() {
            return isAttention;
        }

        public void setIsAttention(Integer isAttention) {
            this.isAttention = isAttention;
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

        public String getChannelname() {
            return channelname;
        }

        public void setChannelname(String channelname) {
            this.channelname = channelname;
        }

        public Integer getChannel() {
            return channel;
        }

        public void setChannel(Integer channel) {
            this.channel = channel;
        }
    }
}
