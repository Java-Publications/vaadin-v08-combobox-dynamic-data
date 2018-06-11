package org.rapidpm.vaadin.helloworld.server.combobox.v08;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import org.rapidpm.dependencies.core.logger.HasLogger;

import java.util.Optional;
import java.util.stream.IntStream;

public class MyUIV8 extends Composite implements HasLogger {

  public MyUIV8() {

    //create the components you want to use
    // and set the main component with setContent(..)
    final Layout layout = new VerticalLayout();

    CallbackDataProvider<String, String> dataProvider = DataProvider
        .fromFilteringCallbacks(query -> {

                                  Optional<String> filter      = query.getFilter();
                                  int              queryLimit  = query.getLimit();
                                  int              queryOffset = query.getOffset();

                                  logger().warning("fetch " + filter + " - offset " + queryOffset + " - limit " + queryLimit);


                                  int startFrom = Integer.parseInt(filter.orElse("0")) + queryOffset;

                                  return IntStream.range(startFrom, startFrom + queryLimit).mapToObj(i -> String.format("%07d", i));
                                },
                                (CallbackDataProvider.CountCallback<String, String>) query -> {
                                  logger().warning("count " + query);
                                  return query.getLimit();
                                }
        );

    ComboBox<String> comboBox = new ComboBox<>("unlimited elements");
    comboBox.setDataProvider(dataProvider);

    comboBox.setPageLength(5);
    comboBox.setReadOnly(false);

    layout.addComponent(comboBox);
    setCompositionRoot(layout);
  }


}
