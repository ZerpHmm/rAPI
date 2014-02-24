package rafoudiablol.api.network;

import java.awt.image.BufferedImage;

import rafoudiablol.api.ModCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class ImageLoader
	implements IDownloadImageHandler
{
	/**
	 *	@see AbstractClientPlayer#getDownloadImage(ResourceLocation, String, ResourceLocation, IImageBuffer)
	**/
	
	public static ThreadDownloadImageDataImpl getDownloadImage(IDownloadImageHandler callback, ResourceLocation res, ResourceLocation defaultRes, String url)
    {
        final TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        Object object = texturemanager.getTexture(res);

        if (object == null)
        {
            object = new ThreadDownloadImageDataImpl(url, defaultRes, callback);
            texturemanager.loadTexture(res, (ITextureObject)object);
        }

        return (ThreadDownloadImageDataImpl)object;
    }
    
    /**
     *	get a downloadable texture from an url
     *	return defaultTexture OpenGL ID if the texture is loading or unloadable
    **/

    public static ResourceLocation getDownloadImage(String url, ResourceLocation defaultTexture, IDownloadImageHandler callback)
    {
    	final ResourceLocation res = new ResourceLocation("download/" + url);
        getDownloadImage(callback, res, defaultTexture, url);
        
        return res;
    }
    
    public static ResourceLocation getDownloadImage(String url, ResourceLocation defaultTexture)
    {
    	return getDownloadImage(url, defaultTexture, new ImageLoader());
    }
    
    public static boolean removeDownloadImage(String url)
    {
    	final ResourceLocation res = new ResourceLocation("download/" + url);
    	final ThreadDownloadImageDataImpl thread = getDownloadImage(new ImageLoader(), res, null, url);
    	final int glID = thread.getGlTextureId();
    	
    	if(glID < 0)
    	{
    		return false;
    	}
    	else
    	{
    		thread.deleteGlTexture();
    		return true;
    	}
    }

	@Override
	public BufferedImage parseBuffer(BufferedImage img)
	{
		return img;
	}

	@Override
	public void textureWrong(Exception exception)
	{
		ModCore.instance.getLogger().info(exception);
	}
}
