package org.rapidpm.vaadin.helloworld.server;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.apache.meecrowave.Meecrowave;
import org.rapidpm.dependencies.core.logger.HasLogger;

import javax.servlet.annotation.WebServlet;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HelloWorld {

  public static class MyUI extends UI implements HasLogger {


    /**
     * Start editing here to create your
     * POC based on a Vaadin App.
     * To start the app, -> start the main Method.
     * <p>
     * You will see in the log´s the randomly used port.
     *
     * @param request that is created by the first request to init the app
     */
    @Override
    protected void init(VaadinRequest request) {

      //create the components you want to use
      // and set the main component with setContent(..)
      final Layout layout = new VerticalLayout();

      CallbackDataProvider<String, String> dataProvider = DataProvider
          .fromFilteringCallbacks(new CallbackDataProvider.FetchCallback<String, String>() {
                                    @Override
                                    public Stream<String> fetch(Query<String, String> query) {

                                      Optional<String> filter      = query.getFilter();
                                      int              queryLimit  = query.getLimit();
                                      int              queryOffset = query.getOffset();

                                      logger().warning("fetch " + filter + " - offset " + queryOffset + " - limit " + queryLimit);


                                      int startFrom = Integer.parseInt(filter.orElse("0")) + queryOffset;

                                      return IntStream.range(startFrom, startFrom + queryLimit).mapToObj(i -> String.format("%07d", i));
                                    }
                                  },
                                  new CallbackDataProvider.CountCallback<String, String>() {
                                    @Override
                                    public int count(Query<String, String> query) {
                                      logger().warning("count " + query);
                                      return query.getLimit();
                                    }
                                  }
          );

      ComboBox<String> comboBox = new ComboBox<>("unlimited elements");
      comboBox.setDataProvider(dataProvider);

      comboBox.setPageLength(5);
      comboBox.setReadOnly(false);

      layout.addComponent(comboBox);
      setContent(layout);
    }

    @WebServlet("/*")
    @VaadinServletConfiguration(productionMode = false, ui = MyUI.class)
    public static class MyProjectServlet extends VaadinServlet { }


    public static ConcurrentMap<String, String> map;

    public static void main(String[] args) {

//      // start Persistence
//      DB db = DBMaker
//          .fileDB("target/file.db")
//          .closeOnJvmShutdown()
//          .fileDeleteAfterClose()
//          .make();
//
//      map = db
//          .hashMap("database",
//                   Serializer.STRING,
//                   Serializer.STRING
//          )
//          .createOrOpen();
//
//
//      IntStream
//          .range(0, 1_000_000)
//          .mapToObj(i -> String.format("%07d", i))
//          .forEach(e -> map.put(e, e));

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
    }
  }
}
