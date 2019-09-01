package com.xuxe.falconHeavy.framework.command;

import com.xuxe.falconHeavy.framework.command.cooldown.CooldownScope;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import net.dv8tion.jda.api.Permission;

public abstract class Command {
    protected String name;
    protected String[] aliases;
    protected String help;
    protected String extraHelp;
    protected String helpImageLink;
    protected int[] cooldown = {0, 0, 0};
    protected CooldownScope cooldownScope;
    protected Permission[] userPermissions;
    protected boolean privateAccessible = false;

    void checkRun(CommandTrigger trigger) {
        //todo set countdown
        run(trigger);
    }
    abstract void run(CommandTrigger trigger);
}
