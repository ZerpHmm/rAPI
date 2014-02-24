package rafoudiablol.api.gui;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *	Customized GuiChat
 *	@see GuiChat
**/

@SideOnly(Side.CLIENT)
public class GuiScreenTextField extends AbstractGuiScreenTextField
{
	protected static final LinkedList<? extends String> EMPTY_HISTORY = new LinkedList<String>();

	@Override
    protected List<? extends String> getHistory() {
    	return EMPTY_HISTORY;
    }
    
    public GuiScreenTextField(String defaultText) {
    	super(defaultText);
    }
	
    @Override
    public void initGui()
    {
    	this.posX = 4;
		this.posY = this.height - 12;
	    this.sizeX = this.width - 4;
	    this.sizeY = 12;
	    
	    super.initGui();
    }
}
