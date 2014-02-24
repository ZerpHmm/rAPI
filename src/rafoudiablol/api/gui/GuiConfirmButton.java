package rafoudiablol.api.gui;

import net.minecraft.client.gui.inventory.GuiBeacon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *	@see GuiBeacon#ConfirmButton
**/

@SideOnly(Side.CLIENT)
public class GuiConfirmButton extends GuiBeaconButton
{
	private boolean isConfirmButton;

    public GuiConfirmButton(int i, int j, int k, boolean flag)
    {
        super(i, j, k, beaconGuiTextures, 90, 220);
        isConfirmButton = flag;
    }

    public GuiConfirmButton(int i, int j, int k)
    {
    	this(i, j, k, true);
    }
    
    public boolean isCancelButton()
    {
    	return !isConfirmButton;
    }
    
    public void toCancelButton()
    {
    	isConfirmButton = false;
    	this.uScale = 112;
    	this.vScale = 220;
    }
    
    public void toConfirmButton()
    {
    	isConfirmButton = true;
    	this.uScale = 90;
    	this.vScale = 220;
    }
    
    public void reverseState()
    {
    	if(isConfirmButton)
    		toCancelButton();
    	else
    		toConfirmButton();
    }
}