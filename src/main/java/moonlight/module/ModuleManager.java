package moonlight.module;

import java.util.ArrayList;

import moonlight.module.combat.AimAssist;
import moonlight.module.combat.AntiBot;
import moonlight.module.combat.AutoClicker;
import moonlight.module.combat.HitBox;
import moonlight.module.combat.Reach;
import moonlight.module.combat.Velocity;
import moonlight.module.misc.FPSBoost;
import moonlight.module.misc.NameProtect;
import moonlight.module.misc.NoPotionShift;
import moonlight.module.misc.Spin;
import moonlight.module.movement.Blink;
import moonlight.module.movement.FastBridge;
import moonlight.module.movement.GuiMove;
import moonlight.module.movement.Sprint;
import moonlight.module.movement.VClip;
import moonlight.module.player.AutoArmor;
import moonlight.module.player.AutoRod;
import moonlight.module.player.AutoTool;
import moonlight.module.player.FastPlace;
import moonlight.module.player.InvManager;
import moonlight.module.render.Chams;
import moonlight.module.render.Chat;
import moonlight.module.render.ClickGUI;
import moonlight.module.render.Fullbright;
import moonlight.module.render.HUD;
import moonlight.module.render.MotionBlur;
import moonlight.module.world.AntiInvisible;
import moonlight.module.world.AutoHypixel;
import moonlight.module.world.ChestStealer;
import moonlight.module.world.LightningTracker;
import moonlight.module.world.TimeChanger;

public class ModuleManager {
	
    public ArrayList<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
    	
    	//Combat
    	modules.add(new AntiBot());
    	modules.add(new AimAssist());
    	modules.add(new AutoClicker());
    	modules.add(new Reach());
    	modules.add(new HitBox());
    	modules.add(new Velocity());
    	
    	//Movement
    	modules.add(new Blink());
    	modules.add(new FastBridge());
    	modules.add(new GuiMove());
    	modules.add(new Sprint());
    	modules.add(new VClip());
    	
    	//Player
    	modules.add(new AutoArmor());
    	modules.add(new AutoRod());
    	modules.add(new AutoTool());
    	modules.add(new FastPlace());
    	modules.add(new InvManager());
    	
    	//Render
    	modules.add(new Chams());
    	modules.add(new Chat());
    	modules.add(new ClickGUI());
    	modules.add(new Fullbright());
    	modules.add(new HUD());
    	modules.add(new MotionBlur());
    	
    	//World
    	modules.add(new AntiInvisible());
    	modules.add(new AutoHypixel());
    	modules.add(new ChestStealer());
    	modules.add(new LightningTracker());
    	modules.add(new TimeChanger());
    	
    	//Misc
    	modules.add(new FPSBoost());
    	modules.add(new NameProtect());
    	modules.add(new NoPotionShift());
    	modules.add(new Spin());
    }

    public ArrayList<Module> getModules() {
        return modules;
    }
    
    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

	public ArrayList<Module> getModulesInCategory(Category c) {
		ArrayList<Module> module = new ArrayList<Module>();
		for (Module m : this.modules) {
			if (m.getCategory() == c) {
				module.add(m);
			}
		}
		return module;
	}
}