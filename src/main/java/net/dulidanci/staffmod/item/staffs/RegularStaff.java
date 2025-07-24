package net.dulidanci.staffmod.item.staffs;

public class RegularStaff implements StaffTemplate{
    @Override
    public StaffTypes getType() {
        return StaffTypes.REGULAR;
    }
}
