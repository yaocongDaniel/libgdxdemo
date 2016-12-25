package edu.nju.wsj.libgdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.utils.GdxExtFontCreator;
import com.badlogic.gdx.scenes.scene2d.utils.GdxExtFontCreator.iCreateBmpFontExt;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;

public class FontFactory{
	 private static Object sGlobalLock = new Object();
	 private static FontFactory instance = null;
	 
	 public static String FONT_PATH = "fonts/"; 
	 public static String DEFAULT_FONT_NAME = "font2.ttc"; 
	
    private int defaultShadow = 0;
    private Color defaultShadowColor = new Color(0.5f, 0.5f, 0.5f, 1);
	
    public SpriteBatch batch = new SpriteBatch();
   
    private ObjectMap<String, FreeTypeFontGenerator> generators = new ObjectMap<String, FreeTypeFontGenerator>();
    private ObjectMap<FreeTypeFontGenerator, IntMap<BitmapFont>> fontMap = new ObjectMap<FreeTypeFontGenerator, IntMap<BitmapFont>>();
    private ObjectMap<BitmapFont, FontMessage> fontSizeMap = new ObjectMap<BitmapFont, FontMessage>();//字体-字体信息的映�?
   
    public static FontFactory getInstance() {
   	synchronized (sGlobalLock) {
   		if(instance == null){
   	  		instance = new FontFactory();
   	  	}
   	   return instance;
   	}  	
    }
    
 	static public FontFactory getSingletonWithOutCreate(){
		synchronized (sGlobalLock) {
         return instance;
     }
	}
    
    private FontFactory(){
   	 FreeTypeFontGenerator.setMaxTextureSize(256);
    }
        
    /**
     * 生成字体
     * @param fontName
     * @param fontSize
     * @return
     */
    public BitmapFont createFreeTypeFont(String fontName, int fontSize) {
        return createFreeTypeFont(fontName, fontSize, defaultShadow);
    }
    
    /**
     * 生成字体
     * @param fontName
     * @param fontSize�?范围 0 ~ 1000
     * @param shadow �?范围 -1000 ~ 1000
     * @return
     */
    private BitmapFont createFreeTypeFont(String fontName, int fontSize, int shadow) {
        
        if(shadow > 1000 || shadow < -1000){
            throw new IllegalArgumentException("shadow error");
        }
        if(fontSize > 1000){
            throw new IllegalArgumentException("size error");
        }
        
        String path = FONT_PATH + fontName;
        if(fontName == null || fontName.length() <= 0){
        	path = FONT_PATH + DEFAULT_FONT_NAME;
        }
        
        FreeTypeFontGenerator generator = generators.get(path);
        BitmapFont font = null;
        if (generator == null) {     
            FileHandle file = Gdx.files.internal(path);;
            if(!file.exists()){
            	throw new IllegalArgumentException("font file " + path + " not exist!");
            }
            
            generator = new FreeTypeFontGenerator(file);
            generators.put(path, generator);
             
            font = createFreeTypeFontbySize(fontSize, generator, shadow);

            IntMap<BitmapFont> fonts = new IntMap<BitmapFont>();
            fonts.put(shadow * 1000 + fontSize, font);
            fontMap.put(generator, fonts);
            
            FontMessage tMessage = new FontMessage();
            tMessage.fontShadow = shadow;
            tMessage.fontSize = fontSize;
            fontSizeMap.put(font, tMessage);
        } else {
            IntMap<BitmapFont> fonts = fontMap.get(generator);
            font = fonts.get(shadow * 1000 + fontSize, null);
            if (font == null) {
                font = createFreeTypeFontbySize(fontSize, generator, shadow);
                fonts.put(shadow * 1000 + fontSize, font);
                
                FontMessage tMessage = new FontMessage();
                tMessage.fontShadow = shadow;
                tMessage.fontSize = fontSize;
                fontSizeMap.put(font, tMessage);
            }
        }
        return font;
    }

