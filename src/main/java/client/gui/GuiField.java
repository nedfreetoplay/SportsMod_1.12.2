package net.sports.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.opengl.GL11;

public class GuiField extends Gui
{
  private final FontRenderer fontRenderer;
  private final int xPos;
  public int yPos;
  private final int width;
  private final int height;
  private String text = "";
  private int maxStringLength = 32;

  private int cursorCounter;

  private boolean enableBackgroundDrawing = true;

  private boolean canLoseFocus = true;

  private boolean isFocused = false;

  private boolean isEnabled = true;

  private int lineScrollOffset = 0;
  private int cursorPosition = 0;

  private int selectionEnd = 0;
  private int enabledColor = 14737632;
  private int disabledColor = 7368816;

  private boolean visible = true;

  public GuiField(FontRenderer par1FontRenderer, int par2, int par3, int par4, int par5) {
    this.fontRenderer = par1FontRenderer;
    this.xPos = par2;
    this.yPos = par3;
    this.width = par4;
    this.height = par5;
  }

  public void updateCursorCounter() {
    this.cursorCounter++;
  }

  public void setText(String par1Str) {
    if (par1Str.length() > this.maxStringLength) {
      
      this.text = par1Str.substring(0, this.maxStringLength);
    }
    else {
      
      this.text = par1Str;
    } 
    
    setCursorPositionEnd();
  }

  public String getText() {
    return this.text;
  }

  public String getSelectedtext() {
    int i = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
    int j = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
    return this.text.substring(i, j);
  }

  public void writeText(String par1Str) {
    int l;
    String s1 = "";
    String s2 = ChatAllowedCharacters.filerAllowedCharacters(par1Str);
    int i = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
    int j = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
    int k = this.maxStringLength - this.text.length() - i - this.selectionEnd;
    boolean flag = false;
    
    if (this.text.length() > 0)
    {
      s1 = s1 + this.text.substring(0, i);
    }

    
    if (k < s2.length()) {
      
      s1 = s1 + s2.substring(0, k);
      l = k;
    }
    else {
      
      s1 = s1 + s2;
      l = s2.length();
    } 
    
    if (this.text.length() > 0 && j < this.text.length())
    {
      s1 = s1 + this.text.substring(j);
    }
    
    this.text = s1;
    moveCursorBy(i - this.selectionEnd + l);
  }

  public void deleteWords(int par1) {
    if (this.text.length() != 0)
    {
      if (this.selectionEnd != this.cursorPosition) {
        
        writeText("");
      }
      else {
        
        deleteFromCursor(getNthWordFromCursor(par1) - this.cursorPosition);
      } 
    }
  }

  public void deleteFromCursor(int par1) {
    if (this.text.length() != 0)
    {
      if (this.selectionEnd != this.cursorPosition) {
        
        writeText("");
      }
      else {
        
        boolean flag = (par1 < 0);
        int j = flag ? (this.cursorPosition + par1) : this.cursorPosition;
        int k = flag ? this.cursorPosition : (this.cursorPosition + par1);
        String s = "";
        
        if (j >= 0)
        {
          s = this.text.substring(0, j);
        }
        
        if (k < this.text.length())
        {
          s = s + this.text.substring(k);
        }
        
        this.text = s;
        
        if (flag)
        {
          moveCursorBy(par1);
        }
      } 
    }
  }

  public int getNthWordFromCursor(int par1) {
    return getNthWordFromPos(par1, getCursorPosition());
  }

  public int getNthWordFromPos(int par1, int par2) {
    return func_73798_a(par1, getCursorPosition(), true);
  }

  public int func_73798_a(int par1, int par2, boolean par3) {
    int k = par2;
    boolean flag1 = (par1 < 0);
    int l = Math.abs(par1);
    
    for (int i1 = 0; i1 < l; i1++) {
      
      if (flag1) {
        
        while (par3 && k > 0 && this.text.charAt(k - 1) == ' ')
        {
          k--;
        }
        
        while (k > 0 && this.text.charAt(k - 1) != ' ')
        {
          k--;
        }
      } 

      
      int j1 = this.text.length();
      k = this.text.indexOf(' ', k);
      
      if (k == -1) {
        
        k = j1;
      }
      else {
        
        while (par3 && k < j1 && this.text.charAt(k) == ' ')
        {
          k++;
        }
      } 
    } 

    return k;
  }

  public void moveCursorBy(int par1) {
    setCursorPosition(this.selectionEnd + par1);
  }

