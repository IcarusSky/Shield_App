package com.crec.shield.model;

import com.alibaba.fastjson.JSON;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.EquipmentManagementContract;
import com.crec.shield.entity.crec22.project.management.EquipmentManagerData;
import com.crec.shield.entity.crec22.project.management.EquipmentManagerResponse;
import com.crec.shield.entity.crec22.project.management.ShieldDanttData;
import com.crec.shield.entity.crec22.project.management.ShieldDanttResponse;
import com.crec.shield.utils.AppUtils;

import static com.crec.shield.global.IstuaryGlobal.getContext;

public class EquipmentManagementModel implements EquipmentManagementContract.Model{
    private static EquipmentManagerData equipmentManagerData;
    private static EquipmentManagerResponse equipmentManagerResponse;
    private static ShieldDanttData shieldDanttData;
    private static ShieldDanttResponse shieldDanttResponse;
    @Override
    public void getNetData(final Callback callback) {

        String json = AppUtils.getJson("json/crec22/project/management/DeviceManagement/DeviceData.json", getContext());
        equipmentManagerResponse = JSON.parseObject(json, EquipmentManagerResponse.class);
        equipmentManagerData = equipmentManagerResponse.getData();
        callback.onSuccess(equipmentManagerData);
        callback.onComplete();

    }

    @Override
    public void getShieldNetData(final Callback callback) {

        String json = AppUtils.getJson("json/crec22/project/management/DeviceManagement/ShieldDanttNewAdditions.json", getContext());
        shieldDanttResponse = JSON.parseObject(json, ShieldDanttResponse.class);
        shieldDanttData = shieldDanttResponse.getData();
        callback.onSuccess(shieldDanttData );
        callback.onComplete();

    }
}
