package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.util.helper_classes.TimePeriod;

import java.util.ArrayList;
import java.util.List;

public class SpiritBuilder {
    private String name;
    private List<TimePeriod> activityPeriods;

    public SpiritBuilder(String name) {
        this.name = name;
        activityPeriods = new ArrayList<>();
    }

    public SpiritBuilder addActivityPeriod(int start, int stop){
        activityPeriods.add(TimePeriod.fromHours(start, stop));
        return this;
    }

    public ModSpirit create(){
        return new ModSpirit(name,activityPeriods);
    }
}