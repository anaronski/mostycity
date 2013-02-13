package pirate.mostycity.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.wicket.markup.html.form.upload.FileUpload;

import pirate.mostycity.util.Constants;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageHelper extends FilesUtils implements Serializable, Constants{

	private static final long serialVersionUID = 1L;

	public String getAvatarUrl(String fileName){
		
		try {
			new FileInputStream("webapps/mostycity/images/avatars/"+fileName+".jpg");
			return "images/avatars/"+fileName+".jpg";
		} catch (Exception e) {
			return "images/avatars/default.jpg";
		}
	}
	
	public static String getMessageImageUrl(String fileName){
		
		try {
			new FileInputStream("webapps/mostycity/images/news_images/"+fileName);
			return "images/news_images/"+fileName;
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getFileName(String string, String ex){
		
		Date date = new Date();
		
		String s = DateFormatUtils.format(date, "dd_MM_yyyy_HH_mm_ss").toString()+
			"."+ex ;
		return s;
	}
	
	public static String getHtmlForImage(String fileName){
		
		return "<div id=\"lightBox\"><a rel=\"prettyPhoto[g_"+fileName+"]\" " +
			"href=\"images/news_images/"+fileName+"\" title=\"\">" +
			"<img src=\"images/news_images/"+fileName+"\" /></a>" +
			"</div>";
	}
	
	private BufferedImage resizeImg(File file, int maxWidth, int maxHeight) throws IOException{
		
		Image image = (Image) ImageIO.read(file);
		if(image.getHeight(null)>maxHeight || image.getWidth(null)>maxWidth){
			file.createNewFile();
			if(image.getHeight(null)>image.getWidth(null))
				image = image.getScaledInstance(-1, maxHeight, Image.SCALE_SMOOTH );
			else
				image = image.getScaledInstance(maxWidth, -1, Image.SCALE_SMOOTH );
		}
		
		return encodeImg(image);
	}
	
	private BufferedImage encodeImg(Image image) throws ImageFormatException, IOException{
		
		int thumbWidth = image.getWidth(null);
		int thumbHeight = image.getHeight(null);
		// Draw the scaled image
		BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
		
		// Write the scaled image to the outputstream
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
		int quality = 100; // Use between 1 and 100, with 100 being highest quality
		quality = Math.max(0, Math.min(quality, 100));
		param.setQuality((float) quality / 100.0f, false);
		encoder.setJPEGEncodeParam(param);
		encoder.encode(thumbImage);
		
		return thumbImage;
	}
	
	private File saveImgInJpg(FileUpload upload, String parentPath, String fileName ) throws IOException{
		
		File file = getFileToWrite(parentPath, fileName);
		String extension = StringHelper.getFileExtension(upload.getClientFileName());
		if ("jpg".equals(extension)){ 
			upload.writeTo(file);
		}else{
			InputStream in = new ByteArrayInputStream(upload.getBytes());
			Image image = ImageIO.read(in);
			BufferedImage bImage = encodeImg(image);
			saveBufferedImg(bImage, parentPath, fileName);
		}
		
		return file;
	}
	private File saveBufferedImg(BufferedImage image, String parentPath, String fileName ) throws IOException{
		
		File file = getFileToWrite(parentPath, fileName);
		ImageIO.write(image, "jpg", file);
		
		return file;
	}
	
	
	
	public void saveAvatar(FileUpload upload, String fileName) throws IOException{
		File bigFile = saveImgInJpg(upload, "avatars/", fileName);
		
		if(bigFile!=null)
			saveBufferedImg(resizeImg(bigFile, 120, 120), "avatars/small/", fileName);
	}
	
	public void saveNewsImage(FileUpload upload, String fileName) throws IOException{
		saveFileFromUpload(upload, "news_images/", fileName);
	}
	
	
	
}
