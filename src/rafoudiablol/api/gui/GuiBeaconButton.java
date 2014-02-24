package rafoudiablol.api.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


/**
 *	@see GuiBeacon#Button
**/

@SideOnly(Side.CLIENT)
public class GuiBeaconButton extends GuiButton
{
    public static final ResourceLocation beaconGuiTextures = new ResourceLocation("textures/gui/container/beacon.png");
    
	public ResourceLocation resourceLocation;
	public int uScale;
	public int vScale;
	private boolean field_146142_r;
	private static final String __OBFID = "CL_00000743";
	
	protected GuiBeaconButton(int par1, int par2, int par3, ResourceLocation par4ResourceLocation, int par5, int par6)
	{
	    super(par1, par2, par3, 22, 22, "");
	    this.resourceLocation = par4ResourceLocation;
	    this.uScale = par5;
	    this.vScale = par6;
	}
	
	/**
	 * Draws this button to the screen.
	 */
	public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
	{
	    if (this.visible)
	    {
	        p_146112_1_.getTextureManager().bindTexture(beaconGuiTextures);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
	        short short1 = 219;
	        int k = 0;
	
	        if (!this.enabled)
	        {
	            k += this.width * 2;
	        }
	        else if (this.field_146142_r)
	        {
	            k += this.width * 1;
	        }
	        else if (this.field_146123_n)
	        {
	            k += this.width * 3;
	        }
	
	        this.drawTexturedModalRect(this.xPosition, this.yPosition, k, short1, this.width, this.height);
	
	        if (!beaconGuiTextures.equals(this.resourceLocation))
	        {
	            p_146112_1_.getTextureManager().bindTexture(this.resourceLocation);
	        }
	
	        this.drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.uScale, this.vScale, 18, 18);
	    }
	}
	
	public boolean func_146141_c()
	{
	    return this.field_146142_r;
	}
	
	public void func_146140_b(boolean p_146140_1_)
	{
	    this.field_146142_r = p_146140_1_;
	}
}
