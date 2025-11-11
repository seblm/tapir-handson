package org.apache.pekko.http.scaladsl.testkit

import munit.FunSuite
import org.apache.pekko.http.scaladsl.server.ExceptionHandler

trait MUnitRouteTest extends TestFrameworkInterface with RouteTest:
  this: FunSuite =>

  override def afterAll(): Unit =
    cleanUp()

  override def failTest(msg: String): Nothing =
    fail(msg)

  override def testExceptionHandler: ExceptionHandler =
    ExceptionHandler:
      case e => throw e
