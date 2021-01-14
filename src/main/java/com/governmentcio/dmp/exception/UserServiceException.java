/**
 * 
 */
package com.governmentcio.dmp.exception;

/**
 * @author <a href=mailto:support@governmentcio.com>support
 *
 */
public class UserServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public UserServiceException() {
	}

	/**
	 * @param message
	 */
	public UserServiceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UserServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public UserServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
