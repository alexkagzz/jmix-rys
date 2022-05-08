package com.kagzz.jmix.rys.order.entity;

/**
 * Entry point for assertions of different data types. Each method in this class is a static factory for the
 * type-specific assertion objects.
 */
public class Assertions extends org.assertj.core.api.Assertions {

  /**
   * Creates a new instance of <code>{@link com.kagzz.jmix.rys.order.entity.OrderAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static com.kagzz.jmix.rys.order.entity.OrderAssert assertThat(com.kagzz.jmix.rys.order.entity.Order actual) {
    return new com.kagzz.jmix.rys.order.entity.OrderAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link com.kagzz.jmix.rys.order.entity.OrderLineAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static com.kagzz.jmix.rys.order.entity.OrderLineAssert assertThat(com.kagzz.jmix.rys.order.entity.OrderLine actual) {
    return new com.kagzz.jmix.rys.order.entity.OrderLineAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link com.kagzz.jmix.rys.product.entity.ProductAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static com.kagzz.jmix.rys.product.entity.ProductAssert assertThat(com.kagzz.jmix.rys.product.entity.Product actual) {
    return new com.kagzz.jmix.rys.product.entity.ProductAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link com.kagzz.jmix.rys.product.entity.ProductCategoryAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static com.kagzz.jmix.rys.product.entity.ProductCategoryAssert assertThat(com.kagzz.jmix.rys.product.entity.ProductCategory actual) {
    return new com.kagzz.jmix.rys.product.entity.ProductCategoryAssert(actual);
  }

  protected Assertions() {
    // empty
  }
}