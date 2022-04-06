package nightmare.mixin.mixins.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiScreen;
import nightmare.event.impl.EventLoadWorld;

@Mixin(GuiDownloadTerrain.class)
public class MixinGuiDownloadTerrain extends GuiScreen{

	@Overwrite
    public void initGui()
    {
        this.buttonList.clear();
        EventLoadWorld event = new EventLoadWorld();
        event.call();
    }
}
