package net.dulidanci.staffmod.item.staffs;

import net.dulidanci.staffmod.render.model.staff.PerfectedStaffModel;
import net.dulidanci.staffmod.render.model.staff.StaffModel;

public class PerfectedStaff implements StaffTemplate{
    @Override
    public StaffTypes getType() {
        return StaffTypes.PERFECTED;
    }

    @Override
    public StaffModel getModel() {
        return new PerfectedStaffModel(PerfectedStaffModel.getTexturedModelData().createModel());
    }
}
