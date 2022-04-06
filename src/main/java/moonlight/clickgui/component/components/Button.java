package moonlight.clickgui.component.components;

import java.util.ArrayList;

import moonlight.Moonlight;
import moonlight.clickgui.component.Component;
import moonlight.clickgui.component.Frame;
import moonlight.clickgui.component.components.sub.Checkbox;
import moonlight.clickgui.component.components.sub.Keybind;
import moonlight.clickgui.component.components.sub.ModeButton;
import moonlight.clickgui.component.components.sub.Slider;
import moonlight.clickgui.component.components.sub.VisibleButton;
import moonlight.fonts.impl.Fonts;
import moonlight.module.Module;
import moonlight.settings.Setting;
import moonlight.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class Button extends Component {

	public Module module;
	public Frame frame;
	public int offset;
	private boolean isHovered;
	private ArrayList<Component> components;
	public boolean open;
	private int height;
	
	private FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
	
	public Button(Module mod, Frame parent, int offset) {
		this.module = mod;
		this.frame = parent;
		this.offset = offset;
		this.components = new ArrayList<Component>();
		this.open = false;
		height = 18;
		int opY = offset + 18;
		if(Moonlight.instance.settingsManager.getSettingsByMod(mod) != null) {
			for(Setting s : Moonlight.instance.settingsManager.getSettingsByMod(mod)){
				if(s.isCombo()){
					this.components.add(new ModeButton(s, this, mod, opY));
					opY += 18;
				}
				if(s.isSlider()){
					this.components.add(new Slider(s, this, opY));
					opY += 18;
				}
				if(s.isCheck()){
					this.components.add(new Checkbox(s, this, opY));
					opY += 18;
				}
			}
		}
		this.components.add(new Keybind(this, opY));
		this.components.add(new VisibleButton(this, mod, opY));
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
		int opY = offset + 18;
		for(Component comp : this.components) {
			comp.setOff(opY);
			opY += 18;
		}
	}
	
	@Override
	public void renderComponent() {
		Gui.drawRect(frame.getX(), this.frame.getY() + this.offset, frame.getX() + frame.getWidth(), this.frame.getY() + 18 + this.offset, ColorUtils.getBackgroundColor());
		
		Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(this.module.getName(), (frame.getX() + 2), (frame.getY() + offset + 2) + 4, module.isToggled() ? ColorUtils.getClientColor() : -1);
		
		if(this.components.size() > 2) {
			Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(this.open ? "-" : "+", (frame.getX() + frame.getWidth() - 10), (frame.getY() + offset + 2) + 4, -1, true);
		}
		
		if(this.open) {
			if(!this.components.isEmpty()) {
				for(Component comp : this.components) {
					comp.renderComponent();
				}
			}
		}
	}
	
	@Override
	public int getHeight() {
		if(this.open) {
			return (18 * (this.components.size() + 1));
		}
		return 18;
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.isHovered = isMouseOnButton(mouseX, mouseY);
		if(!this.components.isEmpty()) {
			for(Component comp : this.components) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0) {
			this.module.toggle();
		}
		if(isMouseOnButton(mouseX, mouseY) && button == 1) {
			this.open = !this.open;
			this.frame.refresh();
		}
		for(Component comp : this.components) {
			comp.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		for(Component comp : this.components) {
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int key) {
		for(Component comp : this.components) {
			comp.keyTyped(typedChar, key);
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > frame.getX() && x < frame.getX() + frame.getWidth() && y > this.frame.getY() + this.offset && y < this.frame.getY() + 18 + this.offset) {
			return true;
		}
		return false;
	}
}
