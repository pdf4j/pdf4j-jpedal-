/*
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.idrsolutions.com
 * Help section for developers at http://www.idrsolutions.com/support/
 *
 * (C) Copyright 1997-2016 IDRsolutions and Contributors.
 *
 * This file is part of JPedal/JPDF2HTML5
 *
     This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


 *
 * ---------------
 * Commands.java
 * ---------------
 */
package org.jpedal.examples.viewer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import org.jpedal.PdfDecoderInt;
import org.jpedal.display.GUIThumbnailPanel;
import org.jpedal.examples.viewer.commands.ViewStack;
import org.jpedal.examples.viewer.gui.generic.GUISearchList;
import org.jpedal.examples.viewer.gui.generic.GUISearchWindow;
import org.jpedal.examples.viewer.utils.PrinterInt;
import org.jpedal.examples.viewer.utils.PropertiesFile;
import org.jpedal.exception.PdfException;
import org.jpedal.external.JPedalActionHandler;
import org.jpedal.external.Options;
import org.jpedal.gui.GUIFactory;
import org.jpedal.utils.LogWriter;
import org.jpedal.utils.Messages;

/**
 * This class contains code to execute the actual commands.
 * http://www.idrsolutions.com/access-pdf-viewer-features-from-your-code/
 */
public class Commands {
    
    /** used to store the IE views so we can go back to previous views and store changes */
    protected final ViewStack viewStack = new ViewStack();
    
    public boolean extractingAsImage;
     
    //<link><a name="commandInts" />
    //0-50 handled in executeMenuBarCommands(final int ID, Object[] args)
    public static final int ABOUT = 1;
    public static final int BITMAP = 2;
    public static final int IMAGES = 3;
    public static final int TEXT = 4;
    public static final int SAVE = 5;
    public static final int PRINT = 6;
    public static final int EXIT = 7;
    public static final int AUTOSCROLL = 8;
    public static final int DOCINFO = 9;
    public static final int OPENFILE = 10;
    public static final int BOOKMARK = 11;
    public static final int FIND = 12;
    public static final int SNAPSHOT = 13;
    public static final int OPENURL = 14;
    public static final int VISITWEBSITE = 15;
    public static final int PREVIOUSDOCUMENT = 16;
    public static final int NEXTDOCUMENT = 17;
    public static final int PREVIOUSRESULT = 18;
    public static final int NEXTRESULT = 19;
    public static final int TIP = 20;
    public static final int CASCADE = 21;
    public static final int TILE = 22;
    //public static final int UPDATE = 23;
    public static final int PREFERENCES = 24;
    public static final int COPY = 25;
    public static final int SELECTALL = 26;
    public static final int DESELECTALL = 27;
    public static final int UPDATEGUILAYOUT = 28;
    public static final int MOUSEMODE = 29;
    public static final int PANMODE = 30;
    public static final int TEXTSELECT = 31;
    public static final int SEPARATECOVER = 32;
    public static final int EXTRACTTEXT = 33;
    public static final int EXTRACTASIMAGE = 34;
    public static final int ROTATELEFT = 35;
    public static final int ROTATERIGHT = 36;
    //0-49 handled in executeMenuBarCommands(final int ID, Object[] args)
    
    //50-249 handled in executeDisplayCommands(final int ID, Object[] args)
    public static final int FIRSTPAGE = 50;
    public static final int FBACKPAGE = 51;
    public static final int BACKPAGE = 52;
    public static final int FORWARDPAGE = 53;
    public static final int FFORWARDPAGE = 54;
    public static final int LASTPAGE = 55;
    public static final int GOTO = 56;
    
    public static final int SINGLE = 57;
    public static final int CONTINUOUS = 58;
    public static final int CONTINUOUS_FACING = 59;
    public static final int FACING = 60;
    public static final int PAGEFLOW = 61;
    
    public static final int FULLSCREEN=62;
    public static final int ZOOMIN = 63;
    public static final int ZOOMOUT = 64;
    //50-249 handled in executeDisplayCommands(final int ID, Object[] args)
    
    //250-299 handled in executeComboCommands(final int ID, Object[] args)
    //combo boxes start at 250
    public static final int ROTATION = 251;
    public static final int SCALING = 252;
    //250-299 handled in executeComboCommands(final int ID, Object[] args)
    
    //300-499 not handled here
    public static final int CURRENTMENU = 300;
    public static final int CROP = 301;
    public static final int PAGETOOLSMENU = 303;
    public static final int CONTENTMENU = 304;
    public static final int ONEPERPAGE = 305;
    public static final int PDFMENU = 306;
    public static final int EXPORTMENU = 307;
    public static final int WINDOWMENU = 308;
    public static final int PAGELAYOUTMENU = 309;
    public static final int EDITMENU = 310;
    public static final int GOTOMENU = 311;
    public static final int VIEWMENU = 312;
    public static final int RESAVEASFORM = 315;
    public static final int OPENMENU = 316;
    public static final int FILEMENU = 317;
    
