package nightmare.clickgui.component.components.sub;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.Gui;
import nightmare.clickgui.component.Component;
import nightmare.clickgui.component.components.Button;
import nightmare.fonts.impl.Fonts;
import nightmare.utils.ColorUtils;

public class Keybind extends Component {

	private boolean binding;
	private Button parent;
	private int offset;
	private int x;
	private int y;
	
	public Keybind(Button button, int offset) {
		this.parent = button;
		this.x = button.frame.getX() + button.frame.getWidth();
		this.y = button.frame.getY() + button.offset;
		this.offset = offset;
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
	}
	
	@Override
	public void renderComponent() {
		Gui.drawRect(parent.frame.getX(), parent.frame.getY() + offset, parent.frame.getX() + (parent.frame.getWidth() * 1), parent.frame.getY() + offset + 18, ColorUtils.getBackgroundColor());
		
		Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(binding ? "Press a key..." : ("Key: " + Keyboard.getKeyName(this.parent.module.getKey())), (parent.frame.getX() + 7), (parent.frame.getY() + offset + 1) + 5, -1);
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.y = parent.frame.getY() + offset;
		this.x = parent.frame.getX();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
			this.binding = !this.binding;
		}
		
		if(binding) {
			if(button == 2) {
				this.parent.module.setKey(Keyboard.KEY_NONE);
				this.binding = false;
			}
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int key) {
		if(this.binding) {
			this.parent.module.setKey(key);
			this.binding = false;
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > this.x && x < this.x + 99 && y > this.y && y < this.y + 18) {
			return true;
		}
		return false;
	}
}
