package org.rapidpm.vaadin.helloworld.server.combobox;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.DataProviderListener;
import com.vaadin.data.provider.Query;
import com.vaadin.shared.Registration;
import org.rapidpm.dependencies.core.logger.HasLogger;

import java.util.stream.Stream;

public class DynamicDataProvider implements DataProvider, HasLogger {

  @Override
  public boolean isInMemory() {
    return false;
  }

  @Override
  public int size(Query query) {
    logger().warning("size : " + query);
    return 0;
  }

  @Override
  public Stream fetch(Query query) {
    logger().warning("fetch : " + query);
    return Stream.empty();
  }

  @Override
  public void refreshItem(Object item) {
    logger().warning("refreshItem : " + item);
  }

  @Override
  public void refreshAll() {
    logger().warning("refreshItem : ALL");
  }

  @Override
  public Registration addDataProviderListener(DataProviderListener listener) {
    logger().warning("addDataProviderListener : " + listener);
    return () -> logger().warning("addDataProviderListener : Registration : remove");
  }
}
