package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.base.Callback;
import java.util.List;
import com.crec.shield.model.CompanyEquipmentManagementModel;
import com.crec.shield.contract.CompanyEquipmentManagementContract;
import javax.inject.Inject;
public class CompanyEquipmentManagementPresenter extends BasePresenter<CompanyEquipmentManagementContract.View> implements CompanyEquipmentManagementContract.Presenter {
    private CompanyEquipmentManagementContract.Model model;
    @Inject
    public CompanyEquipmentManagementPresenter() {
        model = new CompanyEquipmentManagementModel();
    }
}
