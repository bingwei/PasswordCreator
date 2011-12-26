package bing.software.passwordcreator;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class Utils {
	private final static String TAG = "passwordcreator";
	
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"}; 
    
    private static String byteArrayToHexString(byte[] b) {  
        StringBuffer resultSb = new StringBuffer();  
        for (int i = 0; i < b.length; i++) {  
            resultSb.append(byteToHexString(b[i]));  
        }  
        return resultSb.toString();  
    }  
 
    private static String byteToHexString(byte b) {  
        int n = b;  
        if (n < 0) n = 256 + n;  
        int d1 = n / 16;  
        int d2 = n % 16;  
        return hexDigits[d1] + hexDigits[d2];  
    }  
 
    /**
     * Reference: 
     * 1. http://tianya23.blog.51cto.com/1081650/681428
     * 2. http://zh.wikipedia.org/wiki/MD5
     * 3. 
     * MD5("") 
     * = d41d8cd98f00b204e9800998ecf8427e
     * 
     * MD5("The quick brown fox jumps over the lazy dog")
     * = 9e107d9d372bb6826bd81d3542a419d6
     * 
     * More:
     * Considering the security reason, please refer to 
     * http://www.guokr.com/article/81430/
     * ---------------------------------------------------------------------
     * user: sqybi    password: 12345678
     * Calculate "12345678" with MD5 for the first time and get: 25d55ad283aa400af464c76d713c07ad
     * Set salts： salt1 = @#$%   salt2 = ^&*()
     * salt1+user+salt2+MD5 and get: @#$%sqybi^&*()25d55ad283aa400af464c76d713c07ad
     * Calculate MD5 again and get final String：7edfee82ab9b6f5aa50edbc3a9eb6507
     * ---------------------------------------------------------------------
    */
    public static String getMD5(String origin) {  
        String resultString = null;  
        try {  
            resultString=new String(origin);  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            resultString=byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));  
        }  
        catch (Exception ex) { 
            ex.printStackTrace(); 
        }  
        return resultString;  
    } 
    
    // Need to update this
	public static String getDecimal(String strHex) {
		byte[] tmp = strHex.getBytes();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < tmp.length; i++){
				sb.append((char)(tmp[i] | 0x100)%10);
		}
		return sb.toString();
	}
    
	/**
	 * Reference: http://m2tec.be/blog/2010/02/03/java-md5-hex-0093
	 * Not used again
	*/
	public static String getMD5Deprecated(String md5) throws UnsupportedEncodingException {
		   try {
		        MessageDigest md = MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(md5.getBytes("UTF-8"));
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
//		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100));
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1));
		       }
		        return sb.toString();
		    } catch (NoSuchAlgorithmException e) {
		    	Log.e(TAG, e.toString());
		    } catch (UnsupportedEncodingException e) {
		    	Log.e(TAG, e.toString());
		    }
		    return null;
		}
	

}
