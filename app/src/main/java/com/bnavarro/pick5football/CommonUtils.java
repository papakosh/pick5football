package com.bnavarro.pick5football;

/**
 * Shared utility methods
 * 
 * @author brian navarro
 *
 */
public class CommonUtils {

	/** Determines if a string value is populated. A value is considered populated when
	 * the string is not null and the length is greater than zero.
	 * 
	 * @param value <code>String</code> to be evaluated.
	 * @return
	 */
	public static boolean hasText (String value){
		return value != null && value.trim().length() > 0;
	}
	
	/** Join together individual string values into a single string
	 * 
	 * @param values An array of <code>String</code>s to be joined together
	 * @return <code>String</code>
	 */
//	public static String concatenate (String... values){
//		StringBuffer joinedString = new StringBuffer("");
//		for (int i = 0; i<values.length; i++){
//			joinedString.append(values[i]);
//		}
//		return joinedString.toString();
//	}

    public static String concat (String... values){
        StringBuffer joinedString = new StringBuffer("");
        for (int i = 0; i<values.length; i++){
            joinedString.append(values[i]);
        }
        return joinedString.toString();
    }

	public static String replace (String original, String oldChar, String newChar){
		if (original == null)
			return "";
			
		return original.replace(oldChar, newChar);
	}
	
	/** Determines if a <code>Matchup</code> array is empty
	 * 
	 * @param array of <code>Matchup</code> elements
	 * @return <code>Boolean</code> true if null or length is zero, else false
	 */
	public static  boolean isArrayEmpty (Match[] array){
		if (array == null)
			return true;
		else if (array.length == 0)
			return true;
		return false;
	}

	public static void validateNotNull(Object object, String msg){
		if (object == null)
            if (msg == null || msg.isEmpty())
               throw new IllegalArgumentException(Object.class.getName() + " cannot be null");
            else
                throw new IllegalArgumentException(msg);
	}

	public static boolean isStringNotEmpty (String sVal){
		return sVal != null && sVal.trim().length() > 0;
	}


}
