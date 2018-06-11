package org.rapidpm.vaadin.helloworld.server.combobox.v07;

import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;
import org.rapidpm.dependencies.core.logger.HasLogger;

public class MyUIV7 extends Composite implements HasLogger {


  public static final int PAGE_COUNT = 10;


  private static Service service = new Service();

  public MyUIV7() {

    setCompositionRoot(new Label("not implemented"));
  }

}
