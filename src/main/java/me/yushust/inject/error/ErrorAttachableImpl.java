package me.yushust.inject.error;

import me.yushust.inject.util.Validate;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * An implementation of {@link ErrorAttachable}
 * that implements {@link ErrorAttachable#attach} methods but
 * not {@link ErrorAttachable#reportAttachedErrors()}. The
 * error reports are delegated to the sub-class that extends
 * to this class.
 *
 * <p>Use the method {@link ErrorAttachableImpl#getErrorMessages()}
 * to get an immutable copy of all the attached messages.</p>
 */
public class ErrorAttachableImpl implements ErrorAttachable {

  // We don't need access by index to the
  // collection of error messages, so we
  // can use a LinkedList instead of an
  // ArrayList compound by a resizeable array
  private final List<String> errorMessages =
      new LinkedList<>();

  /** Adds all the {@code errors} to the list of error messages */
  public void attach(Throwable... errors) {
    Validate.notNull(errors, "errors");
    for (Throwable error : errors) {
      String stackTrace = Errors.getStackTrace(error);
      errorMessages.add(stackTrace);
    }
  }

  /** Adds all the {@code messages} to the list of error messages */
  public void attach(String... messages) {
    Validate.notNull(messages, "errorMessages");
    Collections.addAll(errorMessages, messages);
  }

  /** Attaches all the error messages of the specified {@code attachable}
   * into this ErrorAttachable */
  public void attachAll(ErrorAttachable attachable) {
    errorMessages.addAll(attachable.getErrorMessages());
  }

  /**
   * @return True if the errors has been attached
   * to this object.
   */
  public boolean hasErrors() {
    return !errorMessages.isEmpty();
  }

  /**
   * Gets an immutable copy of all the attached
   * error messages.
   *
   * @return The attached error messages
   */
  public List<String> getErrorMessages() {
    return Collections.unmodifiableList(errorMessages);
  }

  /** Formats the error messages in this error-attachable */
  public String formatMessages() {
    return Errors.formatErrorMessages(errorMessages);
  }

  /** Per default the errors cannot be reported */
  public void reportAttachedErrors() {
    throw new UnsupportedOperationException("The attached errors cannot be reported here!");
  }
}