  public void setCursorPosition(int par1) {
    this.cursorPosition = par1;
    int j = this.text.length();
    
    if (this.cursorPosition < 0)
    {
      this.cursorPosition = 0;
    }
    
    if (this.cursorPosition > j)
    {
      this.cursorPosition = j;
    }
    
    setSelectionPos(this.cursorPosition);
  }

  public void setCursorPositionZero() {
    setCursorPosition(0);
  }

  public void setCursorPositionEnd() {
    setCursorPosition(this.text.length());
  }

  public boolean textboxKeyTyped(char par1, int par2) {
    if (this.isEnabled && this.isFocused) {
      
      switch (par1) {
        
        case '\001':
          setCursorPositionEnd();
          setSelectionPos(0);
          return true;
        case '\003':
          GuiScreen.setClipboardString(getSelectedtext());
          return true;
        case '\026':
          writeText(GuiScreen.getClipboardString());
          return true;
        case '\030':
          GuiScreen.setClipboardString(getSelectedtext());
          writeText("");
          return true;
      } 
      switch (par2) {
        
        case 14:
          if (GuiScreen.isCtrlKeyDown()) {
            
            deleteWords(-1);
          }
          else {
            
            deleteFromCursor(-1);
          } 
          
          return true;
        case 199:
          if (GuiScreen.isShiftKeyDown()) {
            
            setSelectionPos(0);
          }
          else {
            
            setCursorPositionZero();
          } 
          
          return true;
        case 203:
          if (GuiScreen.isShiftKeyDown()) {
            
            if (GuiScreen.isCtrlKeyDown())
            {
              setSelectionPos(getNthWordFromPos(-1, getSelectionEnd()));
            }
            else
            {
              setSelectionPos(getSelectionEnd() - 1);
            }
          
          } else if (GuiScreen.isCtrlKeyDown()) {
            
            setCursorPosition(getNthWordFromCursor(-1));
          }
          else {
            
            moveCursorBy(-1);
          } 
          
          return true;
        case 205:
          if (GuiScreen.isShiftKeyDown()) {
            
            if (GuiScreen.isCtrlKeyDown())
            {
              setSelectionPos(getNthWordFromPos(1, getSelectionEnd()));
            }
            else
            {
              setSelectionPos(getSelectionEnd() + 1);
            }
          
          } else if (GuiScreen.isCtrlKeyDown()) {
            
            setCursorPosition(getNthWordFromCursor(1));
          }
          else {
            
            moveCursorBy(1);
          } 
          
          return true;
        case 207:
          if (GuiScreen.isShiftKeyDown()) {
            
            setSelectionPos(this.text.length());
          }
          else {
            
            setCursorPositionEnd();
          } 
          
          return true;
        case 211:
          if (GuiScreen.isCtrlKeyDown()) {
            
            deleteWords(1);
          }
          else {
            
            deleteFromCursor(1);
          } 
          
          return true;
      } 
      if (ChatAllowedCharacters.isAllowedCharacter(par1)) {
        
        writeText(Character.toString(par1));
        return true;
      } 

      
      return false;
    } 

    return false;
  }