    public static final int HELPFORUM = 318;
    
    //300-499 not handled here
    
    //<link><a name="constants" />
    // external/itext menu options start at 500 - add your own CONSTANT here
    // and refer to action using name at ALL times
    //500-599 (existing values) handled in executeExternalCommands(final int ID, Object[] args)
    public static final int SAVEFORM = 500;
    public static final int PDF = 501;
    public static final int ROTATE=502;
    public static final int DELETE=503;
    public static final int ADD=504;
    public static final int SECURITY=505;
    public static final int ADDHEADERFOOTER=506;
    public static final int STAMPTEXT=507;
    public static final int STAMPIMAGE=508;
    public static final int SETCROP=509;
    public static final int NUP = 510;
    public static final int HANDOUTS = 511;
    public static final int SIGN = 512;
    //public static final int NEWFUNCTION = 513;
    //500-599 (existing values) handled in executeExternalCommands(final int ID, Object[] args)
    
    //600-699 handled in executePageCommands(final int ID, Object[] args)
    public static final int HIGHLIGHT = 600;
    public static final int SCROLL = 601;
    //600-699 handled in executePageCommands(final int ID, Object[] args)
    
    //700-899 handled in executeViewerFunctionalityCommands(final int ID, Object[] args)
    // commands for the forward and back tracking of views, ie when a page changes
    public static final int ADDVIEW = 700,FORWARD = 701,BACK = 702,PAGECOUNT=703,CURRENTPAGE=704;
    
    public static final int GETOUTLINEPANEL =705;
    public static final int GETTHUMBNAILPANEL =706;
    public static final int GETPAGECOUNTER =707;
    public static final int PAGEGROUPING =708;
    
    public static final int SETPAGECOLOR=709;
    public static final int SETUNDRAWNPAGECOLOR=710;
    public static final int REPLACETEXTCOLOR=711;
    public static final int SETTEXTCOLOR=712;
    public static final int CHANGELINEART=713;
    public static final int SETDISPLAYBACKGROUND=714;
    public static final int SETREPLACEMENTCOLORTHRESHOLD=715;
    public static final int GETPDFNAME=716; //Used for JavaFX Netbeans PDF Viewer Plugin.
    public static final int SETENHANCEFRACTIONALLINES=717; 
    //700-899 handled in executeViewerFunctionalityCommands(final int ID, Object[] args)
    
    //997-999 handled in executeDemoVersionCommands(final int ID, Object[] args)
    public static final int RSS = 997;
    public static final int HELP = 998;
    public static final int BUY = 999;
    //997-999 handled in executeDemoVersionCommands(final int ID, Object[] args)
    
    
    //>=1000 handled in executeDebugCommands(final int ID, Object[] args)
    public static final int TOGGLE = 1000;
    public static final int RESET = 1002;
    public static final int ACCELERATIONON = 1003;
    public static final int ACCELERATIONOFF = 1004;
    public static final int SHOWFORMNAMES = 1006;
    public static final int DELETEPROPERTIESONEXIT = 1007;
    public static final int ALWAYSSHOWMOUSE = 1008;
    //>=1000 handled in executeDebugCommands(final int ID, Object[] args)
    
    //status values returned by command
    public static final Integer FIRST_DOCUMENT_SEARCH_RESULT_NOW_SHOWN = 1;
    public static final Integer SEARCH_RETURNED_TO_START = 2;
    public static final Integer SEARCH_NOT_FOUND = 3;
        
    protected final Values commonValues;
    protected final GUIFactory currentGUI;
    protected PdfDecoderInt decode_pdf;
    
    protected final GUIThumbnailPanel thumbnails;
    
    protected final PropertiesFile properties;
    
    protected final GUISearchWindow searchFrame;
    
    protected final PrinterInt currentPrinter;
        
    /**
     * tracks mouse operation mode currently selected
     */
    protected final MouseMode mouseMode=new MouseMode();
   
    public Commands(final Values commonValues, final GUIFactory currentGUI, final PdfDecoderInt decode_pdf, final GUIThumbnailPanel thumbnails,
            final PropertiesFile properties , final GUISearchWindow searchFrame, final PrinterInt currentPrinter) {
        this.commonValues=commonValues;
        this.currentGUI=currentGUI;
        this.decode_pdf=decode_pdf;
        
        this.thumbnails=thumbnails;
        this.properties=properties;
        this.currentPrinter=currentPrinter;
        
        this.searchFrame=searchFrame;
        
    }
      