    private BitmapFont createFreeTypeFontbySize(int size, FreeTypeFontGenerator generator, int shadow) {
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.characters = "";
        parameter.incremental = true;
//        parameter.size = (int)(size * (Gdx.graphics.getDensity())); 
        parameter.size = size; 
        parameter.magFilter = TextureFilter.Linear;
        parameter.minFilter = TextureFilter.Linear;
        if(shadow > 0){
            parameter.shadowColor = defaultShadowColor;
            parameter.shadowOffsetX = shadow;
            parameter.shadowOffsetY = shadow;
        }
        
        FreeTypeBitmapFontData data = new FreeTypeBitmapFontData() {
            public int getWrapIndex (Array<Glyph> glyphs, int start) {
                return SimplifiedChinese.getWrapIndex(glyphs, start);
            }
        };

        data.breakChars = new char[]{'j','方','国'};
        data.xChars = new char[]{'m','j','方','国'};
        data.capChars = new char[]{'M','j','方','国'};
        
        return generator.generateFont(parameter, data);
    }
    
    /**
     * 释放�?��资源
     */
    public void dispose() {
        for (IntMap<BitmapFont> fonts : fontMap.values()) {
            for (BitmapFont font : fonts.values())
                font.dispose();
        }
        fontMap.clear();
        
        for (FreeTypeFontGenerator generator : generators.values()) {
            generator.dispose();
        }
        generators.clear();
        
        batch.dispose();
        fontSizeMap.clear();
        
        synchronized (sGlobalLock) {
      	  instance = null;
     	  } 
    }
    
    static public class SimplifiedChinese {
        public static int getWrapIndex (Array<Glyph> glyphs, int start) {
            for (int i = start; i > 0; i--) {
                int startChar = glyphs.get(i).id;
                if (!SimplifiedChinese.legalAtStart(startChar)) continue;
                int endChar = glyphs.get(i - 1).id;
                if (!SimplifiedChinese.legalAtEnd(endChar)) continue;
                if (startChar < 127 && endChar < 127) continue; // Don't wrap between ASCII chars.
                return i;
            }
            return start;
        }

        static private boolean legalAtStart (int ch) {
            switch (ch) {
            case '!':
            case '%':
            case ')':
            case ',':
            case '.':
            case ':':
            case ';':
            case '>':
            case '?':
            case ']':
            case '}':
            case '¢':
            case '¨':
            case '°':
            case '·':
            case 'ˇ':
            case 'ˉ':
            case '―':
            case '‖':
            case '’':
            case '”':
            case '„':
            case '‟':
            case '†':
            case '‡':
            case '›':
            case '℃':
            case '∶':
            case '、':
            case '。':
            case '〃':
            case '〆':
            case '〈':
            case '《':
            case '「':
            case '『':
            case '〕':
            case '〗':
            case '〞':
            case '﹘':
            case '﹚':
            case '﹜':
            case '！':
            case '＂':
            case '％':
            case '＇':
            case '）':
            case '，':
            case '．':
            case '：':
            case '；':
            case '？':
            case '］':
            case '｀':
            case '｜':
            case '｝':
            case '～':
                return false;
            }
            return true;
        }

        static private boolean legalAtEnd (int ch) {
            switch (ch) {
            case '$':
            case '(':
            case '*':
            case ',':
            case '£':
            case '¥':
            case '·':
            case '‘':
            case '“':
            case '〈':
            case '《':
            case '「':
            case '『':
            case '【':
            case '〔':
            case '〖':
            case '〝':
            case '﹗':
            case '﹙':
            case '﹛':
            case '＄':
            case '（':
            case '．':
            case '［':
            case '｛':
            case '￡':
            case '￥':
                return false;
            }
            return true;
        }
    }    
    
    public class FontMessage{
        public int fontSize;
        public int fontShadow;
    }
}
