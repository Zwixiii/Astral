package nightmare.module;

import java.util.ArrayList;

import nightmare.module.combat.AimAssist;
import nightmare.module.combat.AntiBot;
import nightmare.module.combat.AutoClicker;
import nightmare.module.combat.BowAimAssist;
import nightmare.module.combat.HitBox;
import nightmare.module.combat.LegitAura;
import nightmare.module.combat.Reach;
import nightmare.module.combat.TriggerBot;
import nightmare.module.combat.Velocity;
import nightmare.module.misc.BlatantMode;
import nightmare.module.misc.FPSBoost;
import nightmare.module.misc.NameProtect;
import nightmare.module.misc.NoPotionShift;
import nightmare.module.misc.Particle;
import nightmare.module.misc.Spin;
import nightmare.module.movement.Blink;
import nightmare.module.movement.FastBridge;
import nightmare.module.movement.Flight;
import nightmare.module.movement.GuiMove;
import nightmare.module.movement.Speed;
import nightmare.module.movement.Sprint;
import nightmare.module.movement.VClip;
import nightmare.module.player.AutoArmor;
import nightmare.module.player.AutoRod;
import nightmare.module.player.AutoTool;
import nightmare.module.player.FastPlace;
import nightmare.module.player.Freecam;
import nightmare.module.player.NoFall;
import nightmare.module.render.Blur;
import nightmare.module.render.Chams;
import nightmare.module.render.Chat;
import nightmare.module.render.ClickGUI;
import nightmare.module.render.ESP;
import nightmare.module.render.Fullbright;
import nightmare.module.render.HUD;
import nightmare.module.render.MotionBlur;
import nightmare.module.world.AntiInvisible;
import nightmare.module.world.AutoHypixel;
import nightmare.module.world.ChestStealer;
import nightmare.module.world.LightningTracker;
import nightmare.module.world.StaffAnalyser;
import nightmare.module.world.TimeChanger;
import nightmare.module.world.Timer;

public class ModuleManager {
	
    public ArrayList<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
    	
    	//Combat
    	modules.add(new AntiBot());
    	modules.add(new AimAssist());
    	modules.add(new AutoClicker());
    	modules.add(new BowAimAssist());
    	modules.add(new Reach());
    	modules.add(new HitBox());
    	modules.add(new LegitAura());
    	modules.add(new TriggerBot());
    	modules.add(new Velocity());
    	
    	//Movement
    	modules.add(new Blink());
    	modules.add(new FastBridge());
    	modules.add(new Flight());
    	modules.add(new GuiMove());
    	modules.add(new Speed());
    	modules.add(new Sprint());
    	modules.add(new VClip());
    	
    	//Player
    	modules.add(new AutoArmor());
    	modules.add(new AutoRod());
    	modules.add(new AutoTool());
    	modules.add(new FastPlace());
    	modules.add(new Freecam());
    	modules.add(new NoFall());
    	
    	//Render
    	modules.add(new Blur());
    	modules.add(new Chams());
    	modules.add(new Chat());
    	modules.add(new ClickGUI());
    	modules.add(new ESP());
    	modules.add(new Fullbright());
    	modules.add(new HUD());
    	modules.add(new MotionBlur());
    	
    	//World
    	modules.add(new AntiInvisible());
    	modules.add(new AutoHypixel());
    	modules.add(new ChestStealer());
    	modules.add(new LightningTracker());
    	modules.add(new StaffAnalyser());
    	modules.add(new TimeChanger());
    	modules.add(new Timer());
    	
    	//Misc
    	modules.add(new BlatantMode());
    	modules.add(new FPSBoost());
    	modules.add(new NameProtect());
    	modules.add(new NoPotionShift());
    	modules.add(new Particle());
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