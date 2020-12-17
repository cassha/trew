package me.yushust.inject;

import me.yushust.inject.assisted.Assist;
import me.yushust.inject.assisted.Assisted;
import me.yushust.inject.assisted.ValueFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.inject.Named;

public class QualifiedAssistedInjectTest {

  @Test
  public void test() {

    Injector injector = Injector.create(binder ->
        binder.bind(Foo.class).toFactory(FooFactory.class));

    FooFactory factory = injector.getInstance(FooFactory.class);
    Foo foo = factory.create("OcNo", "Miranda", "Mr.");

    Assertions.assertEquals("Mr. OcNo Miranda", foo.identifier);
    Assertions.assertNotNull(foo.bar);
    Assertions.assertNotNull(foo.bar2);
  }

  public static class Bar {
  }

  public static class Foo {

    private final String identifier;
    private final Bar bar2;
    @Inject private Bar bar;

    @Assisted
    public Foo(
        @Assist @Named("name") String name,
        @Assist @Named("lastName") String lastName,
        @Assist String prefix,
        Bar bar2
    ) {
      this.bar2 = bar2;
      this.identifier = prefix + " " + name + " " + lastName;
    }

  }

  public interface FooFactory extends ValueFactory {

    Foo create(@Named("name") String name, @Named("lastName") String lastName, String prefix);

  }

}