    //<link><a name="commands" />
    /**
     * main routine which executes code for current command
     *
     * Values can also be passed in so it can be called from your own code
     *
     * some commands return a status Object otherwise null
     * @param ID is of type Int
     * @param args Program arguments passed into the Viewer.
     * @return the status object
     */
    public Object executeCommand(final int ID, Object[] args) {
                
        //teat null and Object[]{null} as both null
        if(args!=null && args.length==1 && args[0]==null) {
            args = null;
        }
                
        Object status =null;
        
        currentGUI.setExecutingCommand(true);

        final Map jpedalActionHandlers = (Map) decode_pdf.getExternalHandler(Options.JPedalActionHandlers);
        
        if(jpedalActionHandlers != null) {
            final JPedalActionHandler jpedalAction = (JPedalActionHandler) jpedalActionHandlers.get(ID);
            if (jpedalAction != null) {
                jpedalAction.actionPerformed(currentGUI, this);
                return null;
            }
        }
        
        if(!SharedViewer.isFX()){
         
            status=executeSwingCommand(ID, args,status);
           
        }
        
        //Mark as executed is not running in thread
        if(!currentGUI.isCommandInThread()) {
            currentGUI.setExecutingCommand(false);
        }
        
        return status;
        
    }

    public void handleTransferedFile(final String file) throws PdfException {

        while (Values.getOpeningTransferedFile() || Values.isProcessing()) {
            try {
                Thread.sleep(250);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
        Values.setOpeningTransferedFile(true);

        final boolean isURL = file.startsWith("http:") || file.startsWith("file:");
        try {

            if (!isURL) {
                commonValues.setFileIsURL(false);
                commonValues.setFileSize(new File(file).length() >> 10);
            } else {
                commonValues.setFileIsURL(true);
            }

            commonValues.setSelectedFile(file);

            currentGUI.setViewerTitle(null);
        } catch (final Exception e) {
            LogWriter.writeLog("Exception " + e + " getting paths");
        }

        // check file exists
        final File testFile = new File(commonValues.getSelectedFile());
        if (!isURL && !testFile.exists()) {
            currentGUI.showMessageDialog(Messages.getMessage("PdfViewerFile.text") + commonValues.getSelectedFile() + Messages.getMessage("PdfViewerNotExist"));
        } else if (commonValues.getSelectedFile() != null && !Values.isProcessing()) {
            openTransferedFile();
            if (commonValues.isPDF()) {
                Values.setOpeningTransferedFile(false);
            }
        }
    }

    protected void openTransferedFile() throws PdfException {
        throw new RuntimeException("openTransferedFile in Commands should never be called");
    }

    public void recentDocumentsOption() {

        currentGUI.setRecentDocument();
        
        final String[] recentDocs=properties.getRecentDocuments();
        if(recentDocs == null) {
            return;
        }
        
        for(int i=0;i< PropertiesFile.getNoRecentDocumentsToDisplay();i++){
            
            if(recentDocs[i]==null) {
                recentDocs[i] = "";
            }
            
           try{
                
                final String fileNameToAdd=recentDocs[i];
                
                currentGUI.getRecentDocument().createMenuItems(fileNameToAdd, i, currentGUI, commonValues, decode_pdf);
                
            }catch(final Exception ee){
                ee.printStackTrace();
            }
        }
    }
             
    public void setPdfDecoder(final PdfDecoderInt decode_pdf) {
        this.decode_pdf = decode_pdf;
    }
      
    
    /**
     * Returns the searchList of the last search preformed.
     * If currently searching this method will return the results for last completed search
     * @return SearchList of all results, all data but the actual highlgiht areas
     */
    public GUISearchList getSearchList() {
        return searchFrame.getResults();
    }
    
    public MouseMode getMouseMode() {
        return mouseMode;
    }
    
    public ViewStack getViewStack(){
        return viewStack;
    }
       
    /**
     * examine first few bytes to see if linearized and return true linearized file
     * @param pdfUrl is of type String
     * @return the isLinear
     */
    public static final boolean isPDFLinearized(final String pdfUrl) {
        
        if (pdfUrl.startsWith("jar")) {
            return false;
        }
        
        boolean isLinear=false;
        //read first few bytes
        final URL url;
        final InputStream is;
        
        try {
            url = new URL(pdfUrl);
            is = url.openStream();
            //final String filename = url.getPath().substring(url.getPath().lastIndexOf('/')+1);
            
            // Download buffer
            final byte[] buffer = new byte[128];
            is.read(buffer);
            is.close();
            
            //test if linearized
            
            //scan for Linearized in text
            final int len=buffer.length;
            for(int i=0;i<len;i++ ){
                
                if(buffer[i]=='/' && buffer[i+1]=='L' && buffer[i+2]=='i' && buffer[i+3]=='n' && buffer[i+4]=='e' && buffer[i+5]=='a' && buffer[i+6]=='r'){
                    isLinear=true;
                    i=len;
                }
            }
            
        } catch (final IOException e) {
            LogWriter.writeLog("[PDF] Exception " + e + " scanning URL " + pdfUrl);
        }
        
        return isLinear;
    }

     Object executeSwingCommand(int ID, Object[] args, Object status) {
         throw new UnsupportedOperationException("executeSwingCommand should not be called");
     }
}
