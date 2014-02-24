package rafoudiablol.api.network;

import java.awt.image.BufferedImage;

public interface IDownloadImageHandler
{
	BufferedImage parseBuffer(BufferedImage img);
	void textureWrong(Exception exception);
}
