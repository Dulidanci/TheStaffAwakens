package net.dulidanci.thestaffawakens.item.staffs;

import net.dulidanci.thestaffawakens.render.model.staff.LogStaffModel;
import net.dulidanci.thestaffawakens.render.model.staff.StaffModel;

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
