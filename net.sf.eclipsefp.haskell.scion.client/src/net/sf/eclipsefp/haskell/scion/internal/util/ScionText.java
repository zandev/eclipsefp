package net.sf.eclipsefp.haskell.scion.internal.util;

import org.eclipse.osgi.util.NLS;

/**
 * Provides access to the internationalized UI texts.
 * 
 * @author Thomas ten Cate
 */
public class ScionText extends NLS {
  public static String        scionJSONParseException_message;
  public static String        scionServerCouldNotStart_message;
  public static String        scionServerConnectError_message;
  public static String        scionServerConnectionError_message;
  public static String        scionServerOutputReadError_message;
  public static String        scionServerNotRunning_message;
  public static String        scionServerLastWords_message;
  public static String        scionServerRestarted_message;

  public static String        scionFailedCommand_message;
  public static String        scionFailedResponse_message;
  public static String        scionServerGotEOF_message;
  public static String        scionServerInvalidResponsePrefix_message;

  public static String        scionVersionMismatch_warning;

  public static String        cabalFileMissing;

  public static String        commandIdMismatch_warning;
  public static String        commandError_message;
  public static String        commandErrorMissing_message;
  public static String        commandProcessingFailed_message;
  public static String        commandUnexpectedResult_message;
  public static String        errorReadingId_warning;
  public static String        commandVersionMismatch_warning;
  public static String        errorReadingVersion_warning;

  public static String        error_deleteMarkers;
  public static String        error_applyMarkers;
  public static String        error_refreshLocal;

  public static String        warning_typecheck_arbitrary_failed;
  public static String        warning_file_component;
  
  public static String        noproject;

  public static String        build_job_name;
  public static String        process_result_job;
  public static String        process_result_job_arg;
  public static String        background_typecheck_job;
  
  private static final String BUNDLE_NAME = ScionText.class.getPackage().getName() + ".sciontext"; //$NON-NLS-1$

  static {
    NLS.initializeMessages(BUNDLE_NAME, ScionText.class);
  }
}
