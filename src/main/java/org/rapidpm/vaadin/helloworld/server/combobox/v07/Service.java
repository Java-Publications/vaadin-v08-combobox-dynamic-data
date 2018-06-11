package org.rapidpm.vaadin.helloworld.server.combobox.v07;

import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.model.Result;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Service implements HasLogger {

  public List<String> findValues(String filter, int queryOffset, int queryLimit) {
    if (filter == null) filter = "0";
    if (filter.isEmpty()) filter = "0";

    int startFrom = Integer.parseInt(Result.ofNullable(filter).getOrElse(() -> "0")) + queryOffset;
    return IntStream
        .range(startFrom, startFrom + queryLimit)
        .mapToObj(i -> String.format("%07d", i))
        .collect(toList());
  }

  public int countValues(String filter) {
    logger().warning("countValues.." + filter);
    return 100;
  }
}
