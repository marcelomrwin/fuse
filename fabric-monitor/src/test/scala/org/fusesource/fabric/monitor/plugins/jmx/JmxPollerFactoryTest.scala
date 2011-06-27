package org.fusesource.fabric.monitor.plugins
package jmx

import org.fusesource.fabric.api.monitor.DataSourceDTO
import org.fusesource.fabric.api.monitor.DataSourceDTO

class JmxPollerFactoryTest extends FunSuiteSupport {

  test("monitors some JMX stats") {
    val dto = new DataSourceDTO()
    dto.id = "jmx.threadCount"
    dto.name = "JVM Thread Count"
    dto.description = "JVM Thread Count"
    dto.kind = "gauge"
    dto.poll = new MBeanAttributePollDTO("java.lang:type=Threading", "ThreadCount")

    val pollerFactory = new JmxPollerFactory()

    assert(pollerFactory.accepts(dto), "factory: " + pollerFactory + "should accept: " + dto)

    val poller = pollerFactory.create(dto)
    val value = poller.poll

    poller.close
    // TODO have a close on the PollerFactory?

    println("value: " + value)
    assert(value != Double.NaN && value > 0, "Invalid thread count: " + value)
  }
}