package com.glassteapot.sample

import java.lang.reflect.Field

//If time spent and need to throw away some code, they can reach here..
class Scrap {
  def getField[A](clazz: Class[A], fieldName: String): Field = {
    try {
      return clazz.getDeclaredField(fieldName);
    } catch {
      case e: NoSuchFieldException => {
        val superClass = clazz.getSuperclass()
        if (superClass == null) {
          throw e;
        } else {
          return getField(superClass, fieldName);
        }
      }
    }
  }

}