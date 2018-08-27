package test.emax;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;


/**
 * Base64编码工具类
 * 
 * @author liufeng 
 * @date 2012-10-11
 */
public class Base64 {
	private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

	public static String encode(byte[] data) {
		int start = 0;
		int len = data.length;
		StringBuffer buf = new StringBuffer(data.length * 3 / 2);

		int end = len - 3;
		int i = start;
		int n = 0;

		while (i <= end) {
			int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 0x0ff) << 8) | (((int) data[i + 2]) & 0x0ff);

			buf.append(legalChars[(d >> 18) & 63]);
			buf.append(legalChars[(d >> 12) & 63]);
			buf.append(legalChars[(d >> 6) & 63]);
			buf.append(legalChars[d & 63]);

			i += 3;

			if (n++ >= 14) {
				n = 0;
				buf.append(" ");
			}
		}

		if (i == start + len - 2) {
			int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 255) << 8);

			buf.append(legalChars[(d >> 18) & 63]);
			buf.append(legalChars[(d >> 12) & 63]);
			buf.append(legalChars[(d >> 6) & 63]);
			buf.append("=");
		} else if (i == start + len - 1) {
			int d = (((int) data[i]) & 0x0ff) << 16;

			buf.append(legalChars[(d >> 18) & 63]);
			buf.append(legalChars[(d >> 12) & 63]);
			buf.append("==");
		}

		return buf.toString();
	}

	private static int decode(char c) {
		if (c >= 'A' && c <= 'Z')
			return ((int) c) - 65;
		else if (c >= 'a' && c <= 'z')
			return ((int) c) - 97 + 26;
		else if (c >= '0' && c <= '9')
			return ((int) c) - 48 + 26 + 26;
		else
			switch (c) {
			case '+':
				return 62;
			case '/':
				return 63;
			case '=':
				return 0;
			default:
				throw new RuntimeException("unexpected code: " + c);
			}
	}

	/**
	 * Decodes the given Base64 encoded String to a new byte array. The byte array holding the decoded data is returned.
	 */

	public static byte[] decode(String s) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			decode(s, bos);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		byte[] decodedBytes = bos.toByteArray();
		try {
			bos.close();
			bos = null;
		} catch (IOException ex) {
			System.err.println("Error while decoding BASE64: " + ex.toString());
		}
		return decodedBytes;
	}

	private static void decode(String s, OutputStream os) throws IOException {
		int i = 0;

		int len = s.length();

		while (true) {
			while (i < len && s.charAt(i) <= ' ')
				i++;

			if (i == len)
				break;

			int tri = (decode(s.charAt(i)) << 18) + (decode(s.charAt(i + 1)) << 12) + (decode(s.charAt(i + 2)) << 6) + (decode(s.charAt(i + 3)));

			os.write((tri >> 16) & 255);
			if (s.charAt(i + 2) == '=')
				break;
			os.write((tri >> 8) & 255);
			if (s.charAt(i + 3) == '=')
				break;
			os.write(tri & 255);

			i += 4;
		}
	}
	public static byte[] GenerateImage(String imgStr) {// 对字节数组字符串进行Base64解码并生成图片  
		if (imgStr == null) // 图像数据为空  
			return null;  
		try {  
			// Base64解码  
			byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(imgStr);  
			for (int i = 0; i < bytes.length; ++i) {  
				if (bytes[i] < 0) {// 调整异常数据  
					bytes[i] += 256;  
				}  
			}  
 			return bytes;
		} catch (Exception e) {  
			return null;  
		}  
	}
	
	public static byte[] compressPic(byte[] bytes,float w,float h) {   
        try {   
            Image img = ImageIO.read(new ByteInputStream(bytes, bytes.length));   
            int iw=img.getWidth(null);
            int ih=img.getHeight(null);   
            if(iw==-1) return null;
            //缩放比例
            float tx=iw>w?w/iw:0;
            float ty=ih>h?h/ih:0;
            //以哪边长度大为准
            float temp=iw>ih?tx:ty;
            float newWidth=w,newHeight=h;
            if(temp>0){
            	newWidth=(int) (iw*temp);    
            	newHeight=(int) (ih*temp);        
            }
           BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);             
           Graphics2D g2d = tag.createGraphics();
           g2d.setColor(Color.white);
           g2d.fillRect(0, 0, (int)newWidth, (int)newHeight);
           g2d.drawImage(img.getScaledInstance((int)newWidth, (int)newHeight, Image.SCALE_SMOOTH), 0, 0, null);  
           ByteArrayOutputStream bos=new ByteArrayOutputStream();
           // JPEGImageEncoder可适用于其他图片类型的转换   
           //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);   
           //encoder.encode(tag); 
           ImageIO.write(tag, "jpg", bos);
           return bos.toByteArray();
        } catch (IOException ex) {   
            ex.printStackTrace();   
        }   
        return null;
	}
	public static byte[] cutImage(byte[] bg, int i, int j) throws IOException {
		Iterator<ImageReader> iter=ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader) iter.next();
		ImageReadParam param = reader.getDefaultReadParam();
		reader.setInput(ImageIO.createImageInputStream(new ByteInputStream(bg, bg.length)), true);
		int w=reader.getWidth(0);
		int h=reader.getHeight(0);
		int x=(w-i)/2;
		int y=(h-j)/2;
		Rectangle rect = new Rectangle(x<0?0:x, y<0?0:y, i, j);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		ImageIO.write(bi, "jpg", bos);
		return bos.toByteArray();
	}
}