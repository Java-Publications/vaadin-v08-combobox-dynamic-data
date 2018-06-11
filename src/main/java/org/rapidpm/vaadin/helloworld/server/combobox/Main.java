package org.rapidpm.vaadin.helloworld.server.combobox;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.apache.meecrowave.Meecrowave;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.helloworld.server.combobox.v08.MyUIV8;

import javax.servlet.annotation.WebServlet;

public class Main {


  public static class MyUI extends UI implements HasLogger {
    @Override
    protected void init(VaadinRequest request) {

//      setContent(new VerticalLayout(new MyUIV7(), new MyUIV8()));
      setContent(new VerticalLayout(new MyUIV8()));

    }
  }


  @WebServlet("/*")
  @VaadinServletConfiguration(productionMode = false, ui = MyUI.class)
  public static class MyProjectServlet extends VaadinServlet { }


  public static void main(String[] args) {

    new Meecrowave(new Meecrowave.Builder() {
      {
        randomHttpPort();
        setTomcatScanning(true);
        setTomcatAutoSetup(false);
        setHttp2(true);
      }
    })
        .bake()
        .await();

    System.getProperties().list(System.out);
  }
}
