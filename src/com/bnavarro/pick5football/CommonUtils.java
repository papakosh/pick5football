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
	public static String concatenate (String... values){
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
	
	public static  boolean isArrayEmpty (Matchup[] array){
		if (array == null)
			return true;
		else if (array.length == 0)
			return true;
		return false;
	}
}
