package net.dulidanci.staffmod.item.staffs;

import net.dulidanci.staffmod.render.model.staff.LogStaffModel;
import net.dulidanci.staffmod.render.model.staff.StaffModel;

public class LogStaff implements StaffTemplate{
    @Override
    public StaffTypes getType() {
        return StaffTypes.LOG;
    }

    @Override
    public StaffModel getModel() {
        return new LogStaffModel(LogStaffModel.getTexturedModelData().createModel());
    }
}
