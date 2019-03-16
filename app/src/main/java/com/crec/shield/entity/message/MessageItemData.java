package com.crec.shield.entity.message;

public class MessageItemData {
    private String messageId;     // 消息ID
    private String  projectName ; // 项目名称
    private String  projectId ; // 项目ID
    private String  lineId ; // 线路名称
    private String time;  // 时间
    private Double moveSize; // 盾构机偏移量
    private Integer riskLevel;  // 风险等级
    private Integer type;
    private String id;
    private Integer isRead;
    private String userCode;

    public Integer getIsRead() { return isRead; }

    public void setIsRead(Integer isRead) { this.isRead = isRead; }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserCode() { return userCode; }

    public void setUserCode(String userCode) { this.userCode = userCode; }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getMoveSize() { return moveSize; }

    public void setMoveSize(Double moveSize) {
        this.moveSize = moveSize;
    }

    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
