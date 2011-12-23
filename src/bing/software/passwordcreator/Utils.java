package bing.software.passwordcreator;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class Utils {
	private final static String TAG = "passwordcreator";
	
	/**
	 * Reference: http://m2tec.be/blog/2010/02/03/java-md5-hex-0093
	*/
	public static String getMD5(String md5) throws UnsupportedEncodingException {
		   try {
		        MessageDigest md = MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(md5.getBytes("UTF-8"));
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
//		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100));
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
		       }
		        return sb.toString();
		    } catch (NoSuchAlgorithmException e) {
		    	Log.e(TAG, e.toString());
		    } catch (UnsupportedEncodingException e) {
		    	Log.e(TAG, e.toString());
		    }
		    return null;
		}
	
	public static String getDecimal(String strHex) {
		byte[] tmp = strHex.getBytes();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < tmp.length; i++){
				sb.append((char)(tmp[i] | 0x100)%10);
		}
		return sb.toString();
	}
}
