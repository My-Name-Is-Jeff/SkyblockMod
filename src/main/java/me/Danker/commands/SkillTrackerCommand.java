package me.Danker.commands;

import java.util.List;

import org.apache.commons.lang3.time.StopWatch;

import me.Danker.TheMod;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

public class SkillTrackerCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "skilltracker";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " <start/stop/reset>";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "start", "resume", "pause", "stop", "reset", "hide", "show");
		}
		return null;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		EntityPlayer player = (EntityPlayer) arg0;
		
		if (arg1.length < 1) {
			player.addChatMessage(new ChatComponentText(TheMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
			return;
		}
		
		switch (arg1[0].toLowerCase()) {
			case "start":
			case "resume":
				if (TheMod.skillStopwatch.isStarted() && TheMod.skillStopwatch.isSuspended()) {
					TheMod.skillStopwatch.resume(); 
				} else if (!TheMod.skillStopwatch.isStarted()) {
					TheMod.skillStopwatch.start();
				}
				player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Skill tracker started."));
				break;
			case "pause":
			case "stop":
				if (TheMod.skillStopwatch.isStarted() && !TheMod.skillStopwatch.isSuspended()) {
					TheMod.skillStopwatch.suspend();
				} else {
					player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Skill tracker paused."));
				}
				break;
			case "reset":
				TheMod.skillStopwatch = new StopWatch();
				TheMod.farmingXPGained = 0;
				TheMod.miningXPGained = 0;
				TheMod.combatXPGained = 0;
				TheMod.foragingXPGained = 0;
				TheMod.fishingXPGained = 0;
				TheMod.enchantingXPGained = 0;
				TheMod.alchemyXPGained = 0;
				player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Skill tracker reset."));
				break;
			case "hide":
				TheMod.showSkillTracker = false;
				ConfigHandler.writeBooleanConfig("misc", "showSkillTracker", false);
				player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Skill tracker hidden."));
				break;
			case "show":
				TheMod.showSkillTracker = true;
				ConfigHandler.writeBooleanConfig("misc", "showSkillTracker", true);
				player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Skill tracker shown."));
				break;
			default:
				player.addChatMessage(new ChatComponentText(TheMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
		}
	}

}
