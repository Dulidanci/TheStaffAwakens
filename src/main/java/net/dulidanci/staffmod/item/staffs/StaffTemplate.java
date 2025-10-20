package net.dulidanci.staffmod.item.staffs;

import net.dulidanci.staffmod.render.model.staff.StaffModel;

public interface StaffTemplate {
    StaffTypes getType();

    StaffModel getModel();
}
