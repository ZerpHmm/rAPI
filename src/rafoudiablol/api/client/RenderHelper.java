package rafoudiablol.api.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import rafoudiablol.util.Size3d;

@SideOnly(Side.CLIENT)
public final class RenderHelper
{
	public static void startQuads() { Tessellator.instance.startDrawingQuads();}
	public static void endQuads() 	{ Tessellator.instance.draw();}

	/**
	 *	Dessine aussi les vertex de la texture
	 *	Toujours autour de l'axe Y
	 *
	 *	@see Gui#drawRect(int, int, int, int, int)
	**/
	
	public static void drawRectWithUV(
		double origX, double origY, double origZ,
		double sizeX, double sizeY, double sizeZ)
	{
        Tessellator t = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        startQuads();
        	t.addVertexWithUV(origX, 	  		origY, 	  		origZ, 	 		0, 1); // l'axe Y est inversé dans OpenGL
        	t.addVertexWithUV(origX + sizeX, 	origY, 	  		origZ + sizeZ, 	1, 1);
        	t.addVertexWithUV(origX + sizeX, 	origY + sizeY, 	origZ + sizeZ, 	1, 0);
        	t.addVertexWithUV(origX, 	  		origY + sizeY, 	origZ, 	 		0, 0);
        endQuads();
        
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
	}
	
	/**
	 * @param box
	 * 		min = origine du dessin
	 * 		max = taille du dessin
	 *
	**/
	
    public static void renderRectWithUV(AxisAlignedBB box)
    {
    	Tessellator tessellator = Tessellator.instance;
    	tessellator.startDrawingQuads();

        	tessellator.setNormal(1.0F, 0.0F, 0.0F);
        	
	        tessellator.addVertexWithUV(box.minX, box.maxY, box.minZ, 0, 0);
	        tessellator.addVertexWithUV(box.maxX, box.maxY, box.minZ, 1, 0);
	        tessellator.addVertexWithUV(box.maxX, box.minY, box.maxZ, 1, 1);
	        tessellator.addVertexWithUV(box.minX, box.minY, box.maxZ, 0, 1);

	        tessellator.setNormal(0.0F, 0.0F, 0.0F);
	        
        tessellator.draw();
    }
 
}
