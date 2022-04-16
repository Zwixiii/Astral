package nightmare.clickgui;

import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import nightmare.Nightmare;
import nightmare.clickgui.component.Component;
import nightmare.clickgui.component.Frame;
import nightmare.fonts.impl.Fonts;
import nightmare.gui.window.Window;
import nightmare.module.Category;
import nightmare.utils.ColorUtils;
import nightmare.utils.MouseUtils;
import nightmare.utils.ScreenUtils;
import nightmare.utils.render.BlurUtils;

public class ClickGUI extends GuiScreen {

	public static ArrayList<Frame> frames;
	
	public ClickGUI() {
		ClickGUI.frames = new ArrayList<Frame>();
		int frameX = 20;
		for(Category category : Category.values()) {
			Frame frame = new Frame(category);
			frame.setX(frameX);
			frame.setY(25);
			frames.add(frame);
			frameX += frame.getWidth() + 20;
		}
	}
	
	@Override
	public void initGui() {	
		if(Nightmare.instance.fileManager != null) {
			Nightmare.instance.fileManager.getPositionManager().save();
		}
		
    	for(Window window : Nightmare.instance.windowManager.getWindows()) {
    		window.setDragging(false);
    	}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		for(Frame frame : frames) {
			frame.renderFrame(this.fontRendererObj);
			frame.updatePosition(mouseX, mouseY);
			for(Component comp : frame.getComponents()) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
		
		for(Window window : Nightmare.instance.windowManager.getWindows()) {
			if(Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), window.getName()).getValBoolean()) {
				Nightmare.instance.windowManager.getWindowByName(window.getName()).onRender();
			}
		}
		
    	for(Window window : Nightmare.instance.windowManager.getWindows()) {
    		
    		if(!window.isShowWindow()) {
    	        Gui.drawRect(window.getX(), window.getY() - 14, window.getWidth(), window.getY(), ColorUtils.getClientColor());
    	        
    			Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(window.getName(), window.getX() + 4, window.getY() - 10, -1, false);
    		}
    		
    		if(window.isDrag() && window.isDragging() && (Nightmare.instance.moduleManager.getModuleByName("HUD").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), window.getName()).getValBoolean())) {
    			window.setX(mouseX + window.getDraggingX());
    			window.setY(mouseY + window.getDraggingY());
    			window.setWidth(mouseX + window.getDraggingWidth());
    			window.setHeight(mouseY + window.getDraggingHeight());
    		}
    	}
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
    
	@Override
    public void updateScreen() {
    	super.updateScreen();
        if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead)
        {
            mc.thePlayer.closeScreen();
        }
    }
    
	@Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		for(Frame frame : frames) {
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
				frame.setDrag(true);
				frame.dragX = mouseX - frame.getX();
				frame.dragY = mouseY - frame.getY();
			}
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
				frame.setOpen(!frame.isOpen());
			}
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseClicked(mouseX, mouseY, mouseButton);
					}
				}
			}
		}
		
        for(Window window : Nightmare.instance.windowManager.getWindows()) {
        	if(MouseUtils.isInside(mouseX, mouseY, window.getX(), window.getY() - 14, window.getWidth(), window.getY()) && (Nightmare.instance.moduleManager.getModuleByName("HUD").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), window.getName()).getValBoolean()) && mouseButton == 0) {
        		window.setDragging(true);
        		window.setDraggingX(window.getX() - mouseX);
        		window.setDraggingY(window.getY() - mouseY);
        		window.setDraggingWidth(window.getWidth() - mouseX);
        		window.setDraggingHeight(window.getHeight() - mouseY);
        	}
        }
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		for(Frame frame : frames) {
			if(frame.isOpen() && keyCode != 1) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.keyTyped(typedChar, keyCode);
					}
				}
			}
		}
		if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
	}

	
	@Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {    
		for(Frame frame : frames) {
			frame.setDrag(false);
		}
		for(Frame frame : frames) {
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseReleased(mouseX, mouseY, state);
					}
				}
			}
		}
		
    	for(Window window : Nightmare.instance.windowManager.getWindows()) {
    		window.setDragging(false);
    	}
	}
	
	@Override
	public void onGuiClosed() {
    	for(Window window : Nightmare.instance.windowManager.getWindows()) {
    		window.setDragging(false);
    	}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}