  public void mouseClicked(int par1, int par2, int par3) {
    boolean flag = (par1 >= this.xPos && par1 < this.xPos + this.width && par2 >= this.yPos && par2 < this.yPos + this.height);
    
    if (this.canLoseFocus)
    {
      setFocused((this.isEnabled && flag));
    }
    
    if (this.isFocused && par3 == 0) {
      
      int l = par1 - this.xPos;
      
      if (this.enableBackgroundDrawing)
      {
        l -= 4;
      }
      
      String s = this.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), getWidth());
      setCursorPosition(this.fontRenderer.trimStringToWidth(s, l).length() + this.lineScrollOffset);
    } 
  }

  public void drawTextBox() {
    if (getVisible()) {
      
      if (getEnableBackgroundDrawing()) {
        
        drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, -6250336);
        drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, -16777216);
      } 
      
      int i = this.isEnabled ? this.enabledColor : this.disabledColor;
      int j = this.cursorPosition - this.lineScrollOffset;
      int k = this.selectionEnd - this.lineScrollOffset;
      String s = this.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), getWidth());
      boolean flag = (j >= 0 && j <= s.length());
      boolean flag1 = (this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag);
      int l = this.enableBackgroundDrawing ? (this.xPos + 4) : this.xPos;
      int i1 = this.enableBackgroundDrawing ? (this.yPos + (this.height - 8) / 2) : this.yPos;
      int j1 = l;
      
      if (k > s.length())
      {
        k = s.length();
      }
      
      if (s.length() > 0) {
        
        String s1 = flag ? s.substring(0, j) : s;
        j1 = this.fontRenderer.drawStringWithShadow(s1, l, i1, i);
      } 
      
      boolean flag2 = (this.cursorPosition < this.text.length() || this.text.length() >= getMaxStringLength());
      int k1 = j1;
      
      if (!flag) {
        
        k1 = (j > 0) ? (l + this.width) : l;
      }
      else if (flag2) {
        
        k1 = j1 - 1;
        j1--;
      } 
      
      if (s.length() > 0 && flag && j < s.length())
      {
        this.fontRenderer.drawStringWithShadow(s.substring(j), j1, i1, i);
      }
      
      if (flag1)
      {
        if (flag2) {
          
          Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + this.fontRenderer.FONT_HEIGHT, -3092272);
        }
        else {
          
          this.fontRenderer.drawStringWithShadow("_", k1, i1, i);
        } 
      }
      
      if (k != j) {
        
        int l1 = l + this.fontRenderer.getStringWidth(s.substring(0, k));
        drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRenderer.FONT_HEIGHT);
      } 
    } 
  }

  private void drawCursorVertical(int par1, int par2, int par3, int par4) {
    if (par1 < par3) {
      
      int i1 = par1;
      par1 = par3;
      par3 = i1;
    } 
    
    if (par2 < par4) {
      
      int i1 = par2;
      par2 = par4;
      par4 = i1;
    } 
    
    Tessellator tessellator = Tessellator.instance;
    GL11.glColor4f(0.0F, 0.0F, 255.0F, 255.0F);
    GL11.glDisable(3553);
    GL11.glEnable(3058);
    GL11.glLogicOp(5387);
    tessellator.startDrawingQuads();
    tessellator.addVertex(par1, par4, 0.0D);
    tessellator.addVertex(par3, par4, 0.0D);
    tessellator.addVertex(par3, par2, 0.0D);
    tessellator.addVertex(par1, par2, 0.0D);
    tessellator.draw();
    GL11.glDisable(3058);
    GL11.glEnable(3553);
  }

  public void setMaxStringLength(int par1) {
    this.maxStringLength = par1;
    
    if (this.text.length() > par1)
    {
      this.text = this.text.substring(0, par1);
    }
  }

  public int getMaxStringLength() {
    return this.maxStringLength;
  }

  public int getCursorPosition() {
    return this.cursorPosition;
  }

  public boolean getEnableBackgroundDrawing() {
    return this.enableBackgroundDrawing;
  }

  public void setEnableBackgroundDrawing(boolean par1) {
    this.enableBackgroundDrawing = par1;
  }

  public void setTextColor(int par1) {
    this.enabledColor = par1;
  }

  
  public void func_82266_h(int par1) {
    this.disabledColor = par1;
  }

  public void setFocused(boolean par1) {
    if (par1 && !this.isFocused)
    {
      this.cursorCounter = 0;
    }
    
    this.isFocused = par1;
  }

  public boolean isFocused() {
    return this.isFocused;
  }

  public void func_82265_c(boolean par1) {
    this.isEnabled = par1;
  }

  public int getSelectionEnd() {
    return this.selectionEnd;
  }

  public int getWidth() {
    return getEnableBackgroundDrawing() ? (this.width - 8) : this.width;
  }

  public void setSelectionPos(int par1) {
    int j = this.text.length();
    
    if (par1 > j)
    {
      par1 = j;
    }
    
    if (par1 < 0)
    {
      par1 = 0;
    }
    
    this.selectionEnd = par1;
    
    if (this.fontRenderer != null) {
      
      if (this.lineScrollOffset > j)
      {
        this.lineScrollOffset = j;
      }
      
      int k = getWidth();
      String s = this.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), k);
      int l = s.length() + this.lineScrollOffset;
      
      if (par1 == this.lineScrollOffset)
      {
        this.lineScrollOffset -= this.fontRenderer.trimStringToWidth(this.text, k, true).length();
      }
      
      if (par1 > l) {
        
        this.lineScrollOffset += par1 - l;
      }
      else if (par1 <= this.lineScrollOffset) {
        
        this.lineScrollOffset -= this.lineScrollOffset - par1;
      } 
      
      if (this.lineScrollOffset < 0)
      {
        this.lineScrollOffset = 0;
      }
      
      if (this.lineScrollOffset > j)
      {
        this.lineScrollOffset = j;
      }
    } 
  }

  public void setCanLoseFocus(boolean par1) {
    this.canLoseFocus = par1;
  }

  public boolean getVisible() {
    return this.visible;
  }

  public void setVisible(boolean par1) {
    this.visible = par1;
  }
}
