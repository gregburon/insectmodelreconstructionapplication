

////////////////////////////////////////////////////////////////////////
//
//  InsectModeler Program
//
//  Created by Greg Buron
//  Spring 2003
//  Western Washington University
//
//
//  ImageRecord.java
//  
//  The <code>ImageRecord</code> class is responsible for containing
//  the actual 2D image data for each "view" of the insect.  Each
//  record essentially consists of a snapshot of the insect, the angle
//  that snapshot was taken from, and the accessor methods which help
//  determine where insect data starts/ends, where the leg points for
//  each segment of each leg are located in that image, and so on. 
//  This implementation uses ImageJ classes to help do the image 
//  necessary to report the locations of insect data.
//
////////////////////////////////////////////////////////////////////////

package myprojects.insectmodeler;

import java.awt.image.ColorModel;

import ij.gui.ImageCanvas;
import ij.ImagePlus;

import ij.process.ImageProcessor;
import ij.process.ShortProcessor;
import ij.process.TypeConverter;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;


public class ImageRecord
{
	// CONSTRUCTOR
	//
	public ImageRecord( )
	{
		
	}	
	
	public void setPath( String path )
	{
		this.path = path;
	}
	
	public String getPath( )
	{
		return path;
	}
	
	public void setAngle( float angle )
	// Set the rotational angle from which this image was taken.  The
	// rotational angle is a value in degrees.
	{
		this.angle = angle;
	}
	
	public float getAngle( )
	// Return the rotational angle that this image represents.  The 
	// rotational angle is a value in degrees.
	{
		return angle;
	}
	
	public ImageProcessor getImageProcessor( )
	// Return the image processor.
	{
		return imageProcessor;
	}
	
	public ColorModel getColorModel( )
	// Return the color model.
	{
		return colorModel;
	}
	
	public int getBodyDataStartColumn( )
	// Find the first column in which the insect data starts in the image.
	{
		int height = imageProcessor.getHeight( );
		int width  = imageProcessor.getWidth( );
						
		for( int x=0; x<width; ++x )
		{
			for( int y=0; y<height; ++y )
			{
				int pixelValue = imageProcessor.getPixel( x, y );
				
				float red   = (colorModel.getRed( pixelValue ))/255.0f;
				float green = (colorModel.getGreen( pixelValue ))/255.0f;
				float blue  = (colorModel.getBlue( pixelValue ))/255.0f;
				
				if( red == 0.0f && green == 1.0f && blue == 0.0f )
				{
					// Found the first green pixel.  The data 
					// starts on this vertical scan line.	
					return x;
				}// end if	
	
			}// end for
					
		}// end for	
		
		return -1;	
	}	
		
	public int getBodyDataEndColumn( )
	// Find the last column in which insect data appears in the image.
	{
		int height = imageProcessor.getHeight( );
		int width  = imageProcessor.getWidth( );
				
		for( int x=width-1; x>=0; --x )
		{
			for( int y=0; y<height; ++y )
			{
				int pixelValue = imageProcessor.getPixel( x, y );
				
				float red   = (colorModel.getRed( pixelValue ))/255.0f;
				float green = (colorModel.getGreen( pixelValue ))/255.0f;
				float blue  = (colorModel.getBlue( pixelValue ))/255.0f;
				
				if( red == 0.0f && green == 1.0f && blue == 0.0f )
				{
					// Found the first green pixel.  The 
					// data starts on this vertical scan line.	
					return x;
				}// end if	
			}// end for
		}// end for	
			
		return -1;
	}	
	
	public int getBodyDataStartRow( )
	// Find the first row in which insect data starts in the image.
	{
		int height = imageProcessor.getHeight( );
		int width  = imageProcessor.getWidth( );
				
		for( int y=0; y<height; ++y )
		{
			for( int x=0; x<width; ++x )
			{
				int pixelValue = imageProcessor.getPixel( x, y );
				
				float red   = (colorModel.getRed( pixelValue ))/255.0f;
				float green = (colorModel.getGreen( pixelValue ))/255.0f;
				float blue  = (colorModel.getBlue( pixelValue ))/255.0f;
				
				if( red == 0.0f && green == 1.0f && blue == 0.0f )
				{
					// Found the first green pixel.  The data 
					// starts on this horizontal scan line.	
					return y;
				}// end if	
			}// end for
		}// end for
		
		return -1;
	}
		
	public int getBodyDataEndRow( )
	// Find the last row in which insect data appears in the image.
	{
		int height = imageProcessor.getHeight( );
		int width  = imageProcessor.getWidth( );
		
		for( int y=height-1; y>=0; --y )
		{
			for( int x=0; x<width; ++x )
			{
				int pixelValue = imageProcessor.getPixel( x, y );
				
				float red   = (colorModel.getRed( pixelValue ))/255.0f;
				float green = (colorModel.getGreen( pixelValue ))/255.0f;
				float blue  = (colorModel.getBlue( pixelValue ))/255.0f;
				
				if( red == 0.0f && green == 1.0f && blue == 0.0f )
				{
					// Found the first green pixel.  The data 
					// starts on this vertical scan line.	
					return y;
				}// end if	
			}// end for
		}// end for
				
		return -1;
	}	
	
