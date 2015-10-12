package ServerCode;
/**
*	Defines error numbers used throught the system
*	error numbers are all negative, starting from -1
**/
public class RHErrors {
	/* Error number definitions */
	public static final int RHE_GENERAL = -1;
	public static final int RHE_FNF = -2;
	public static final int RHE_BADCOM = -3;
	public static final int RHE_IOE = -4;
	public static final int RHE_NOARG = -5;
	public static final int RHE_NODATA = -6;
	public static final int RHE_BADAUTH = -7;
	public static final int RHE_SUBPROC = -8;
	public static final int RHE_NULL = -9;
	public static final int RHE_NOPERM = -10;
	public static final int RHE_BADARG = -11;
	public static final int RHE_LOCKED = -12;
	public static final int RHE_OP_LOCKED = -13;

	/**
	* Get a description of the error from an error number
	* @param errorNum The error number
	* @return String The description of the error
	*/
	public static synchronized String getErrorDescription(int errorNum) {
		if (errorNum >= 0)
			return "No error";

		String desc = "";
		switch (errorNum) {
		case RHE_GENERAL:
			desc = "General error";
			break;
		case RHE_FNF:
			desc = "File not found";
			break;
		case RHE_BADCOM:
			desc = "Bad command";
			break;
		case RHE_NOARG:
			desc = "Not enough arguments";
			break;
		case RHE_NODATA:
			desc = "Null string received, socket probably closed";
			break;
		case RHE_BADAUTH:
			desc = "Credential verification unsuccessful";
			break;
		case RHE_SUBPROC:
			desc = "Child process exited with non-zero return value";
			break;
		case RHE_NULL:
			desc = "Unexpected null value";
			break;
		case RHE_NOPERM:
			desc = "Operation not permitted";
			break;
		case RHE_BADARG:
			desc = "Wrong arguments";
			break;
		case RHE_LOCKED:
			desc = "File locked by Doctor";
			break;
		case RHE_OP_LOCKED:
			desc = "File locked by Operator";
			break;
		default:
			desc = "Error";
		}
		desc += " (" + Integer.toString(errorNum) + ")";
		return desc;
	}

}
