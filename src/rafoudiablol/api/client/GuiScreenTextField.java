package rafoudiablol.api.client;

import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.DefaultTreeCellEditor.DefaultTextField;

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
public class GuiScreenTextField extends GuiScreen
{
	protected static final LinkedList<? extends String> EMPTY_HISTORY = new LinkedList<String>();
	
	// 1 == dernier élément entré, historyReference.size() == premier élément rentré
	private int currentHistoryPos = 0;
	private String currentText = "";
	
	protected GuiTextField inputGui = null;
	private String defaultText = "";
    
    protected int posX;
    protected int posY;
    protected int sizeX;
    protected int sizeY;

    public void setPosition(int posX, int posY)
    {
    	this.posX = posX;
    	this.posY = posY;
    }
    
    public void setOffset(int sizeX, int sizeY)
    {
    	this.sizeX = sizeX;
    	this.sizeY = sizeY;
    }
    
    public GuiScreenTextField(String defaultText) {
    	this.currentText = this.defaultText = defaultText;
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
    	return EMPTY_HISTORY;
    }

    public int getMaxFieldLength() {
    	return 32;
    }

    protected String getPrefixMsg() {
    	return "";
    }
    
    protected final void setText(String str)
    {
		inputGui.setText(str);
		
    	if(currentHistoryPos == 0)
    	{
    		currentText = inputGui.getText();
    	}
    }
	
    @Override
    public void initGui()
    {
    	this.posX = 4;
		this.posY = this.height - 12;
	    this.sizeX = this.width - 4;
	    this.sizeY = 12;
    
        Keyboard.enableRepeatEvents(true);
        inputGui = new GuiTextField(this.fontRenderer, posX, posY, sizeX, sizeY);
        inputGui.setEnableBackgroundDrawing(false);
        inputGui.setFocused(true);
        inputGui.setMaxStringLength(getMaxFieldLength());
        inputGui.setText(this.defaultText);
        inputGui.setCanLoseFocus(false);
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
	protected void keyTyped(char c, int i)
	{
    	final List<? extends String> history = this.getHistory();
    	
		if(i == Keyboard.KEY_ESCAPE)
		{
	        this.mc.displayGuiScreen((GuiScreen)null);
	    }
		else if(i == Keyboard.KEY_UP || i == Keyboard.KEY_DOWN)
		{
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
    protected void mouseClicked(int i, int j, int k)
    {
        this.inputGui.mouseClicked(i, j, k);
        super.mouseClicked(i, j, k);
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

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
