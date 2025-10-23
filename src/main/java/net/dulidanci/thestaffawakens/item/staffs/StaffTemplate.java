package net.dulidanci.thestaffawakens.item.staffs;

import net.dulidanci.thestaffawakens.render.model.staff.StaffModel;

public interface StaffTemplate {
    StaffTypes getType();

    StaffModel getModel();
}