	public Vector2f getLegDataPoint( Color3f color )
	// The getLegDataPoint( ) method finds the first occurrence of a pixel in 
	// the image processor that is a close match to the color parameter to this
	// method.  The image is searched and if a close match is found, the method
	// returns the x,y pixel coordinates of the matching pixel.  If no match is
	// found, then this method returns a pixel location of (-1, -1).
	{
		Vector2f location = new Vector2f( -1, -1 );

		int height = imageProcessor.getHeight( );
		int width  = imageProcessor.getWidth( );
		
		// Search the pixels for the first one that closely matches the color parameter.
		for( int y=0; y<height; ++y )
		{
			for( int x=0; x<width; ++x )
			{
				int pixelValue = imageProcessor.getPixel( x, y );
				
				float red   = ((float)colorModel.getRed( pixelValue ))/255.0f;
				float green = ((float)colorModel.getGreen( pixelValue ))/255.0f;
				float blue  = ((float)colorModel.getBlue( pixelValue ))/255.0f;
				
				if( red   <= color.x + COLOR_ERROR  &&  red   >= color.x - COLOR_ERROR && 
				    green <= color.y + COLOR_ERROR  &&  green >= color.y - COLOR_ERROR && 
				    blue  <= color.z + COLOR_ERROR  &&  blue  >= color.z - COLOR_ERROR )
				{
					// Found a match, break and return this value.
					location.x = x;
					location.y = y;
					
					return location;
				}// end if	
			}// end for
		}// end for				
		
		return location;
	}
	
	public Vector4f getColumnData( int imageColumn )
	// Determine the vertical length of the number of pixels in this column
	// that represent insect data.  This method calculates the entire length
	// of the vertical scanline that represents insect data, along with the 
	// center of the data relative to the center of the image.  The third and
	// fourth parameters are the locations where the data starts and ends.
	{
		Vector4f data = new Vector4f( );
		
		int height     = imageProcessor.getHeight( );
		int length     = -1;
		int center     = -1;
		int dataStartY = -1;
		int dataEndY   = -1;
		
		boolean dataStarted = false;

		for( int y=0; y<height; ++y )
		{
			int pixelValue = imageProcessor.getPixel( imageColumn, y );
			
			float red   = (colorModel.getRed( pixelValue ))/255.0f;
			float green = (colorModel.getGreen( pixelValue ))/255.0f;
			float blue  = (colorModel.getBlue( pixelValue ))/255.0f;
			
			if( red == 0.0f && green == 1.0f && blue == 0.0f && dataStarted == false )
			{
				dataStarted = true;
				dataStartY  = y;
			}// end if
			
			if( red == 0.0f && green == 0.0f && blue == 0.0f && dataStarted == true )
			{
				dataStarted = false;
				dataEndY    = y - 1;
				break;
			}// end if					
			
		}// end for
		
		if( dataStarted == true )
		{
			// data went all the way to the bottom of the image.
			dataEndY = height;
		}// end if	

		length = dataEndY - dataStartY;
		center = ( dataStartY + (dataEndY - dataStartY)/2 ) - height/2;
		
		
		data.x = (float)length;
		data.y = (float)center;
		data.z = (float)dataStartY;
		data.w = (float)dataEndY;
		
		return data;
	}
	
	public int getImageHeight( )
	{
		if( imageProcessor != null )
			return imageProcessor.getHeight( );
			
		return -1;
	}
	
	public int getImageWidth( )
	{
		if( imageProcessor != null )
			return imageProcessor.getWidth( );
			
		return -1;
	}
	
	public void setImagePlus( ImagePlus imagePlus )
	{
		this.imagePlus = imagePlus;
	}
	
	public void createImageProcessor( ImagePlus imagePlus )
	{
		this.imagePlus = imagePlus;
		
		if( imagePlus == null )
		{
			System.err.println( "Create image processor failed. Image plus is null." );
			return;
		}
		
	    // Setup the image processor to import the pixel data.
	    imageProcessor   = imagePlus.getProcessor( );
	    TypeConverter tC = new TypeConverter( imageProcessor, false );
	    imageProcessor   = tC.convertToRGB();    
	    colorModel       = imageProcessor.getColorModel();		
	}
	
	// ATTRIBUTES
	//
	private String path;
	private float  angle;
	
	private ImagePlus      imagePlus;
	private ImageProcessor imageProcessor;
	private ColorModel     colorModel;
	
	
	// STATICS
	//
	private static final float COLOR_ERROR = 0.005f;	
	
}