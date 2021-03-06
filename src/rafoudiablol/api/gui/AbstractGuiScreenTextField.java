package rafoudiablol.api.gui;

import java.util.List;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(Side.CLIENT)
public abstract class AbstractGuiScreenTextField extends GuiScreen
{
	// 1 == dernier élément entré, historyReference.size() == premier élément rentré
	private int currentHistoryPos = 0;
	private String currentText = "";
	
	protected GuiTextField inputGui = null;
	private String defaultText = "";
	
    protected int posX;
    protected int posY;
    protected int sizeX;
    protected int sizeY;
    
    public AbstractGuiScreenTextField(String defaultText) {
    	this.currentText = this.defaultText = defaultText;
    }
    
    public final void setPosition(int posX, int posY)
    {
    	this.posX = posX;
    	this.posY = posY;
    }
    
    public final void setOffset(int sizeX, int sizeY)
    {
    	this.sizeX = sizeX;
    	this.sizeY = sizeY;
    }

    @Override
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        inputGui = new GuiTextField(this.fontRendererObj, posX, posY, sizeX, sizeY);
        inputGui.setEnableBackgroundDrawing(false);
        inputGui.setFocused(true);
        inputGui.setMaxStringLength(getMaxLength());
        inputGui.setText(this.defaultText);
        inputGui.setCanLoseFocus(false);
    }
    
    protected final void setText(String str)
    {
    	if(str.length() > getMaxLength())
    	{
    		str = str.substring(0, getMaxLength());
    	}
    	
		inputGui.setText(str);
		
		if(getHistory() != null)
		{
	    	if(currentHistoryPos == 0)
	    	{
	    		currentText = inputGui.getText();
	    	}
		}
    }
    
    /**
     * 	@return true
     * 		Si l'entrée est valide
     * 	@return false
     * 		Si l'entrée n'est pas valide
    **/
	protected boolean processLine(String s) {
		return true;
	}
	
    protected List<? extends String> getHistory() {
    	return null;
    }

    public int getMaxLength() {
    	return 32;
    }

    protected String getPrefixMsg() {
    	return "";
    }
    
    @Override
	protected void keyTyped(char c, int i)
	{
    	final List<? extends String> history = this.getHistory();
    	
		if(i == Keyboard.KEY_ESCAPE)
		{
	        this.mc.displayGuiScreen((GuiScreen)null);
	    }
		else if(i == Keyboard.KEY_UP || i == Keyboard.KEY_DOWN)
		{
			if(history == null)
				return;
			
			if(i == Keyboard.KEY_UP)
			{
				if(currentHistoryPos < history.size())
				{
					currentHistoryPos++;
				}
	        }
	        else if(i == Keyboard.KEY_DOWN)
	        {
	        	if(currentHistoryPos > 0)
	        	{
	        		currentHistoryPos--;
	        	}
	        }
			
			if(currentHistoryPos == 0)
			{
				inputGui.setText(currentText);
			}
			else
			{
				inputGui.setText(history.get(history.size() - currentHistoryPos));
			}
		}
	    else if(i != Keyboard.KEY_RETURN && i != Keyboard.KEY_NUMPADENTER)
	    {
			if(history == null)
				return;
			
	    	inputGui.textboxKeyTyped(c, i);
	    	
	    	if(currentHistoryPos == 0)
	    	{
	    		currentText = inputGui.getText();
	    	}
	    }
	    else
	    {
	        String s = this.inputGui.getText().trim();
	
	        if(processLine(s))
	        {
	        	this.mc.displayGuiScreen((GuiScreen)null);
	        }
	    }
	}

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
    	this.inputGui.updateCursorCounter();
    }

    @Override
    protected void mouseClicked(int i, int j, int k)
    {
        this.inputGui.mouseClicked(i, j, k);
        super.mouseClicked(i, j, k);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void drawScreen(int i, int j, float k)
    {
        this.drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        
        String text = inputGui.getText().trim();
        inputGui.setText(getPrefixMsg() + text);
        this.inputGui.drawTextBox();
        inputGui.setText(text);
        
        super.drawScreen(i, j, k);
    }
}
