package me.yushust.inject.scope;

import javax.inject.Provider;

/**
 * Collection of built-in scopes
 */
public final class Scopes {

  public static final Scope SINGLETON = SingletonScope.INSTANCE;
  public static final Scope NONE = EmptyScope.INSTANCE;

  private Scopes() {
    throw new UnsupportedOperationException("This class couldn't be instantiated!");
  }

  /**
   * Represents an scope that always returns the
   * same unscoped provider. The implementation is
   * an enum to let the JVM make sure only one
   * instance exists.
   */
  private enum EmptyScope implements Scope {
    INSTANCE;

    public <T> Provider<T> scope(Provider<T> unscoped) {
      return unscoped;
    }
  }

}
