package com.glassteapot.sample

import java.lang.reflect.Field

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