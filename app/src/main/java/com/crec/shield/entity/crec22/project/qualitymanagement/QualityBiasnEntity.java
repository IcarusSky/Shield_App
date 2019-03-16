package com.crec.shield.entity.crec22.project.qualitymanagement;

import java.util.List;

public class QualityBiasnEntity {
    QualityParamter levelTbm;
    QualityParamter levelBiasn;
    QualityParamter highTbm;
    QualityParamter highBiasn;

    public QualityParamter getLevelTbm() {
        return levelTbm;
    }

    public void setLevelTbm(QualityParamter levelTbm) {
        this.levelTbm = levelTbm;
    }

    public QualityParamter getLevelBiasn() {
        return levelBiasn;
    }

    public void setLevelBiasn(QualityParamter levelBiasn) {
        this.levelBiasn = levelBiasn;
    }

    public QualityParamter getHighTbm() {
        return highTbm;
    }

    public void setHighTbm(QualityParamter highTbm) {
        this.highTbm = highTbm;
    }

    public QualityParamter getHighBiasn() {
        return highBiasn;
    }

    public void setHighBiasn(QualityParamter highBiasn) {
        this.highBiasn = highBiasn;
    }
}
