package rafoudiablol.api.network;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ThreadDownloadImageDataImpl extends SimpleTexture
{
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicInteger threadDownloadCounter = new AtomicInteger(0);
    private final String imageUrl;
    private final IDownloadImageHandler eventHandler;
    private BufferedImage bufferedImage;
    private Thread imageThread;
    private boolean textureUploaded;
    private static final String __OBFID = "CL_00001049";

    public ThreadDownloadImageDataImpl(String par1Str, ResourceLocation defaultRes, IDownloadImageHandler imagebuffer)
    {
        super(defaultRes);
        this.imageUrl = par1Str;
        this.eventHandler = imagebuffer;
    }

    private void checkTextureUploaded()
    {
        if (!this.textureUploaded)
        {
            if (this.bufferedImage != null)
            {
                if (this.textureLocation != null)
                {
                    this.deleteGlTexture();
                }

                TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
                this.textureUploaded = true;
            }
        }
    }

    public int getGlTextureId()
    {
        this.checkTextureUploaded();
        return super.getGlTextureId();
    }

    public void setBufferedImage(BufferedImage p_147641_1_)
    {
        this.bufferedImage = p_147641_1_;
    }

    public void loadTexture(IResourceManager par1ResourceManager) throws IOException
    {
        if (this.bufferedImage == null && this.textureLocation != null)
        {
            super.loadTexture(par1ResourceManager);
        }

        if (this.imageThread == null)
        {
            this.imageThread = new Thread("[rAPI] Texture Downloader #" + threadDownloadCounter.incrementAndGet())
            {
                private static final String __OBFID = "CL_00001050";
                public void run()
                {
                    HttpURLConnection httpurlconnection = null;

                    try
                    {
                        httpurlconnection = (HttpURLConnection)(new URL(ThreadDownloadImageDataImpl.this.imageUrl)).openConnection(Minecraft.getMinecraft().getProxy());
                        httpurlconnection.setDoInput(true);
                        httpurlconnection.setDoOutput(false);
                        httpurlconnection.connect();

                        if (httpurlconnection.getResponseCode() / 100 != 2)
                        {
                            return;
                        }

                        BufferedImage bufferedimage = ImageIO.read(httpurlconnection.getInputStream());

                        if(eventHandler != null)
                        {
                            bufferedimage = eventHandler.parseBuffer(bufferedimage);
                        }

                        ThreadDownloadImageDataImpl.this.setBufferedImage(bufferedimage);
                    }
                    catch (Exception exception)
                    {
                    	ThreadDownloadImageDataImpl.logger.error("Couldn\'t download http texture", exception);
                    	
                    	if(eventHandler != null)
                    	{
                    		eventHandler.textureWrong(exception);
                    	}
                    }
                    finally
                    {
                        if (httpurlconnection != null)
                        {
                            httpurlconnection.disconnect();
                        }
                    }
                }
            };
            this.imageThread.setDaemon(true);
            this.imageThread.setName("Skin downloader: " + this.imageUrl);
            this.imageThread.start();
        }
    }

    public boolean isTextureUploaded()
    {
        this.checkTextureUploaded();
        return this.textureUploaded;
    }
}
